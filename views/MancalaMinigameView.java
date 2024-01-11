package views;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

/**
 * The main class for the Mancala minigame.
 */
public class MancalaMinigameView extends Application {
    private int[] pits; // Array to represent the pits
    private boolean playersTurn;
    private boolean prize;
    private GridPane grid;

    /**
     * The entry point for the JavaFX application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        initializeGame();

        grid = createGameBoard();

        Scene scene = new Scene(grid, 1000, 1000);
        primaryStage.setTitle("Mancala Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Initializes the game state.
     */
    private void initializeGame() {
        pits = new int[14];
        playersTurn = true;
        for (int i = 0; i < 14; i++) {
            pits[i] = 4; // Initial stones in each pit
        }
        pits[6] = pits[7] = 0; // Mancala stores start empty
    }

    /**
     * Creates the game board UI.
     *
     * @return The GridPane representing the game board.
     */
    private GridPane createGameBoard() {
        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color: #587498;");
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        // Adding buttons for each Mancala pit
        for (int i = 0; i < 14; i++) {
            int row;
            int col;
            Button pitButton = new Button(Integer.toString(pits[i]));
            pitButton.setPrefWidth(75);
            pitButton.setPrefHeight(75);
            if (i < 6) {
                row = 1;
                col = i % 7 + 2;
                Image image = new Image("Images/Mancala4.png");
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(75);
                imageView.setFitHeight(75);
                pitButton = new Button(Integer.toString(pits[i]), imageView);
                pitButton.setPrefWidth(75);
                pitButton.setPrefHeight(75);
            } else if (7 < i) {
                row = 0;
                col = i % 7 + 1;
                Image image = new Image("Images/Mancala4.png");
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(75);
                imageView.setFitHeight(75);
                pitButton = new Button(Integer.toString(pits[i]), imageView);
                pitButton.setPrefWidth(75);
                pitButton.setPrefHeight(75);
            } else if (i == 6) {
                row = 0;
                col = 8;
                Image image = new Image("Images/Mancala0.png");
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(100);
                imageView.setFitHeight(150);
                pitButton = new Button(Integer.toString(pits[i]), imageView);
                pitButton.setPrefWidth(100);
                pitButton.setPrefHeight(150);
            } else {
                row = 0;
                col = 0;
                Image image = new Image("Images/Mancala0.png");
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(100);
                imageView.setFitHeight(150);
                pitButton = new Button(Integer.toString(pits[i]), imageView);
                pitButton.setPrefWidth(100);
                pitButton.setPrefHeight(150);
            }
            int index = i;
            pitButton.setOnAction(event -> handlePitClick(index));
            if (i == 6 || i == 7) {
                grid.add(pitButton, col, row, 2, 2);
            } else{
                grid.add(pitButton, col, row);
            }
        }
        Label label = new Label("Welcome to the Mancala Game!");
        label.setFont(new Font("Arial", 16));
        label.setStyle("-fx-text-fill: white;");
        grid.add(label, 4, 4, 2, 2);
        return grid;
    }

    /**
     * Handles the click on a pit button.
     *
     * @param pitIndex The index of the clicked pit.
     */
    private void handlePitClick(int pitIndex) {
        prize = false;
        int stones = pits[pitIndex];
        if (playersTurn && (stones == 0 || pitIndex > 5)) {
            return;
        }
        pits[pitIndex] = 0;
        updatePitImage(pitIndex);
        if (playersTurn) {
            pitIndex ++;
        } else {
            pitIndex --;
        }
        boolean savePlayersTurn = playersTurn;
        // Empty the stones in the correct pits
        while (stones > 0) {
            if (savePlayersTurn) {
                stones = exhaustPlayersRow(pitIndex, stones);
                pitIndex = 13;
            } else {
                stones = exhaustOpponentsRow(pitIndex, stones);
                pitIndex = 0;
            }
            savePlayersTurn = !savePlayersTurn;
        }

        updateGameBoard();
        checkGameOver();
        // Checks if player has got an extra turn
        if (!prize) {
            // Checks if its computer turn
            if (playersTurn) {
                playersTurn = false;
                computerMove();
            }
        }
        playersTurn = true;
    }

