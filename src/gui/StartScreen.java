package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class StartScreen extends StackPane{
	//This is the first Screen that the user will see, the stage is created in Main and then passed between all the scenes.

	public StartScreen(Stage stage){
		super();
		Button btn = new Button();
		btn.setText("Start");
		btn.setPrefSize(100d, 75d);
		btn.setTranslateY(200d);
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
			stage.setScene(new Scene(new ListSelectPane(stage), 800 ,600));
			stage.sizeToScene();
			
			}
		});

		
		this.getChildren().add(btn); 
	}
}
