package gui;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import testing.TestConductor;

//@@TODO: add a correct/incorrect indicator
//@@TODO: add a stage after answering to show correct/incorrect;

//@@TODO reorganize buttons so that things look nicer

/**
 * This pane is to be generated every time a new question needs to be asked, generates a number and contains 
 * buttons to do relevant actions 
 */
public class TestPane extends StackPane implements Observer {

	private Stage _stage;
	private ListMode _mode;
	private Integer _number;
	private Label _label;
	
	private Label _correctness;
	
	private Button _record;
	private Button _commitAnswer;
	
	private int BUTTON_WIDTH = 160;

	public TestPane(Stage stage, ListMode mode, TestConductor tester) {
		super();
		
		VBox buttonBox = new VBox();
		//Use this to properly pad all the buttons
	   
	    buttonBox.setSpacing(10);
	    buttonBox.setTranslateY(340d);
	    buttonBox.setTranslateX(FrameConstants.WINDOW_WIDTH/2-BUTTON_WIDTH/2);
		
		HBox smallerBox = new HBox();	    
	    smallerBox.setSpacing(10);
	    
	    tester.addObserver(this);

		_stage = stage;
		_mode = mode;
		_commitAnswer = new Button();
		_record = new Button();
		_label = new Label();
		_correctness = new Label();
						
		_correctness.setTranslateY(-40d);
		reset();

		_label.setScaleX(5);
		_label.setScaleY(5);
		
		
		
		_record.setText("Record");
		_record.setPrefSize(BUTTON_WIDTH, 75d);
		
		
		// Disable for duraion of recording
		_record.setOnAction(e -> tester.record());
		
		_commitAnswer.setText("Commit Answer");
		_commitAnswer.setPrefSize(BUTTON_WIDTH, 75d);
		_commitAnswer.setDisable(true);
		
		_commitAnswer.setOnAction(a -> {
			// Either create a new testPane, keeping the score and difficulty enum,
			// or reset the number.
			// Call to a tester class which will fire back a correct/incorrect event.
			try {
				tester.test(_number);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		Button skip = new Button();
		skip.setText("Skip");
		skip.setPrefSize(75d, 30d);		
		skip.setOnAction(e -> tester.skip());
		
		Button play = new Button();
		play.setText("Play");
		play.setPrefSize(75d, 30d);

		Button back = new Button();		
		back.setText("Exit");
		back.setPrefSize(BUTTON_WIDTH, 30d);

		back.setOnAction(e -> {
			// Back button will bring us back to the start menu 
			_stage.setScene(new Scene(
				new ListSelectPane(_stage), 
				FrameConstants.WINDOW_WIDTH, 
				FrameConstants.WINDOW_HEIGHT
			));
			_stage.sizeToScene();
		});
		
		smallerBox.getChildren().add(play);
		smallerBox.getChildren().add(skip);
		
		// Fix alignment of ButtonBox		
		buttonBox.getChildren().add(_record);
		buttonBox.getChildren().add(_commitAnswer);
		buttonBox.getChildren().add(smallerBox);
		buttonBox.getChildren().add(back);

		// Put this into a HBox, then put the HBox into the buttonBox		
		this.getChildren().add(buttonBox);	
		this.getChildren().add(_correctness);
		this.getChildren().add(_label);
	}
	
	/**
	 * Reset to a new starting position.
	 */
	public void reset() {
		// Random number generator class to generate numbers.
		Random randomGenerator = new Random();
		
		// Depending on the enum, the number attached to this screen will have a different range
		if (_mode.equals(ListMode.EASY)) {
			_number = randomGenerator.nextInt(8) + 1;
		}
		else if (_mode.equals(ListMode.HARD)) {
			_number = randomGenerator.nextInt(98) + 1;
		}
		
		// Number is in the centre.		
		_label.setText(_number.toString());
		_commitAnswer.setDisable(true);
		_correctness.setVisible(false);
	}

	@Override
	public void update(Observable arg0, Object recorded) {
		if (recorded =="endRecord") {
			_record.setDisable(false);
			_commitAnswer.setDisable(false);
		}
		else if(recorded == "beginRecord") {
			_record.setDisable(true);
			_commitAnswer.setDisable(true);
		}
		else if (recorded == "Correct"){
			_correctness.setText("Correct");
			_correctness.setVisible(true);			
			
		}
		else if (recorded == "Incorrect"){
			
			_correctness.setText("Incorrect");
			_correctness.setVisible(true);		
			
		}
	}

}
