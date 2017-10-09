package testing;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/*
 * Class representing an answered question, used for the gameList TableView.
 */
public class Question {
	private StringProperty question;
	private StringProperty answer;
	private StringProperty tick = new SimpleStringProperty("❌	Incorrect");
	
	private BooleanProperty correct;
	
	public Question(String question, String answer,boolean correct){
		this.question = new SimpleStringProperty(question);
		this.answer = new  SimpleStringProperty(answer);
		if (correct){
			tick = new SimpleStringProperty("✓ 	Correct");
		}
		
		this.correct = new SimpleBooleanProperty(correct);
	}
	
	public String getQuestion(){
		return question.get();
	}
	
	public String getAnswer(){
		return answer.get();
	}
	
	public boolean getCorrect(){
		return correct.get();
	}
	
	public String getTick(){
		return tick.get();
	}
	

}
