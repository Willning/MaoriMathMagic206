package testing;

public class IntegerMaoriConverter {
	//This class is responsible for having an enum of the numbers

	private String convertDigit(int digit){
		String output=null;
		switch (digit){
		case 1: digit =1;
		output="tahi";
		break;
		case 2: digit=2;
		output="rua";
		break;
		case 3: digit=3;
		output="toru";
		break;
		case 4: digit=4;
		output="wha";
		break;
		case 5: digit=5;
		output="rima";
		break;
		case 6: digit=6;
		output="ono";
		break;
		case 7: digit=7;
		output="whitu";
		break;
		case 8: digit=8;
		output="waru";
		break;
		case 9: digit=9;
		output="iwa";
		break;
		case 10: digit=10;
		output="tekau";
		break;
		}

		return output;

	}

	public String convertNumber(int input){
		if (input<=10){
			return convertDigit(input);
		}

		int leadDigit=input/10;
		int backDigit=input%10;

		if (backDigit==0){
			return String.format("%s tekau", convertDigit(leadDigit));
		}else if(leadDigit==1){
			return String.format("tekau ma %s", convertDigit(backDigit));			
		}else{
			return String.format("%s tekau ma %s", convertDigit(leadDigit), convertDigit(backDigit));
		}

	}
}
