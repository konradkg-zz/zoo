package zoo.camel.boot;

import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.IOException;
import java.io.InputStream;
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

	final Path contextDir = Paths.get("p:/temp/contexts");
	private WatchService watcher;
	private Thread watchDogTask;

	private AtomicBoolean started = new AtomicBoolean(false);
	private final EventsListener eventsListener = new EventsListener();

	private ModelCamelContext camelContext;
	
	private Log logger = LogFactory.getLog(getClass());

	public void start() throws IOException {
		watcher = contextDir.getFileSystem().newWatchService();
		contextDir.register(watcher, ENTRY_DELETE, ENTRY_MODIFY);

		if (started.compareAndSet(false, true)) {
			watchDogTask = new Thread(new WatchDogTask(), "RoutesWatchDogTask");
			watchDogTask.setDaemon(true);
			watchDogTask.start();
		}
	}

	public void stop() throws IOException {
		if (started.compareAndSet(true, false)) {
			Thread t = watchDogTask;
			if (t != null) {
				t.interrupt();
				watchDogTask = null;
			}

			if (watcher != null) {
				watcher.close();
			}
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		start();

	}

	@Override
	public void destroy() throws Exception {
		stop();
	}

	public void setCamelContext(ModelCamelContext camelContext) {
		this.camelContext = camelContext;
	}

	private class WatchDogTask implements Runnable {
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
						final Path filename = contextDir.resolve(((WatchEvent<Path>) event).context());
						if (event.kind() == ENTRY_DELETE) {
							eventsListener.onDelete(filename);
						}
						if (event.kind() == ENTRY_MODIFY) {
							eventsListener.onCreate(filename);
						}
					}
					boolean valid = watchKey.reset();
					if (!valid) {
						logger.warn("Directory no longer valid!!!");
						break;
					}
				} catch (InterruptedException e) {
					return;
				} catch (Throwable e) {
					logger.error("Error occured: ", e);
				}
			}
		}
	}

	private class EventsListener {
		
		private final ConcurrentMap<Path, RoutesDefinition> routesMap = new ConcurrentHashMap<>();
		
		public void onCreate(Path path) {
			try (InputStream is = tryOpen(path)) {
				
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
			if(routes == null) {
				logger.warn("Routes file: " + path + " not registered. Nothing to remove.");
				return;
			}
			
			logger.info("Removing routes defined in file: " + path);
			for(RouteDefinition rd : routes.getRoutes()) {
				try {
					removeRoute(rd.getId());
				} catch (Exception e) {
					logger.error(e);
				}
			}
		}

		private void removeRoute(String name) throws Exception{
			camelContext.stopRoute(name, 10, TimeUnit.SECONDS);
			logger.info("Remove route [" + name + "]: " + camelContext.removeRoute(name));
		}
		
		private InputStream tryOpen(Path path) throws IOException {
			IOException error = null;
			for(int i = 1; i<=10; i++) {
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

	// http://camel.apache.org/loading-routes-from-xml-files.html
	// http://docs.oracle.com/javase/tutorial/essential/io/notification.html
}
