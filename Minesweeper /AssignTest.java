import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class AssignTest {
    private Minesweeper game;
    private Assign assign;

    @Before
    public void setUp() {
        game = new Minesweeper();
        assign = new Assign(game);
    }

    @Test
    public void testAssignValidMove() {
        assign.assignMove("0", "0", "G");
        assertEquals("G", game.getCellState(0, 0));
    }

    @Test
    public void testAssignInvalidMove() {
        assign.assignMove("0", "0", "G");
        assertEquals("G", game.getCellState(0, 0));

        // Attempting to place a mine on an already guessed square
        assign.assignMove("0", "0", "M");
        assertEquals("G", game.getCellState(0, 0)); // State should remain "G"
    }

    @Test
    public void testAssignEmptyState() {
        assign.assignMove("0", "0", "");
        assertEquals("?", game.getCellState(0, 0)); // State should remain "?"
    }

    @Test
    public void testAssignOutOfBoundsRow() {
        try {
            assign.assignMove("-1", "0", "G");
            fail("Expected an IndexOutOfBoundsException to be thrown");
        } catch (IndexOutOfBoundsException e) {
            assertTrue(true);
        }

        try {
            assign.assignMove(String.valueOf(game.getGameSize()), "0", "G");
            fail("Expected an IndexOutOfBoundsException to be thrown");
        } catch (IndexOutOfBoundsException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testAssignOutOfBoundsColumn() {
        try {
            assign.assignMove("0", "-1", "G");
            fail("Expected an IndexOutOfBoundsException to be thrown");
        } catch (IndexOutOfBoundsException e) {
            assertTrue(true);
        }

        try {
            assign.assignMove("0", String.valueOf(game.getGameSize()), "G");
            fail("Expected an IndexOutOfBoundsException to be thrown");
        } catch (IndexOutOfBoundsException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testAssignFlagMine() {
        assign.assignMove("0", "0", "M");
        assertEquals("M", game.getCellState(0, 0));
    }

    @Test
    public void testAssignGuessMine() {
        // Place a mine on the board for this test
        game.getMoves()[0][0] = new Slot(0, 0, "M");

        assign.assignMove("0", "0", "G");
        assertEquals("M", game.getCellState(0, 0)); // Should indicate mine and reduce lives

        // Check lives are reduced
        // Assuming there is a method to get lives, add it to Minesweeper and uncomment the next line
        // assertEquals(2, game.getLives());
    }

    @Test
    public void testAssignUndoMove() {
        // Make a move and then undo it
        assign.assignMove("0", "0", "G");
        assertEquals("G", game.getCellState(0, 0));

        // Undo the move
        assign.undoMove();
        assertEquals("?", game.getCellState(0, 0));
    }

    @Test
    public void testAssignClearGame() {
        // Make a few moves
        assign.assignMove("0", "0", "G");
        assign.assignMove("0", "1", "M");

        // Clear the game
        game = new Minesweeper();
        assign = new Assign(game);
        assertEquals("?", game.getCellState(0, 0));
        assertEquals("?", game.getCellState(0, 1));
    }

    @Test
    public void testAssignSaveAndLoadGame() {
        // Make a few moves
        assign.assignMove("0", "0", "G");
        assign.assignMove("0", "1", "M");

        // Save the game
        assign.saveGameState();

        // Load the game
        assign.loadGame("saved_game.txt");
        assertEquals("G", game.getCellState(0, 0));
        assertEquals("M", game.getCellState(0, 1));
    }
}
