package testing;

/**
 * This class is responsible for converting a number to words.
 */
public class IntegerMaoriConverter {

	/**
	 * Convert a digit to a Te Reo word.
	 */
	private String convertDigit(int digit) {
		String output = null;
		switch (digit) {
			case 1:
				output = "tahi";
				break;
			
			case 2:
				output = "rua";
				break;
			
			case 3:
				output = "toru";
				break;
			
			case 4:
				output = "whaa";
				break;
			
			case 5:
				output = "rima";
				break;

			case 6:
				output = "ono";
				break;
			
			case 7: 
				output = "whitu";
				break;
		
			case 8: 
				output = "waru";
				break;
		
			case 9: 
				output = "iwa";
				break;
			
			case 10: 
				output = "tekau";
				break;
		}
		return output;
	}

	/**
	 * Convert a full number to a Te Reo word.
	 */
	public String convertNumber(int input) {
		if (input <= 10) {
			return convertDigit(input);
		}

		int leadDigit = input / 10;
		int backDigit = input % 10;

		if (backDigit == 0) {
			return String.format(
				"%s tekau", 
				convertDigit(leadDigit)
			);
		}
		else if (leadDigit == 1) {
			return String.format(
				"tekau ma %s", 
				convertDigit(backDigit)
			);			
		}
		else {
			return String.format(
				"%s tekau ma %s", 
				convertDigit(leadDigit), 
				convertDigit(backDigit)
			);
		}
	}
}
