package AdventureModel;

/*
 * Gnome class
 */
public class Goku extends Player {

    /*
     * Goku constructor
     *
     * @param name, the name of character
     * @param inputWeapon, the weapon used by the character
     */
    public Goku() {
        super("Goku", new MagicAttack());
        this.hitPoints = 50;
        this.weakness = "gun";
    }
}