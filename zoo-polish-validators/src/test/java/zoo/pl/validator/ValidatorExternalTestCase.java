package zoo.pl.validator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class ValidatorExternalTestCase {

	@Test
	public void nip1() {
		Assert.assertTrue(ValidatorExternal.isValidNip("655-140-24-30"));
		
	}
	
}
