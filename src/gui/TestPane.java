package gui;

import java.io.IOException;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import testing.TestConductor;
import testing.WordCheck;

public class TestPane extends StackPane{
	//this pane is to be generated every time 
	//nest this in a parent pane that keeps the score?
	//TODO fix alignment of buttons in HBox and VBox
	//TODO add media here
	 
	
	private Stage _stage;
	private ListMode _mode;
	private Integer _number;
	private Label _label;
	
	private int BUTTON_WIDTH=160;

	public TestPane(Stage stage, ListMode mode, TestConductor tester){
		super();
		
		VBox buttonBox=new VBox();
		//Use this to properly pad all the buttons
	   
	    buttonBox.setSpacing(10);
	    
	    buttonBox.setTranslateY(340d);
	    buttonBox.setTranslateX(FrameConstants.WINDOW_WIDTH/2-BUTTON_WIDTH/2);
		
		HBox smallerBox= new HBox();	    
	    smallerBox.setSpacing(10);		
			

		_stage=stage;
		_mode=mode;
		
		_label=new Label();
		//Have a parameter to decide whether it was the hard mode or the easy mode
		reset();

		_label.setScaleX(5);
		_label.setScaleY(5);

		Button record=new Button();
		record.setText("Record");
		record.setPrefSize(BUTTON_WIDTH, 75d);
		
		
		record.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				tester.record();
				
			}
		});
		
		Button commitAnswer=new Button();
		commitAnswer.setText("Commit Answer");
		commitAnswer.setPrefSize(BUTTON_WIDTH, 75d);
		
		
		commitAnswer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//either create a new testPane keeping the score and difficulty enum or reset the number
				
				//call to a tester class which will fire back a correct/incorrect event.
				try {
					tester.test(_number);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

			}
		});
		
		Button skip=new Button();
		skip.setText("Skip");
		skip.setPrefSize(75d, 30d);
		
		skip.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				tester.skip();
			}
		});
		
		Button play=new Button();
		play.setText("Play");
		play.setPrefSize(75d, 30d);

		Button back = new Button();		
		back.setText("Exit");
		back.setPrefSize(BUTTON_WIDTH, 30d);


		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//back button will bring us back to the start menu 
				_stage.setScene(new Scene(new ListSelectPane(_stage), FrameConstants.WINDOW_WIDTH ,FrameConstants.WINDOW_HEIGHT));
				_stage.sizeToScene();

			}
		});
		
		smallerBox.getChildren().add(play);
		smallerBox.getChildren().add(skip);
		
		
		buttonBox.getChildren().add(record);
		buttonBox.getChildren().add(commitAnswer);
		buttonBox.getChildren().add(smallerBox);
		buttonBox.getChildren().add(back);
		//fix alignment of ButtonBox		


		this.getChildren().add(buttonBox);		
		
		//Put this into a HBox, then put the HBox into the buttonBox		
		
		this.getChildren().add(_label);
	}
	
	public void reset(){
		//Reset to a new starting position.
		Random randomGenerator=new Random();
		//Random number generator class to generate numbers.
		if (_mode.equals(ListMode.EASY)){
			_number=randomGenerator.nextInt(8)+1;
		}else if(_mode.equals(ListMode.HARD)){
			_number=randomGenerator.nextInt(98)+1;
		}
		
		//Depending on the enum, the number attached to this screen will have different range

		//Number is in the centre.
		_label.setText(_number.toString());
		
	}

}
