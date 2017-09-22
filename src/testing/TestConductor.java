package testing;

import java.io.IOException;
import java.util.Observable;

import javafx.concurrent.Task;

/**
 * This class is responsible for doing the testing, records and fires correct/incorrect, calls the HTK.
 * @author William
 *
 */
public class TestConductor extends Observable{

	private IntegerMaoriConverter _convert;	
	
	private boolean _recording=false; //this is a state variable, true when recording



	public TestConductor(){
		_convert=new IntegerMaoriConverter();

	}

	public void test(int input){
		//STUB, right now will 50/50 correct and incorrect
		this.setChanged();
	

		WordCheck check=new WordCheck();
		check.test();
		//check.test() should return what was heard as a string.
		
		String expected=_convert.convertNumber(input);
		System.out.println(expected);
		
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

	public void record() {
		//One recording at a time, need to add a way to send out event when recording is done
		
		Task<String> task=new Task<String>(){
			//This method is responsible for creating a thread that does the recording, send a event when recording ends.
			protected String call() throws IOException, InterruptedException{
				_recording=true;
				String[] command={"/bin/bash", "./HTK/MaoriNumbers/record.sh"};
				ProcessBuilder builder=new ProcessBuilder(command);
				builder.redirectErrorStream(true);
				Process process=builder.start();				
				process.waitFor();
				_recording=false;				
							
				return null;

			}
		};		
		if (!_recording) {
			Thread thread=new Thread(task);			
			thread.start();			
		}
	}
}
