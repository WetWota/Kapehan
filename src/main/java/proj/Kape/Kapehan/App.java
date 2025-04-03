package proj.Kape.Kapehan;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import proj.Kape.Kapehan.controllers.LoginController;

public class App extends Application {
	LoginController loginController = new LoginController();
    @Override
    public void start(Stage stage) {
    	try {
    		loginController.setStage(stage);
    		FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/scenes/login.fxml"));
        	Parent root = loginLoader.load();
            Scene login = new Scene(root, 600, 400);
            stage.setScene(login);
            stage.setTitle("TestFX Example");
            stage.show();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    public static void main(String[] args) {
        launch(args);
    }
}