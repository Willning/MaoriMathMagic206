package testing;

import java.io.IOException;
import java.util.Observable;

import javafx.concurrent.Task;

/**
 * This class is responsible for doing the testing, recording and firing 'correct'/'incorrect',
 * and calling HTK.
 */
public class TestConductor extends Observable {

	private IntegerMaoriConverter _convert;
	WordCheck _check = new WordCheck(this);

	private boolean _recording = false;

	public TestConductor() {
		_convert = new IntegerMaoriConverter();
	}

	public void test(int input) throws InterruptedException, IOException {
		
		// Cannot process if _recording is true:
		if (!_recording) {
			// Should return what was heard as a string.
			String expected = _convert.convertNumber(input);		
			_check.concurrentTest(expected);			

		}
	}
	
	public void correct() {
		this.setChanged();
		this.notifyObservers("Correct");
	}
	
	public void incorrect() {
		this.setChanged();
		this.notifyObservers("Incorrect");
	}

	public void record() {

		Task<String> task=new Task<String >(){
			
			/**
			 * This method is responsible for creating a thread that does the recording,
			 * and sending an event when recording ends.
			 */
			protected String call() throws IOException, InterruptedException {
				_recording = true;
				String[] command = {"/bin/bash", "./record.sh"};
				ProcessBuilder builder = new ProcessBuilder(command);
				builder.redirectErrorStream(true);
				Process process = builder.start();				
				process.waitFor();
				_recording = false;

				return null;
			}
		};
		
		task.setOnSucceeded(e->{
			this.setChanged();
			this.notifyObservers("endRecord");
		});

		if (!_recording) {
			setChanged();
			notifyObservers("beginRecord");
			
			Thread thread = new Thread(task);			
			thread.start();			
			return;		
		}		
		
	}
}
