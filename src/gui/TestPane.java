package gui;

import java.util.Random;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TestPane extends StackPane{
	//this pane is to be generated every time 
	
	//TODO create an exit button to take us back to the list selection menu
	//TODO maybe make this so an enum controls which mode this constructs in 
	private Stage _stage;
	
	public TestPane(Stage stage){
		super();
		
		_stage=stage;
		//Have a parameter to decide whether it was the hard mode or the easy mode
		
		Random randomGenerator=new Random();
		//Random number generator class to generate numbers.
		
		Integer number=randomGenerator.nextInt(10);	
		
		//Generated number.
		
		Label label=new Label();
		label.setText(number.toString());
		label.setScaleX(5);
		label.setScaleY(5);
		
		Button record=new Button();
		record.setText("Record");
		record.setPrefSize(100d, 75d);
		record.setTranslateY(100d);
		
		Button back = new Button();		
		back.setText("Back");
		back.setPrefSize(75d, 30d);
		back.setTranslateY(270d);
		
		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//back button will bring us back to the start menu 
			_stage.setScene(new Scene(new ListSelectPane(_stage), FrameConstants.WINDOW_WIDTH ,FrameConstants.WINDOW_HEIGHT));
			_stage.sizeToScene();
			
			}
		});
		
		this.getChildren().add(record);
		this.getChildren().add(back);
		this.getChildren().add(label);
	}

}
