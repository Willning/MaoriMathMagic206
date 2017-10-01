package testing;

import javafx.concurrent.Task;

/**
 * Background task used to play a recording.
 */
public class PlayRecordingScript {
	
	private TestConductor _parent;
	
	public PlayRecordingScript(TestConductor tester) {
		_parent = tester;
	}
	
	/**
	 * Play a recording.
	 */
	public void play() {
		_parent.beginPlay();
		
		Task<Void> play = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				String[] command = {"/bin/bash", "./play.sh"};				
				
				ProcessBuilder builder = new ProcessBuilder(command);
				builder.redirectErrorStream(true);			
				
				Process process = builder.start();			
				process.waitFor();

				return null;
			}
		};
		
		play.setOnSucceeded(e -> {
			_parent.endPlay();
		});
		play.setOnFailed(e ->{
			_parent.endPlay();
		});
		
		Thread playThread = new Thread(play);
		playThread.start();
	}
	
}
