import javax.swing.*;

/**
 * The MainApp class serves as the entry point for the Minesweeper application.
 * It allows users to select between a graphical user interface (GUI) and a text-based user interface (UI),
 * or to exit the application.
 * 
 * @version 2.0
 * @author Paul Carmody
 */
public class MainApp {
    /**
     * The main method to start the Minesweeper application.
     * 
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        String[] options = {"Graphical User Interface", "Text-Based User Interface", "Exit"};
        
        // Show a dialogue to allow the user to select the desired interface.
        int choice = JOptionPane.showOptionDialog(null, 
                                                  "Select the interface you want to use:",
                                                  "Minesweeper",
                                                  JOptionPane.DEFAULT_OPTION, 
                                                  JOptionPane.INFORMATION_MESSAGE, 
                                                  null, options, options[0]);

        // Handle the user's choice and launch the corresponding interface.
        switch (choice) {
            case 0:
                Minesweeper game = new Minesweeper(); // Initialise the game logic.
                new MinesweeperGUI(game); // Launch the graphical user interface.
                break;
            case 1:
                new UI(); // Launch the text-based user interface.
                break;
            case 2:
                System.exit(0); // Exit the application.
                break;
            default:
                System.exit(0); // Exit the application for any other input.
        }
    }
}
