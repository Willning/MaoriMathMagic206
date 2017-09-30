package gui;

import java.io.File;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;

/**
 * This class allows us to change and manage scenes.
 */
public class SceneManager {
    public enum SceneType {
        START,
        HARD_TEST,
        EASY_TEST,
        SCORE,
        HIGHSCORE,
        LIST
    }
    
    private static SceneManager instance = null;
    private Stage _stage;

    public SceneManager(Stage stage) {
        _stage = stage;

    }

    public static void create(Stage stage) {
        instance = new SceneManager(stage);
    }

    public static SceneManager get() {
        return instance;
    }

    public void changeScene(SceneType sceneType) {
        Parent root;
        
        switch (sceneType) {
            case START:
				root = new StartPane(_stage);
                break;

            case HARD_TEST:
				root = new TestPane(_stage, ListMode.HARD);
                break;

            case EASY_TEST:
				root = new TestPane(_stage, ListMode.EASY);
                break;

            case LIST:
				root = new ListSelectPane(_stage); 
                break;

            case HIGHSCORE:
                root = new HighScoreScreen(_stage);
                break;

            case SCORE:

            default:
                root = new StartPane(_stage);
        }

        Scene scene = new Scene(
            root,
            FrameConstants.WINDOW_WIDTH, 
            FrameConstants.WINDOW_HEIGHT
        );

        scene.getStylesheets().add(new File("src/styles.css").toURI().toString());

        _stage.setScene(scene);
    }
}