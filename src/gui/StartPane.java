package gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * This is the first Screen that the user will see.
 * The stage is created in Main and then passed between all the scenes. 
 */
//@@TODO add an instruction screen button

public class StartPane extends StackPane {

	public StartPane(Stage stage) {
		super();

		Button btn = new Button();
		btn.setText("Start");
		btn.setPrefSize(100d, 75d);
		btn.setTranslateY(150d);
		btn.setOnAction(e -> SceneManager.get().changeScene(SceneManager.SceneType.LIST));

		Button highScore = new Button(); 
		highScore.setText("HighScores");
		highScore.setPrefSize(100d,75d);
		highScore.setTranslateY(230d);

		highScore.setOnAction(e -> SceneManager.get().changeScene(SceneManager.SceneType.HIGHSCORE));

		Label label = new Label();
		label.setText("Tāitai");
		label.setScaleX(5);
		label.setScaleY(5);

		this.getChildren().add(btn);
		this.getChildren().add(highScore);
		this.getChildren().add(label); 
	}
}
