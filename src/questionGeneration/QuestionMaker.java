package questionGeneration;

import java.util.Random;

/*
 * This class is used to generate the questions that need to be answered. 
 */
public class QuestionMaker {

	private Random randomGenerator = new Random();
	
	private String outputQuestion;
	private int answer;
	
	public void generateEasyAddtion(){
		int numOne = 100;
		int numTwo = 100; 
		
		while (numOne+numTwo>10 || numOne + numTwo == 0){
		
		numOne = randomGenerator.nextInt(9);
		numTwo = randomGenerator.nextInt(9);
		}
		
		outputQuestion = String.format("%s+%s=", numOne, numTwo);
		
		answer= numOne + numTwo;	
		
	}
	
	public String getEquation(){
		return outputQuestion;
		
	}
	
	public int getAnswer(){
		return answer;
		
	}
}
