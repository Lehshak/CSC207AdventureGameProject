package AdventureModel;

public class Batman extends Player {
    public Batman() {
        super("Batman", new HandToHandAttack());
        this.hitPoints = 70;
        this.weakness = "magic";
    }
}
