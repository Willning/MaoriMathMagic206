package testing;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javafx.concurrent.Task;

public class WordCheck {
	//background Task used to check what word a recording is.

	Task wordCheck=new Task<String>(){

		@Override
		protected String call() throws Exception {
			//Start HTK and get the word returned.
			String[] command={"/bin/bash", "./HTK/MaoriNumbers/check.sh"};

			ProcessBuilder builder=new ProcessBuilder(command);
			builder.redirectErrorStream(true);
			Process process=builder.start();
			try {

				BufferedReader in =new BufferedReader(new InputStreamReader(process.getInputStream()));
				//use a BufferedReader to read the result of the text, then return it.
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					System.out.println(inputLine);
					//fish out the word sil then output the words until the next sil.

				}
				process.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("interrupted");
			} //this process is done when it fully executes,
			return null;

		}

	};	

	public void test() {
		Thread thread=new Thread(wordCheck);
		thread.start();
	}

}
