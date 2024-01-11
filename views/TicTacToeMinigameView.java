package views;

import AdventureModel.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import javafx.scene.input.KeyEvent; //you will need these!
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.event.EventHandler; //you will need this too!

import java.io.File;
import java.util.ArrayList;


import java.util.Random;

public class TicTacToeMinigameView extends Application {
    //"Make a tictactoe game in javafx" ChatGPT, 27 now 3.5 version, OpenAI, 27 Nov. 2023,
    //chat.openai.com/chat.


    private boolean playerXTurn; // To determine player turn
    private final Button[][] buttons = new Button[3][3]; // Grid of buttons

    public boolean win = false;

    @Override
    public void start(Stage primaryStage) {
        //have a player class here and if you win the game gives you some items!

        primaryStage.setTitle("Tic Tac Toe");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(5);
        grid.setVgap(5);

        // Create buttons and add to the grid
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button btn = new Button();
                btn.setPrefSize(100, 100);
                btn.setOnAction(e -> handleButtonClick(btn));
                buttons[i][j] = btn;
                grid.add(btn, j, i);
            }
        }

        playerXTurn = true; // Player 1 starts

        //play music
        String musicFile = "Music/ZeldaRing.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

        StackPane root = new StackPane();

        Image backgroundImage = new Image("Images/ZeldaSword.png");


        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(1000);
        backgroundView.setFitHeight(1000);


        root.getChildren().add(backgroundView);


        grid.setPadding(new Insets(10));
        grid.setHgap(5);
        grid.setVgap(5);

        Label label1 = new Label("You stumble upon the Master Sword, however a curse is written on the rock it lays upon.");
        label1.setStyle("-fx-font-size: 24px;");

        Label label2 = new Label("Break the curse to pull out the legendary sword!");
        label2.setStyle("-fx-font-size: 24px;");

        //this rootgrid centers the tic tac toe grid.
        GridPane rootGrid = new GridPane();
        rootGrid.setAlignment(Pos.CENTER);
        rootGrid.getChildren().add(grid);
        rootGrid.add(label1, 0, 2);
        rootGrid.add(label2, 0, 3);

        root.getChildren().add(rootGrid);


        StackPane.setAlignment(grid, Pos.CENTER);

        Scene scene = new Scene(root, 1000, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();


/**
        GridPane rootGrid = new GridPane();
        rootGrid.setAlignment(Pos.CENTER); // Center the nested grid within the outer grid
        rootGrid.getChildren().add(grid);

        Scene scene = new Scene(rootGrid, 1000, 1000);

        primaryStage.setScene(scene);
        primaryStage.show();
        */

    }

    private void handleButtonClick(Button button) {
        if (button.getText().isEmpty()) {
            if (playerXTurn) {
                button.setText("X"); // Player X's turn
                if (checkWin(buttons, 'X')) {
                    System.out.println("You win!");
                    win = true;
                    closeWindow();
                }

                if (checkDraw()) {
                    System.out.println("It's a draw, you lose!");
                    closeWindow();
                } else {
                    playerXTurn = false; // Switch to Player O's turn
                    makeRandomMoveForPlayerO(); // Make a random move for Player O
                    if (checkWin(buttons, 'O')) {
                        System.out.println("You lose!");
                        closeWindow();
                    }

                    if (checkDraw()) {
                        System.out.println("It's a draw, you lose!");
                        closeWindow();
                    } else {
                        playerXTurn = true; // Switch back to Player X's turn
                    }
                }
            }
        }
    }

    private void makeRandomMoveForPlayerO() {
        Random rand = new Random();
        int row, col;
        do {
            row = rand.nextInt(3);
            col = rand.nextInt(3);
        } while (!buttons[row][col].getText().isEmpty()); // Repeat if the cell is not empty

        buttons[row][col].setText("O");
    }

    public boolean checkWin(Button[][] buttons, char player) {
        //This method was generated by chatgpt 3.5 on nov 27 2023

        // Convert buttons to a char array representing the game board
        char[][] board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("X")) {
                    board[i][j] = 'X';
                } else if (buttons[i][j].getText().equals("O")) {
                    board[i][j] = 'O';
                } else {
                    board[i][j] = ' ';
                }
            }
        }

        // Use the existing checkWin method for the board state
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true; // Row win
            }
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true; // Column win
            }
        }
        // Check diagonals
        if ((board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
                (board[0][2] == player && board[1][1] == player && board[2][0] == player)) {
            return true; // Diagonal win
        }

        return false; // No win
    }

    private void closeWindow() {
        Stage stage = (Stage) buttons[0][0].getScene().getWindow();
        stage.close();
    }

    private boolean checkDraw() {
        for (Button[] row : buttons) {
            for (Button button : row) {
                if (button.getText().isEmpty()) {
                    return false; // If any cell is empty, the game continues
                }
            }
        }
        return true; // All cells are filled, it's a draw
    }

    public static void main(String[] args) {

    }
}
