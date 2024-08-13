import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;

/**
 * The test class GamePanelTest.
 *
 * @version 2.0
 */
public class GamePanelTest {
    private Minesweeper game;
    private GamePanel gamePanel;
    private ControlPanel controlPanel;
    private UI ui;

    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @BeforeEach
    public void setUp() {
        game = new Minesweeper();
        ui = new UI(game);
        controlPanel = new ControlPanel(game, gamePanel, ui);
        gamePanel = new GamePanel(game, false, controlPanel);
    }

    /**
     * UNIT-028
     * Test GamePanel button click in flag mode
     * Ensures that clicking a button in flag mode updates the cell state to 'M'.
     */
    @Test
    public void testButtonClickFlagMode() {
        gamePanel.setFlagMode(true);
        gamePanel.getButtons()[0][0].doClick();
        assertEquals("M", game.getCellState(0, 0), "Cell state should be updated to 'M' in flag mode");
    }
}
