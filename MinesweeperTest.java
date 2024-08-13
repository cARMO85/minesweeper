import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class MinesweeperTest.
 *
 * @version 1.0
 */
public class MinesweeperTest {

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
     * UNIT-001
     * Test game initiasation
     * Ensures the game is initialised correctly.
     */
    @Test
    public void testGameInitialisation() {
        assertNotNull(game.getMoves(), "Game moves should be initialised");
        assertEquals(5, game.getGameSize(), "Game size should be 5x5");
    }

    /**
     * UNIT-003
     * Test makeMove with valid guess
     * Ensures making a move with a valid guess updates the cell state correctly.
     */
    @Test
    public void testMakeMoveWithValidGuess() {
        String result = game.makeMove(1, 1, "G");
        assertEquals("Phew! You survived this time!", result);
        assertEquals("1", game.getCellState(1, 1), "Cell state should be '1' after a valid guess");
    }

    /**
     * UNIT-004
     * Test makeMove with mine hit
     * Ensures making a move that hits a mine updates the lives and cell state correctly.
     */
    @Test
    public void testMakeMoveWithMineHit() {
        String result = game.makeMove(0, 0, "G");
        assertTrue(result.contains("You have lost one life."), "Result should indicate a life loss when hitting a mine");
    }

    /**
     * UNIT-005
     * Test win game scenario
     * Simulates a sequence of moves that lead to a win and ensures the game detects the win condition.
     */
    @Test
    public void testWinGameScenario() {
        // Simulate moves that lead to a win
        game.makeMove(0, 0, "M");
        game.makeMove(4, 0, "M");
        game.makeMove(0, 4, "M");
        game.makeMove(1, 4, "M");
        // Guess all other squares
        game.makeMove(0, 1, "G");
        game.makeMove(0, 2, "G");
        game.makeMove(0, 3, "G");
        game.makeMove(1, 0, "G");
        game.makeMove(1, 1, "G");
        game.makeMove(1, 2, "G");
        game.makeMove(1, 3, "G");
        game.makeMove(2, 0, "G");
        game.makeMove(2, 1, "G");
        game.makeMove(2, 2, "G");
        game.makeMove(2, 3, "G");
        game.makeMove(2, 4, "G");
        game.makeMove(3, 0, "G");
        game.makeMove(3, 1, "G");
        game.makeMove(3, 2, "G");
        game.makeMove(3, 3, "G");
        game.makeMove(3, 4, "G");
        game.makeMove(4, 1, "G");
        game.makeMove(4, 2, "G");
        game.makeMove(4, 3, "G");
        game.makeMove(4, 4, "G");

        assertEquals("won", game.checkWin(), "Game should be won after all correct moves are made");
    }

    /**
     * UNIT-006
     * Test lose all lives scenario
     * Simulates a sequence of moves that deplete all lives and ensures the game detects the loss condition.
     */
    @Test
    public void testLoseAllLivesScenario() {
        assertEquals("continue", game.checkWin(), "Game should continue initially");
        
        // Deplete all lives
        game.makeMove(0, 0, "G"); // There's a mine at (0, 0)
        game.makeMove(0, 4, "G"); // There's a mine at (0, 4)
        game.makeMove(4, 0, "G"); // There's a mine at (4, 0)

        assertEquals("lives", game.checkWin(), "Game should be lost when all lives are depleted");
    }
    
     /**
     * UNIT-007
     * Test get and set lives
     * Ensures that the lives of the game can be set and retrieved correctly.
     */
    @Test
    public void testGetAndSetLives() {
        game.setLives(5);
        assertEquals(5, game.getLives(), "Lives should be set to 5");
    }
    
    /**
     * UNIT-008
     * Test reset game
     * Ensures that the game board and lives are reset correctly.
     */
    @Test
    public void testResetGame() {
        game.makeMove(1, 1, "G"); // Make a move to change the game state
        game.reset(); // Reset the game
        assertEquals("?", game.getCellState(1, 1), "Cell state should be reset to '?'");
        assertEquals(3, game.getLives(), "Lives should be reset to 3");
    }


