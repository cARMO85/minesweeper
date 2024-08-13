import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class UITest.
 *
 * @version 1.0
 */
public class UITest {
    private Minesweeper game;
    private UI ui;

    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @BeforeEach
    public void setUp() {
        game = new Minesweeper();
        ui = new UI(game);
    }
    
    /**
     * UNIT-015
     * Test undo move
     * Ensures that the last move made by the player can be undone correctly.
     */
    @Test
    public void testUndoMove() {
        game.makeMove(0, 0, "G");
        ui.pushCurrentStateToUndoStack(); // Save the state before making another move
        game.makeMove(0, 1, "G");
        ui.undoMove(); // Undo the last move
        assertEquals("?", game.getCellState(0, 1), "Undo should revert the last move");
    }
    
    /**
     * UNIT-016
     * Test clear move
     * Ensures that the game board is cleared and reset correctly.
     */
    @Test
    public void testClearMove() {
        game.makeMove(1, 1, "G"); // Make a move to change the game state
        ui.clearGame(); // Clear the game
        assertEquals("?", game.getCellState(1, 1), "Cell state should be reset to '?'");
        assertEquals(3, game.getLives(), "Lives should be reset to 3");
    }
    
    /**
     * INTEGRATION-018
     * Test UI integration with Minesweeper
     * Ensures that the UI correctly updates and reflects the game state.
     */
    @Test
    public void testUIIntegrationWithMinesweeper() {
        ui.pushCurrentStateToUndoStack();
        game.makeMove(0, 0, "G");
        ui.undoMove();
        assertEquals("?", game.getCellState(0, 0), "The UI should correctly update the game state");
    }
    
    /**
     * UNIT-030
     * Test UI pushCurrentStateToUndoStack
     * Ensures that the current state of the game is pushed to the undo stack correctly.
     */
    @Test
    public void testPushCurrentStateToUndoStack() {
        game.makeMove(0, 0, "G");
        ui.pushCurrentStateToUndoStack();
        game.makeMove(0, 1, "G");
        ui.undoMove();
        assertEquals("?", game.getCellState(0, 1), "Undo should revert the last move");
    }
}
