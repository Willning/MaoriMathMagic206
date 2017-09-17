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
	//nest this in a parent pane that keeps the score?
	//TODO create an exit button to take us back to the list selection menu
	//TODO maybe make this so an enum controls which mode this constructs in 
	
	private Stage _stage;
	private ListMode _mode;
	private Integer _number;

	public TestPane(Stage stage, ListMode mode){
		super();

		_stage=stage;
		_mode=mode;
		//Have a parameter to decide whether it was the hard mode or the easy mode

		Random randomGenerator=new Random();
		//Random number generator class to generate numbers.

		if (_mode.equals(ListMode.EASY)){
			_number=randomGenerator.nextInt(10);
		}else if(_mode.equals(ListMode.HARD)){
			_number=randomGenerator.nextInt(100);
		}
		//Depending on the enum, the number attached to this screen will have different range

		//Generated number.

		Label label=new Label();
		label.setText(_number.toString());
		label.setScaleX(5);
		label.setScaleY(5);

		Button record=new Button();
		record.setText("Record");
		record.setPrefSize(150d, 75d);
		record.setTranslateY(100d);
		
		Button commitAnswer=new Button();
		commitAnswer.setText("Commit Answer");
		commitAnswer.setPrefSize(150d, 75d);
		commitAnswer.setTranslateY(180d);
		
		commitAnswer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//either create a new testPane keeping the score and difficulty enum or reset the number
				_stage.setScene(new Scene(new TestPane(_stage, _mode),FrameConstants.WINDOW_WIDTH ,FrameConstants.WINDOW_HEIGHT));

			}
		});

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
		this.getChildren().add(commitAnswer);
		this.getChildren().add(back);
		this.getChildren().add(label);
	}

}
