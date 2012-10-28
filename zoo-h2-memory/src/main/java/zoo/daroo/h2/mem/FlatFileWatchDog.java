package zoo.daroo.h2.mem;

import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FlatFileWatchDog {
	
	private final Log logger = LogFactory.getLog(getClass());
	
	private final Path file;
	private final FileChangeEventListener eventListener;
	
	private volatile Thread watchDogTask;
	private volatile BasicFileAttributes lastFileAttributes;
	
	private AtomicBoolean started = new AtomicBoolean(false);
	
	public FlatFileWatchDog(Path fileToWatch, FileChangeEventListener eventListener) {
		this.file = fileToWatch.toAbsolutePath();
		this.eventListener = eventListener;
	}
	
	public void start() throws IOException {
		if (started.compareAndSet(false, true)) {
			try {
				lastFileAttributes = FileUtils.getFileAttributes(file);
			} catch (NoSuchFileException e) {
				logger.warn(e);
			}
			
			watchDogTask = new Thread(new WatchDogTask(), "FlatFileWatchDogTask");
			watchDogTask.setDaemon(true);
			watchDogTask.start();
			logger.info("Started watching file: " + file);
		}
	}
	
	public void stop() {
		if (started.compareAndSet(true, false)) {
			logger.info("Stopping watching file: " + file);
			
			final Thread t = watchDogTask;
			if (t != null) {
				watchDogTask = null;
				t.interrupt();
			}
		}
	}
	
	private class WatchDogTask implements Runnable {
		private Log logger = LogFactory.getLog(getClass());

		@Override
		public void run() {
			while (true) {
				try {
					BasicFileAttributes currentFileAttributes = null;
					try {
						currentFileAttributes = FileUtils.getFileAttributes(file);
					} catch (NoSuchFileException e) {
						if(logger.isDebugEnabled()) {
							logger.debug(e.getMessage());
						}
					} catch (FileSystemException e) {
						// no longer available
						logger.error(e);
					} catch (IOException e) {
						logger.error("Unexpected IOException while getting file attributes", e);
					}
					
					boolean newOrChanged = false;
					if (currentFileAttributes != null && lastFileAttributes == null) {
						//new file
						newOrChanged = true;
					} else if (currentFileAttributes != null && lastFileAttributes != null) {
						if(currentFileAttributes.size() != lastFileAttributes.size() || 
								currentFileAttributes.lastModifiedTime().equals(lastFileAttributes.lastModifiedTime()) == false) {
							//changed
							newOrChanged = true;
						}
					}
					
					if(newOrChanged) {
						lastFileAttributes = currentFileAttributes;
						eventListener.onModify(file);
					}
					
					TimeUnit.MILLISECONDS.sleep(1000);
				} catch (InterruptedException e) {
					return;
				} catch (Throwable e) {
					logger.error("Unexpected error occured: ", e);
				}
			}
		}
	}
	

	public static interface FileChangeEventListener {
		public void onModify(Path path);
	}
}
