package views;

import AdventureModel.AdventureGame;
import AdventureModel.AdventureObject;
import AdventureModel.GunAttack;
import AdventureModel.Item;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import javafx.scene.input.KeyEvent; //you will need these!
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.event.EventHandler; //you will need this too!
import javafx.scene.AccessibleRole;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.util.ArrayList;

/**
 * Class AdventureGameView.
 *
 * This is the Class that will visualize your model.
 * You are asked to demo your visualization via a Zoom
 * recording. Place a link to your recording below.
 * the URL IS GOOGLE DOCS
 * ZOOM LINK: https://drive.google.com/file/d/1znUSY2kmrN3liw8Lr000ng8XVHOrn55Y/view?usp=sharing
 * PASSWORD: <NO PASSWORD NEEDED!>
 */
public class AdventureGameView {

    AdventureGame model; //model of the game
    Stage stage; //stage on which all is rendered
    Button saveButton, loadButton, helpButton; //buttons
    Boolean helpToggle = false; //is help on display?

    GridPane gridPane = new GridPane(); //to hold images and buttons
    Label roomDescLabel = new Label(); //to hold room description and/or instructions
    VBox objectsInRoom = new VBox(); //to hold room items
    VBox objectsInInventory = new VBox(); //to hold inventory items
    ImageView roomImageView; //to hold room image
    TextField inputTextField; //for user input
    HBox attackButtons;
    VBox textEntry;

    GridPane InventoryGrid = new GridPane(); // holds inventory conumables and attack items etc
    ChoiceBox<String> weaponsChoiceBox = new ChoiceBox<>();

    private MediaPlayer mediaPlayer; //to play audio

    private MediaPlayer mediaPlayer2; //to play tts

    private boolean mediaPlaying; //to know if the audio is playing

    public boolean tictactoe = false;

    TicTacToeMinigameView tic = new TicTacToeMinigameView();

    public boolean word = false;
    WordleView wordle = new WordleView();

    public boolean manca = false;
    MancalaMinigameView mancala = new MancalaMinigameView();
    GridPane menuPane = new GridPane(); //to hold main menu buttons and images
    Stage menuStage = new Stage(); //stage for menu
    public Label objLabel =  new Label("Objects in Room");
    public Label invLabel =  new Label("Your Inventory");
    public String backgroundColor = "-fx-background-color: black;";
    public String textColor = "-fx-text-fill: white;";
    public Color instructionsTextColor = Color.WHITE;
    private Rectangle foreground;
    private Rectangle background;
    private StackPane stackPane;

    /**
     * Adventure Game View Constructor
     * __________________________
     * Initializes attributes
     */
    public AdventureGameView(AdventureGame model, Stage stage) {
        this.model = model;
        this.stage = stage;
        menuUI();
    }

