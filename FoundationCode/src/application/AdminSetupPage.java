package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;

import databasePart1.*;

/**
 * AdminSetupPage: Setup screen for the first admin user
 */
public class AdminSetupPage {

    private final DatabaseHelper databaseHelper;

    public AdminSetupPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public void show(Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Input fields
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

        Label feedbackLabel = new Label();
        feedbackLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");

        Button setupButton = new Button("Create Admin Account");

        setupButton.setOnAction(a -> {
            String username = userNameField.getText();
            String password = passwordField.getText();
            String email = emailField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();

            // Validate inputs
            String error = validateInputs(username, password, email, firstName, lastName);
            if (!error.isEmpty()) {
                feedbackLabel.setText(error); // Immediate feedback
                return;
            }

            try {
                // Register admin in database
                User admin = new User(username, password, "admin");
                databaseHelper.register(admin);
                feedbackLabel.setStyle("-fx-text-fill: green;");
                feedbackLabel.setText("Admin account created!");
                
                // Navigate to login page
                new SetupLoginSelectionPage(databaseHelper).show(primaryStage);

            } catch (SQLException e) {
                feedbackLabel.setText("Error creating account: " + e.getMessage());
            }
        });

        layout.getChildren().addAll(userNameField, passwordField, emailField, firstNameField, lastNameField, setupButton, feedbackLabel);
        primaryStage.setScene(new Scene(layout, 600, 400));
        primaryStage.setTitle("Admin Setup");
        primaryStage.show();
    }

    // Input validation logic
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