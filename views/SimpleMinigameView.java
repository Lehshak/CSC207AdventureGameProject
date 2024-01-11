package views;

import AdventureModel.AdventureGame;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;


// Class based off SaveView
public class SimpleMinigameView {

    private AdventureGameView adventureGameView;
    private int counter = 0;
    private Stage dialog;
    private AdventureGame model;
    private Label mainLabel;
    private boolean gameOver;

    /**
     * Constructor
     */
    public SimpleMinigameView(AdventureGameView adventureGameView, AdventureGame model) {
        this.gameOver = false;
        this.adventureGameView = adventureGameView;
        this.model = model;
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(adventureGameView.stage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.setPadding(new Insets(20, 20, 20, 20));
        dialogVbox.setStyle("-fx-background-color: #121212;");

        Button button1 = new Button("1");
        button1.setId("button1");
        button1.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        button1.setPrefSize(200, 50);
        button1.setFont(new Font(16));
        button1.setOnAction(e -> playGame(1));

        Button button2 = new Button("2");
        button2.setId("button2");
        button2.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        button2.setPrefSize(200, 50);
        button2.setFont(new Font(16));
        button2.setOnAction(e -> playGame(2));

        Button button3 = new Button("3");
        button3.setId("button3");
        button3.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        button3.setPrefSize(200, 50);
        button3.setFont(new Font(16));
        button3.setOnAction(e -> playGame(3));

        Button button4 = new Button("4");
        button4.setId("button4");
        button4.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        button4.setPrefSize(200, 50);
        button4.setFont(new Font(16));
        button4.setOnAction(e -> playGame(4));

        Button button5 = new Button("5");
        button5.setId("button5");
        button5.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        button5.setPrefSize(200, 50);
        button5.setFont(new Font(16));
        button5.setOnAction(e -> playGame(5));

        Label mainLabel = new Label("Press the buttons in numerical order.");
        mainLabel.setTextFill(Color.WHITE);
        mainLabel.setFont(new Font(16));
        this.mainLabel = mainLabel;

        Button closeWindowButton = new Button("Close Window");
        closeWindowButton.setOnAction(e -> {
            if (model.player.getHitPoints() == 0) {
                adventureGameView.endGame();
            }
            dialog.close();
        });
        VBox saveGameBox = new VBox(10, mainLabel, button4, button2, button1, button5, button3, closeWindowButton);
        saveGameBox.setAlignment(Pos.CENTER);

        dialogVbox.getChildren().add(saveGameBox);
        Scene dialogScene = new Scene(dialogVbox, 400, 400);
        dialog.setScene(dialogScene);
        dialog.show();
        this.dialog = dialog;
    }

    public void playGame(int input) {
        if (!gameOver) {
            if (input != counter + 1) {
                model.player.takeDamage(30);
                String dead = "";
                if (model.player.getHitPoints() == 0) {
                    dead = " -- You have died!";
                }
                mainLabel.setText("You lost! -30 HP" + dead);
                gameOver = true;
            }
            counter = input;
            if (counter == 5) {
                model.player.levelUpAttacks();
                mainLabel.setText("You won! +10 attack");
                gameOver = true;
            }
        }
    }

}