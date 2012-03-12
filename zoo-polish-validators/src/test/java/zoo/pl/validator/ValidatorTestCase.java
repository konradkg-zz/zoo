package zoo.pl.validator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class ValidatorTestCase {
	
	@Test
	public void pesel4() {
		Assert.assertFalse(Validator.isValidPesel("00000000000"));
	}
	
	//http://wipos.p.lodz.pl/zylla/ut/pesel.html
	@Test
	public void pesel5() {
		Assert.assertFalse(Validator.isValidPesel("10000000009"));
	}
	
	@Test
	public void pesel6() {
		Assert.assertFalse(Validator.isValidPesel("11000000006"));
	}
	
	@Test
	public void regon2() {
		//orlen
		Assert.assertTrue(Validator.isValidRegon("610188201"));
	}
	
	@Test
	public void regon3() {
		//UM Poznan
		Assert.assertTrue(Validator.isValidRegon("631257822"));
	}

	@Test
	public void regon4() {
		//UM Gdynia
		Assert.assertTrue(Validator.isValidRegon("000598486"));
	}
	
	public void other() {
		//STOWARZYSZENIE PRO FILIA
		Assert.assertTrue(Validator.isValidRegon("070429562"));
		Assert.assertTrue(Validator.isValidNip("5482103266"));
		
		//nfz warszawa
		Assert.assertTrue(Validator.isValidRegon("015775258"));
		
		//statoil
		Assert.assertTrue(Validator.isValidRegon("004857520"));
		Assert.assertTrue(Validator.isValidNip("7790001083"));
			
		
	}
	
	public void kgs_regon_14() {
		//Karpacka Spolka Gazownictwa sp. z o.o.
		Assert.assertTrue(Validator.isValidNip("993 02 46 349"));
		Assert.assertTrue(Validator.isValidRegon("852484171"));
		
		Assert.assertTrue(Validator.isValidRegon("852484171-00063"));
		Assert.assertTrue(Validator.isValidRegon("852484171-00056"));
		Assert.assertTrue(Validator.isValidRegon("852484171-00088"));
		Assert.assertTrue(Validator.isValidRegon("852484171-00024"));
		Assert.assertTrue(Validator.isValidRegon("852484171-00031"));
		Assert.assertTrue(Validator.isValidRegon("852484171-00070"));
		Assert.assertTrue(Validator.isValidRegon("852484171-00049"));
		Assert.assertTrue(Validator.isValidRegon("852484171-00095"));
		
		//fake
		Assert.assertFalse(Validator.isValidRegon("852484171-00071"));
		Assert.assertFalse(Validator.isValidRegon("852484171-00096"));
	}
	 
	
	
	//
	
	
	
	/*http://www.designend.net/blog-webmastera,walidacja-danych-czesc-3
	 * 	                $weights = array(8, 9, 2, 3, 4, 5, 6, 7);
04
//wagi stosowane dla REGONów 9-znakowych
05
            }elseif(strlen($value) == 14){
06
                $weights = array(2, 4, 8, 5, 0, 9, 7, 3, 6, 1, 2, 4, 8);
07
//wagi stosowane dla REGONów 14-znakowych

	 */

}
