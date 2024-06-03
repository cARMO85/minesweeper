import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class MinesweeperTest.
 * This class contains unit tests for the Minesweeper class.
 */
public class MinesweeperTest {
    private Minesweeper game;

    /**
     * Sets up the test fixture.
     * Called before every test case method.
     */
    @Before
    public void setUp() {
        game = new Minesweeper();
    }

    /**
     * Tears down the test fixture.
     * Called after every test case method.
     */
    @After
    public void tearDown() {
        game = null;
    }

    /**
     * Tests the initialisation of the game board.
     * Ensures that the game and moves array are created, and the game size is set to 5.
     */
    @Test
    public void testBoardInitialisation() {
        assertNotNull(game);
        assertNotNull(game.getMoves()); // Ensures that the moves array is created
        assertEquals(5, game.getGameSize()); // Default game size is set to 5
    }

    /**
     * Tests reading the level file.
     * Ensures that the level file is read correctly and specific slots are set as expected.
     */
    @Test
    public void testReadLevelFile() {
        String[][] result = game.readLevelFile();
        assertNotNull(result);
        assertEquals("M", result[0][0]);
        assertEquals("1", result[0][1]);
        assertEquals("-", result[0][2]);
        assertEquals("2", result[0][3]);
        assertEquals("M", result[0][4]);
    }

    /**
     * Tests flagging a mine with an upper-case input.
     * Ensures that flagging a mine works with an upper-case input.
     */
    @Test
    public void testMakeMoveFlagMineUpperCase() {
        game.readLevelFile();
        game.makeMove("0", "0", "M");
        assertEquals("M", game.getIndividualMove(0, 0));
    }

    /**
     * Tests flagging a mine with a lower-case input.
     * Ensures that flagging a mine works with a lower-case input.
     */
    @Test
    public void testMakeMoveFlagMineLowerCase() {
        game.readLevelFile();
        game.makeMove("0", "0", "m");
        assertEquals("M", game.getIndividualMove(0, 0));
    }

    /**
     * Tests guessing a mine with an upper-case input.
     * Ensures that guessing a mine returns the expected message and updates the board state correctly.
     */
    @Test
    public void testMakeMoveGuessMineUpperCase() {
        game.readLevelFile();
        String result = game.makeMove("0", "4", "G");
        assertEquals("You have lost one life. \nNew life total: 2", result);
        assertEquals("M", game.getIndividualMove(0, 4));
    }

    /**
     * Tests guessing a mine with a lower-case input.
     * Ensures that guessing a mine returns the expected message and updates the board state correctly.
     */
    @Test
    public void testMakeMoveGuessMineLowerCase() {
        game.readLevelFile();
        String result = game.makeMove("0", "4", "g");
        assertEquals("You have lost one life. \nNew life total: 2", result);
        assertEquals("M", game.getIndividualMove(0, 4));
    }

    /**
     * Tests guessing an empty slot.
     * Ensures that guessing an empty slot sets the state correctly and returns the appropriate message.
     */
    @Test
    public void testMakeMoveGuessEmpty() {
        game.readLevelFile();
        String result = game.makeMove("1", "2", "G");
        assertEquals("You have survived this time", result);
        assertEquals("0", game.getIndividualMove(1, 2));
    }

    /**
     * Tests the game state when the game is still ongoing.
     * Ensures that the game continues if not all mines are flagged or guessed.
     */
    @Test
    public void testCheckWinContinue() {
        game.readLevelFile();
        assertEquals("continue", game.checkWin());
    }

    /**
     * Tests the game state when the game is won.
     * Simulates a series of moves to win the game and checks the win condition.
     */
    @Test
    public void testCheckWinWon() {
        game.readLevelFile();
        // Simulate moves that would result in winning the game
        game.makeMove("0", "1", "G");
        game.makeMove("0", "2", "G");
        game.makeMove("0", "3", "G");
        game.makeMove("1", "0", "G");
        game.makeMove("1", "1", "G");
        game.makeMove("1", "2", "G");
        game.makeMove("1", "3", "G");
        game.makeMove("2", "0", "G");
        game.makeMove("2", "1", "G");
        game.makeMove("2", "2", "G");
        game.makeMove("2", "3", "G");
        game.makeMove("3", "0", "G");
        game.makeMove("3", "1", "G");
        game.makeMove("3", "2", "G");
        game.makeMove("3", "3", "G");
        game.makeMove("4", "1", "G");
        game.makeMove("4", "2", "G");
        game.makeMove("4", "3", "G");
        // Final move to ensure win
        game.makeMove("1", "4", "G");
        assertEquals("won", game.checkWin());
    }

    /**
     * Tests the game state when all lives are lost.
     * Simulates moves to lose all lives and checks the lose condition.
     */
    @Test
    public void testCheckWinLives() {
        game.readLevelFile();
        // Simulate moves that would result in losing all lives
        game.makeMove("0", "0", "G");
        game.makeMove("0", "4", "G");
        game.makeMove("1", "4", "G");
        assertEquals("lives", game.checkWin());
    }

    /**
     * Tests the boundary conditions for the makeMove method.
     * Ensures that out-of-bounds moves are handled correctly.
     */
    @Test
    public void testMakeMoveOutOfBounds() {
        game.readLevelFile();
        try {
            game.makeMove("5", "5", "G");
            fail("Expected ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Test passes
        }
    }

    /**
     * Tests the getCellState method.
     * Ensures that the correct state is returned for given coordinates.
     */
    @Test
    public void testGetCellState() {
        game.readLevelFile();
        assertEquals("?", game.getCellState(0, 0));
        game.makeMove("0", "1", "G");
        assertEquals("1", game.getCellState(0, 1));
    }

    /**
     * Tests the makeMove method with invalid inputs.
     * Ensures that invalid inputs are handled correctly.
     */
    @Test
    public void testMakeMoveInvalidInputs() {
        game.readLevelFile();
        try {
            game.makeMove("a", "b", "G");
            fail("Expected NumberFormatException");
        } catch (NumberFormatException e) {
            // Test passes
        }
    }

}
