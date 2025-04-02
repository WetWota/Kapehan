package proj.Kape.Kapehan.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.io.IOException;

public class SceneManager {
    private static Stage primaryStage;

    // ✅ Set the stage
    public static void setStage(Stage stage) {
        primaryStage = stage;
    }

    // ✅ Switch scenes by replacing the root
    public static void switchScene(String name) {
        try {
        	System.out.println("Trying to load: " + "/scene/" + name + ".fxml");
            FXMLLoader sceneLoader = new FXMLLoader(SceneManager.class.getResource("/scene/" + name + ".fxml"));
            Parent newScene = sceneLoader.load();
            
            if (primaryStage != null) {
                primaryStage.getScene().setRoot(newScene);
            } else {
                System.out.println("❌ Stage is null when switching scene: " + name);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("❌ Failed to switch scene: " + name);
        }
    }

}
