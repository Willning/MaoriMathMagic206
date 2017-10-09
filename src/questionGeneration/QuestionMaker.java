package questionGeneration;

import java.util.Random;

/*
 * This class is used to generate the questions that need to be answered. 
 */

//@@TODO: add a way to randomly call one of the generation methods.
//@@TODO: change the way generation works to lever off an input in the function? ENUM for easy and hard?

public class QuestionMaker {

	private Random randomGenerator = new Random();

	private String outputQuestion;
	private int answer;
	
	/*
	 * Generate an easy addition question, that is answer less than 10 and bigger than 0 
	 */
	public void generateEasyAddtion(){
		int numOne = 100;
		int numTwo = 100; 
		
		
		//While loop until we reach a valid equation
		while (numOne + numTwo >= 10 || numOne + numTwo == 0){

			numOne = randomGenerator.nextInt(9);
			numTwo = randomGenerator.nextInt(9);
		}

		outputQuestion = String.format("%s+%s", numOne, numTwo);

		answer= numOne + numTwo;	

	}	
	
	/*
	 * generate a hard addition question, that is where the answer is less than 100 and bigger than 0 
	 */
	public void generateHardAddition(){
		int numOne = 100;
		int numTwo = 100; 
		while (numOne + numTwo >= 100 || numOne + numTwo == 0){

			numOne = randomGenerator.nextInt(99);
			numTwo = randomGenerator.nextInt(99);
			
			outputQuestion = String.format("%s+%s", numOne, numTwo);
			answer = numOne + numTwo;
		}
		
	}
	
	public void generateEasyMultiplication(){
		int numOne = 100;
		int numTwo = 100; 
		
		while (numOne * numTwo >= 10 || numOne * numTwo == 0){
			numOne = randomGenerator.nextInt(9);
			numTwo = randomGenerator.nextInt(9);
		}		
		outputQuestion = String.format("%sx%s", numOne, numTwo);
		answer = numOne * numTwo;
	}
	
	public void generateEasyDivision(){
		int numOne = 100;
		int numTwo = 15; 
		
		while (numOne/numTwo >= 10 || numOne%numTwo !=  0){
			numOne = randomGenerator.nextInt(98)+1;
			numTwo = randomGenerator.nextInt(8)+1;
		}
		
		outputQuestion = String.format("%s÷%s", numOne, numTwo);
		answer = numOne / numTwo;
		
	}
	
	
	/*
	 * generate a random easy subtraction problem, that is where the answer is less than 10 and bigger than 0 
	 */
	public void generateEasySubtraction(){
		int numOne = -100;
		int numTwo = 100; 

		while (numOne - numTwo >10 || numOne - numTwo <= 0){
			numOne = randomGenerator.nextInt(9);
			numTwo = randomGenerator.nextInt(9);
		}

		outputQuestion = String.format("%s-%s", numOne, numTwo);

		answer= numOne - numTwo;	
	}	
	
	/*
	 * return the question as a string, must be used after generating a question otherwise will return a null.
	 */
	public String getEquation(){
		return outputQuestion;
	}
	
	/*
	 * Return the answer as an int, use after generating a question otherwise will return a null.
	 */
	public int getAnswer(){
		return answer;

	}
}
