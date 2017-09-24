package testing;

import java.io.IOException;
import java.util.Observable;

import javafx.application.Platform;
import javafx.concurrent.Task;

/**
 * This class is responsible for doing the testing, records and fires correct/incorrect, calls the HTK.
 * @author William
 *
 */
public class TestConductor extends Observable{

	private IntegerMaoriConverter _convert;
	WordCheck _check=new WordCheck();

	private boolean _recording=false; //this is a state variable, true when recording



	public TestConductor(){
		_convert=new IntegerMaoriConverter();

	}

	public void test(int input) throws InterruptedException, IOException{
		//cannot processif _recording is true;
		if (!_recording) {
			String expected=_convert.convertNumber(input);		
			boolean correct=_check.concurrentTest(expected);				
			//should return what was heard as a string.
			this.setChanged();	
			if (correct){
				this.notifyObservers("Correct");
			}else{
				this.notifyObservers("Incorrect");
			}
		}

	}

	public void skip(){
		//Used for skipping a question, defaults to incorrect
		this.setChanged();
		this.notifyObservers("Incorrect");
	}

	public int record() {
		//One recording at a time, need to add a way to send out event when recording is done

		Task<String> task=new Task<String>(){
			//This method is responsible for creating a thread that does the recording, send a event when recording ends.
			protected String call() throws IOException, InterruptedException{
				_recording=true;
				String[] command={"/bin/bash", "./record.sh"};
				ProcessBuilder builder=new ProcessBuilder(command);
				builder.redirectErrorStream(true);
				Process process=builder.start();				
				process.waitFor();
				_recording=false;


				return null;

			}
		};

		if (!_recording) {
			setChanged();
			notifyObservers("beginRecord");
			
			Thread thread=new Thread(task){
				@Override 
				public void run(){
					try{
						task.run();
					}finally{
						
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								setChanged();
								notifyObservers("endRecord");
							}
						});

						//jig this up to push out an event						
					}
				}
			};			
			thread.start();			
			return 1;			
		}
		return 0;
		//0 if not recording?
	}
}
