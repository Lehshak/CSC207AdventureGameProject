package AdventureModel;

public class CombatMediator {

    private Player mainCharacter;
    public Player currEnemy;
    public CombatMediator(Player mainCharacter) {
        this.mainCharacter = mainCharacter;
    }
    public static boolean checkPreCombatRoom(String roomName) {
        if (roomName.split(" ").length > 1) {
            return (roomName.split(" ")[1].equals("Intro"));
        }
        else return false;
    }

    public static boolean checkCombatRoom(String roomName) {
        if (roomName.split(" ").length > 1) {
            return (roomName.split(" ")[1].equals("Fight"));
        }
        else return false;
    }

    public String mediateCombat(String enemyName) {
        Player enemy;
        if (currEnemy == null || !currEnemy.getName().equals(enemyName)) {
            if (enemyName.equals("Tate")) {
                enemy = new Tate();
            } else if (enemyName.equals("Goku")) {
                enemy = new Goku();
            } else if (enemyName.equals("Batman")) {
                    enemy = new Batman();
            } else if (enemyName.equals("Impostor")) {
                enemy = new Impostor();
            } else {
                return "UNRECOGNIZED CHARACTER";
            }
            this.currEnemy = enemy;
        }
        else enemy = this.currEnemy;
        String yourAttack = mainCharacter.attack(enemy);
        String enemyAttack = enemy.attack(mainCharacter);
        String enemyStatus;
        String mainCharacterStatus;
        String result;
        if (enemy.getHitPoints() == 0) { enemyStatus = enemy.getName() + " has died!"; }
        else enemyStatus = enemy.getName() + " has " + enemy.getHitPoints() + " hitpoints left.";
        if (mainCharacter.getHitPoints() == 0) mainCharacterStatus = mainCharacter.getName() + " has died!";
        else mainCharacterStatus = mainCharacter.getName() + " has " + mainCharacter.getHitPoints() + " hitpoints left.";
        if (mainCharacter.getHitPoints() == 0) result = "GAME OVER!";
        else if (enemy.getHitPoints() == 0) {
            result = enemy.getName().toUpperCase() + " DEFEATED! +50 HP!";
            mainCharacter.levelUpHitpoints();
        }
        else result = "";
        return yourAttack + "\n" + enemyAttack + "\n" + mainCharacterStatus + "\n" + enemyStatus + "\n\n" + result;
    }

}
