package zoo.camel.boot;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.model.RoutesDefinition;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class RoutesWatchDog implements InitializingBean, DisposableBean {

    final Path contextDir = Paths.get("D:/temp/contexts");
    private WatchService watcher;
    private Thread watchDogTask;

    private AtomicBoolean started = new AtomicBoolean(false);
    private final EventsListener eventsListener = new EventsListener();
    
    private ModelCamelContext camelContext;
    
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
			if (event.kind() == ENTRY_CREATE) {
			    System.out.println("Created: " + event.context().toString());
			    eventsListener.onCreate(filename);
			    continue;
			}
			if (event.kind() == ENTRY_DELETE) {
			    System.out.println("Delete: " + event.context().toString());
			    eventsListener.onDelete(filename);
			}
			if (event.kind() == ENTRY_MODIFY) {
			    System.out.println("Modify: " + event.context().toString());
			    eventsListener.onModify(filename);
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
    
    private class EventsListener {
	
	public void onCreate(Path path) {
	    final File file = path.toFile();
	    try (InputStream is = new FileInputStream(file)) {
	        RoutesDefinition routes = camelContext.loadRoutesDefinition(is);
	        camelContext.addRouteDefinitions(routes.getRoutes());
            } catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
            }
	}
	
	public void onDelete(Path path) {
	    try {
		camelContext.stopRoute("test_route1");
		System.out.println("Remove route: " + camelContext.removeRoute("test_route1"));
            } catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
            }
	}
	
	public void onModify(Path path) {
	    
	}
	
    }
    /*
     * InputStream is = getClass().getResourceAsStream("barRoute.xml");
     * RoutesDefinition routes = context.loadRoutesDefinition(is);
     * context.addRouteDefinitions(routes.getRoutes());
     */

    
    //http://camel.apache.org/loading-routes-from-xml-files.html
    // http://docs.oracle.com/javase/tutorial/essential/io/notification.html
}
