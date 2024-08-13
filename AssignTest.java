import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class AssignTest.
 *
 * @version 2.0
 */
public class AssignTest {

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
     * UNIT-013
     * Test Assign constructor
     * Ensures the Assign constructor correctly assigns a move in the Minesweeper game.
     */
    @Test
    public void testAssignConstructor() {
        Assign assign = new Assign(game, 1, 1, "G");
        assertEquals("G", game.getCellState(1, 1), "Ensure the cell state is set correctly");
    }

    /**
     * UNIT-014
     * Test Assign move
     * Ensures the assignMove method correctly updates the state of the slot.
     */
    @Test
    public void testAssignMove() {
        Assign assign = new Assign(game, 2, 2, "M");
        assertEquals("M", game.getCellState(2, 2), "Ensure the move is assigned correctly");
    }

    /**
     * UNIT-019
     * Test Assign integration with Minesweeper
     * Ensures that the Assign class properly integrates with the Minesweeper game.
     */
    @Test
    public void testAssignIntegrationWithMinesweeper() {
        Assign assign1 = new Assign(game, 1, 1, "1");
        assertEquals("1", game.getCellState(1, 1), "The cell state should be '1' after assignment");

        Assign assign2 = new Assign(game, 0, 0, "M");
        assertEquals("M", game.getCellState(0, 0), "The cell state should be 'M' after assignment");
    }

    /**
     * UNIT-031
     * Test Assign state setting
     * Ensures that the state of a slot can be set correctly through the Assign class.
     */
    @Test
    public void testSetState() {
        Assign assign = new Assign(game, 4, 4, "?");
        assign.setState(4, 4, "M");
        assertEquals("M", game.getCellState(4, 4), "The cell state should be updated to 'M'");
    }

    /**
     * Test the getRow and getCol methods
     * Ensures that the row and column values can be retrieved correctly.
     */
    @Test
    public void testGetRowAndCol() {
        Assign assign = new Assign(game, 3, 3, "G");
        assertEquals(3, assign.getRow(), "The row value should be 3");
        assertEquals(3, assign.getCol(), "The column value should be 3");
    }
}
