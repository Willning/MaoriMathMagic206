package gui;

import java.io.File;
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


/**
 * This pane is to be generated every time a new question needs to be asked, generates a number and contains 
 * buttons to do relevant actions.
 */

//@@TODO add a try again mode

public class TestPane extends StackPane implements Observer {

	private Stage _stage;
	private ListMode _mode;
	private Integer _number;
	private Label _label;

	private Label _qLabel;

	private Label _correctness;
	private Label _status;
	private Label _correctAnswers;

	private Button _record;
	private Button _commitAnswer;
	private Button _play;
	private Button _next;
	private Button _tryAgain;

	private int BUTTON_WIDTH = 160;
	private int BUTTON_HEIGHT=30;

	//_playing is a state variable, other actions are locked if _playing is true.

	private int _questionNumber=1;
	private Integer numCorrect=0;

	private boolean answered;
	private boolean firstTry;


	public TestPane(Stage stage, ListMode mode) {
		super();

		TestConductor tester = new TestConductor();

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
		_play=new Button();
		_next = new Button();
		_tryAgain=new Button();

		_label = new Label();
		_qLabel=new Label();
		_correctness = new Label();
		_correctAnswers = new Label();
		_status=new Label();


		_correctAnswers.setText("Correct Answers: 0");
		_correctAnswers.setScaleX(2);
		_correctAnswers.setScaleY(2);
		_correctAnswers.setTranslateY(-250d);


		_qLabel.setText(String.format("Question #%s", _questionNumber));
		_qLabel.setTranslateY(-140);
		_qLabel.setScaleX(1.5);
		_qLabel.setScaleY(1.5);


		_correctness.setTranslateY(-60d);
		_status.setTranslateY(-80d);



		_record.setText("Record");
		_record.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);


		// Disable for duraion of recording
		_record.setOnAction(e -> tester.record());

		_commitAnswer.setText("Check Answer");
		_commitAnswer.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		_commitAnswer.setDisable(true);

		_commitAnswer.setOnAction(a -> {
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


		_next.setText("Next Question");
		_next.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);		
		_next.setOnAction(e ->{
			if (_questionNumber <= 10){
				this.reset();
				
				_questionNumber++;
				_qLabel.setText(String.format("Question #%s", _questionNumber));

				if(_questionNumber == 10) {
					_next.setText("Finish quiz");
				}
			}else{				
				_stage.setScene(new Scene(new ScoreScreen(_stage, _mode, numCorrect),FrameConstants.WINDOW_WIDTH,FrameConstants.WINDOW_HEIGHT));
			}
		});


		_play.setText("Play Recording");
		_play.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		_play.setDisable(true);

		_play.setOnAction(e -> {
			File playThis=new File("./temp/foo.wav");
			if (playThis.exists()) {
				tester.play();
			}

		});

		_tryAgain.setText("Try Again?");
		_tryAgain.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		_tryAgain.setVisible(false);

		_tryAgain.setOnAction(e -> {
			this.secondTryReset();
		});

		Button back = new Button();		
		back.setText("Exit");
		back.setPrefSize(75d, 30d);
		back.setTranslateY(270d);
		back.setOnAction(e -> SceneManager.get().changeScene(SceneManager.SceneType.LIST));

		// Fix alignment of ButtonBox		
		buttonBox.getChildren().add(_record);
		buttonBox.getChildren().add(_commitAnswer);
		buttonBox.getChildren().add(_play);
		buttonBox.getChildren().add(_next);
		buttonBox.getChildren().add(_tryAgain);


		// Put this into a HBox, then put the HBox into the buttonBox		
		this.getChildren().add(buttonBox);
		this.getChildren().add(back);
		this.getChildren().add(_correctness);
		this.getChildren().add(_status);
		this.getChildren().add(_correctAnswers);
		this.getChildren().add(_label);
		this.getChildren().add(_qLabel);

		reset();
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
		answered=false;
		firstTry=true;

		_label.setText(_number.toString());
		_label.setScaleX(5);
		_label.setScaleY(5);
		
		_commitAnswer.setDisable(true);
		_next.setDisable(true);
		_play.setDisable(true);
		_record.setDisable(false);
		_correctness.setVisible(false);
		_status.setVisible(false);
		_tryAgain.setVisible(false);

	}

	/**
	 * This is the reset used when we want to try the same question again. This will reset the buttons but will keep the label.
	 * Can only be done once per question.
	 */

	public void secondTryReset(){

		answered=false;
		firstTry=false;
		//lock all the buttons into the right state.		
		_commitAnswer.setDisable(true);
		_next.setDisable(true);
		_play.setDisable(true);
		_record.setDisable(false);
		_correctness.setVisible(false);
		_status.setVisible(false);
		_tryAgain.setVisible(false);

		//label, doesn't change
	}


	@Override
	public void update(Observable arg0, Object recorded) {
		if (recorded =="endRecord") {
			_record.setDisable(false);
			_commitAnswer.setDisable(false);
			_play.setDisable(false);
			_status.setVisible(false);
		}
		else if(recorded == "beginRecord") {
			_record.setDisable(true);
			_commitAnswer.setDisable(true);
			_play.setDisable(true);
			_status.setText("Recording...");
			_status.setVisible(true);
		}
		else if (recorded == "Correct"){
			answered=true;

			numCorrect++;
			String output=String.format("Correct Answers: %d", numCorrect);			
			_correctAnswers.setText(output);

			_correctness.setText("Correct");
			_correctness.setVisible(true);
			_record.setDisable(true);
			_commitAnswer.setDisable(true);
			_next.setDisable(false);


		}
		else if (recorded == "Incorrect"){
			answered=true;

			_correctness.setText("Incorrect");
			_correctness.setVisible(true);
			_record.setDisable(true);
			_commitAnswer.setDisable(true);
			_next.setDisable(false);

			if (firstTry){	
				//add in a try again button which appears when this event occurs.
				_tryAgain.setVisible(true);

			}

		}
		else if (recorded == "BeginPlay") {
			_status.setText("Playing...");
			_status.setVisible(true);
			_record.setDisable(true);
			_commitAnswer.setDisable(true);
			_play.setDisable(true);

			if (!_next.isDisabled()){
				_next.setDisable(true);
			}
		}
		else if (recorded == "EndPlay"){
			_status.setVisible(false);			
			_play.setDisable(false);

			if (answered){
				//unlock the next button only if the questions has been answered
				_next.setDisable(false);
			}else {
				//unlock recorind gand checking if unanswered
				_record.setDisable(false);
				_commitAnswer.setDisable(false);
			}
		}
	}

}