package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * This pane is responsible for selecting which list will be done, 1-9 or 1-99. 
 */
public class ListSelectPane extends StackPane {
	
	private Stage _stage;
	
	public ListSelectPane(Stage primaryStage) {
		super();
		_stage = primaryStage;
		
		// Button for Easy List(1-100)
		Button easy = new Button();
		easy.setText("Easy List");
		easy.setPrefSize(100d, 75d);
		easy.setTranslateY(80d);
		
		easy.setOnAction(e -> {
			_stage.setScene(new Scene(
				new TestPane(_stage, ListMode.EASY), 
				FrameConstants.WINDOW_WIDTH, 
				FrameConstants.WINDOW_HEIGHT
			));
		});
		
		// Button for hard List (1-100)
		Button hard = new Button();
		hard.setText("Hard List");
		hard.setPrefSize(100d, 75d);
		hard.setTranslateY(160d);
		
		hard.setOnAction(e -> {
			_stage.setScene(new Scene(
				new TestPane(_stage, ListMode.HARD), 
				FrameConstants.WINDOW_WIDTH, 
				FrameConstants.WINDOW_HEIGHT
			));
		});
		
		Label label = new Label();
		label.setText("Select Difficulty List");
		label.setTranslateY(-20d);
		label.setScaleX(3);
		label.setScaleY(3);
		
		Button back = new Button();
		back.setText("Back");
		back.setPrefSize(75d, 30d);
		back.setTranslateY(270d);
		
		// Back button will bring us back to the start menu 
		back.setOnAction(e -> {
			_stage.setScene(new Scene(
				new StartPane(_stage), 
				FrameConstants.WINDOW_WIDTH, 
				FrameConstants.WINDOW_HEIGHT
			));
			_stage.sizeToScene();
		});
		
		this.getChildren().add(label);
		this.getChildren().add(easy); 
		this.getChildren().add(hard);
		this.getChildren().add(back);
		
	}
}
