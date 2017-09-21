package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ScoreScreen extends StackPane{
	//Score screen to hold onto the end score and the difficulty, add a button to take back to the start screen
	

	private Label _score;
	
	
	public ScoreScreen(Stage _stage, ListMode mode, int score){
		_score=new Label();
		
		String difficulty="none";
		
		if (mode==ListMode.EASY){
			difficulty="easy";
		}else if (mode==ListMode.HARD){
			difficulty="hard";
		}
		
		String scoreOutput=String.format("You got %d correct on %s mode", score, difficulty);
		_score.setText(scoreOutput);
		_score.setScaleX(3);
		_score.setScaleY(3);
		
		Button back=new Button();
		back.setText("back");
		back.setPrefSize(160d, 35d);
		back.setTranslateY(100d);
		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//back button will bring us back to the start menu 
				_stage.setScene(new Scene(new StartPane(_stage), FrameConstants.WINDOW_WIDTH ,FrameConstants.WINDOW_HEIGHT));
				_stage.sizeToScene();

			}
		});
		
		this.getChildren().add(_score);
		this.getChildren().add(back);
		
	}
	
	
}
