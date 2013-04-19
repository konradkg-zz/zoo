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

	private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
	private AtomicBoolean started = new AtomicBoolean(false);

	private Lock masterLock = createDistributedLock();
	//private Lock masterLock = createLocalLock();
	private final static String MASTER_LOCK_KEY = "MASTER_LOCK"; 
	private HazelcastInstance hz;

	public static void main(String[] args) {
		LockTest lockTest = new LockTest();
		lockTest.startup();
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
		try {
			while (masterLock.tryLock(1, TimeUnit.SECONDS) == false)
				;
			try {
				doStart();

				TimeUnit.SECONDS.sleep(30);
				
				doStop();
			} finally {
				masterLock.unlock();
				if(hz != null) {
					hz.getLifecycleService().shutdown();
				}
			}

		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private void doStart() {
		if (started.compareAndSet(false, true)) {
			scheduledExecutorService.scheduleAtFixedRate(new DummyTask(), 5, 5, TimeUnit.SECONDS);
		}
	}

	private void doStop() {
		if (started.compareAndSet(true, false)) {
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
