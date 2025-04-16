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
import proj.Kape.Kapehan.utils.SceneManager;
public class LoginController {
	private final AuthService authService = new AuthService();
	
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
	
	private void loadDashboard() {
		try {
			SceneManager.switchDashboard();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleLogin(ActionEvent event) {
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
	
	@FXML
	private void handleRegister(ActionEvent event) {
	    try {
	        String username = usernameField.getText().trim();
	        String password = passwordField.getText().trim();

	        if (username.isEmpty() || password.isEmpty()) {
	            showError("Fields cannot be empty!");
	            return;
	        }

	        if (authService.userExists(username)) {
	            showError("Username already exists.");
	            return;
	        }

	        boolean success = authService.registerUser(username, password,"admin");

	        if (success) {
	            errorLabel.setStyle("-fx-text-fill: green;");
	            errorLabel.setText("✅ Registration successful! You can now log in.");
	        } else {
	            showError("Registration failed. Try again.");
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
