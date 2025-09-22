package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import databasePart1.DatabaseHelper;

/**
 * AdminHomePage: Simple admin page with clear navigation.
 */
public class AdminHomePage {

    private final DatabaseHelper databaseHelper;
    private final String adminUserName;

    public AdminHomePage(DatabaseHelper databaseHelper, String adminUserName) {
        this.databaseHelper = databaseHelper;
        this.adminUserName = adminUserName;
    }

    public void show(Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Label adminLabel = new Label("Hello, Admin: " + adminUserName);
        adminLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label feedbackLabel = new Label();
        feedbackLabel.setStyle("-fx-text-fill: green; -fx-font-size: 12px;");

        Button updateAccountButton = new Button("Update Account Info");
        updateAccountButton.setOnAction(a -> feedbackLabel.setText("To be implemented"));

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(a -> {
            feedbackLabel.setStyle("-fx-text-fill: blue;");
            feedbackLabel.setText("Logged out successfully.");
            new SetupLoginSelectionPage(databaseHelper).show(primaryStage);
        });

        layout.getChildren().addAll(adminLabel, updateAccountButton, logoutButton, feedbackLabel);

        Scene adminScene = new Scene(layout, 600, 350);
        primaryStage.setScene(adminScene);
        primaryStage.setTitle("Admin Home Page");
        primaryStage.show();
    }
}