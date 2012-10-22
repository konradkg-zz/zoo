package zoo.daroo.autonumber;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class IntegerAutonumberGeneratorTestCase {

	@Test
	public void integerTest() {
		final IAutonumberGenerator<Integer> autonumberGenerator = new IntegerAutonumberGenerator();
		Object result = null;
		result = autonumberGenerator.getCachedAutoId(666);
		assertTrue(result instanceof Integer);
		result = autonumberGenerator.getCachedAutoId(666);
		assertTrue(result instanceof Integer);
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
