package AdventureModel;

public class MagicAttack implements Attack {

    public String getType() {
        return "magic";
    }
    public String announce(Player attacker, Player victim) {
        return attacker.getName() + " launches an energy blast at " + victim.getName() + "!";
    }
    public void use(Player attacker, Player victim) {
        double extra = 0.0;
        if (getType().equals(victim.weakness)) {
            extra = 25.0;
        }
        victim.takeDamage(40+attacker.getAttackBonus()+extra);
    }

}