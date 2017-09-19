package testing;

import java.util.Observable;

/**
 * This class is responsible for doing the testing, records and fires correct/incorrect, calls the HTK.
 * @author William
 *
 */
public class TestConductor extends Observable{

	public TestConductor(){

	}

	public void test(){
		//STUB, right now will 50/50 correct and incorrect
		this.setChanged();

		if (Math.random()<=0.5){		
			this.notifyObservers("Correct");
		}else{
			this.notifyObservers("Incorrect");
		}


	}
}
