package zoo.htmunit.tryouts.throttler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.buffer.CircularFifoBuffer;

public class ThrottleTest {

	static DateFormat df = new SimpleDateFormat("HH:mm:ss.SSS");
	static long TIME_OFFSET = System.nanoTime();

	public static void main(String[] args) {
		final int m = 2;
		final TimeUnit unit = TimeUnit.SECONDS;
		final int n = 10; // [s]
		final long nNano = unit.toNanos(n);

		final CircularFifoBuffer fifoBuffer = new CircularFifoBuffer(m);

		while (true) {
			Long oldestEntry = (fifoBuffer.isEmpty()) ? null : (Long) fifoBuffer.get();
			long now = System.nanoTime() - TIME_OFFSET;
			if (oldestEntry == null || !fifoBuffer.isFull() || now - oldestEntry >= nNano) {
				fifoBuffer.add(now);
				testMethod();
			} else {
				try {
					TimeUnit.NANOSECONDS.sleep(nNano - (now - oldestEntry));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		// fifoBuffer.

	}

	public static void testMethod() {
		System.out.println(df.format(new Date()));
	}

}
