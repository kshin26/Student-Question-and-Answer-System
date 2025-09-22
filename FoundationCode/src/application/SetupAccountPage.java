package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;

import databasePart1.*;

/**
 * SetupAccountPage class handles the account setup process for new users.
 * Users provide their userName, password, and a valid invitation code to register.
 */
public class SetupAccountPage {
	
    private final DatabaseHelper databaseHelper;
    // DatabaseHelper to handle database operations.
    public SetupAccountPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    /**
     * Displays the Setup Account page in the provided stage.
     * @param primaryStage The primary stage where the scene will be displayed.
     */
    public void show(Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        TextField userNameField = new TextField();
        userNameField.setPromptText("Username (5-20 alphanumeric)");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password (≥8 chars, 1 number, 1 special)");

        TextField emailField = new TextField();
        emailField.setPromptText("Email (example@domain.com)");

        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name (letters only)");

        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last Name (letters only)");

        TextField inviteCodeField = new TextField();
        inviteCodeField.setPromptText("Invitation Code");

        Label feedbackLabel = new Label();
        feedbackLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");

        Button setupButton = new Button("Create Account");

        setupButton.setOnAction(a -> {
            String username = userNameField.getText();
            String password = passwordField.getText();
            String email = emailField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String code = inviteCodeField.getText();

            // Validate inputs
            String error = validateInputs(username, password, email, firstName, lastName);
            if (!error.isEmpty()) {
                feedbackLabel.setText(error);
                return;
            }

            try {
                if (!databaseHelper.doesUserExist(username)) {
                    if (databaseHelper.validateInvitationCode(code)) {
                        User user = new User(username, password, "user");
                        databaseHelper.register(user);
                        feedbackLabel.setStyle("-fx-text-fill: green;");
                        feedbackLabel.setText("Account created successfully!");
                        new WelcomeLoginPage(databaseHelper).show(primaryStage, user);
                    } else {
                        feedbackLabel.setText("Invalid or used invitation code.");
                    }
                } else {
                    feedbackLabel.setText("Username already taken. Please choose another.");
                }
            } catch (SQLException e) {
                feedbackLabel.setText("Database error: " + e.getMessage());
            }
        });

        layout.getChildren().addAll(userNameField, passwordField, emailField, firstNameField, lastNameField, inviteCodeField, setupButton, feedbackLabel);
        primaryStage.setScene(new Scene(layout, 600, 450));
        primaryStage.setTitle("User Account Setup");
        primaryStage.show();
    }

    private String validateInputs(String username, String password, String email, String firstName, String lastName) {
        if (!username.matches("[a-zA-Z0-9]{5,20}")) {
            return "Username must be 5–20 characters and contain only letters and numbers.";
        }
        if (!password.matches("^(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}$")) {
            return "Password must be ≥8 chars, include 1 number and 1 special character.";
        }
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            return "Please enter a valid email address.";
        }
        if (!firstName.matches("[a-zA-Z]+")) {
            return "First name must contain letters only.";
        }
        if (!lastName.matches("[a-zA-Z]+")) {
            return "Last name must contain letters only.";
        }
        return "";
    }
}