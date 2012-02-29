package zoo.pl.validator;

public final class Validator {
	
	private Validator() {}
	
	public static boolean isValidNip(String nip) {
		nip = trimInput(nip);
		int nsize = nip.length();
        if (nsize != 10) {
            return false;
        }
        
		final int[] weights = {6,5,7,2,3,4,5,6,7};
		final int csum = Integer.valueOf(nip.substring(nsize - 1));
		int sum = 0;
		for (int i = 0; i < nsize - 1; i++) {
            final char c = nip.charAt(i);
            final int val = Integer.valueOf(String.valueOf(c));
            sum += val * weights[i];
        }
        final int control = sum % 11;
        return (control == csum);
	}
	
	public static boolean isValidRegon(String nip) {
		nip = trimInput(nip);
		int nsize = nip.length();
		if (!(nsize == 7 || nsize == 9 || nsize == 14)) {
			return false;
		}

		int[] weights = null;
		if(nsize == 9) {
			weights = new int[] {8,9,2,3,4,5,6,7};
		} else if(nsize == 14) {
			weights = new int[] {2,4,8,5,0,9,7,3,6,1,2,4,8};
		} else if (nsize == 7) {
			weights = new int[] {2,3,4,5,6,7};
		}
		
		final int csum = Integer.valueOf(nip.substring(nsize - 1));
		int sum = 0;
		for (int i = 0; i < nsize - 1; i++) {
			final char c = nip.charAt(i);
			final int val = Integer.valueOf(String.valueOf(c));
			sum += val * weights[i];
		}
		int control = sum % 11;
		if(control == 10) {
			control = 0;
		}
		return (control == csum);
	}
	
	public static boolean isValidPesel(String nip) {
		nip = trimInput(nip);
		int nsize = nip.length();
        if (nsize != 11) {
            return false;
        }
        
		final int[] weights = {9,7,3,1,9,7,3,1,9,7};
		final int csum = Integer.valueOf(nip.substring(nsize - 1));
		int sum = 0;
		for (int i = 0; i < nsize - 1; i++) {
            final char c = nip.charAt(i);
            final int val = Integer.valueOf(String.valueOf(c));
            sum += val * weights[i];
        }
        final int control = sum % 10;
        return (control == csum);
	}
	
	private static String trimInput(String input) {
        return input.replaceAll("\\D*","");
    }
}
