package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import testing.LocalScore;

//@@TODO: Add a progress to next stage button
/**
 * Score screen to hold onto the end score and the difficulty.
 * add a button to take back to the start screen
 */
public class ScoreScreen extends StackPane{

	// Based on the score and the mode, these will say different things.
	private Label _score;
	private Label _status;
	
	public ScoreScreen(Stage _stage, ListMode mode, int score) {
		_score = new Label();
		_status = new Label();
		
		LocalScore scoreInstance=LocalScore.getInstance();
		scoreInstance.addScore(mode, score);
		
		String difficulty = "none";
		
		if (mode == ListMode.EASY) {
			difficulty = "easy";
		}
		else if (mode == ListMode.HARD) {
			difficulty = "hard";
		}
		
		String scoreOutput = String.format("You got %d correct on %s mode", score, difficulty);
		_score.setText(scoreOutput);
		_score.setScaleX(3);
		_score.setScaleY(3);
		
		Button back = new Button();
		back.setText("Back To Start");
		back.setPrefSize(160d, 35d);
		back.setTranslateY(150d);
		back.setOnAction(e -> {
			// Back button will bring us back to the start menu 
			_stage.setScene(new Scene(
				new StartPane(_stage), 
				FrameConstants.WINDOW_WIDTH, 
				FrameConstants.WINDOW_HEIGHT
			));
			_stage.sizeToScene();
		});
		
		if (score > 8 && mode == ListMode.EASY) {
			_status.setText("Congradulations, you are now able to move onto the Hard List ");
			_status.setTranslateY(40d);
			_status.setScaleX(2);
			_status.setScaleY(2);
			this.getChildren().add(_status);
			
			// Add a button to play Hard List here.
			Button advance = new Button();
			advance.setText("Play Hard List");
			advance.setPrefSize(160d, 35d);
			advance.setTranslateY(100d);
			
			advance.setOnAction(e -> {
				_stage.setScene(new Scene(
					new TestHolder(_stage, ListMode.HARD), 
					FrameConstants.WINDOW_WIDTH, 
					FrameConstants.WINDOW_HEIGHT
				));
			});
				
			this.getChildren().add(advance);
		}
		
		this.getChildren().add(_score);
		this.getChildren().add(back);
	}
}
