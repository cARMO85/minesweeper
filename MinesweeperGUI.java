import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * The MinesweeperGUI class represents the main graphical user interface for the Minesweeper game.
 * It observes the Minesweeper game state and updates the UI accordingly.
 */
public class MinesweeperGUI extends JFrame implements Observer {
    private Minesweeper theGame;
    private GamePanel gamePanel;
    private ControlPanel controlPanel;
    private UI theUI;

    /**
     * Constructs the MinesweeperGUI with the given game instance.
     * 
     * @param theGame The Minesweeper game instance.
     */
    public MinesweeperGUI(Minesweeper theGame) {
        this.theGame = theGame;
        this.theGame.addObserver(this); // Adds the GUI as an observer to the game state

        setTitle("Minesweeper Assessment");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        theUI = new UI(theGame); // Initialise the UI with theGame but do not start the game loop
        
        // Step 1: Initialise without dependencies
        controlPanel = new ControlPanel(theGame, null, theUI);
        gamePanel = new GamePanel(theGame, false, null);

        // Step 2: Set the dependencies
        controlPanel.setGamePanel(gamePanel); // Set the game panel in the control panel
        gamePanel.setControlPanel(controlPanel); // Set the control panel in the game panel

        add(gamePanel, BorderLayout.CENTER); // Adds the game panel to the center of the frame
        add(controlPanel, BorderLayout.EAST); // Adds the control panel to the right side of the frame

        setVisible(true); // Makes the GUI visible
    }

    /**
     * Updates the GUI when the observed object changes.
     * 
     * @param o The observable object.
     * @param arg An argument passed to the notifyObservers method.
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Minesweeper) { // Ensure the update is from the Minesweeper instance
            gamePanel.updateBoard(); // Update the game board panel
            controlPanel.update(); // Update the control panel

            // Check if the game is won and prompt the user accordingly
            if (theGame.checkWin().equals("won")) {
                int choice = JOptionPane.showOptionDialog(this,
                        "Wahoo, you solved the level! Do you want to play again?",
                        "Game Won",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new String[]{"Play Again", "Quit"},
                        "Play Again");
                if (choice == JOptionPane.YES_OPTION) {
                    theGame.reset(); // Reset the game if the user wants to play again
                } else {
                    System.exit(0); // Exit the game if the user chooses to quit
                }
            } else if (theGame.getLives() <= 0) { // Check if the player has run out of lives
                int choice = JOptionPane.showOptionDialog(this,
                        "You lose!No more lives, the game is over. Do you want to play again?",
                        "Game Over",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new String[]{"Play Again", "Quit"},
                        "Play Again");
                if (choice == JOptionPane.YES_OPTION) {
                    theGame.reset(); // Reset the game if the user wants to play again
                } else {
                    System.exit(0); // Exit the game if the user chooses to quit
                }
            }
        }
    }

    /**
     * Restarts the game by resetting the game state and updating the UI.
     * This method provides a convenient way to reset the game from other parts of the GUI.
     */
    private void restartGame() {
        theGame.reset(); // Reset the game state
        gamePanel.updateBoard(); // Update the game board panel
        controlPanel.update(); // Update the control panel
    }

    /**
     * The main method to start the Minesweeper game with a GUI.
     * 
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Minesweeper game = new Minesweeper(); // Create a new Minesweeper game instance
        new MinesweeperGUI(game); // Create and show the GUI
    }
} // End of MinesweeperGUI Class
