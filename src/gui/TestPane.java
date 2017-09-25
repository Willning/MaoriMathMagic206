package gui;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
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

public class TestPane extends StackPane implements Observer{
	//this pane is to be generated every time 
	//nest this in a parent pane that keeps the score?	
	//TODO add media here to play recording
	//TODO add a correct/incorrect indicator
	 
	
	private Stage _stage;
	private ListMode _mode;
	private Integer _number;
	private Label _label;
	
	private Button _record;
	private Button _commitAnswer;
	
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
	    
	    tester.addObserver(this);
			

		_stage=stage;
		_mode=mode;
		
		_commitAnswer=new Button();
		_record=new Button();
		
		_label=new Label();
		//Have a parameter to decide whether it was the hard mode or the easy mode
		reset();

		_label.setScaleX(5);
		_label.setScaleY(5);
		
		_record.setText("Record");
		_record.setPrefSize(BUTTON_WIDTH, 75d);
		
		
		_record.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){

				//disable for duraion of recording
				tester.record();
				
			}
		});
		
		
		_commitAnswer.setText("Commit Answer");
		_commitAnswer.setPrefSize(BUTTON_WIDTH, 75d);
		_commitAnswer.setDisable(true);
		
		
		_commitAnswer.setOnAction(new EventHandler<ActionEvent>() {
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
		
		
		buttonBox.getChildren().add(_record);
		buttonBox.getChildren().add(_commitAnswer);
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
		
		//commit is disabled by default, there has to be a recording done to commit

		//Number is in the centre.		
		_label.setText(_number.toString());
		_commitAnswer.setDisable(true);
		
	}

	@Override
	public void update(Observable arg0, Object recorded) {
		if (recorded=="endRecord"){
			_record.setDisable(false);
			_commitAnswer.setDisable(false);
		}else if(recorded=="beginRecord"){
			_record.setDisable(true);
			_commitAnswer.setDisable(true);
		}
		
	}

}
