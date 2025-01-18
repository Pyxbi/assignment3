package org.example.frontend;
/**
 * @author <Butter>
 */
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.frontend.pages.Page;



public class SceneManager {
    private static SceneManager instance;
    private StackPane currentRoot;
    private Scene currentScene;
    private Stage currentStage;

    private SceneManager() {
        currentRoot = new StackPane();

        currentScene = new Scene(currentRoot);
        currentStage = new Stage();
        currentStage.setScene(currentScene);
    }


    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }

        return instance;
    }

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void startLoading() {
        ProgressIndicator indicator = new ProgressIndicator();
        currentRoot.getChildren().add(indicator);
    }

    public void endLoading() {
        if (currentRoot.getChildren().size() <= 1) {
            // Avoid deleting root page if called wrongly
            return;
        }
        currentRoot.getChildren().remove(1);
    }

}

