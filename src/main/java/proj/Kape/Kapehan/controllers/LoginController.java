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
import proj.Kape.Kapehan.utils.SessionManager;
public class LoginController {
	private final AuthService authService = new AuthService();
	
	@FXML TextField usernameField;
	@FXML TextField passwordField;
	@FXML Button loginBTN;
	@FXML Button registerBTN;
	@FXML Label errorLabel;
	@FXML Button bckLoginBTN;
	
	 // New fields
    private int failedAttempts = 0;
    private long lockoutStartTime = 0; // in milliseconds
    private static final int MAX_ATTEMPTS = 3;
    private static final long LOCKOUT_DURATION = 60_000; // 1 minute in milliseconds
	
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
			 // Check for lockout
            if (failedAttempts >= MAX_ATTEMPTS) {
                long timeElapsed = System.currentTimeMillis() - lockoutStartTime;
                if (timeElapsed < LOCKOUT_DURATION) {
                    long secondsLeft = (LOCKOUT_DURATION - timeElapsed) / 1000;
                    showError("Too many failed attempts. Try again in " + secondsLeft + "s.");
                    return;
                } else {
                    // Reset after timeout
                    failedAttempts = 0;
                }
            }
			
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                showError("Fields cannot be empty!");
                return;
            }

            if (authService.login(username, password)) {
                System.out.println("✅ Login Successful! Redirecting...");
                failedAttempts = 0; // reset attempts
                int accountId = authService.getAccountId(username);
                String role = authService.getRole(username);
                SessionManager.getUsername();
                loadDashboard();
            } else {
                failedAttempts++;
                if (failedAttempts >= MAX_ATTEMPTS) {
                    lockoutStartTime = System.currentTimeMillis();
                    showError("Too many failed attempts. Locked for 60 seconds.");
                } else {
                    showError("Invalid username or password. (" + failedAttempts + "/" + MAX_ATTEMPTS + ")");
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); 
            showError("An unexpected error occurred.");
        }
	}
	
	@FXML
	private void handleRegister(ActionEvent event) {
	   SceneManager.switchSignup();
	}
	
	
	
	private void showError(String message) {
        errorLabel.setText("⚠️ " + message);
    }
}
