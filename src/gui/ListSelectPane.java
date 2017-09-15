package gui;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ListSelectPane extends StackPane {
//This pane is responsible for selecting which list will be done, 1-9 or 1-99. 
	public ListSelectPane(Stage primarystage){
		super();
		
		Button easy = new Button();
		easy.setText("Easy List");
		easy.setPrefSize(100d, 75d);
		easy.setTranslateY(100d);
		
		
		
		Button hard = new Button();
		hard.setText("Hard List");
		hard.setPrefSize(100d, 75d);
		hard.setTranslateY(200d);
		
		this.getChildren().add(easy); 
		this.getChildren().add(hard);
		
	}
}
