package AdventureModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class keeps track of the progress
 * of the player in the game.
 */
public class Player implements Serializable {
    /**
     * The current room that the player is located in.
     */
    private Room currentRoom;

    /**
     * The list of items that the player is carrying at the moment.
     */
    public ArrayList<AdventureObject> inventory;
    public CombatMediator combatMediator;
    public Attack attack; //attack used by player
    public String name; //name of player
    public double hitPoints; //strength of player
    public double attackBonus; // attacks are strengthened by this
    public String weakness;

    public List<Item> items;


    /**
     * Adventure Game Player Constructor
     */
    public Player(String name, Attack attack, int hitpoints) {
        this.inventory = new ArrayList<AdventureObject>();
        this.name = name;
        this.attack = attack;
        this.hitPoints = hitpoints;
        this.attackBonus = 0.0;
        this.items = new ArrayList<Item>();

        Item item1 = new Item("Basic Health Potion", "Consumable", 5);
        Item item2 = new Item("Rusted Sword Sharpener", "Weapon", 5);
        this.items.add(item1);
        this.items.add(item2);

    }

    /**
     * This method adds an object into players inventory if the object is present in
     * the room and returns true. If the object is not present in the room, the method
     * returns false.
     *
     * @param object name of the object to pick up
     * @return true if picked up, false otherwise
     */
    public boolean takeObject(String object){
        if(this.currentRoom.checkIfObjectInRoom(object)){
            AdventureObject object1 = this.currentRoom.getObject(object);
            this.currentRoom.removeGameObject(object1);
            this.addToInventory(object1);
            return true;
        } else {
            return false;
        }
    }


    /**
     * checkIfObjectInInventory
     * __________________________
     * This method checks to see if an object is in a player's inventory.
     *
     * @param s the name of the object
     * @return true if object is in inventory, false otherwise
     */
    public boolean checkIfObjectInInventory(String s) {
        for(int i = 0; i<this.inventory.size();i++){
            if(this.inventory.get(i).getName().equals(s)) return true;
        }
        return false;
    }


    /**
     * This method drops an object in the players inventory and adds it to the room.
     * If the object is not in the inventory, the method does nothing.
     *
     * @param s name of the object to drop
     */
    public void dropObject(String s) {
        for(int i = 0; i<this.inventory.size();i++){
            if(this.inventory.get(i).getName().equals(s)) {
                this.currentRoom.addGameObject(this.inventory.get(i));
                this.inventory.remove(i);
            }
        }
    }

    /**
     * Setter method for the current room attribute.
     *
     * @param currentRoom The location of the player in the game.
     */
    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    /**
     * This method adds an object to the inventory of the player.
     *
     * @param object Prop or object to be added to the inventory.
     */
    public void addToInventory(AdventureObject object) {
        this.inventory.add(object);
    }


    /**
     * Getter method for the current room attribute.
     *
     * @return current room of the player.
     */
    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    /**
     * Getter method for string representation of inventory.
     *
     * @return ArrayList of names of items that the player has.
     */
    public ArrayList<String> getInventory() {
        ArrayList<String> objects = new ArrayList<>();
        for(int i=0;i<this.inventory.size();i++){
            objects.add(this.inventory.get(i).getName());
        }
        return objects;
    }

    /*
     * Player constructor
     *
     * @param name, the name of character
     * @param inputAttack, the attack used by the character
     */
    public Player(String name, Attack inputAttack)
    {
        this.attack = inputAttack;
        this.name = name;
    }

    public void levelUpHitpoints(){
        this.hitPoints += 50;
    }

    public void levelUpAttacks() {
        this.attackBonus += 10;
    }

    /*
     * Make this character fight another character
     * @param otherPlayer, the character to fight
     */
    public String attack(Player otherPlayer)
    {
        getAttack().use(this, otherPlayer);
        return (getAttack().announce(this, otherPlayer));
    }

    public void useItem(String item){
        for (int i = 0; i < items.size(); i++){
            if (items.get(i).name.equals(item)){
                if (items.get(i).description.equals("Consumable")){
                    hitPoints += items.get(i).power;
                } else if (items.get(i).description.equals("Weapon")){
                    this.attackBonus += items.get(i).power;
                } else {
                    hitPoints += items.get(i).power;
                }
                items.remove(items.get(i));

            }
        }
    }

    /*
     * Damage this Player
     * @param points, the amount of damage to take from player's hit points
     */
    public void takeDamage(double points) {
        this.hitPoints -= Math.min(this.hitPoints, points);
    }

    /*
     * Getter for hit points
     * @return hit points
     */
    public double getHitPoints() {
        return this.hitPoints;
    }


    /*
     * Getter for name
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /*
     * Getter for attack
     * @return attack
     */
    public Attack getAttack() {
        return this.attack;
    }
    public void setAttack(Attack attack) { this.attack = attack; }
    public double getAttackBonus() {
        return this.attackBonus;
    }
}


