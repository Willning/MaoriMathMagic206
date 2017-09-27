package gui;

import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

<<<<<<< HEAD

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
=======
>>>>>>> branch
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import testing.TestConductor;

//@@TODO: add a correct/incorrect indicator
//@@TODO: add a stage after answering to show correct/incorrect;

//@@TODO reorganize buttons so that things look nicer, 3 main buttons, back in the corner. Skip becomes next and only visible when answer is committed

/**
 * This pane is to be generated every time a new question needs to be asked, generates a number and contains 
 * buttons to do relevant actions 
 */
public class TestPane extends StackPane implements Observer {

	private Stage _stage;
	private ListMode _mode;
	private Integer _number;
	private Label _label;

<<<<<<< HEAD
	private Button _record;
	private Button _commitAnswer;
	private Boolean _playing=false;
=======
	private Label _correctness;

	private Button _record;
	private Button _commitAnswer;
	private Button _play;
	private Button _next;
>>>>>>> branch

	private int BUTTON_WIDTH = 160;
	private int BUTTON_HEIGHT=30;
	
	//_playing is a state variable, other actions are locked if _playing is true.
	private boolean _playing;

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
		_play=new Button();
		_label = new Label();
		_correctness = new Label();

<<<<<<< HEAD
		reset();

		_label.setScaleX(5);
		_label.setScaleY(5);

		_record.setText("Record");
		_record.setPrefSize(BUTTON_WIDTH, 75d);
=======
		_correctness.setTranslateY(-40d);
		


		_record.setText("Record");
		_record.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
>>>>>>> branch


		// Disable for duraion of recording
		_record.setOnAction(e -> tester.record());

<<<<<<< HEAD
		_commitAnswer.setText("Commit Answer");
		_commitAnswer.setPrefSize(BUTTON_WIDTH, 75d);
=======
		_commitAnswer.setText("Check Answer");
		_commitAnswer.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
>>>>>>> branch
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

<<<<<<< HEAD
		Button skip = new Button();
		skip.setText("Skip");
		skip.setPrefSize(75d, 30d);		
		skip.setOnAction(e -> tester.skip());

		Button play = new Button();
		play.setText("Play");
		play.setPrefSize(75d, 30d);

		play.setOnAction(e -> {
			//TODO, if not 

			File playThis=new File("./temp/foo.wav");
			if (playThis.exists()&&_playing==false) {
				System.out.println("ok");
				Media sound=new Media(playThis.toURI().toString());
				MediaPlayer player=new MediaPlayer(sound);
				player.play();
				_playing=true;
				
				player.setOnEndOfMedia(new Runnable(){
					@Override
					public void run(){
						_playing=false;
					}
				});
			}
		});

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
			this.getChildren().add(_label);

=======
		_next = new Button();
		_next.setText("Next Question");
		_next.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);		
		_next.setOnAction(e -> this.reset());
		_next.setDisable(true);

		_play = new Button();
		_play.setText("Play Recording");
		_play.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		_play.setDisable(true);

		_play.setOnAction(e -> {
			File playThis=new File("./temp/foo.wav");
			if (playThis.exists()&&_playing==false) {
				System.out.println("ok");
				Media sound=new Media(playThis.toURI().toString());
				MediaPlayer player=new MediaPlayer(sound);
				player.play();
				_playing=true;

				player.setOnEndOfMedia(new Runnable(){
					@Override
					public void run(){
						_playing=false;
					}
				});
			}
		});
		

		Button back = new Button();		
		back.setText("Exit");
		back.setPrefSize(75d, 30d);
		back.setTranslateY(270d);


		back.setOnAction(e -> {
			// Back button will bring us back to the start menu 
			_stage.setScene(new Scene(
					new ListSelectPane(_stage), 
					FrameConstants.WINDOW_WIDTH, 
					FrameConstants.WINDOW_HEIGHT
					));
			_stage.sizeToScene();
		});



		// Fix alignment of ButtonBox		
		buttonBox.getChildren().add(_record);
		buttonBox.getChildren().add(_commitAnswer);
		buttonBox.getChildren().add(_play);
		buttonBox.getChildren().add(_next);


		// Put this into a HBox, then put the HBox into the buttonBox		
		this.getChildren().add(buttonBox);
		this.getChildren().add(back);

		this.getChildren().add(_correctness);
		this.getChildren().add(_label);
		
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
		
		_label.setText(_number.toString());
		_label.setScaleX(5);
		_label.setScaleY(5);
		_commitAnswer.setDisable(true);
		_next.setDisable(true);
		_play.setDisable(true);
		_record.setDisable(false);
		_correctness.setVisible(false);

	}

	/*
	 * Use this to transition between answering screen and marked Screen. marking will create a new button called next question,
	 * Mark should also show an indicator as to whether the answer given is correct or wrong
	 */

	public void mark(boolean correct){
		if (correct){

		}

	}

	@Override
	public void update(Observable arg0, Object recorded) {
		if (recorded =="endRecord") {
			_record.setDisable(false);
			_commitAnswer.setDisable(false);
			_play.setDisable(false);
>>>>>>> branch
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
			_play.setDisable(true);
		}
		else if (recorded == "Correct"){
			_correctness.setText("Correct");
			_correctness.setVisible(true);
			_record.setDisable(true);
			_commitAnswer.setDisable(true);
			_next.setDisable(false);

		}
		else if (recorded == "Incorrect"){

			_correctness.setText("Incorrect");
			_correctness.setVisible(true);
			_record.setDisable(true);
			_commitAnswer.setDisable(true);
			_next.setDisable(false);

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
		}

	}
