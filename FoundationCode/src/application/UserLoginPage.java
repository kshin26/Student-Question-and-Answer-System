package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;

import databasePart1.*;

/**
 * The UserLoginPage class provides a login interface for users to access their accounts.
 * It validates the user's credentials and navigates to the appropriate page upon successful login.
 */
public class UserLoginPage {
	
    private final DatabaseHelper databaseHelper;

    public UserLoginPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public void show(Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        TextField userNameField = new TextField();
        userNameField.setPromptText("Username (5-20 alphanumeric)");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Label feedbackLabel = new Label();
        feedbackLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");

        Button loginButton = new Button("Login");
        loginButton.setOnAction(a -> {
            String username = userNameField.getText();
            String password = passwordField.getText();

            if (!username.matches("[a-zA-Z0-9]{5,20}")) {
                feedbackLabel.setText("Username must be 5–20 characters and letters/numbers only.");
                return;
            }
            if (password.isEmpty()) {
                feedbackLabel.setText("Please enter your password.");
                return;
            }

            try {
                User user = new User(username, password, "");
                String role = databaseHelper.getUserRole(username);
                if (role != null) {
                    user.setRole(role);
                    if (databaseHelper.login(user)) {
                        new WelcomeLoginPage(databaseHelper).show(primaryStage, user);
                    } else {
                        feedbackLabel.setText("Incorrect username or password.");
                    }
                } else {
                    feedbackLabel.setText("Account does not exist.");
                }
            } catch (SQLException e) {
                feedbackLabel.setText("Database error: " + e.getMessage());
            }
        });

        layout.getChildren().addAll(userNameField, passwordField, loginButton, feedbackLabel);

        Scene loginScene = new Scene(layout, 600, 350);
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("User Login");
        primaryStage.show();
    }
}