    /**
     * UNIT-009
     * Test boundary conditions
     * Ensures making a move out of bounds throws an IndexOutOfBoundsException.
     */
    @Test
    public void testBoundaryConditions() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            game.makeMove(-1, 1, "G");
        }, "Making a move out of bounds should throw IndexOutOfBoundsException");
        assertThrows(IndexOutOfBoundsException.class, () -> {
            game.makeMove(1, 5, "G");
        }, "Making a move out of bounds should throw IndexOutOfBoundsException");
    }

    /**
     * UNIT-010
     * Test invalid moves
     * Ensures making an invalid move returns an appropriate message.
     */
    @Test
    public void testInvalidMoves() {
        String result = game.makeMove(1, 1, "X");
        assertEquals("Nope - this was not a mine", result, "Invalid move should return an unsuccessful message");
    }

    /**
     * UNIT-011
     * Test move boundaries
     * Ensures moves at the boundaries are processed correctly and moves outside the boundaries throw exceptions.
     */
    @Test
    public void testMoveBoundaries() {
        int gameSize = game.getGameSize();

        // Test moves at the boundaries
        assertDoesNotThrow(() -> game.makeMove(0, 0, "G"), "Move at (0, 0) should not throw exception");
        assertDoesNotThrow(() -> game.makeMove(gameSize - 1, gameSize - 1, "G"), "Move at (size-1, size-1) should not throw exception");

        // Test invalid moves outside the boundaries
        assertThrows(IndexOutOfBoundsException.class, () -> game.makeMove(-1, 0, "G"), "Move at (-1, 0) should throw IndexOutOfBoundsException");
        assertThrows(IndexOutOfBoundsException.class, () -> game.makeMove(0, -1, "G"), "Move at (0, -1) should throw IndexOutOfBoundsException");
        assertThrows(IndexOutOfBoundsException.class, () -> game.makeMove(gameSize, 0, "G"), "Move at (size, 0) should throw IndexOutOfBoundsException");
        assertThrows(IndexOutOfBoundsException.class, () -> game.makeMove(0, gameSize, "G"), "Move at (0, size) should throw IndexOutOfBoundsException");
    }
    
     /**
     * UNIT-012
     * Test lives boundaries
     * Ensures that the lives are correctly set to minimum and maximum values, and the game ends when lives reach zero.
     */
    @Test
    public void testLivesBoundaries() {
        game.setLives(0);
        assertEquals(0, game.getLives(), "Lives should be set to 0");
        assertEquals("lives", game.checkWin(), "Game should end when lives are 0");

        game.setLives(10);
        assertEquals(10, game.getLives(), "Lives should be set to 10");
    }
    
    /**
     * BOUNDARY-020
     * Test invalid move boundaries
     * Ensures that making moves with invalid coordinates throws an IndexOutOfBoundsException.
     */
    @Test
    public void testInvalidMoveBoundaries() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            game.makeMove(-1, 20, "G");
        }, "Making a move with invalid coordinates should throw IndexOutOfBoundsException");
    }
    

    
    /**
     * UNIT-032
     * Test UI initialisation with default game
     * Ensures the UI is initialised correctly with the default Minesweeper game.
     */
    @Test
    public void testUIInitialisationWithDefaultGame() {
        UI ui = new UI(game);
        assertNotNull(ui, "UI should be initialised with default Minesweeper game");
    }

    /**
     * UNIT-034
     * Test Minesweeper move processing
     * Ensures moves are processed correctly in the Minesweeper game.
     */
    @Test
    public void testMinesweeperMoveProcessing() {
        game.makeMove(0, 0, "M");
        assertEquals("M", game.getCellState(0, 0), "Move should be processed correctly");

        game.makeMove(1, 1, "G");
        assertEquals("1", game.getCellState(1, 1), "Move should be processed correctly");
    }
    
    /**
     * UNIT-035
     * Test Minesweeper mine hit
     * Ensures hitting a mine correctly decrements the player's lives.
     */
    @Test
    public void testMinesweeperMineHit() {
        game.makeMove(0, 0, "G");
        assertEquals(2, game.getLives(), "Life should be decremented when hitting a mine");
    }

    /**
     * UNIT-036
     * Test Minesweeper win condition
     * Simulates moves that lead to a win and ensures the game detects the win condition.
     */
    @Test
    public void testMinesweeperWinCondition() {
        // Simulate moves that lead to a win
        game.makeMove(0, 0, "M");
        game.makeMove(4, 0, "M");
        game.makeMove(0, 4, "M");
        game.makeMove(1, 4, "M");
        // Guess all other squares
        game.makeMove(0, 1, "G");
        game.makeMove(0, 2, "G");
        game.makeMove(0, 3, "G");
        game.makeMove(1, 0, "G");
        game.makeMove(1, 1, "G");
        game.makeMove(1, 2, "G");
        game.makeMove(1, 3, "G");
        game.makeMove(2, 0, "G");
        game.makeMove(2, 1, "G");
        game.makeMove(2, 2, "G");
        game.makeMove(2, 3, "G");
        game.makeMove(2, 4, "G");
        game.makeMove(3, 0, "G");
        game.makeMove(3, 1, "G");
        game.makeMove(3, 2, "G");
        game.makeMove(3, 3, "G");
        game.makeMove(3, 4, "G");
        game.makeMove(4, 1, "G");
        game.makeMove(4, 2, "G");
        game.makeMove(4, 3, "G");
        game.makeMove(4, 4, "G");

        assertEquals("won", game.checkWin(), "Game should be won after all correct moves are made");
    }

    /**
     * UNIT-037
     * Test Minesweeper lose condition
     * Simulates moves that deplete all lives and ensures the game detects the loss condition.
     */
    @Test
    public void testMinesweeperLoseCondition() {
        assertEquals("continue", game.checkWin(), "Game should continue initially");
        
        // Deplete all lives
        game.makeMove(0, 0, "G"); // There's a mine at (0, 0)
        game.makeMove(0, 4, "G"); // There's a mine at (0, 4)
        game.makeMove(4, 0, "G"); // There's a mine at (4, 0)

        assertEquals("lives", game.checkWin(), "Game should be lost when all lives are depleted");
    }

    /**
     * UNIT-038
     * Test Minesweeper boundary conditions
     * Ensures making a move out of bounds throws an IndexOutOfBoundsException.
     */
    @Test
    public void testMinesweeperBoundaryConditions() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            game.makeMove(-1, 1, "G");
        }, "Making a move out of bounds should throw IndexOutOfBoundsException");
        assertThrows(IndexOutOfBoundsException.class, () -> {
            game.makeMove(1, 5, "G");
        }, "Making a move out of bounds should throw IndexOutOfBoundsException");
    }

    /**
     * UNIT-039
     * Test Minesweeper invalid moves
     * Ensures making an invalid move returns an appropriate message.
     */
    @Test
    public void testMinesweeperInvalidMoves() {
        String result = game.makeMove(1, 1, "X");
        assertEquals("Nope - this was not a mine", result, "Invalid move should return an unsuccessful message");
    }

    /**
     * UNIT-040
     * Test Minesweeper boundary move processing
     * Ensures moves at the boundaries are processed correctly and moves outside the boundaries throw exceptions.
     */
    @Test
    public void testMinesweeperBoundaryMoveProcessing() {
        int gameSize = game.getGameSize();

        // Test moves at the boundaries
        assertDoesNotThrow(() -> game.makeMove(0, 0, "G"), "Move at (0, 0) should not throw exception");
        assertDoesNotThrow(() -> game.makeMove(gameSize - 1, gameSize - 1, "G"), "Move at (size-1, size-1) should not throw exception");

        // Test invalid moves outside the boundaries
        assertThrows(IndexOutOfBoundsException.class, () -> game.makeMove(-1, 0, "G"), "Move at (-1, 0) should throw IndexOutOfBoundsException");
        assertThrows(IndexOutOfBoundsException.class, () -> game.makeMove(0, -1, "G"), "Move at (0, -1) should throw IndexOutOfBoundsException");
        assertThrows(IndexOutOfBoundsException.class, () -> game.makeMove(gameSize, 0, "G"), "Move at (size, 0) should throw IndexOutOfBoundsException");
        assertThrows(IndexOutOfBoundsException.class, () -> game.makeMove(0, gameSize, "G"), "Move at (0, size) should throw IndexOutOfBoundsException");
    }
}
