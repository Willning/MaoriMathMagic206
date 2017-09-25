package testing;

import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.concurrent.Task;

/**
 * Background task, used to check what word a recording is.
 */
public class WordCheck {

	private String _heardWord = null;	
	private boolean correct;

	public boolean concurrentTest(String expected) throws InterruptedException {

		Task<String> wordCheck = new Task<String>() {
			@Override
			protected String call() throws Exception {
				// Start HTK and get the word returned.
				String[] command = {"/bin/bash", "./check.sh"};

				ProcessBuilder builder = new ProcessBuilder(command);
				builder.redirectErrorStream(true);			
				Process process = builder.start();			

				process.waitFor();

				// Read the mlf file, once the recording finishes:
				String content = new String(Files.readAllBytes(Paths.get("./recout.mlf")));
				content = content.replace("\n", " ");				

				// If the string has "sil ", return the word between it.
				if (content.contains("sil")) {
					String[] output = content.split("sil ");
					_heardWord = output[1];
					_heardWord = _heardWord.substring(0, _heardWord.lastIndexOf(" "));
				}
				return null;
			}
		};

		Thread thread = new Thread(wordCheck) {
			@Override
			public void run() {
				try {
					wordCheck.run();
				}
				finally {
					if (_heardWord != null) {
											
						System.out.println(_heardWord.compareTo(expected));						
										
						correct = (_heardWord.compareTo(expected) == 0);								
					}
					else {						
						correct = false;
					}
				}

			}
		};
		thread.start();
				
		//@@TODO: fandangle your way so this thread.join() is not needed later on, but it works now
		thread.join();
		return correct;
	}	

}
