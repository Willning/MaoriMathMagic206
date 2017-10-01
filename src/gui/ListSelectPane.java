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
public class ListSelectPane extends StackPane {
		
	public ListSelectPane() {
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
		easy.setText("Easy List");
		easy.setOnAction(e -> SceneManager.get().changeScene(SceneManager.SceneType.EASY_TEST));
		
		// Button for hard List (1-100)
		Button hard = new Button();
		hard.getStyleClass().add("large-button");
		hard.setText("Hard List");
		hard.setOnAction(e -> SceneManager.get().changeScene(SceneManager.SceneType.HARD_TEST));
		
		Label title = new Label();
		title.getStyleClass().add("subheading");
		title.setText("Select Difficulty List");
		
		// Back button will bring us back to the start menu 
		Button back = new Button();
		back.setText("Back");
		back.setOnAction(e -> SceneManager.get().changeScene(SceneManager.SceneType.START));
		
		subLayout.getChildren().addAll(easy, hard);
		layout.getChildren().addAll(title, subLayout, back);
		this.getChildren().add(layout);	
	}
}
