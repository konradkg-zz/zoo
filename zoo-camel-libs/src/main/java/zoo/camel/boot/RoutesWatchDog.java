package zoo.camel.boot;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class RoutesWatchDog {

    final Path contextDir = Paths.get("D:/temp/contexts");
    private WatchService watcher;
    private Thread watchDogTask;

    private AtomicBoolean started = new AtomicBoolean(false);

    public void start() throws IOException {
	watcher = contextDir.getFileSystem().newWatchService();
	contextDir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

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
			
			if (event.kind() == ENTRY_CREATE) {
			    System.out.println("Created: " + event.context().toString());
			}
			if (event.kind() == ENTRY_DELETE) {
			    System.out.println("Delete: " + event.context().toString());
			}
			if (event.kind() == ENTRY_MODIFY) {
			    System.out.println("Modify: " + event.context().toString());
			}
		    }
		    boolean valid = watchKey.reset();
		    if (!valid) {
			System.out.println("Directory no longer valid!!!");
			break;
		    }
		} catch (InterruptedException e) {
		    return;
		} catch (Throwable e) {
		    System.out.println(e.getMessage());
		}
	    }
	}
    }
    /*
     * InputStream is = getClass().getResourceAsStream("barRoute.xml");
     * RoutesDefinition routes = context.loadRoutesDefinition(is);
     * context.addRouteDefinitions(routes.getRoutes());
     */

    // http://docs.oracle.com/javase/tutorial/essential/io/notification.html
}
