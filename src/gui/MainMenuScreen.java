package gui;

import javafx.geometry.Pos;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This is the first Screen that the user will see.
 * The stage is created in Main and then passed between all the scenes. 
 */
//@@TODO add an instruction screen button

public class MainMenuScreen extends StackPane {

	public MainMenuScreen() {
		super();

		// Main layout
		VBox layout = new VBox();
		layout.getStyleClass().add("vbox");
		layout.setAlignment(Pos.CENTER);

		// Row of buttons
		TilePane subLayout = new TilePane(Orientation.HORIZONTAL);
		subLayout.getStyleClass().add("button-pane");
		subLayout.getStyleClass().add("spaced");
		subLayout.setAlignment(Pos.CENTER);

		Label title = new Label();
		title.getStyleClass().add("heading");
		title.setText("Tātai!");

		Button startButton = new Button();
		startButton.getStyleClass().add("large-button");
		startButton.setText("Start");
		startButton.setMaxWidth(Double.MAX_VALUE);
		startButton.setOnAction(e -> ScreenManager.get().changeScreen(ScreenManager.ScreenType.LIST_SELECT));

		Button highScoreButton = new Button(); 
		highScoreButton.getStyleClass().add("large-button");
		highScoreButton.setText("High Scores");
		highScoreButton.setMaxWidth(Double.MAX_VALUE);
		highScoreButton.setOnAction(e -> ScreenManager.get().changeScreen(ScreenManager.ScreenType.HIGHSCORE));

		subLayout.getChildren().addAll(startButton, highScoreButton);
		layout.getChildren().addAll(title, subLayout);
		this.getChildren().addAll(layout);
	}
}
