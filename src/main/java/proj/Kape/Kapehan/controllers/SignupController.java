package proj.Kape.Kapehan.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import proj.Kape.Kapehan.service.AuthService;
import proj.Kape.Kapehan.utils.SceneManager;

public class SignupController {

    private final AuthService authService = new AuthService();

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Button registerButton;
    @FXML private Button backToLoginButton;
    @FXML private Label messageLabel;

    @FXML
    private void initialize() {
        messageLabel.setText(null);
    }

    @FXML
    private void registerUser(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showMessage("Please fill in all fields.", false);
            return;
        }

        if (!password.equals(confirmPassword)) {
            showMessage("Passwords do not match.", false);
            return;
        }

        if (authService.userExists(username)) {
            showMessage("Username already taken.", false);
            return;
        }

        boolean success = authService.registerUser(username, password, "admin");
        if (success) {
            showMessage("✅ Registration successful!", true);
        } else {
            showMessage("Registration failed. Please try again.", false);
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        SceneManager.switchLogin();
    }

    private void showMessage(String message, boolean success) {
        messageLabel.setText(message);
        messageLabel.setStyle(success ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
    }
}
