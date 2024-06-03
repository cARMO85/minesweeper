import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Scanner;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class UITest {
    @Test
    public void testHandleInvalidInput() {
        // Simulate invalid input followed by valid input
        String simulatedInput = "o\n0\n1\n";
        Scanner simulatedScanner = new Scanner(simulatedInput);

        // Create a UI instance with the simulated scanner
        Minesweeper game = new Minesweeper();
        UI ui = new UI(game, simulatedScanner);

        // Capture the output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        // Call the method to be tested
        ui.handleMove("G");

        // Check the output for the correct error message
        String output = outputStream.toString();
        assertTrue(output.contains("Invalid input. Please enter numeric values for row and column."));
    }
}
