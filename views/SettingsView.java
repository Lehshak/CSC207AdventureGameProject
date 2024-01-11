package views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The SettingsView class represents the settings window for the adventure game,
 * allowing the user to customize the game's appearance.
 */
public class SettingsView{
    private AdventureGameView adventureGameView;
    private Button darkButton = new Button("Dark"); // Button to turn the UI to dark
    private Button lightButton = new Button("Light"); // Button to turn the UI to light
    private GridPane settingsGridPane = new GridPane(); // GridPane for the dark and light buttons
    private Button closeWindowButton;

    /**
     * Constructor for the SettingsView class.
     *
     * @param adventureGameView The reference to the main AdventureGameView instance.
     */
    public SettingsView(AdventureGameView adventureGameView) {
        this.adventureGameView = adventureGameView;
        // Set the settings Stage
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(adventureGameView.stage);

        // settings Gridpane customization
        settingsGridPane.setHgap(10);
        settingsGridPane.setVgap(10);
        settingsGridPane.setAlignment(Pos.CENTER);
        // Dark button customizations
        darkButton.setPrefSize(200, 75);
        darkButton.setFont(new Font("Segoe UI", 16));
        darkButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        // Light button customizations
        lightButton.setPrefSize(200, 75);
        lightButton.setFont(new Font("Segoe UI", 16));
        lightButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        settingsGridPane.add(darkButton, 0, 0);
        settingsGridPane.add(lightButton, 1, 0);
        // Change the adventureGameView items to dark
        darkButton.setOnAction(event -> {
            adventureGameView.gridPane.setStyle("-fx-background-color: black;");
            adventureGameView.backgroundColor = "-fx-background-color: black;";
            adventureGameView.textColor = "-fx-text-fill: white;";
            adventureGameView.instructionsTextColor = Color.WHITE;
        });
        // Change the adventureGameView items to dark
        lightButton.setOnAction(event -> {
            adventureGameView.gridPane.setStyle("-fx-background-color: white;");
            adventureGameView.backgroundColor = "-fx-background-color: white;";
            adventureGameView.textColor = "-fx-text-fill: black;";
            adventureGameView.instructionsTextColor = Color.BLACK;
        });

        Scene dialogScene = new Scene(settingsGridPane, 400, 400);
        dialog.setScene(dialogScene);
        dialog.show();
    }
}
