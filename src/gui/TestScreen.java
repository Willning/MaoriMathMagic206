package gui;

import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.Random;

import javafx.geometry.Pos;
import javafx.scene.paint.Paint;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import questionGeneration.QuestionMaker;
import testing.GameList;
import testing.Question;
import testing.TestConductor;

//@@TODO every time answered, pipe the number to a stats class
/**
 * The main pane for the practise mode of naming maori numbers 
 * generates a number and contains buttons to do relevant actions.
 */
public class TestScreen extends StackPane implements Observer {

	
	// Labels
	private Circle _circle;
	private Label _numberLabel;
	private Label _questionNumberLabel;
	private Label _correctnessLabel;
	private Label _statusLabel;
	private Label _correctAnswersLabel;

	//Pre-Answer Phase Buttons
	// Media control buttons
	private Button _recordButton;
	private Button _playButton;
	// Question control buttons
	private Button _checkAnswerButton;

	// Post Answer control buttons
	private Button _nextQuestionButton;
	private Button _tryAgainButton;

	// State variables
	private ListMode _mode;
	private Integer _number;
	private int _questionNumber = 1;
	private Integer numCorrect = 0;
	private boolean answered;
	private boolean firstTry;
	private boolean correct;
	
	//QuestionList
	private GameList _questionList = new GameList();

