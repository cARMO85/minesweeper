import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;

/**
 * The test class FileHandlerTest.
 *
 * @version 1.0
 */
public class FileHandlerTest {
    private Minesweeper game;

    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @BeforeEach
    public void setUp() {
        game = new Minesweeper();
    }

    /**
     * UNIT-026
     * Test FileHandler saveGame
     * Ensures that the game state is saved to a file correctly.
     */
    @Test
    public void testSaveGame() {
        FileHandler.saveGame(game, "test_save.txt");
        File file = new File("test_save.txt");
        assertTrue(file.exists(), "File should be saved");
    }

    /**
     * UNIT-027
     * Test FileHandler loadGame
     * Ensures that the game state is loaded from a file correctly.
     */
    @Test
    public void testLoadGame() {
        FileHandler.saveGame(game, "test_save.txt");
        game.makeMove(0, 0, "G");
        FileHandler.loadGame(game, "test_save.txt");
        assertEquals("?", game.getCellState(0, 0), "Cell state should be restored to '?' after loading");
    }
}
