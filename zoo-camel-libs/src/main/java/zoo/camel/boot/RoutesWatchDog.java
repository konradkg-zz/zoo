package zoo.camel.boot;

import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.RoutesDefinition;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class RoutesWatchDog implements InitializingBean, DisposableBean {

	private Log logger = LogFactory.getLog(getClass());

	private Path routesDir;
	private ModelCamelContext camelContext;

	private WatchService watcher;
	private Thread watchDogTask;

	private AtomicBoolean started = new AtomicBoolean(false);
	private final EventsListener eventsListener = new EventsListener();

	public void start() throws IOException {
		if (started.compareAndSet(false, true)) {
			watcher = registerNewWatchService();

			watchDogTask = new Thread(new WatchDogTask(this, watcher), "RoutesWatchDogTask");
			watchDogTask.setDaemon(true);
			watchDogTask.start();
		}
	}

	public void stop() throws IOException {
		if (started.compareAndSet(true, false)) {
			final Thread t = watchDogTask;
			if (t != null) {
				watchDogTask = null;
				t.interrupt();
			}

			final WatchService w = watcher;
			if (w != null) {
				watcher = null;
				w.close();
			}
		}
	}

	public void restart() {
		logger.info("Routes watch dog is restarting...");
		try {
			stop();
		} catch (IOException e) {
			logger.error("Restart: Error occured during stop().", e);
		}
		try {
			start();
		} catch (IOException e) {
			logger.error("Restart: Error occured during start().", e);
		}
	}

	private void init() throws IOException {
		try (DirectoryStream<Path> ds = Files.newDirectoryStream(routesDir, "*.{xml}")) {
			for (Path file : ds) {
				eventsListener.onCreate(file);
			}
		}

		start();
	}

	private WatchService registerNewWatchService() throws IOException {
		while (started.get()) {
			WatchService newWatchService = null;
			try {
				newWatchService = routesDir.getFileSystem().newWatchService();
				routesDir.register(newWatchService, ENTRY_DELETE, ENTRY_MODIFY);
				return newWatchService;
			} catch (IOException e) {
				final String msg = "Unable to create WatchService. Reason: " + e.getMessage();
				if (logger.isDebugEnabled()) {
					logger.error(msg, e);
				} else {
					logger.error(msg);
				}

				if (newWatchService != null) {
					try {
						newWatchService.close();
					} catch (Exception ignore) {
					}
				}

				try {
					TimeUnit.SECONDS.sleep(10);
				} catch (InterruptedException e1) {
					Thread.currentThread().interrupt();
					throw e;
				}
			}
		}
		throw new IllegalStateException("Ups. registerNewWatchService() called while stopping.");
	}

	EventsListener getListener() {
		return eventsListener;
	}

	Path getRoutesDirPath() {
		return routesDir;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		init();
	}

	@Override
	public void destroy() throws Exception {
		stop();
	}

	public void setCamelContext(ModelCamelContext camelContext) {
		this.camelContext = camelContext;
	}

	public void setRoutesDir(String routesDir) {
		this.routesDir = Paths.get(routesDir).toAbsolutePath();
	}

	private static class WatchDogTask implements Runnable {
		private Log logger = LogFactory.getLog(getClass());

		private final WatchService watcher;
		private final RoutesWatchDog controller;

		WatchDogTask(RoutesWatchDog controller, WatchService watcher) {
			this.watcher = watcher;
			this.controller = controller;
		}

		@Override
		public void run() {
			while (true) {
				try {
					final WatchKey watchKey = watcher.take();
					List<WatchEvent<?>> events = watchKey.pollEvents();
					for (WatchEvent<?> event : events) {
						if (event.kind() == OVERFLOW)
							continue;

						@SuppressWarnings("unchecked")
						final Path filename = controller.getRoutesDirPath().resolve(
								((WatchEvent<Path>) event).context());
						if (event.kind() == ENTRY_DELETE) {
							controller.getListener().onDelete(filename);
						}
						if (event.kind() == ENTRY_MODIFY) {
							controller.getListener().onCreate(filename);
						}
					}
					boolean valid = watchKey.reset();
					if (!valid) {
						logger.warn("Directory no longer valid!!!");
						controller.restart();
						break;
					}
				} catch (InterruptedException e) {
					return;
				} catch (Throwable e) {
					logger.error("Unexpected error occured: ", e);
				}
			}
		}
	}

	private class EventsListener {

		private final ConcurrentMap<Path, RoutesDefinition> routesMap = new ConcurrentHashMap<>();

		public void onCreate(Path path) {
			try (InputStream is = tryOpen(path)) {
				if(!isValid(path)) {
					logger.warn("Not supported file or invalid file content: " + path);
					return;
				}
				final RoutesDefinition routes = camelContext.loadRoutesDefinition(is);
				final List<RouteDefinition> routesList = routes.getRoutes();
				camelContext.addRouteDefinitions(routesList);
				routesMap.put(path, routes);

				logger.info("Routes loaded from " + path);
			} catch (Exception e) {
				logger.error(e);
			}
		}

		public void onDelete(Path path) {
			final RoutesDefinition routes = routesMap.remove(path);
			if (routes == null) {
				logger.warn("Routes file: " + path + " not registered. Nothing to remove.");
				return;
			}

			logger.info("Removing routes defined in file: " + path);
			for (RouteDefinition rd : routes.getRoutes()) {
				try {
					removeRoute(rd.getId());
				} catch (Exception e) {
					logger.error(e);
				}
			}
		}

		private void removeRoute(String name) throws Exception {
			camelContext.stopRoute(name, 10, TimeUnit.SECONDS);
			logger.info("Remove route [" + name + "]: " + camelContext.removeRoute(name));
		}
		
		private boolean isValid(Path path) throws IOException {
			final String contentType = Files.probeContentType(path);
			return contentType != null && contentType.equals("text/xml");
		}

		private InputStream tryOpen(Path path) throws IOException {
			IOException error = null;
			for (int i = 1; i <= 10; i++) {
				try {
					return Files.newInputStream(path, StandardOpenOption.READ);
				} catch (IOException e) {
					error = e;
					logger.debug("File open attempt: " + i, e);
					try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch (InterruptedException e1) {
						Thread.currentThread().interrupt();
						break;
					}
				}
			}
			throw error;
		}

	}
}
