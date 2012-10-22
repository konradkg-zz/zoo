package zoo.daroo.autonumber;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class IntegerAutonumberGeneratorTestCase {

	private final static int DEFAULT_NODE = 666;

	@Test
	public void checkCachedType() {
		final IAutonumberGenerator<Integer> autonumberGenerator = new IntegerAutonumberGenerator();
		Object result = null;
		result = autonumberGenerator.getCachedAutoId(DEFAULT_NODE);
		assertTrue(result instanceof Integer);
		result = autonumberGenerator.getCachedAutoId(DEFAULT_NODE);
		assertTrue(result instanceof Integer);
	}

	@Test
	public void checkCachedContinuum() {
		final IAutonumberGenerator<Integer> autonumberGenerator = new IntegerAutonumberGenerator();
		autonumberGenerator.setCachedAutoIdRange(DEFAULT_NODE, 3);

		for (int i = 0; i < 5000; i++) {
			final Integer result = autonumberGenerator.getCachedAutoId(DEFAULT_NODE);
			assertEquals(i, result.intValue());
		}
	}

	@Test
	public void multithread() throws ExecutionException {
		final IAutonumberGenerator<Integer> autonumberGenerator = new IntegerAutonumberGenerator();
		autonumberGenerator.setCachedAutoIdRange(DEFAULT_NODE, 3);

		final int threads = 5;
		final Object placeholder = new Object();
		final ConcurrentHashMap<Integer, Object> resultMap = new ConcurrentHashMap<Integer, Object>();
		final ExecutorService executorService = Executors.newFixedThreadPool(threads);
		final CompletionService<Void> completionService = new ExecutorCompletionService<Void>(executorService);
		final CountDownLatch latch = new CountDownLatch(1);
		
		for (int i = 0; i < threads; i++) {
			completionService.submit(new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					latch.await();
					for(int i = 0; i < 100000; i++) {
						final Integer result = autonumberGenerator.getCachedAutoId(DEFAULT_NODE);
						if(resultMap.putIfAbsent(result, placeholder) != null) {
							Assert.fail("Duplicate found: " + result);
						}
					}
					
					return null;
				}
			});
		}
		latch.countDown();
		
		for(int i = 0; i < threads; i++) {
			try {
				completionService.take().get();
			} catch (InterruptedException e) {
				executorService.shutdownNow();
				Thread.currentThread().interrupt();
				break;
			}
		}
		
		executorService.shutdown();
		
		final TreeSet<Integer> sortedSet = new TreeSet<Integer>(resultMap.keySet());
		int count = 0;
		for(Integer r : sortedSet) {
			assertEquals(count++, r.intValue());
		}

		final int numbersProviderLast = ((IntegerAutonumberGenerator) autonumberGenerator).numbersProvider.get();
		assertTrue(numbersProviderLast - count <= 3); 
	}

	private static class IntegerAutonumberGenerator extends AutonumberGeneratorBase<Integer> {
		private final AtomicInteger numbersProvider = new AtomicInteger(0);

		@Override
		protected Integer getAutoId0(int node, int range) {
			return numbersProvider.getAndAdd(range);
		}

		@Override
		protected Integer convertToTargetType(Number number) {
			return number.intValue();
		}

	}
}
