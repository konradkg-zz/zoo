package zoo.hazelcast.tests;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class LockTest {

	private final static String instanceId = UUID.randomUUID().toString();

	private ScheduledExecutorService scheduledExecutorService;
	private AtomicBoolean started = new AtomicBoolean(false);

	private Lock masterLock = createDistributedLock();
	// private Lock masterLock = createLocalLock();
	private final static String MASTER_LOCK_KEY = "MASTER_LOCK";
	private static HazelcastInstance hz;

	public static void main(String[] args) {
		LockTest lockTest = new LockTest();
		lockTest.startup();

		if (hz != null) {
			hz.getLifecycleService().shutdown();
		}
	}

	protected Lock createLocalLock() {
		return new ReentrantLock();
	}

	protected Lock createDistributedLock() {
		Config cfg = new Config();
		cfg.getGroupConfig().setName("test").setPassword("test-pass");
		hz = Hazelcast.newHazelcastInstance(cfg);
		return hz.getLock(MASTER_LOCK_KEY);
	}

	public void startup() {
		int count = 0;
		try {
			while (true) {
				while (masterLock.tryLock(1, TimeUnit.SECONDS)) {

					try {
						doStart();
						while (masterLock.tryLock(1, TimeUnit.SECONDS)) {
							if (count++ > 30)
								break;

							TimeUnit.SECONDS.sleep(1);
						}
						doStop();
					} finally {
						masterLock.unlock();
					}

					if (count > 30)
						return;
				}
			}

		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private synchronized void doStart() {
		if (started.compareAndSet(false, true)) {
			System.out.println("Starting... instanceId=" + instanceId);
			this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
			scheduledExecutorService.scheduleAtFixedRate(new DummyTask(), 5, 5, TimeUnit.SECONDS);
		}
	}

	private synchronized void doStop() {
		if (started.compareAndSet(true, false)) {
			System.out.println("Stopping... instanceId=" + instanceId);
			final ScheduledExecutorService executorService = this.scheduledExecutorService;

			if (executorService == null)
				return;
			scheduledExecutorService.shutdown();
			try {
				scheduledExecutorService.awaitTermination(10, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	private static class DummyTask implements Runnable {
		@Override
		public void run() {
			System.out.println("Executed... instanceId=" + instanceId);

		}

	}
}
