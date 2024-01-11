package AdventureModel;

/*
 * Elf class
 */
public class Tate extends Player {

    /*
     * Tate constructor
     *
     * @param name, the name of character
     * @param inputWeapon, the weapon used by the character
     */
    public Tate() {
        super("Tate", new HandToHandAttack());
        this.hitPoints = 65;
        this.weakness = "magic";
    }
}
