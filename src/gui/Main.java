package gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

//@@TODO: rejig the constants to a constants class 
//@@TODO: make a media player to play the recording
/**
 * Entry point.
 */
public class Main extends Application{
	
    public static void main(String[] args) {
        launch(args);
    }

	@Override
	public void start(Stage stage) {
		stage.setTitle("Tātai");
		stage.setWidth(800);
		stage.setHeight(600);

		SceneManager.create(stage);
		SceneManager.get().changeScene(SceneManager.SceneType.START);

		stage.setOnCloseRequest(e -> {
			Platform.exit();
			System.exit(0);
        });
		
		stage.show();
	}
}




