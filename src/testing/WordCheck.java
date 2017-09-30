package testing;

import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.concurrent.Task;

/**
 * Background task, used to check what word a recording is.
 */
public class WordCheck {

	private String _heardWord = null;	
	private TestConductor _parent;
	
	public WordCheck(TestConductor tester) {
		_parent = tester;
	}

	public void concurrentTest(String expected) throws InterruptedException {

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
		
		// On succeed, will write correct as the word that was heard.
		wordCheck.setOnSucceeded(e -> {			
			if (_heardWord != null && _heardWord.equals(expected)) {				
				_parent.correct();												
			}
			else {						
				_parent.incorrect();				
			}
		});

		Thread thread = new Thread(wordCheck);
		thread.start();
	}	

}
