package gui;

import javafx.geometry.Pos;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

//@@TODO add descriptors for what each list entails
/**
 * This pane is responsible for selecting which list will be done, 1-9 or 1-99. 
 */
public class ListSelectScreen extends StackPane {
		
	public ListSelectScreen() {
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

		// Button for Easy List(1-10)
		Button easy = new Button();
		easy.getStyleClass().add("large-button");
		easy.getStyleClass().add("green");
		easy.setText("Easy List");
		easy.setOnAction(e -> ScreenManager.get().changeScreen(ScreenManager.ScreenType.EASY_TEST));
		
		// Button for hard List (1-100)
		Button hard = new Button();
		hard.getStyleClass().add("large-button");
		hard.getStyleClass().add("red");
		hard.setText("Hard List");
		hard.setOnAction(e -> ScreenManager.get().changeScreen(ScreenManager.ScreenType.HARD_TEST));
		
		Label title = new Label();
		title.getStyleClass().add("subheading");
		title.setText("Select Difficulty List");
		
		// Back button will bring us back to the main menu 
		Button back = new Button();
		back.setText("Back");
		back.setOnAction(e -> ScreenManager.get().changeScreen(ScreenManager.ScreenType.MAIN_MENU));
		
		TilePane subRowTwo = new TilePane(Orientation.HORIZONTAL);
		subRowTwo.getStyleClass().add("button-pane");
		subRowTwo.getStyleClass().add("spaced");
		subRowTwo.setAlignment(Pos.CENTER);
		
		Button practice = new Button();
		practice.getStyleClass().add("large-button");
		practice.setText("Practice Mode");
		practice.setOnAction(e ->  ScreenManager.get().changeScreen(ScreenManager.ScreenType.PRACTICE_MODE));
		
		
		subLayout.getChildren().addAll(easy, hard);
		subRowTwo.getChildren().addAll(practice);
		layout.getChildren().addAll(title, subLayout, subRowTwo, back);
		this.getChildren().add(layout);	
	}
}
