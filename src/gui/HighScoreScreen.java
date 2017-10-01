package gui;

import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import testing.HighScoreManager;

//@@TODO Have a leaderboard or just have stats
/*
 * This is the high score screen, accessed either from the start screen, or from the post game screen;
 * contains 2 ListViews
 */
public class HighScoreScreen extends StackPane {

	private ListView<Integer> easyList;
	private ListView<Integer> hardList;

	public HighScoreScreen() {
		HighScoreManager scoreManager = HighScoreManager.getInstance();
		
		BorderPane layout = new BorderPane();
	
		// Left:

		Label easyLabel = new Label("Easy Mode");
		easyLabel.getStyleClass().add("subsubheading");

		VBox easyBox = new VBox();
		easyBox.setAlignment(Pos.CENTER_LEFT);
		easyBox.getStyleClass().add("vbox-left");
		easyBox.getChildren().add(easyLabel);

		scoreManager.addScore(ListMode.EASY, 0);
		scoreManager.addScore(ListMode.EASY, 0);
		scoreManager.addScore(ListMode.EASY, 0);
		scoreManager.addScore(ListMode.EASY, 0);
		scoreManager.addScore(ListMode.EASY, 0);

		int numScores = 0;
		for (int score : scoreManager.getScores(ListMode.EASY)) {
			if (numScores >= 5) {
				break;
			}

			Button scoreLabel = new Button("" + score);
			scoreLabel.setMaxWidth(Double.MAX_VALUE);
			scoreLabel.getStyleClass().add("green");
			
			easyBox.getChildren().add(scoreLabel);
			numScores++;
		}

		// Right:

		Label hardLabel = new Label("Hard Mode");
		hardLabel.getStyleClass().add("subsubheading");

		VBox hardBox = new VBox();
		hardBox.setAlignment(Pos.CENTER_RIGHT);
		hardBox.getStyleClass().add("vbox-right");
		hardBox.getChildren().add(hardLabel);

		scoreManager.addScore(ListMode.HARD, 0);
		scoreManager.addScore(ListMode.HARD, 0);
		scoreManager.addScore(ListMode.HARD, 0);
		scoreManager.addScore(ListMode.HARD, 0);
		scoreManager.addScore(ListMode.HARD, 0);

		numScores = 0;
		for (int score : scoreManager.getScores(ListMode.HARD)) {
			if (numScores >= 5) {
				break;
			}
			
			Button scoreLabel = new Button("" + score);
			scoreLabel.setMaxWidth(Double.MAX_VALUE);
			scoreLabel.getStyleClass().add("red");
			
			hardBox.getChildren().add(scoreLabel);
			numScores++;
		}

		// Centre:

		Label highScoreTitle = new Label("High Scores");
		highScoreTitle.getStyleClass().add("subheading");

		Button back = new Button("Back");
		back.setOnAction(e -> ScreenManager.get().changeScreen(ScreenManager.ScreenType.MAIN_MENU));

		VBox middle = new VBox();
		middle.setAlignment(Pos.CENTER);
		middle.getStyleClass().add("vbox");
		middle.getChildren().addAll(highScoreTitle, back);
		
		// Layout:

		layout.setLeft(easyBox);
		layout.setRight(hardBox);
		layout.setCenter(middle);

		this.getChildren().add(layout);
	}

	public ListView<Integer> listAdapt(List<Integer> inputs) {
		ListView<Integer> outputList = new ListView<Integer>();
		if (inputs.isEmpty()) {
			outputList.getItems().add(0);			
		}
		else {
			for (Integer integer : inputs) {
				outputList.getItems().add(integer);
			}
		}

		return outputList;
	}
}
