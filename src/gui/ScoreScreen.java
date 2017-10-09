package gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import testing.GameList;
import testing.HighScoreManager;
import testing.Question;

//@@TODO: Add a memory function to add the questions and wrong questions to a table

/**
 * Score screen to hold onto the end score and the difficulty.
 * add a button to take back to the start screen. Add a TableView of questions
 * and right and wrong. 
 */
public class ScoreScreen extends StackPane{

	// Based on the score and the mode, these will say different things.
	private Label _score;
	private Label _status;

	public ScoreScreen(ListMode mode, int score, GameList list) {		

		HighScoreManager scoreInstance = HighScoreManager.getInstance();
		scoreInstance.addScore(mode, score);

		String difficulty = "none";

		if (mode == ListMode.EASY) {
			difficulty = "easy";
		}
		else if (mode == ListMode.HARD) {
			difficulty = "hard";
		}
		else if (mode == ListMode.PRACTICE){
			difficulty = "practice";
		}

		VBox layout = new VBox();
		layout.getStyleClass().add("vbox");
		layout.setAlignment(Pos.CENTER);

		String scoreOutput = String.format("You got %d correct on %s mode", score, difficulty);
		_score = new Label(scoreOutput);
		_score.getStyleClass().add("subheading");
		layout.getChildren().add(_score);

		_status = new Label();

		Button back = new Button();
		back.setText("Back");
		back.setOnAction(e -> ScreenManager.get().changeScreen(ScreenManager.ScreenType.MAIN_MENU));

		TableView<Question> questionTable = new TableView<Question>();
		questionTable.setMaxWidth(455);
		questionTable.setItems(list.getList());

		TableColumn<Question, String> question= new TableColumn<Question, String>("Question");
		question.setPrefWidth(150);
		question.setCellValueFactory(new PropertyValueFactory<Question, String>("question"));

		TableColumn<Question, String> answer = new TableColumn<Question, String>("Answer");
		answer.setPrefWidth(150);
		answer.setCellValueFactory(new PropertyValueFactory<Question, String>("answer"));

		TableColumn<Question, String> correct = new TableColumn<Question, String>("Correct?");
		correct.setPrefWidth(150);
		correct.setCellValueFactory(new PropertyValueFactory<Question, String>("tick"));

		questionTable.getColumns().addAll(question, answer, correct);


		if (score >= 8 && mode == ListMode.EASY) {
			_status.setText("Congratulations! You are now able to move onto the hard list.");

			// Button to play the hard list.
			Button advance = new Button("Play Hard List");
			advance.getStyleClass().add("large-button");
			advance.getStyleClass().add("green");
			advance.setOnAction(e -> ScreenManager.get().changeScreen(ScreenManager.ScreenType.HARD_TEST));

			layout.getChildren().addAll(_status, advance);
		}

		layout.getChildren().addAll(questionTable, back);

		this.getChildren().add(layout);
	}
}
