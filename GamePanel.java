import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The GamePanel class represents the main game board of the GUI Minesweeper game.
 * It handles the display and interaction of the game tiles.
 */
public class GamePanel extends JPanel {
    private Minesweeper theGame; // The Minesweeper game instance being managed
    private JButton[][] buttons; // 2D array of buttons representing the game board
    private boolean flagMode; // Boolean flag indicating whether the game is in flag mode
    private ControlPanel controlPanel; // Reference to the ControlPanel instance

    /**
     * Constructs the GamePanel with the given game instance, flag mode, and control panel.
     * 
     * @param theGame The Minesweeper game instance.
     * @param flagMode The initial flag mode state.
     * @param controlPanel The ControlPanel instance.
     */
    public GamePanel(Minesweeper theGame, boolean flagMode, ControlPanel controlPanel) {
        this.theGame = theGame;
        this.flagMode = flagMode;
        this.controlPanel = controlPanel;
        int gameSize = theGame.getGameSize();
        setLayout(new GridLayout(gameSize, gameSize)); // Sets the layout to a grid based on the game size
        buttons = new JButton[gameSize][gameSize];

        // Initialize buttons for each cell on the game board
        for (int row = 0; row < gameSize; row++) {
            for (int col = 0; col < gameSize; col++) {
                buttons[row][col] = new JButton("?");
                buttons[row][col].setFont(new Font("Arial", Font.BOLD, 28)); // Set font size to 28 for better visibility
                buttons[row][col].setActionCommand(row + " " + col); // Set the action command to the cell coordinates
                buttons[row][col].addActionListener(new ButtonClickListener()); // Add action listener to handle clicks
                add(buttons[row][col]); // Add button to the panel
            }
        }
    }

    /**
     * Sets the flag mode state.
     * 
     * @param flagMode The flag mode state to set.
     */
    public void setFlagMode(boolean flagMode) {
        this.flagMode = flagMode; // Updates the flag mode state
    }

    /**
     * Sets the ControlPanel instance.
     * 
     * @param controlPanel The ControlPanel instance to set.
     */
    public void setControlPanel(ControlPanel controlPanel) {
        this.controlPanel = controlPanel; // Updates the reference to the ControlPanel
    }

    /**
     * Handles button click events on the game board.
     */
    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Get the coordinates from the action command
            String[] coords = e.getActionCommand().split(" ");
            int row = Integer.parseInt(coords[0]);
            int col = Integer.parseInt(coords[1]);
            String result;

            // Save the current game state to the undo stack before making a move
            controlPanel.getGameUI().pushCurrentStateToUndoStack();

            // Determine the type of move based on the current mode (flag or guess)
            if (flagMode) {
                result = theGame.makeMove(row, col, "M"); // Flag mode: mark the cell as a mine
            } else {
                result = theGame.makeMove(row, col, "G"); // Guess mode: make a guess move
            }

            System.out.println(result); // Print the result of the move to the console
            updateBoard(); // Update the board to reflect the new game state
        }
    }

    /**
     * Updates the display of the game board based on the game state.
     */
    public void updateBoard() {
        int gameSize = theGame.getGameSize();

        // Update each button on the board to reflect its current state
        for (int row = 0; row < gameSize; row++) {
            for (int col = 0; col < gameSize; col++) {
                String state = theGame.getCellState(row, col);
                buttons[row][col].setText(state); // Update the button text

                // Update button appearance based on its state
                if (state.equals("M")) {
                    buttons[row][col].setBackground(Color.RED); // Mine state: set background to red
                    buttons[row][col].setForeground(Color.RED);
                } else if (state.matches("\\d")) {
                    buttons[row][col].setBackground(Color.WHITE); // Number state: set background to white
                    buttons[row][col].setForeground(Color.BLUE);
                } else if (state.equals("-")) {
                    buttons[row][col].setBackground(Color.WHITE); // Empty state: set background to white
                    buttons[row][col].setForeground(Color.BLACK);
                } else {
                    buttons[row][col].setBackground(Color.LIGHT_GRAY); // Default state: set background to light grey
                    buttons[row][col].setForeground(Color.BLACK); // Set default text colour to black
                }
            }
        }
    }

    /**
     * Gets the buttons array.
     * 
     * @return The buttons array.
     */
    public JButton[][] getButtons() {
        return buttons; // Returns the 2D array of buttons
    }
} // End of GamePanel Class.
 