package proj.Kape.Kapehan;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import proj.Kape.Kapehan.controllers.LoginController;
import proj.Kape.Kapehan.utils.SceneManager;

public class App extends Application {
	LoginController loginController = new LoginController();
    @Override
    public void start(Stage primaryStage) {
    	SceneManager.setStage(primaryStage);
    	
    	try {
    		FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/scenes/Login.fxml"));
        	Parent root = loginLoader.load();
            Scene login = new Scene(root, 600, 400);
            primaryStage.setScene(login);
            primaryStage.show();
    	} catch (Exception e) {
    		e.printStackTrace();
            System.out.println("❌ Failed to load initial scene");
    	}
    }

    public static void main(String[] args) {
        launch(args);
    }
}