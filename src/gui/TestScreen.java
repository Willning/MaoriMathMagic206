package gui;

import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import javafx.geometry.Pos;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import testing.TestConductor;

//@@TODO add a try again mode
/**
 * The main pane for testing the user.
 * This pane is to be generated every time a new question needs to be asked, 
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

	// Media control buttons
	private Button _recordButton;
	private Button _playButton;

	// Question control buttons
	private Button _checkAnswerButton;
	private Button _nextQuestionButton;
	private Button _tryAgainButton;

	// State variables
	private ListMode _mode;
	private Integer _number;
	private int _questionNumber = 1;
	private Integer numCorrect = 0;
	private boolean answered;
	private boolean firstTry;

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

		// Second row of buttons
		TilePane bottomButtonBox = new TilePane();
		bottomButtonBox.getStyleClass().add("button-pane");
		bottomButtonBox.setAlignment(Pos.CENTER);

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
		_questionNumberLabel.setText(String.format("Question #%s", _questionNumber));
		
		// The correctness label displays whether the answer given was correct or not.
		_correctnessLabel = new Label();

		// Tracks the number of correct answers.
		_correctAnswersLabel = new Label();
		_correctAnswersLabel.setText("Correct Answers: 0");
		
		// The status label tracks the state of the state machine in this class.
		_statusLabel = new Label();

		// Initiate the recording. This button is disabled for the duration of recording.
		_recordButton = new Button("⏺ RECORD");
		_recordButton.getStyleClass().add("large-button");
		_recordButton.getStyleClass().add("red");
		_recordButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		_recordButton.setOnAction(e -> tester.record());

		// Plays the recording
		_playButton = new Button("▶️ PLAY");
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
		_checkAnswerButton = new Button("Check Answer");
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
		_tryAgainButton.setDisable(true);
		_tryAgainButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		_tryAgainButton.setOnAction(e -> this.secondTryReset());

		// Button to go to the next question. If 
		_nextQuestionButton = new Button("Next Question →");
		_nextQuestionButton.getStyleClass().add("blue");
		_nextQuestionButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		_nextQuestionButton.setOnAction(e -> {
			if (_questionNumber < 10) {
				this.reset();
				
				_questionNumber++;
				_questionNumberLabel.setText(String.format("Question #%s", _questionNumber));

				if (_questionNumber == 10) {
					_nextQuestionButton.setText("Finish Quiz");
				}
			}
			else {
				ScreenManager.get().changeScreen(new ScoreScreen(_mode, numCorrect));
			}
		});

		// Returns to the main menu.
		Button back = new Button("Quit");
		back.setOnAction(e -> ScreenManager.get().changeScreen(ScreenManager.ScreenType.MAIN_MENU));

		topButtonBox.getChildren().addAll(
			_recordButton, 
			_playButton
		);

		bottomButtonBox.getChildren().addAll(
			_checkAnswerButton, 
			_tryAgainButton,
			_nextQuestionButton 
		);

		layout.getChildren().addAll(
			_correctAnswersLabel,
			_questionNumberLabel,
			_correctnessLabel,
			_statusLabel,
			numberPane, 
			topButtonBox, 
			bottomButtonBox, 
			back
		);
		
		this.getChildren().add(layout);

		reset();
	}

	/**
	 * Reset to a new starting position.
	 */
	public void reset() {
		Random randomGenerator = new Random();
		
		// Depending on the state of 'mode', the number attached to this screen
		// will have a different range
		if (_mode.equals(ListMode.EASY)) {
			_number = randomGenerator.nextInt(8) + 1;
		}
		else if (_mode.equals(ListMode.HARD)) {
			_number = randomGenerator.nextInt(98) + 1;
		}

		answered = false;
		firstTry = true;
		
		_recordButton.setDisable(false);
		_playButton.setDisable(true);
		_checkAnswerButton.setDisable(true);
		_nextQuestionButton.setDisable(true);
		_tryAgainButton.setDisable(true);

		_circle.setFill(Paint.valueOf("#2366d1"));
		_numberLabel.setText(_number.toString());
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
		
		_recordButton.setDisable(false);
		_playButton.setDisable(true);
		_checkAnswerButton.setDisable(true);
		_nextQuestionButton.setDisable(true);
		_tryAgainButton.setDisable(true);

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

			numCorrect++;
			String output = String.format("Correct Answers: %d", numCorrect);			

			_recordButton.setDisable(true);
			_checkAnswerButton.setDisable(true);
			_nextQuestionButton.setDisable(false);

			_correctAnswersLabel.setText(output);
			_correctnessLabel.setText("Correct");
			_correctnessLabel.setVisible(true);
			_circle.setFill(Paint.valueOf("#20bc56"));
		}
		else if (recorded.equals("Incorrect")) {
			answered = true;
			
			_recordButton.setDisable(true);
			_checkAnswerButton.setDisable(true);
			_nextQuestionButton.setDisable(false);
			if (firstTry) {	
				// Enable the 'try again' button if this is the end of the first attempt.
				_tryAgainButton.setDisable(false);
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

			_statusLabel.setText("Playing...");
			_statusLabel.setVisible(true);
		}
		else if (recorded.equals("EndPlay")) {
			_statusLabel.setVisible(false);			
			_playButton.setDisable(false);

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