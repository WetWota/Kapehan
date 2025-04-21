package proj.Kape.Kapehan.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SceneManager {
    private static Stage primaryStage;

    // ✅ Set the stage
    public static void setStage(Stage stage) {
        primaryStage = stage;
    }

    // ✅ Switch scenes by replacing the root
    public static void switchDashboard() {
        try {
        	System.out.println("Trying to load: " + "/scenes/Dashboard.fxml");
            FXMLLoader sceneLoader = new FXMLLoader(SceneManager.class.getResource("/scenes//Dashboard.fxml"));
            Parent root = sceneLoader.load();
            
            if (primaryStage != null) {
            	Scene newScene = new Scene(root, 1366, 728);
                primaryStage.setScene(newScene);
            } else {
                System.out.println("❌ Stage is null when switching Dashboard");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("❌ Failed to switch to Dashboard");
        }
    }
    
    public static void switchLogin() {
        try {
        	System.out.println("Trying to load: " + "/scenes/Login.fxml");
            FXMLLoader sceneLoader = new FXMLLoader(SceneManager.class.getResource("/scenes/Login.fxml"));
            Parent root = sceneLoader.load();
            
            if (primaryStage != null) {
            	Scene newScene = new Scene(root, 600, 400);
                primaryStage.setScene(newScene);
            } else {
                System.out.println("❌ Stage is null when switching to Login");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("❌ Failed to switch Login");
        }
    }
    
    public static void switchSignup() {
        try {
        	System.out.println("Trying to load: " + "/scenes/Signup.fxml");
            FXMLLoader sceneLoader = new FXMLLoader(SceneManager.class.getResource("/scenes/Signup.fxml"));
            Parent root = sceneLoader.load();
            
            if (primaryStage != null) {
            	Scene newScene = new Scene(root, 600, 400);
                primaryStage.setScene(newScene);
            } else {
                System.out.println("❌ Stage is null when switching to Login");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("❌ Failed to switch Login");
        }
    }
}
