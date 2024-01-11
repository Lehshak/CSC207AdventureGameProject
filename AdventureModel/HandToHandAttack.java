package AdventureModel;

public class HandToHandAttack implements Attack {
    public String getType() {
        return "handtohand";
    }
    public String announce(Player attacker, Player victim) {
        return attacker.getName() + " punches " + victim.getName() + "!";
    }
    public void use(Player attacker, Player victim) {
        double extra = 0.0;
        if (getType().equals(victim.weakness)) {
            extra = 25.0;
        }
        victim.takeDamage(30+attacker.getAttackBonus()+extra);
    }


}