    public void menuUI(){
        this.menuStage.setTitle("Group 73's Adventure Game Menu");
        stopArticulation();


        Image menuImageFile = new Image("Images/MainMenu.png");
        BackgroundImage menuBackground = new BackgroundImage(menuImageFile, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        menuPane.setBackground(new Background(menuBackground));


        menuPane.setPadding(new Insets(10, 10, 10, 10));
        menuPane.setVgap(10);
        menuPane.setHgap(10);

        Button playButton = new Button("Play");
        playButton.setFont(Font.font("Titillium Web", 18));
        playButton.setMinSize(150, 50);
        playButton.setStyle("-fx-background-color: #F7E0C0; -fx-text-fill: #000000;");
        makeButtonAccessible(playButton, "Play Button", "This button starts the actual game", "This button loads the game from a file. Click it in order to start the game.");
        playButton.setOnAction(e -> {
            intiUI();
        });

        Button settingsButton = new Button("Settings");
        settingsButton.setFont(Font.font("Titillium Web", 18));
        settingsButton.setMinSize(150, 50);
        settingsButton.setStyle("-fx-background-color: #F7E0C0; -fx-text-fill: #000000;");
        settingsButton.setOnAction(e -> {
            menuPane.requestFocus();
            SettingsView settingsView = new SettingsView(this);
        });

        Button backButton = new Button("Back");
        backButton.setFont(Font.font("Titillium Web", 18));
        backButton.setMinSize(150, 50);
        backButton.setStyle("-fx-background-color: #F7E0C0; -fx-text-fill: #000000;");

        Button instructionsButton = new Button("Instructions");
        instructionsButton.setFont(Font.font("Titillium Web", 18));
        instructionsButton.setMinSize(150, 50);
        instructionsButton.setStyle("-fx-background-color: #F7E0C0; -fx-text-fill: #000000;");


        Button loadGameButton = new Button("Load Game");
        loadGameButton.setFont(Font.font("Titillium Web", 18));
        loadGameButton.setMinSize(150, 50);
        loadGameButton.setStyle("-fx-background-color: #F7E0C0; -fx-text-fill: #000000;");
        makeButtonAccessible(loadGameButton, "Load Button", "This button loads a game from a file.", "This button loads the game from a file. Click it in order to load a game that you saved at a prior date.");
        loadGameButton.setOnAction(e -> {
            menuPane.requestFocus();
            LoadView loadView = new LoadView(this);
        });

        Button saveGameButton = new Button("Save Game");
        saveGameButton.setFont(Font.font("Titillium Web", 18));
        saveGameButton.setMinSize(150, 50);
        saveGameButton.setStyle("-fx-background-color: #F7E0C0; -fx-text-fill: #000000;");
        makeButtonAccessible(saveGameButton, "Save Button", "This button saves the game.", "This button saves the game. Click it in order to save your current progress, so you can play more later.");
        saveGameButton.setOnAction(e -> {
            menuPane.requestFocus();
            SaveView saveView = new SaveView(this);
        });

        instructionsButton.setOnAction(e -> {
            stopArticulation(); //if speaking, stop
            if (helpToggle){
                removeNodeFromGridPane(menuPane, 2,1);
                removeNodeFromGridPane(menuPane, 2,2);
                menuPane.add(playButton, 1, 1);
                menuPane.add(settingsButton, 1, 2);
                menuPane.add(instructionsButton, 1, 3);
                menuPane.add(loadGameButton, 1, 4);
                menuPane.add(saveGameButton, 1, 5);


                helpToggle = false;

            } else {
                menuPane.getChildren().clear();
                Label label = new Label(model.getInstructions());
                label.setStyle("-fx-background-color: #F7E0C0; -fx-padding: 10px;");
                removeNodeFromGridPane(menuPane, 2,1);
                menuPane.add(label, 2, 1);
                menuPane.add(backButton, 2, 2);
                helpToggle = true;

            }
        });

        backButton.setOnAction(e -> {
            stopArticulation(); //if speaking, stop
            if (helpToggle){
                removeNodeFromGridPane(menuPane, 2,1);
                removeNodeFromGridPane(menuPane, 2,2);
                menuPane.add(playButton, 1, 1);
                menuPane.add(settingsButton, 1, 2);
                menuPane.add(instructionsButton, 1, 3);
                menuPane.add(loadGameButton, 1, 4);
                menuPane.add(saveGameButton, 1, 5);


                helpToggle = false;

            } else {
                menuPane.getChildren().clear();
                Label label = new Label(model.getInstructions());
                label.setStyle("-fx-background-color: white; -fx-padding: 10px;");
                removeNodeFromGridPane(menuPane, 2,1);
                menuPane.add(label, 2, 1);
                menuPane.add(backButton, 2, 2);
                helpToggle = true;

            }
        });


        menuPane.add(playButton, 1, 1);
        menuPane.add(settingsButton, 1, 2);
        menuPane.add(instructionsButton, 1, 3);
        menuPane.add(loadGameButton, 1, 4);
        menuPane.add(saveGameButton, 1, 5);

        menuPane.setAlignment(Pos.CENTER);


        var scene = new Scene( menuPane ,  1200, 675);
        this.stage.setScene(scene);
        this.stage.setResizable(true);
        this.stage.show();
    }


    /**
     * Initialize the UI
     */
    public void intiUI() {

        // setting up the stage
        this.stage.setTitle("Group 73's Adventure Game");

        //Inventory + Room items
        objectsInInventory.setSpacing(10);
        objectsInInventory.setAlignment(Pos.TOP_CENTER);
        objectsInRoom.setSpacing(10);
        objectsInRoom.setAlignment(Pos.TOP_CENTER);

        // GridPane, anyone?
        gridPane.setPadding(new Insets(20));
        gridPane.setBackground(new Background(new BackgroundFill(
                Color.valueOf("#000000"),
                new CornerRadii(0),
                new Insets(0)
        )));

        //Three columns, three rows for the GridPane
        ColumnConstraints column1 = new ColumnConstraints(150);
        ColumnConstraints column2 = new ColumnConstraints(650);
        ColumnConstraints column3 = new ColumnConstraints(150);
        column3.setHgrow( Priority.SOMETIMES ); //let some columns grow to take any extra space
        column1.setHgrow( Priority.SOMETIMES );

        // Row constraints
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints( 550 );
        RowConstraints row3 = new RowConstraints();
        row1.setVgrow( Priority.SOMETIMES );
        row3.setVgrow( Priority.SOMETIMES );

        gridPane.getColumnConstraints().addAll( column1 , column2 , column1 );
        gridPane.getRowConstraints().addAll( row1 , row2 , row1 );

        // Buttons
        Button simpleMinigameButton = new Button("Train");
        simpleMinigameButton.setId("Train");
        customizeButton(simpleMinigameButton, 100, 50);
        simpleMinigameButton.setOnAction(e -> {
            openSimpleMinigame();
        });

        saveButton = new Button("Save");
        saveButton.setId("Save");
        customizeButton(saveButton, 100, 50);
        makeButtonAccessible(saveButton, "Save Button", "This button saves the game.", "This button saves the game. Click it in order to save your current progress, so you can play more later.");
        addSaveEvent();

        loadButton = new Button("Load");
        loadButton.setId("Load");
        customizeButton(loadButton, 100, 50);
        makeButtonAccessible(loadButton, "Load Button", "This button loads a game from a file.", "This button loads the game from a file. Click it in order to load a game that you saved at a prior date.");
        addLoadEvent();

        helpButton = new Button("Instructions");
        helpButton.setId("Instructions");
        customizeButton(helpButton, 200, 50);
        makeButtonAccessible(helpButton, "Help Button", "This button gives game instructions.", "This button gives instructions on the game controls. Click it to learn how to play.");
        addInstructionEvent();

        HBox topButtons = new HBox();
        topButtons.getChildren().addAll(simpleMinigameButton, saveButton, helpButton, loadButton);
        topButtons.setSpacing(10);
        topButtons.setAlignment(Pos.CENTER);

        inputTextField = new TextField();
        inputTextField.setFont(new Font("Segoe UI", 16));
        inputTextField.setFocusTraversable(true);

        inputTextField.setAccessibleRole(AccessibleRole.TEXT_AREA);
        inputTextField.setAccessibleRoleDescription("Text Entry Box");
        inputTextField.setAccessibleText("Enter commands in this box.");
        inputTextField.setAccessibleHelp("This is the area in which you can enter commands you would like to play.  Enter a command and hit return to continue.");
        addTextHandlingEvent(); //attach an event to this input field

        //labels for inventory and room items
        objLabel.setAlignment(Pos.CENTER);
        objLabel.setStyle(textColor);
        objLabel.setFont(new Font("Segoe UI", 16));

        invLabel.setAlignment(Pos.CENTER);
        invLabel.setStyle(textColor);
        invLabel.setFont(new Font("Segoe UI", 16));

        //add all the widgets to the GridPane
        gridPane.add( objLabel, 0, 0, 1, 1 );  // Add label
        gridPane.add( topButtons, 1, 0, 1, 1 );  // Add buttons
        gridPane.add( invLabel, 2, 0, 1, 1 );  // Add label

        //start of gridpane add itemMenu

        //whenever the player gets an item it is
        ChoiceBox<String> consumablesChoiceBox = new ChoiceBox<>();

        consumablesChoiceBox.getItems().addAll("Basic Health Potion");

        Button useConsumableButton = new Button("Use Consumable");
        useConsumableButton.setOnAction(event -> {
            String selectedConsumable = consumablesChoiceBox.getValue();
            model.player.useItem(selectedConsumable);

            consumablesChoiceBox.getItems().remove(selectedConsumable);
            consumablesChoiceBox.getSelectionModel().clearSelection();

            System.out.println("Using Consumable: " + selectedConsumable);
        });

        // ChoiceBox for Weapons
        weaponsChoiceBox.getItems().addAll("Rusted Sword Sharpener");
        Button useWeaponButton = new Button("Use Weapon");
        useWeaponButton.setOnAction(event -> {
            String selectedWeapon = weaponsChoiceBox.getValue();

            model.player.useItem(selectedWeapon);
            weaponsChoiceBox.getItems().remove(selectedWeapon);
            weaponsChoiceBox.getSelectionModel().clearSelection();


            System.out.println("Using Weapon: " + selectedWeapon);
        });

        // ChoiceBox for Armor
        ChoiceBox<String> armorChoiceBox = new ChoiceBox<>();
        armorChoiceBox.getItems().addAll();
        Button useArmorButton = new Button("Use Armor");
        useArmorButton.setOnAction(event -> {
            String selectedArmor = armorChoiceBox.getValue();

            model.player.useItem(selectedArmor);
            armorChoiceBox.getItems().remove(selectedArmor);
            armorChoiceBox.getSelectionModel().clearSelection();


            System.out.println("Using Armor: " + selectedArmor);
        });

        //Now we will make the gridPane
        BorderPane root = new BorderPane();

        //  col-index, row-index, col-span, row-span
        InventoryGrid.add(useConsumableButton, 0, 0, 1,1);
        InventoryGrid.add(consumablesChoiceBox, 0, 1, 1,1);

        InventoryGrid.add(useArmorButton, 1,0, 1,1);
        InventoryGrid.add(armorChoiceBox, 1,1, 1,1);

        InventoryGrid.add(useWeaponButton, 2,0, 1,1);
        InventoryGrid.add(weaponsChoiceBox, 2,1, 1,1);

        InventoryGrid.setHgap(10);
        InventoryGrid.setVgap(5);

        InventoryGrid.setPadding(new Insets(10,10,10,10));

        gridPane.add( InventoryGrid, 0, 4, 3, 3 );

        //end of gridpane add item menu

        Label commandLabel = new Label("What would you like to do?");
        commandLabel.setStyle(textColor);

        commandLabel.setFont(new Font("Segoe UI", 16));

        updateScene(""); //method displays an image and whatever text is supplied
        updateItems(); //update items shows inventory and objects in rooms

        // adding the text area and submit button to a VBox
        VBox textEntry = new VBox();
        textEntry.setStyle(backgroundColor);
        textEntry.setPadding(new Insets(20, 20, 20, 20));
        textEntry.getChildren().addAll(commandLabel, inputTextField);
        textEntry.setSpacing(10);
        textEntry.setAlignment(Pos.CENTER);
        this.textEntry = textEntry;

        // attack buttons
        Button GunButton = new Button("Shoot!");
        GunButton.setId("GunButton");
        customizeButton(GunButton, 100, 50);

        Button HandToHandButton = new Button("Punch!");
        HandToHandButton.setId("HandtoHandButton");
        customizeButton(HandToHandButton, 100, 50);

        Button MagicButton = new Button("Fireball!");
        MagicButton.setId("MagicButton");
        customizeButton(MagicButton, 100, 50);

        // attack button handlers
        GunButton.setOnAction(e -> {
            submitEvent("attack gun");
        });
        HandToHandButton.setOnAction(e -> {
            submitEvent("attack handtohand");
        });
        MagicButton.setOnAction(e -> {
            submitEvent("attack magic");
        });

        HBox attackButtons = new HBox();
        attackButtons.getChildren().addAll(GunButton, HandToHandButton, MagicButton);
        attackButtons.setSpacing(10);
        attackButtons.setAlignment(Pos.CENTER);

        this.attackButtons = attackButtons;

        toggleAttackButtons(false);

        // Render everything
        var scene = new Scene( gridPane ,  1000, 800);
        scene.setFill(Color.BLACK);
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();


    }


    /**
     * makeButtonAccessible
     * __________________________
     * For information about ARIA standards, see
     * https://www.w3.org/WAI/standards-guidelines/aria/
     *
     * @param inputButton the button to add screenreader hooks to
     * @param name ARIA name
     * @param shortString ARIA accessible text
     * @param longString ARIA accessible help text
     */
    public static void makeButtonAccessible(Button inputButton, String name, String shortString, String longString) {
        inputButton.setAccessibleRole(AccessibleRole.BUTTON);
        inputButton.setAccessibleRoleDescription(name);
        inputButton.setAccessibleText(shortString);
        inputButton.setAccessibleHelp(longString);
        inputButton.setFocusTraversable(true);
    }

    /**
     * customizeButton
     * __________________________
     *
     * @param inputButton the button to make stylish :)
     * @param w width
     * @param h height
     */
    private void customizeButton(Button inputButton, int w, int h) {
        inputButton.setPrefSize(w, h);
        inputButton.setFont(new Font("Segoe UI", 16));
        inputButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
    }

    /**
     * addTextHandlingEvent
     * __________________________
     * Add an event handler to the myTextField attribute 
     *
     * Your event handler should respond when users 
     * hits the ENTER or TAB KEY. If the user hits 
     * the ENTER Key, strip white space from the
     * input to myTextField and pass the stripped 
     * string to submitEvent for processing.
     *
     * If the user hits the TAB key, move the focus 
     * of the scene onto any other node in the scene 
     * graph by invoking requestFocus method.
     */
    private void addTextHandlingEvent() {
        //add your code here!

        inputTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER){
                String input = inputTextField.getText().strip();
                //remove all white space
                submitEvent(input);
            } else if (event.getCode() == KeyCode.TAB) {
                inputTextField.getParent().requestFocus();
                //change focus

            }
        });

    }


    /**
     * submitEvent
     * __________________________
     *
     * @param text the command that needs to be processed
     */
    private void submitEvent(String text) {

        text = text.strip(); //get rid of white space
        stopArticulation(); //if speaking, stop

        if (text.equalsIgnoreCase("LOOK") || text.equalsIgnoreCase("L")) {
            String roomDesc = this.model.getPlayer().getCurrentRoom().getRoomDescriptionOriginal();
            String objectString = this.model.getPlayer().getCurrentRoom().getObjectString();
            if (!objectString.isEmpty()) roomDescLabel.setText(roomDesc + "\n\nObjects in this room:\n" + objectString);
            articulateRoomDescription(); //all we want, if we are looking, is to repeat description.
            return;
        } else if (text.equalsIgnoreCase("HELP") || text.equalsIgnoreCase("H")) {
            showInstructions();
            return;
        } else if (text.equalsIgnoreCase("COMMANDS") || text.equalsIgnoreCase("C")) {
            showCommands(); //this is new!  We did not have this command in A1
            return;
        }
        inputTextField.setText("");
        //try to move!
        String output = this.model.interpretAction(text); //process the command!

        if (output == null || (!output.equals("GAME OVER") && !output.equals("FORCED") && !output.equals("HELP"))) {
            updateScene(output);
            updateItems();
        } else if (output.equals("GAME OVER")) {
            endGame();
        } else if (output.equals("FORCED")) {
            //write code here to handle "FORCED" events!
            //Your code will need to display the image in the
            //current room and pause, then transition to
            //the forced room.
            moveToForcedRoom(5);
        }
    }

    public void endGame() {
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> {
            Platform.exit();
        });
        pause.play();
    }

    public void moveToForcedRoom(int duration) {

        updateScene(model.getPlayer().getCurrentRoom().getRoomDescription());
        articulateRoomDescription();

        PauseTransition pause = new PauseTransition(Duration.seconds(duration));

        pause.setOnFinished(event -> {
            //transition to forced room
            submitEvent("FORCED");
            updateItems();

        });

        pause.play();
    }

    public void toggleAttackButtons(boolean visible) {
        if (visible) {
            gridPane.getChildren().remove(textEntry);
            gridPane.add(attackButtons, 0, 3, 3, 1);
        }
        else {
            gridPane.getChildren().remove(attackButtons);
            gridPane.add( textEntry, 0, 2, 3, 1 );
        }
    }

    /**
     * showCommands
     * __________________________
     *
     * update the text in the GUI (within roomDescLabel)
     * to show all the moves that are possible from the 
     * current room.
     */
    private void showCommands() {
        //throw new UnsupportedOperationException("showCommands is not implemented!");
        //model.getPlayer().getCurrentRoom().getMotionTable()
        String commands = model.getPlayer().getCurrentRoom().getCommands();
        if (commands.contains("ELDROW")){
            commands = commands.replace("ELDROW", "[SECRET PASSWORD]");
        }
        roomDescLabel.setText("Commands Available : \n" + commands);


    }


    /**
     * updateScene
     * __________________________
     *
     * Show the current room, and print some text below it.
     * If the input parameter is not null, it will be displayed
     * below the image.
     * Otherwise, the current room description will be dispplayed
     * below the image.
     * 
     * @param textToDisplay the text to display below the image.
     */
    public void updateScene(String textToDisplay) {


        if (this.model.getPlayer().getCurrentRoom().getRoomName().equals("Zelda") && !tictactoe){
            Stage stage1 = new Stage();
            tic.start(stage1);
            tictactoe = true;

        }

        if (tic.win){
            Item item = new Item("Master Sword", "Weapon", 10);
            model.getPlayer().items.add(item);
            weaponsChoiceBox.getItems().add("Master Sword");
            tic.win = false;
        }

        if (this.model.getPlayer().getCurrentRoom().getRoomName().equals("Wordle") && !word){
            Stage stage1 = new Stage();
            wordle.start(stage1);
            word = true;
        }
        if (this.model.getPlayer().getCurrentRoom().getRoomName().equals("Mancala") && !word){
            Stage stage1 = new Stage();
            mancala.start(stage1);
            manca = true;
        }


        getRoomImage(); //get the image of the current room
        formatText(textToDisplay); //format the text to display

        // Update Healthbar Graphic
        gridPane.getChildren().remove(stackPane);
        background = new Rectangle(75*3, 30); // the total amount of health represented in a rectangle
        background.setFill(Color.GRAY);
        double x = model.player.hitPoints;
        foreground = new Rectangle(model.player.hitPoints*3, 30); // the current amount of health represented in a rectangle
        foreground.setFill(Color.GREEN);
        stackPane = new StackPane(background, foreground);
        stackPane.setAlignment(Pos.BASELINE_RIGHT);
        gridPane.add(stackPane,1, 4);

        roomDescLabel.setPrefWidth(500);
        roomDescLabel.setPrefHeight(500);
        roomDescLabel.setTextOverrun(OverrunStyle.CLIP);
        roomDescLabel.setWrapText(true);
        VBox roomPane = new VBox(roomImageView,roomDescLabel);
        roomPane.setPadding(new Insets(10));
        roomPane.setAlignment(Pos.TOP_CENTER);
        roomPane.setStyle(backgroundColor);
        gridPane.add(roomPane, 1, 1);
        stage.sizeToScene();

        //finally, articulate the description
        if (textToDisplay == null || textToDisplay.isBlank()) articulateRoomDescription();
    }

    /**
     * formatText
     * __________________________
     *
     * Format text for display.
     * 
     * @param textToDisplay the text to be formatted for display.
     */
    private void formatText(String textToDisplay) {
        if (textToDisplay == null || textToDisplay.isBlank()) {
            String roomDesc = this.model.getPlayer().getCurrentRoom().getRoomDescription() + "\n";
            String objectString = this.model.getPlayer().getCurrentRoom().getObjectString();
            if (objectString != null && !objectString.isEmpty()) roomDescLabel.setText(roomDesc + "\n\nObjects in this room:\n" + objectString);
            else roomDescLabel.setText(roomDesc);
        } else roomDescLabel.setText(textToDisplay);
        roomDescLabel.setStyle(textColor);
        roomDescLabel.setFont(new Font("Segoe UI", 16));
        roomDescLabel.setAlignment(Pos.CENTER);
    }

    /**
     * getRoomImage
     * __________________________
     *
     * Get the image for the current room and place 
     * it in the roomImageView 
     */
    private void getRoomImage() {

        int roomNumber = this.model.getPlayer().getCurrentRoom().getRoomNumber();
        String roomImage = this.model.getDirectoryName() + "/room-images/" + roomNumber + ".png";
        Image roomImageFile = new Image(roomImage);
        roomImageView = new ImageView(roomImageFile);
        roomImageView.setPreserveRatio(true);
        roomImageView.setFitWidth(400);
        roomImageView.setFitHeight(400);

        //set accessible text
        roomImageView.setAccessibleRole(AccessibleRole.IMAGE_VIEW);
        roomImageView.setAccessibleText(this.model.getPlayer().getCurrentRoom().getRoomDescription());
        roomImageView.setFocusTraversable(true);
    }

    /**
     * updates Vbox
     * "Write code to update a vbox with buttons" ChatGPT,  3.5 version, OpenAI, 23 Oct. 2023,
     *  chat.openai.com/chat.
     *  I changed things a lot here
     */
    private void updateVBox(VBox vbox, ArrayList<AdventureObject> objects) {
        vbox.getChildren().clear();
        for (AdventureObject object : objects) {
            ImageView image = new ImageView(this.model.getDirectoryName() + "/objectImages/" + object.getName() + ".jpg");
            image.setFitWidth(100);
            image.setFitHeight(100);

            Button button = new Button();
            button.setGraphic(image);
            makeButtonAccessible(button, "Object", object.getName(), object.getDescription());

            button.setOnAction(event -> {
                if (vbox == objectsInRoom) {
                    //model.getPlayer().addToInventory(object);
                    //model.getPlayer().getCurrentRoom().removeGameObject(object);
                    submitEvent("TAKE " + object.getName());
                } else if (vbox == objectsInInventory) {
                    //model.getPlayer().inventory.remove(object);
                    //model.getPlayer().getCurrentRoom().addGameObject(object);
                    submitEvent("DROP " + object.getName());
                }
                updateVBox(objectsInRoom, model.getPlayer().getCurrentRoom().objectsInRoom);
                updateVBox(objectsInInventory, model.getPlayer().inventory);
            });

            vbox.getChildren().add(button);
        }

    }
    /**
     * updateItems
     * __________________________
     *
     * This method is partially completed, but you are asked to finish it off.
     *
     * The method should populate the objectsInRoom and objectsInInventory Vboxes.
     * Each Vbox should contain a collection of nodes (Buttons, ImageViews, you can decide)
     * Each node represents a different object.
     *
     * Images of each object are in the assets
     * folders of the given adventure game.
     */
    public void updateItems() {

        //write some code here to add images of objects in a given room to the objectsInRoom Vbox
        //write some code here to add images of objects in a player's inventory room to the objectsInInventory Vbox
        updateVBox(objectsInRoom, model.getPlayer().getCurrentRoom().objectsInRoom);
        updateVBox(objectsInInventory, model.getPlayer().inventory);


        inputTextField.setOnAction(event ->{
            String instruction = inputTextField.getText().toLowerCase();
            if (instruction.startsWith("take ")){
                String object = instruction.substring(5).toUpperCase();
                for (Node node : objectsInRoom.getChildren()) {
                    if (node instanceof Button) {
                        Button button = (Button) node;
                        if (button.getText().equalsIgnoreCase(object)) {
                            objectsInRoom.getChildren().remove(button);
                            break; // Exit loop after finding the button
                        }
                    }
                }

            }

            if (instruction.startsWith("drop ")){
                String object = instruction.substring(5).toUpperCase();
                //use checkifobject in inventory
                if (model.getPlayer().checkIfObjectInInventory(object)){
                    //model.getPlayer().dropObject(object);
                    //removed the object now we need to find object button

                    //updateVBox(objectsInRoom, model.getPlayer().getCurrentRoom().objectsInRoom);
                    //updateVBox(objectsInInventory, model.getPlayer().inventory);

                    for (Node node : objectsInInventory.getChildren()) {
                        if (node instanceof Button) {
                            Button button = (Button) node;
                            if (button.getText().equalsIgnoreCase(object)) {
                                objectsInInventory.getChildren().remove(button);
                                break; // Exit loop after finding the button
                            }
                        }
                    }


                }
            }

        });

        //please use setAccessibleText to add "alt" descriptions to your images!
        //the path to the image of any is as follows:
        //this.model.getDirectoryName() + "/objectImages/" + objectName + ".jpg";

        ScrollPane scO = new ScrollPane(objectsInRoom);
        scO.setPadding(new Insets(10));
        scO.setStyle(backgroundColor);
        scO.setFitToWidth(true);
        gridPane.add(scO,0,1);

        ScrollPane scI = new ScrollPane(objectsInInventory);
        scI.setFitToWidth(true);
        scI.setStyle(backgroundColor);
        gridPane.add(scI,2,1);


    }

    /*
     * remove the node from the grid pane
     * "Write java code that remove a node from a GridPane in javafx" ChatGPT,  3.5 version, OpenAI, 23 Oct. 2023,
      *  chat.openai.com/chat.
     */

    private void removeNodeFromGridPane(GridPane gridPane, int col, int row) {
        javafx.scene.Node nodeToRemove = null;
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                nodeToRemove = node;
                break;
            }
        }
        if (nodeToRemove != null) {
            gridPane.getChildren().remove(nodeToRemove);
        }
    }

    /*
     * Show the game instructions.
     *
     * If helpToggle is FALSE:
     * -- display the help text in the CENTRE of the gridPane (i.e. within cell 1,1)
     * -- use whatever GUI elements to get the job done!
     * -- set the helpToggle to TRUE
     * -- REMOVE whatever nodes are within the cell beforehand!
     *
     * If helpToggle is TRUE:
     * -- redraw the room image in the CENTRE of the gridPane (i.e. within cell 1,1)
     * -- set the helpToggle to FALSE
     * -- Again, REMOVE whatever nodes are within the cell beforehand!
     */
    public void showInstructions() {
        //throw new UnsupportedOperationException("showInstructions is not implemented!");

        //read help text
        //model.getInstructions(); is the help text

        if (helpToggle){
            removeNodeFromGridPane(gridPane, 1,1);

            updateScene("");

            //gridPane.add(roomImageView, 1,1);
            helpToggle = false;

        } else {
            //TextField text = new TextField();
            //could also use a label
            Text text = new Text(model.getInstructions());
            text.setFill(instructionsTextColor);
            text.setFont(javafx.scene.text.Font.font("Arial", 15));
            TextFlow instructionsFlow = new TextFlow(text);
            instructionsFlow.setStyle(backgroundColor);
            ScrollPane scrollText = new ScrollPane(instructionsFlow);
            scrollText.setStyle(backgroundColor);
            scrollText.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollText.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            scrollText.setFitToHeight(true);
            scrollText.setFitToWidth(true);
            removeNodeFromGridPane(gridPane, 1,1);
            gridPane.add(scrollText, 1,1);
            helpToggle = true;

        }

    }

    /**
     * This method handles the event related to the
     * help button.
     */
    public void addInstructionEvent() {
        helpButton.setOnAction(e -> {
            stopArticulation(); //if speaking, stop
            showInstructions();
        });
    }

    /**
     * This method handles the event related to the
     * save button.
     */
    public void addSaveEvent() {
        saveButton.setOnAction(e -> {
            gridPane.requestFocus();
            SaveView saveView = new SaveView(this);
        });
    }

    public void openSimpleMinigame() {
        gridPane.requestFocus();
        SimpleMinigameView simpleMinigameView = new SimpleMinigameView(this, model);
    }

    /**
     * This method handles the event related to the
     * load button.
     */
    public void addLoadEvent() {
        loadButton.setOnAction(e -> {
            gridPane.requestFocus();
            LoadView loadView = new LoadView(this);
        });
    }


    /**
     * This method articulates Room Descriptions
     */
    public void articulateRoomDescription() {
        String musicFile;
        String adventureName = this.model.getDirectoryName();
        String roomName = this.model.getPlayer().getCurrentRoom().getRoomName();

        if (!this.model.getPlayer().getCurrentRoom().getVisited()) musicFile = adventureName + "/sounds/" + roomName.toLowerCase() + "-long.mp3" ;
        else musicFile = adventureName + "/sounds/" + roomName.toLowerCase() + "-short.mp3" ;
        musicFile = musicFile.replace(" ","-");

        Media sound = new Media(new File(musicFile).toURI().toString());

        String roomNum = String.valueOf(this.model.getPlayer().getCurrentRoom().getRoomNumber());
        String ttsFile = adventureName + "/sounds/" + roomNum + "desc.mp3";
        Media sound2 = new Media(new File(ttsFile).toURI().toString());

        mediaPlayer2 = new MediaPlayer(sound2);

        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        mediaPlayer2.play();
        mediaPlaying = true;

    }

    /**
     * This method stops articulations
     * (useful when transitioning to a new room or loading a new game)
     */
    public void stopArticulation() {
        if (mediaPlaying) {
            mediaPlayer.stop(); //shush!
            mediaPlayer2.stop();
            mediaPlaying = false;
        }
    }
}
