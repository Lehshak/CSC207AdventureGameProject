package AdventureModel;

public interface Attack {
    public String getType();
    public String announce(Player attacker, Player victim);
    public void use(Player attacker, Player victim);
}