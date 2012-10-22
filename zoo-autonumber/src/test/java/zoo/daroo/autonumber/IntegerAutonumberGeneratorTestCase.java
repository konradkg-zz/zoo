package zoo.daroo.autonumber;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.atomic.AtomicInteger;

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
		
		for(int i = 0; i < 50; i++) {
			final Integer result = autonumberGenerator.getCachedAutoId(DEFAULT_NODE);
			assertEquals(i, result.intValue());
		}
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
