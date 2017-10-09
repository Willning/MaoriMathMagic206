package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import testing.HighScoreManager;

//@@TODO: Add a progress to next stage button
/**
 * Score screen to hold onto the end score and the difficulty.
 * add a button to take back to the start screen
 */
public class ScoreScreen extends StackPane{

	// Based on the score and the mode, these will say different things.
	private Label _score;
	private Label _status;
	
	public ScoreScreen(ListMode mode, int score) {		

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
		
		if (score >= 8 && mode == ListMode.EASY) {
			_status.setText("Congratulations! You are now able to move onto the hard list.");
			
			// Button to play the hard list.
			Button advance = new Button("Play Hard List");
			advance.getStyleClass().add("large-button");
			advance.getStyleClass().add("green");
			advance.setOnAction(e -> ScreenManager.get().changeScreen(ScreenManager.ScreenType.HARD_TEST));
				
			layout.getChildren().addAll(_status, advance);
		}
		
		layout.getChildren().add(back);

		this.getChildren().add(layout);
	}
}
