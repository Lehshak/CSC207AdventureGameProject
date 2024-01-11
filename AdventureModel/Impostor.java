package AdventureModel;

public class Impostor extends Player {

    public Impostor() {
        super("Impostor", new MagicAttack());
        this.hitPoints = 70;
        this.weakness = "gun";
    }
}
