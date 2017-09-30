package testing;

import java.io.IOException;
import java.util.Observable;
import javafx.concurrent.Task;

/**
 * This class is responsible for doing the testing, recording and firing 'correct'/'incorrect',
 * and calling HTK.
 */
public class TestConductor extends Observable {

	WordCheck _check = new WordCheck(this);
	PlayScript _player = new PlayScript(this);

	private boolean _recording = false;
	private boolean _playing = false;

	public TestConductor() {

	}

	public void test(int input) throws InterruptedException, IOException {
		// Cannot process if _recording is true:
		if (!_recording && !_playing) {
			// Should return what was heard as a string.
			String expected = MaoriNumberConverter.convertNumber(input);		
			_check.testConcurrently(expected);
		}
	}
	
	public void play() {
		if (!_recording && !_playing) {
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
	
	/**
	 * Change the state to begin playing a recording. 
	 */
	public void beginPlay() {
		_playing = true;
		this.setChanged();
		this.notifyObservers("BeginPlay");
	}
	
	/**
	 * Change the state to finish playing a recording. 
	 */
	public void endPlay() {
		_playing = false;
		this.setChanged();
		this.notifyObservers("EndPlay");
	}

	/**
	 * This method is responsible for creating a thread that does the recording,
	 * and sending an event when recording ends.
	 */
	public void record() {
		
		Task<Void> task = new Task<Void>() {
			protected Void call() throws IOException, InterruptedException {
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
		
		task.setOnSucceeded(e-> {
			setChanged();
			notifyObservers("endRecord");
		});

		if (!_recording && !_playing) {
			setChanged();
			notifyObservers("beginRecord");
			
			Thread thread = new Thread(task);			
			thread.start();			
			return;		
		}
	}
}