    /**
     * Distributes stones in the player's row.
     *
     * @param pitIndex The current pit index.
     * @param stones   The number of stones to distribute.
     * @return The remaining stones after distribution.
     */
    private int exhaustPlayersRow(int pitIndex, int stones) {
        for (; pitIndex < 7; pitIndex++) {
            if (stones == 0) {
                return stones;
            }
            // Condition stops the opponent from placing
            // stones in the players pocket
            if (!playersTurn && pitIndex == 6) {
                return stones;
            }
            pits[pitIndex] += 1;
            updatePitImage(pitIndex);
            stones --;
            // Checks if player gets an extra turn
            if (stones == 0 && pitIndex == 6 && playersTurn) {
                prize = true;
            }
        }
        return stones;
    }

    /**
     * Distributes stones in the opponent's row.
     *
     * @param pitIndex The current pit index.
     * @param stones   The number of stones to distribute.
     * @return The remaining stones after distribution.
     */
    private int exhaustOpponentsRow(int pitIndex, int stones) {
        for (; pitIndex > 6; pitIndex--) {
            if (stones == 0) {
                return stones;
            }
            // Condition stops the player from placing
            // stones in the opponents pocket
            if (playersTurn && pitIndex == 7) {
                return stones;
            }
            pits[pitIndex] += 1;
            updatePitImage(pitIndex);
            stones --;
        }
        return stones;
    }

    /**
     * Updates the image of a pit button based on the number of stones.
     *
     * @param pitIndex The index of the pit button.
     */
    private void updatePitImage(int pitIndex) {
        Button changeButton = (Button) grid.getChildren().get(pitIndex);
        int stonesInPit = pits[pitIndex];
        Image image;
        // If pit exceed 5 stones the image for that pit will be
        // a pit of 6 stones
        if (stonesInPit > 5) {
            image = new Image("Images/Mancala6.png");
        } else {
            image = new Image("Images/Mancala" + stonesInPit + ".png");
        }
        ImageView imageView = new ImageView(image);
        if (pitIndex == 6 || pitIndex == 7) {
            imageView.setFitWidth(100);
            imageView.setFitHeight(150);
        } else {
            imageView.setFitWidth(75);
            imageView.setFitHeight(75);
        }
        changeButton.setGraphic(imageView);
    }

    /**
     * Checks if the game is over and determines the winner.
     */
    private void checkGameOver() {
        boolean playersRowEmpty = true;
        boolean opponentsRowEmpty = true;
        // The loop searches for an empty row
        for (int i = 0; i < 14; i++) {
            if (i < 6 && pits[i] > 0) {
                playersRowEmpty = false;
            } else if (i > 7 && pits[i] > 0) {
                opponentsRowEmpty = false;
            }
        }
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        boolean finalOpponentsRowEmpty = opponentsRowEmpty;
        boolean finalPlayersRowEmpty = playersRowEmpty;
        pause.setOnFinished(event -> {
            if (finalPlayersRowEmpty || finalOpponentsRowEmpty){
                calculateWinner();
            }
        });
        pause.play();
        if (finalPlayersRowEmpty || finalOpponentsRowEmpty) {
            Stage stage = (Stage) grid.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Performs the computer's move.
     */
    private void computerMove() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(6) + 8;
        // If the chosen pit is empty we choose another
        while (pits[randomNumber] == 0) {
            randomNumber = rand.nextInt(6) + 8;
        }
        handlePitClick(randomNumber);
    }

    /**
     * Calculates and prints the winner of the game.
     */
    private void calculateWinner() {
        int playersStones = 0;
        // Adding the remaining stones in the pits
        for (int i = 0; i < 7; i++) {
            playersStones += pits[i];
        }
        int computersStones = 0;
        // Adding the remaining stones in the pits
        for (int i = 7; i < 14; i++) {
            computersStones += pits[i];
        }
        if (playersStones > computersStones) {
            System.out.println("Players Stones: " + playersStones);
            System.out.println("Computers Stones: "+ computersStones);
            System.out.println("You Win!");
        } else if (playersStones < computersStones) {
            System.out.println("Players Stones: " + playersStones);
            System.out.println("Computers Stones: "+ computersStones);
            System.out.println("You Lost!");
        } else {
            System.out.println("Players Stones: " + playersStones);
            System.out.println("Computers Stones: "+ computersStones);
            System.out.println("Draw!");
        }
    }

    /**
     * Updates the entire game board UI.
     */
    private void updateGameBoard() {
        // Update the UI to reflect the current state of the game
        for (int i = 0; i < 14; i++) {
            Button pitButton = (Button) grid.getChildren().get(i);
            pitButton.setText(Integer.toString(pits[i]));
        }
    }
}
