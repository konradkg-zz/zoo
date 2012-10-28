package zoo.daroo.h2.mem;

import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FlatFileWatchDog {

	private Log logger = LogFactory.getLog(getClass());

	private Path baseDir;

	private WatchService watcher;
	private Thread watchDogTask;

	private AtomicBoolean started = new AtomicBoolean(false);
	private EventsListener eventsListener;

	public void start() throws IOException {
		if (started.compareAndSet(false, true)) {
			watcher = registerNewWatchService();

			watchDogTask = new Thread(new WatchDogTask(this, watcher), "FlatFileWatchDogTask");
			watchDogTask.setDaemon(true);
			watchDogTask.start();
			logger.info("Started watching dir: " + baseDir);
		}
	}

	public void stop() throws IOException {
		if (started.compareAndSet(true, false)) {
			logger.info("Stopping watching dir: " + baseDir);
			
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
		logger.info("FlatFileWatchDog is restarting...");
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

	public void init(Path baseDir, EventsListener listener) throws IOException {
		try (DirectoryStream<Path> ds = Files.newDirectoryStream(baseDir)) {
			for (Path file : ds) {
				eventsListener.onInit(file);
			}
		}
		
		this.baseDir = baseDir.toAbsolutePath();
		this.eventsListener = listener;
		
		start();
	}

	private WatchService registerNewWatchService() throws IOException {
		while (started.get()) {
			WatchService newWatchService = null;
			try {
				newWatchService = baseDir.getFileSystem().newWatchService();
				baseDir.register(newWatchService, ENTRY_DELETE, ENTRY_MODIFY);
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

	private EventsListener getListener() {
		return eventsListener;
	}

	private Path getRoutesDirPath() {
		return baseDir;
	}
	
	private static class WatchDogTask implements Runnable {
		private Log logger = LogFactory.getLog(getClass());

		private final WatchService watcher;
		private final FlatFileWatchDog controller;

		WatchDogTask(FlatFileWatchDog controller, WatchService watcher) {
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
						logger.debug("Base dir no longer valid.");
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

	public interface EventsListener {
		public void onInit(Path path);
		public void onCreate(Path path);
		public void onDelete(Path path);
	}
}
