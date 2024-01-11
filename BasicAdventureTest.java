
import java.io.IOException;

import AdventureModel.AdventureGame;
import org.junit.jupiter.api.Test;
import views.InventoryView;
import views.TicTacToeMinigameView;

import static org.junit.jupiter.api.Assertions.*;

public class BasicAdventureTest {
    @Test
    void getCommandsTest() throws IOException {
        AdventureGame game = new AdventureGame("TinyGame");
        String commands = game.player.getCurrentRoom().getCommands();
        assertEquals("WEST, UP, NORTH, IN, SOUTH, DOWN", commands);
    }

    @Test
    void getObjectString() throws IOException {
        AdventureGame game = new AdventureGame("TinyGame");
        String objects = game.player.getCurrentRoom().getObjectString();
        assertEquals("a water bird", objects);
    }


    public static void main(String[] args) {

        TicTacToeMinigameView.launch(TicTacToeMinigameView.class, args);

    }


}
