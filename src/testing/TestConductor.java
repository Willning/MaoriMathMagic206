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
	PlayScript _player = new PlayScript(this);

	private boolean _recording = false;
	private boolean _playing = false;

	public TestConductor() {
		_convert = new IntegerMaoriConverter();
	}

	public void test(int input) throws InterruptedException, IOException {
		
		// Cannot process if _recording is true:
		if (!_recording&&!_playing) {
			// Should return what was heard as a string.
			String expected = _convert.convertNumber(input);		
			_check.concurrentTest(expected);			

		}
	}
	
	public void play(){
		if (!_recording&&!_playing) {
			_player.play();
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
	
	public void beginPlay(){
		_playing=true;
		this.setChanged();
		this.notifyObservers("BeginPlay");
	}
	
	public void endPlay(){
		_playing=false;
		this.setChanged();
		this.notifyObservers("EndPlay");
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

		if (!_recording&&!_playing) {
			setChanged();
			notifyObservers("beginRecord");
			
			Thread thread = new Thread(task);			
			thread.start();			
			return;		
		}		
		
	}
}
