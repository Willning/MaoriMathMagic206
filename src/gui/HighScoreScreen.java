package gui;

import java.util.List;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
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
		Label highScoreTitle = new Label();
		highScoreTitle.setText("High Scores");
		highScoreTitle.setScaleX(5);
		highScoreTitle.setScaleY(5);
		highScoreTitle.setTranslateY(-250d);

		Label easyLabel = new Label();
		easyLabel.setText("Easy Mode");
		easyLabel.setScaleX(2);
		easyLabel.setScaleY(2);
		
		Label hardLabel = new Label();
		hardLabel.setText("Hard Mode");
		hardLabel.setScaleX(2);
		hardLabel.setScaleY(2);

		easyList = listAdapt(scoreManager.returnList(ListMode.EASY));
		easyList.setMaxHeight(400d);
		easyList.setMaxWidth(100d);

		hardList = listAdapt(scoreManager.returnList(ListMode.HARD));		
		hardList.setMaxHeight(400d);
		hardList.setMaxWidth(100d);
		
		VBox easyBox = new VBox();
		easyBox.setSpacing(10d);
		easyBox.getChildren().add(easyLabel);
		easyBox.getChildren().add(easyList);
		
		VBox hardBox = new VBox();
		hardBox.setSpacing(10d);
		hardBox.getChildren().add(hardLabel);
		hardBox.getChildren().add(hardList);
		hardBox.setTranslateX(700d);
		
		Button back = new Button();
		back.setText("Back");
		back.setPrefSize(200d, 100d);
		back.setOnAction(e -> SceneManager.get().changeScene(SceneManager.SceneType.START));
		
		this.getChildren().add(back);
		this.getChildren().add(easyBox);
		this.getChildren().add(hardBox);
		this.getChildren().add(highScoreTitle);
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
