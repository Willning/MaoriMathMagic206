package testing;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*
 * List of all the questions done in a quiz and their answers, used to pipe into a Listview
 */

public class GameList {

	private ObservableList<Question> questionList = FXCollections.observableArrayList();
	
	public void add(Question question){
		questionList.add(question);
	}
	
	public ObservableList<Question> getList(){
		return questionList;
	}
	
}
