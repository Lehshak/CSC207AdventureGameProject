package views;

import AdventureModel.Item;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class InventoryView extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Inventory");

        // ChoiceBox for Consumable Items
        ChoiceBox<String> consumablesChoiceBox = new ChoiceBox<>();
        consumablesChoiceBox.getItems().addAll("Health Potion", "Mana Potion", "Elixir");
        Button useConsumableButton = new Button("Use Consumable");
        useConsumableButton.setOnAction(event -> {
            String selectedConsumable = consumablesChoiceBox.getValue();
            System.out.println("Using Consumable: " + selectedConsumable);
        });

        // ChoiceBox for Weapons
        ChoiceBox<String> weaponsChoiceBox = new ChoiceBox<>();
        weaponsChoiceBox.getItems().addAll("Sword", "Bow", "Staff");
        Button useWeaponButton = new Button("Use Weapon");
        useWeaponButton.setOnAction(event -> {
            String selectedWeapon = weaponsChoiceBox.getValue();
            System.out.println("Using Weapon: " + selectedWeapon);
        });

        // ChoiceBox for Armor
        ChoiceBox<String> armorChoiceBox = new ChoiceBox<>();
        armorChoiceBox.getItems().addAll("Helmet", "Chestplate", "Boots");
        Button useArmorButton = new Button("Use Armor");
        useArmorButton.setOnAction(event -> {
            String selectedArmor = armorChoiceBox.getValue();
            System.out.println("Using Armor: " + selectedArmor);
        });

        // Close Button
        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> primaryStage.close());

        //Now we will make the gridPane
        BorderPane root = new BorderPane();
        GridPane grid = new GridPane();

        //  col-index, row-index, col-span, row-span
        grid.add(useConsumableButton, 0, 0, 1,1);
        grid.add(consumablesChoiceBox, 0, 1, 1,1);

        grid.add(useArmorButton, 1,0, 1,1);
        grid.add(armorChoiceBox, 1,1, 1,1);

        grid.add(useWeaponButton, 2,0, 1,1);
        grid.add(weaponsChoiceBox, 2,1, 1,1);

        grid.add(closeButton, 3,0, 1,1);

        grid.setHgap(10);
        grid.setVgap(5);

        grid.setPadding(new Insets(10,10,10,10));


        root.setCenter(grid);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    // Helper method to create a labeled box with a ChoiceBox and Button
    private HBox createLabeledBox(String label, ChoiceBox<String> choiceBox, Button button) {
        HBox box = new HBox(10);
        box.getChildren().addAll(choiceBox, button);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-border-radius: 5px;");
        return box;
    }


    public GridPane getGridPane() {
        //This is a inventory gridpane that can be added to other gridpanes

        ChoiceBox<String> consumablesChoiceBox = new ChoiceBox<>();
        consumablesChoiceBox.getItems().addAll("Basic Health Potion");
        Button useConsumableButton = new Button("Use Consumable");
        useConsumableButton.setOnAction(event -> {
            String selectedConsumable = consumablesChoiceBox.getValue();


            System.out.println("Using Consumable: " + selectedConsumable);
        });

        // ChoiceBox for Weapons
        ChoiceBox<String> weaponsChoiceBox = new ChoiceBox<>();
        weaponsChoiceBox.getItems().addAll("Rusted Sword Sharpener");
        Button useWeaponButton = new Button("Use Weapon");
        useWeaponButton.setOnAction(event -> {
            String selectedWeapon = weaponsChoiceBox.getValue();
            System.out.println("Using Weapon: " + selectedWeapon);
        });

        // ChoiceBox for Armor
        ChoiceBox<String> armorChoiceBox = new ChoiceBox<>();
        armorChoiceBox.getItems().addAll();
        Button useArmorButton = new Button("Use Armor");
        useArmorButton.setOnAction(event -> {
            String selectedArmor = armorChoiceBox.getValue();
            System.out.println("Using Armor: " + selectedArmor);
        });

        //Now we will make the gridPane
        BorderPane root = new BorderPane();
        GridPane InventoryGrid = new GridPane();

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

        return InventoryGrid;
    }

}
