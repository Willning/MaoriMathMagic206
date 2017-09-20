package testing;

import java.util.Observable;

/**
 * This class is responsible for doing the testing, records and fires correct/incorrect, calls the HTK.
 * @author William
 *
 */
public class TestConductor extends Observable{

	private IntegerMaoriConverter _convert;
	
	public TestConductor(){
		_convert=new IntegerMaoriConverter();
	}

	public void test(int input){
		//STUB, right now will 50/50 correct and incorrect
		this.setChanged();
		System.out.println(_convert.convertNumber(input));
		
		if (Math.random()<=0.5){
			
			this.notifyObservers("Correct");
		}else{
			this.notifyObservers("Incorrect");
		}

	}
	
	public void skip(){
		this.setChanged();
		this.notifyObservers("Incorrect");
	}
}
