package proj.Kape.Kapehan.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import proj.Kape.Kapehan.service.AuthService;
public class LoginController {
	private final AuthService authService = new AuthService();
	private static Stage stage;
	
	@FXML TextField usernameField;
	@FXML TextField passwordField;
	@FXML Button loginBTN;
	@FXML Button registerBTN;
	@FXML Label errorLabel;
	
	@FXML
	private void initialize() {
		errorLabel.setText(null);
	}
	@FXML
    private void nextField(ActionEvent event) {
        passwordField.requestFocus();
    }
	
	public void setStage(Stage stage) {
        this.stage = stage;
    }
	
	private void loadDashboard() {
		try {
			FXMLLoader dashboard = new FXMLLoader(getClass().getResource("/scenes/Dashboard.fxml"));
			Parent newScene = dashboard.load();
			Scene scene = new Scene(newScene, 1280, 720);
			if (stage != null) {
                stage.setScene(scene);
            } else {
                System.out.println("❌ Stage is null when switching scene");
            }
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleLogin() {
		try {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                showError("Fields cannot be empty!");
                return;
            }

            if (authService.login(username, password)) {
                System.out.println("✅ Login Successful! Redirecting...");
                int accountId = authService.getAccountId(username);
                String role = authService.getRole(username);
                loadDashboard();
            } else {
                showError("Invalid username or password.");
            }
        } catch (Exception e) {
            e.printStackTrace(); 
            showError("An unexpected error occurred.");
        }
	}
	
	private void showError(String message) {
        errorLabel.setText("⚠️ " + message);
    }
}
