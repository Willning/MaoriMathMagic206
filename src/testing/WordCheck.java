package testing;

import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.concurrent.Task;

public class WordCheck {
	//background Task used to check what word a recording is.

	private String _heardWord=null;	
	private boolean correct;

	public boolean concurrentTest(String expected) throws InterruptedException{

		Task<String> wordCheck=new Task<String>(){
			@Override
			protected String call() throws Exception {
				//Start HTK and get the word returned.
				String[] command={"/bin/bash", "./check.sh"};

				ProcessBuilder builder=new ProcessBuilder(command);
				builder.redirectErrorStream(true);			
				Process process=builder.start();			

				process.waitFor();
				//read the mlf file, once the recording finishes

				String content = new String(Files.readAllBytes(Paths.get("./recout.mlf")));
				content=content.replace("\n", " ");				

				if (content.contains("sil")){
					//if the string has sil, return the word between it.
					String[] output=content.split("sil ");
					_heardWord=output[1];
					_heardWord=_heardWord.substring(0, _heardWord.lastIndexOf(" "));
				}
				return null;
			}

		};

		Thread thread=new Thread(wordCheck){
			@Override
			public void run(){
				try{
					wordCheck.run();
				}finally{
					if (_heardWord!=null) {
						System.out.println(_heardWord);
						//not accurate at all in terms of whats heard
						correct= (_heardWord.equals(expected));
					}else {						
						correct=false;
					}
				}

			}
		};
		thread.start();
		//return if correct is true.
		return correct;
	}	

}
