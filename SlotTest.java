import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class SlotTest.
 *
 * @version 2.0
 */
public class SlotTest {
    private Slot slot;

    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @BeforeEach
    public void setUp() {
        slot = new Slot(0, 0, "?");
    }

    /**
     * UNIT-002
     * Test set and get state
     * Ensures that the state of the slot can be set and retrieved correctly.
     */
    @Test
    public void testSetAndGetState() {
        slot.setState("M");
        assertEquals("M", slot.getState(), "State should be 'M' after setting it");
        
        slot.setState("1");
        assertEquals("1", slot.getState(), "State should be '1' after setting it");
    }
}
