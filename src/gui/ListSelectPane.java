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

//@@TODO add descriptors for what each list entails

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
		
		easy.setOnAction(e -> SceneManager.get().changeScene(SceneManager.SceneType.EASY_TEST));
		
		// Button for hard List (1-100)
		Button hard = new Button();
		hard.setText("Hard List");
		hard.setPrefSize(100d, 75d);
		hard.setTranslateY(160d);
		
		hard.setOnAction(e -> SceneManager.get().changeScene(SceneManager.SceneType.HARD_TEST));
		
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
		back.setOnAction(e -> SceneManager.get().changeScene(SceneManager.SceneType.START));
		
		this.getChildren().add(label);
		this.getChildren().add(easy); 
		this.getChildren().add(hard);
		this.getChildren().add(back);
		
	}
}
