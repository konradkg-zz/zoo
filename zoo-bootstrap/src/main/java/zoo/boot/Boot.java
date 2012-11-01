package zoo.boot;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Boot {

	private final static CountDownLatch latch = new CountDownLatch(1);
	private final static AtomicBoolean started = new AtomicBoolean(false);

	private final static Thread shutdownHook = new Thread(new Runnable() {
		@Override
		public void run() {
			stop(null);
		}
	});
	
	public static void start(String[] args) {
		if (started.compareAndSet(false, true)) {
			System.out.println("start");
			Runtime.getRuntime().addShutdownHook(shutdownHook);
			try {
				while(latch.await(1, TimeUnit.SECONDS) == false);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				return;
			}
		}
	}

	public static void stop(String[] args) {
		if (started.compareAndSet(true, false)) {
			System.out.println("stop");
			
			Runtime.getRuntime().removeShutdownHook(shutdownHook);
			latch.countDown();
		}
	}

	public static void main(String[] args) {
		final String command = (args.length == 0) ? "start" : args[0];
		if ("start".equals(command)) {
			start(args);
		} else if ("stop".equals(command)) {
			stop(args);
		}
	}

}
