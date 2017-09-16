package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class StartPane extends StackPane{
	//This is the first Screen that the user will see, the stage is created in Main and then passed between all the scenes.

	public StartPane(Stage stage){
		super();
		Button btn = new Button();
		btn.setText("Start");
		btn.setPrefSize(100d, 75d);
		btn.setTranslateY(200d);
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
			stage.setScene(new Scene(new ListSelectPane(stage), FrameConstants.WINDOW_WIDTH ,FrameConstants.WINDOW_HEIGHT));
			stage.sizeToScene();
			
			}
		});
		
		Label label=new Label();
		label.setText("Maori Maths Mentor");
		
		
		label.setScaleX(5);
		label.setScaleY(5);

		this.getChildren().add(btn);
		this.getChildren().add(label); 
	}
}