	public TestScreen(ListMode mode) {
		super();

		_mode = mode;

		// Create the test conductor, which will actually carry out the functions to check
		// whether the word that the user has said is correct. 
		TestConductor tester = new TestConductor();
		tester.addObserver(this);

		// Main layout
		VBox layout = new VBox();
		layout.getStyleClass().add("vbox");
		layout.setAlignment(Pos.CENTER);

		// First row of buttons
		TilePane topButtonBox = new TilePane();
		topButtonBox.getStyleClass().add("button-pane");
		topButtonBox.getStyleClass().add("spaced");
		topButtonBox.setAlignment(Pos.CENTER);

		// Second row of buttons, trying a vertical stack for next and retry so there isn't asymmetry
		VBox bottomButtonBox = new VBox();
		bottomButtonBox.getStyleClass().add("vbox");
		bottomButtonBox.setAlignment(Pos.CENTER);
		bottomButtonBox.setMaxWidth(150);

		// The number label is the main label displaying the current number.
		_numberLabel = new Label();
		_numberLabel.getStyleClass().add("number");

		_circle = new Circle();
		_circle.setFill(Paint.valueOf("#2366d1"));
		_circle.setRadius(_numberLabel.getLayoutBounds().getWidth() / 2 + 100);

		StackPane numberPane = new StackPane(); 
		numberPane.getChildren().addAll(_circle, _numberLabel);

		// The question number label displays how many questions have been completed.
		_questionNumberLabel = new Label();
		_questionNumberLabel.getStyleClass().add("subheading");
		_questionNumberLabel.setText(String.format("Question %s", _questionNumber));

		// The correctness label displays whether the answer given was correct or not.
		_correctnessLabel = new Label();

		// Tracks the number of correct answers.
		_correctAnswersLabel = new Label();
		_correctAnswersLabel.getStyleClass().add("subsubheading");
		_correctAnswersLabel.setText("Correct Answers: 0");

		// The status label tracks the state of the state machine in this class.
		_statusLabel = new Label();

		// Initiate the recording. This button is disabled for the duration of recording.
		_recordButton = new Button("● RECORD");
		_recordButton.getStyleClass().add("large-button");
		_recordButton.getStyleClass().add("pink");
		_recordButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		_recordButton.setOnAction(e -> tester.record());

		// Plays the recording
		_playButton = new Button("▶ LISTEN");
		_playButton.getStyleClass().add("large-button");
		_playButton.getStyleClass().add("green");
		_playButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		_playButton.setDisable(true);
		_playButton.setOnAction(e -> {
			File playThis = new File("./temp/foo.wav");
			if (playThis.exists()) {
				tester.play();
			}
		});

		// Pressing the 'check answer' button calls into the tester class which will fire back 
		// a 'correct' or 'incorrect' event.
		_checkAnswerButton = new Button("✓ CHECK");
		_checkAnswerButton.getStyleClass().add("large-button");
		_checkAnswerButton.getStyleClass().add("blue");
		_checkAnswerButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		_checkAnswerButton.setDisable(true);
		_checkAnswerButton.setOnAction(a -> {
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

		// Allows the user to try a question again
		_tryAgainButton = new Button("Retry ⟲");
		_tryAgainButton.getStyleClass().add("blue");
		_tryAgainButton.setVisible(false);
		_tryAgainButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		_tryAgainButton.setOnAction(e -> this.secondTryReset());

		// Button to go to the next question. If 
		_nextQuestionButton = new Button("Next Question ➡");
		_nextQuestionButton.getStyleClass().add("blue");
		_nextQuestionButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		_nextQuestionButton.setOnAction(e -> {
			_questionList.add(new Question( _numberLabel.getText(),_number.toString(), correct));
			if (_questionNumber < 10) {
				this.reset();

				_questionNumber++;
				_questionNumberLabel.setText(String.format("Question %s", _questionNumber));

				if (_questionNumber == 10) {
					_nextQuestionButton.setText("Finish Quiz");
				}
			}
			else {
				ScreenManager.get().changeScreen(new ScoreScreen(_mode, numCorrect, _questionList));
			}
		});

		// Returns to the main menu.
		Button back = new Button("Quit");
		back.setOnAction(e -> 	quitAlert()		
				);



		topButtonBox.getChildren().addAll(
				_recordButton, 
				_playButton,
				_checkAnswerButton
				);

		bottomButtonBox.getChildren().addAll(			
				_nextQuestionButton, 
				_tryAgainButton			
				);

		layout.getChildren().addAll(				
				_questionNumberLabel,
				_correctAnswersLabel,
				_correctnessLabel,
				_statusLabel,
				numberPane, 
				topButtonBox, 
				bottomButtonBox	,
				back
				);

		this.getChildren().add(layout);		

		reset();
	}

	private void quitAlert(){
		Alert alert= new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Really Quit?");
		alert.setHeaderText("Quitting will automatically close this quiz session.");
		alert.setContentText("Do you really want to quit?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			// ... user chose OK
			ScreenManager.get().changeScreen(new ScoreScreen(_mode, numCorrect, _questionList));
		} else {
			// ... user chose CANCEL or closed the dialog
		}		


	}

	/**
	 * Reset to a new starting position.
	 */
	public void reset() {
		Random randomGenerator = new Random();
		QuestionMaker make = new QuestionMaker();
		
		correct = false;

		// Depending on the state of 'mode', the number attached to this screen
		// will have a different range
		if (_mode.equals(ListMode.EASY)) {			
			make.generateEasyDivision();
			
			_number=make.getAnswer();
			_numberLabel.setText(make.getEquation());
						
		}
		else if (_mode.equals(ListMode.HARD)) {
			make.generateEasyMultiplication();			
			_number=make.getAnswer();
			_numberLabel.setText(make.getEquation());
			
		}else if (_mode.equals(ListMode.PRACTICE)){
			_number = randomGenerator.nextInt(8) + 1;
			_numberLabel.setText(_number.toString());
		}
		
		//_numberLabel.setText(_number.toString());

		answered = false;
		firstTry = true;

		_recordButton.setVisible(true);
		_checkAnswerButton.setVisible(true);		
		_recordButton.setDisable(false);

		_playButton.setDisable(true);
		_checkAnswerButton.setDisable(true);

		_nextQuestionButton.setVisible(false);
		_tryAgainButton.setVisible(false);

		_circle.setFill(Paint.valueOf("#2366d1"));
		
		_correctnessLabel.setVisible(false);
		_statusLabel.setVisible(false);
	}

	/**
	 * This is the reset used when we want to try the same question again. 
	 * This will reset the buttons but will keep the number.
	 * Can only be done once per question.
	 */
	public void secondTryReset() {
		answered = false;
		firstTry = false;		

		_recordButton.setVisible(true);
		_checkAnswerButton.setVisible(true);


		_playButton.setDisable(true);
		_checkAnswerButton.setDisable(true);

		_nextQuestionButton.setVisible(false);
		_tryAgainButton.setVisible(false);

		_circle.setFill(Paint.valueOf("#2366d1"));
		_correctnessLabel.setVisible(false);
		_statusLabel.setVisible(false);
	}

	@Override
	public void update(Observable arg0, Object recorded) {
		if (recorded.equals("endRecord")) {
			_recordButton.setDisable(false);
			_checkAnswerButton.setDisable(false);
			_playButton.setDisable(false);

			_circle.setFill(Paint.valueOf("#2366d1"));
			_statusLabel.setVisible(false);
		}
		else if (recorded.equals("beginRecord")) {
			_recordButton.setDisable(true);
			_checkAnswerButton.setDisable(true);
			_playButton.setDisable(true);

			_circle.setFill(Paint.valueOf("#ffdb4a"));
			_statusLabel.setText("Recording...");
			_statusLabel.setVisible(true);
		}
		else if (recorded.equals("Correct")) {
			answered = true;
			correct = true;
		

			numCorrect++;
			String output = String.format("Correct Answers: %d", numCorrect);			

			//mask the unneccesary buttons instead of locking them 
			_recordButton.setVisible(false);
			_checkAnswerButton.setVisible(false);

			_nextQuestionButton.setVisible(true);
			_nextQuestionButton.setDisable(false);

			_correctAnswersLabel.setText(output);
			_correctnessLabel.setText("Correct");
			_correctnessLabel.setVisible(true);
			_circle.setFill(Paint.valueOf("#20bc56"));
			
			_numberLabel.setText(_number.toString());
		}
		else if (recorded.equals("Incorrect")) {
			answered = true;
			

			_recordButton.setVisible(false);
			_checkAnswerButton.setVisible(false);

			_nextQuestionButton.setVisible(true);
			_nextQuestionButton.setDisable(false);

			if (firstTry) {	
				// Enable the 'try again' button if this is the end of the first attempt.
				_tryAgainButton.setVisible(true);
			}else{				
				_numberLabel.setText(_number.toString());
			}
			
			_correctnessLabel.setText("Incorrect");
			_correctnessLabel.setVisible(true);
			_circle.setFill(Paint.valueOf("#ff3860"));
		}
		else if (recorded.equals("BeginPlay")) {
			_recordButton.setDisable(true);
			_playButton.setDisable(true);			
			_checkAnswerButton.setDisable(true);

			_nextQuestionButton.setDisable(true);			
			_tryAgainButton.setDisable(true);

			_statusLabel.setText("Playing...");
			_statusLabel.setVisible(true);
		}
		else if (recorded.equals("EndPlay")) {
			_statusLabel.setVisible(false);			
			_playButton.setDisable(false);

			_tryAgainButton.setDisable(false);

			if (answered) {
				// Unlock the next button only if the questions has been answered
				_nextQuestionButton.setDisable(false);
			}
			else {
				// Unlock the recording button, and unlock checking, if unanswered
				_recordButton.setDisable(false);
				_checkAnswerButton.setDisable(false);
			}
		}
	}
}