package AdventureModel;

public class GunAttack implements Attack{

    public String getType() {
        return "gun";
    }
    public String announce(Player attacker, Player victim) {
        return attacker.getName() + " shoots at " + victim.getName() + "!";
    }
    public void use(Player attacker, Player victim) {
        double extra = 0.0;
        if (getType().equals(victim.weakness)) {
            extra = 25.0;
        }
        victim.takeDamage(50+attacker.getAttackBonus()+extra);
    }
}
