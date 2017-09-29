package testing;

import javafx.concurrent.Task;

/**
 * Background task, used to play a recording because media doesn't work
 */

public class PlayScript {
	
	private TestConductor _parent;
	
	public PlayScript(TestConductor tester){
		_parent=tester;
	}
	
	public void play(){
		_parent.beginPlay();
		
		Task<String> play = new Task<String>(){
			@Override
			protected String call() throws Exception {
				String[] command = {"/bin/bash", "./play.sh"};				
				ProcessBuilder builder = new ProcessBuilder(command);
				builder.redirectErrorStream(true);			
				Process process = builder.start();			

				process.waitFor();
				
				
				// TODO Auto-generated method stub
				return null;
			}
			
		};
		
		play.setOnSucceeded(e ->{
			//Call the tester parent to fire out a play event
			_parent.endPlay();
		});
		
		play.setOnFailed(e ->{
			_parent.endPlay();
		});
		
		Thread playThread= new Thread(play);
		playThread.start();
	}
	
}
