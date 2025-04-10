package proj.Kape.Kapehan.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import proj.Kape.Kapehan.utils.SceneManager;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DashboardController {
	LoginController loginController = new LoginController();
	@FXML private Button logoutBTN;
	
	@FXML
	private void handleLogout() {
		try {
			SceneManager.switchLogin();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
