import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * The FileHandler class handles the saving and loading of game states for the Minesweeper game.
 * 
 * @version 2.0
 */
public class FileHandler {

    /**
     * Saves the current game state to a specified file.
     * 
     * @param theGame  The current Minesweeper game instance.
     * @param filename The name of the file to save the game state.
     */
    public static void saveGame(Minesweeper theGame, String filename) {
        try {
            PrintWriter writer = new PrintWriter(filename); // Create a PrintWriter object to write to the file
            Slot[][] board = theGame.getMoves(); // Retrieve the current game board

            // Write the state of each slot to the file
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    writer.print(board[i][j].getState() + " ");
                }
                writer.println();
            }
            writer.println(theGame.getLives()); // Write the number of lives to the file
            writer.close(); // Close the PrintWriter to free up resources
            System.out.println("Game saved successfully!");
        } catch (FileNotFoundException e) { // Handle any file not found exceptions
            System.out.println("Error saving game.");
            e.printStackTrace();
        }
    }

    /**
     * Loads a game state from a specified file.
     * 
     * @param theGame  The current Minesweeper game instance.
     * @param filename The name of the file to load the game state from.
     */
    public static void loadGame(Minesweeper theGame, String filename) {
        try {
            File file = new File(filename); // Create a File object to read from the specified filename
            Scanner scanner = new Scanner(file); // Create a Scanner object to read from the file
            Slot[][] board = new Slot[theGame.getGameSize()][theGame.getGameSize()]; // Initialise the game board with the correct size

            // Read the state of each slot from the file and populate the game board
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    String state = scanner.next(); // Read the state of each slot from the file
                    board[i][j] = new Slot(j, i, state); // Create a new slot object with the read state and assign it to the board
                }
            }
            int lives = scanner.nextInt(); // Read the number of lives from the file
            theGame.setBoard(board); // Set the game board with the loaded state
            theGame.setLives(lives); // Set the number of lives with the loaded value
            scanner.close(); // Close the Scanner to free up resources
            System.out.println("Game loaded successfully!");
        } catch (FileNotFoundException e) { // Handle any file not found exceptions
            System.out.println("Error loading the game.");
            e.printStackTrace();
        }
    }
} // End of FileHandler Class
