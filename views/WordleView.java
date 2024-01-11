package views;

import AdventureModel.AdventureObject;
import AdventureModel.Room;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.AccessibleRole;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;


public class WordleView extends Application {
    public boolean win = false;
    private String word;

    private GridPane gridPane = new GridPane();

    private Button[][] buttons = new Button[5][6]; // Grid of buttons


    @Override
    public void start(Stage stage) {
        stage.setTitle("Wordle");
        GetRandomWord();

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Image backgroundImage = new Image("Images/WordleBack.png");

        StackPane root = new StackPane();

        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(1000);
        backgroundView.setFitHeight(700);

        root.getChildren().add(backgroundView);


        for (int row = 0; row < 6; row++) {
            for (int column = 0; column < 5; column++) {
                Font font = Font.font("Arial", FontWeight.BOLD, 20);
                Button btn = new Button();
                btn.setFont(font);
                btn.setPrefSize(75, 75);
                buttons[column][row] = btn;
                gridPane.add(btn, column, row);
            }
        }

        TextField inputTextField = new TextField();
        inputTextField.setFont(new Font("Segoe UI", 16));
        inputTextField.setFocusTraversable(true);

        inputTextField.setAccessibleRole(AccessibleRole.TEXT_AREA);
        inputTextField.setAccessibleRoleDescription("Text Entry Box");
        inputTextField.setAccessibleText("Enter words  in this box.");
        inputTextField.setAccessibleHelp("This is the area in which you can enter words to guess.  Enter a 5 letter word and hit return to continue.");
        addTextHandlingEvent(inputTextField); //attach an event to this input field

        gridPane.add(inputTextField, 7, 4);
        root.getChildren().add(gridPane);
        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Word Grid");
        stage.setScene(scene);
        stage.show();

    }

    private void addTextHandlingEvent(TextField inputTextField) {
        //add your code here!

        inputTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER){
                Button[] columnButton = new Button[5];

                String input = inputTextField.getText().strip().toUpperCase();

                inputTextField.clear();

                if (input.equals(word)){
                    win = true;
                    Text wintext = new Text("You guessed it! \n The password is: ELDROW. \n Use it to enter the next room! \n Close this window to continue.");
                    Font font = new Font("Arial", 20);
                    wintext.setFont(font);
                    wintext.setFill(Color.WHITE);
                    gridPane.add(wintext, 7, 5);

                }

                if (input.length() == 5){
                    if (buttons[0][0].getText().isEmpty()){

                        for (int  i = 0; i < 5; i++)
                        {
                            columnButton[i] = buttons[i][0];
                        }

                        for (int  j = 0; j < columnButton.length; j++)
                        {
                            columnButton[j].setText(Character.toString(input.charAt(j)));
                        }

                        changeColors(columnButton, input);

                    }
                    else if (buttons[0][1].getText().isEmpty()){
                        for (int  i = 0; i < 5; i++)
                        {
                            columnButton[i] = buttons[i][1];
                        }

                        for (int  j = 0; j < columnButton.length; j++)
                        {
                            columnButton[j].setText(Character.toString(input.charAt(j)));
                        }

                        changeColors(columnButton, input);
                    }
                    else if (buttons[0][2].getText().isEmpty()){

                        for (int  i = 0; i < 5; i++)
                        {
                            columnButton[i] = buttons[i][2];
                        }

                        for (int  j = 0; j < columnButton.length; j++)
                        {
                            columnButton[j].setText(Character.toString(input.charAt(j)));
                        }

                        changeColors(columnButton, input);
                    }
                    else if (buttons[0][3].getText().isEmpty()){
                        for (int  i = 0; i < 5; i++)
                        {
                            columnButton[i] = buttons[i][3];
                        }

                        for (int  j = 0; j < columnButton.length; j++)
                        {
                            columnButton[j].setText(Character.toString(input.charAt(j)));
                        }

                        changeColors(columnButton, input);
                    }
                    else if (buttons[0][4].getText().isEmpty()){
                        for (int  i = 0; i < 5; i++)
                        {
                            columnButton[i] = buttons[i][4];
                        }

                        for (int  j = 0; j < columnButton.length; j++)
                        {
                            columnButton[j].setText(Character.toString(input.charAt(j)));
                        }

                        changeColors(columnButton, input);
                    }
                    else if (buttons[0][5].getText().isEmpty()){
                        for (int  i = 0; i < 5; i++)
                        {
                            columnButton[i] = buttons[i][5];
                        }

                        for (int  j = 0; j < columnButton.length; j++)
                        {
                            columnButton[j].setText(Character.toString(input.charAt(j)));
                        }

                        changeColors(columnButton, input);

                        if (!win){
                            Text wintext = new Text("You failed to guess it! \n No password for you! \n Close this window to continue.");
                            Font font = new Font("Arial", 20);
                            wintext.setFont(font);
                            wintext.setFill(Color.WHITE);
                            gridPane.add(wintext, 7, 5);
                        }
                    }
                }
            } else if (event.getCode() == KeyCode.TAB) {
                inputTextField.getParent().requestFocus();
                //change focus

            }
        });
    }

    private void changeColors(Button[] buttons, String input) {
        for (int i = 0; i < 5; i++){
            if (buttons[i].getText().equals(Character.toString(word.charAt(i)))){
                buttons[i].setStyle("-fx-background-color: #36FF00; -fx-text-fill: #000000;"); //change to green
            }
            else{
                if(word.indexOf(input.charAt(i)) != -1)
                {
                    buttons[i].setStyle("-fx-background-color: #FBFF00; -fx-text-fill: #000000;"); //change to yellow
                }
                else{
                    buttons[i].setStyle("-fx-background-color: #FF0000; -fx-text-fill: #000000;");   //change to red
                }
            }
        }


    }
    private void GetRandomWord(){

        String randomWordsName = "Text/randomwords.txt";

        try {
            List<String> words = readWords(randomWordsName);
            this.word = chooseRandomWord(words).toUpperCase();
        } catch (IOException e) {
            return;
        }
    }

    private String chooseRandomWord(List<String> words) {
        Random rand = new Random();
        int randomIndex = rand.nextInt(words.size());
        String randomWord = words.get(randomIndex);


        return randomWord;
    }

    private List<String> readWords(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader buff = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = buff.readLine()) != null) {
                lines.add(line);
            }
        }

        return lines;
    }
}
