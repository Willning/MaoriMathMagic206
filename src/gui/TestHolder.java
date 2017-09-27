package gui;


import java.util.Observable;
import java.util.Observer;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import testing.TestConductor;

/**
 * this is a pane that holds one TestPane, what this will do is keep track of the score.
 * i.e. this is the rootPane
 */
public class TestHolder extends StackPane implements Observer {

	private Integer numCorrect=0;


	private Stage _stage;
	private final ListMode _mode;
	private TestConductor _tester;

	private TestPane _testPane;

	private Label _questionNumber;

	public TestHolder(Stage stage, ListMode mode) {		
		_stage = stage;
		_mode = mode;

		_tester = new TestConductor();
		_tester.addObserver(this);

		_testPane = new TestPane(_stage, _mode);

		_questionNumber = new Label();
		_questionNumber.setText("Correct Answers: 0");

		_questionNumber.setScaleX(2);
		_questionNumber.setScaleY(2);

		//@@TODO: put this into a nice location
	
		_questionNumber.setTranslateY(-250d);

		this.getChildren().add(_questionNumber);
		this.getChildren().add(_testPane);


	}
	
	public void transition(){
		_stage.setScene(new Scene(new ScoreScreen(_stage, _mode, numCorrect),FrameConstants.WINDOW_WIDTH,FrameConstants.WINDOW_HEIGHT));
	}

	@Override
	public void update(Observable arg0, Object answer) {

		if (answer=="Correct"||answer=="Incorrect") {
						
			if (answer=="Correct"){
				numCorrect++;				

			}	
			//reset based on next button only now						
						
			String output=String.format("Correct Answers: %d", numCorrect);
			//Make aesthetic, maybe have a questions answered and questions correct label
			_questionNumber.setText(output);

		}

	}




}
