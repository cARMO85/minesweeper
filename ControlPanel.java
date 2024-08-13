import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * The ControlPanel class provides the control interface for the Minesweeper game.
 * It includes buttons for toggling modes, saving, loading, undoing, and clearing the game.
 */
public class ControlPanel extends JPanel {
    private Minesweeper theGame;
    private UI theUI;
    private JLabel livesLabel;
    private JLabel modeLabel;
    private JProgressBar progressBar; // For improved UX
    private boolean flagMode;
    private GamePanel gamePanel;

    /**
     * Constructs the ControlPanel with the provided game instance, game panel, and UI.
     * 
     * @param theGame The Minesweeper game instance.
     * @param gamePanel The panel displaying the game board.
     * @param theUI The text-based UI instance.
     */
    public ControlPanel(Minesweeper theGame, GamePanel gamePanel, UI theUI) {
        // Initialise instance variables with the provided parameters
        this.theGame = theGame;
        this.theUI = theUI;
        this.gamePanel = gamePanel;
        this.flagMode = false; // Default mode is Guess mode
    
        // Set the layout manager and panel properties
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding around the panel
        setBackground(Color.ORANGE); // Set the background color of the panel
    
        // Define fonts for labels and buttons
        Font labelFont = new Font("Arial", Font.BOLD, 24);
        Font buttonFont = new Font("Arial", Font.BOLD, 20);
    
        // Create and configure the lives label
        livesLabel = new JLabel("Lives: " + theGame.getLives());
        livesLabel.setFont(labelFont);
    
        // Create and configure the mode label
        modeLabel = new JLabel("Mode: Guess");
        modeLabel.setFont(labelFont);
    
        // Create and configure the progress bar
        progressBar = new JProgressBar(0, theGame.getGameSize() * theGame.getGameSize());
        progressBar.setStringPainted(true); // Display the progress percentage
        progressBar.setValue(0); // Initial value is 0
    
        // Create and configure the toggle button
        JButton toggleButton = new JButton("Toggle");
        toggleButton.setFont(buttonFont);
        toggleButton.addActionListener(e -> toggleMode()); // Add action listener to handle mode toggling
    
        // Create and configure the save button
        JButton saveButton = new JButton("Save");
        saveButton.setFont(buttonFont);
        saveButton.addActionListener(e -> saveGame()); // Add action listener to handle game saving
    
        // Create and configure the undo button
        JButton undoButton = new JButton("Undo");
        undoButton.setFont(buttonFont);
        undoButton.addActionListener(e -> undoMove()); // Add action listener to handle undoing moves
    
        // Create and configure the clear button
        JButton clearButton = new JButton("Clear");
        clearButton.setFont(buttonFont);
        clearButton.addActionListener(e -> clearGame()); // Add action listener to handle clearing the game
    
        // Create and configure the load button
        JButton loadButton = new JButton("Load");
        loadButton.setFont(buttonFont);
        loadButton.addActionListener(e -> loadGame()); // Add action listener to handle game loading
    
        // Add components to the panel
        add(livesLabel);
        add(modeLabel);
        add(progressBar);
        add(Box.createRigidArea(new Dimension(0, 20))); // Adds some vertical spacing
        add(toggleButton);
        add(Box.createRigidArea(new Dimension(0, 30))); // Adds some vertical spacing
        add(saveButton);
        add(Box.createRigidArea(new Dimension(0, 10))); // Adds some vertical spacing
        add(undoButton);
        add(Box.createRigidArea(new Dimension(0, 10))); // Adds some vertical spacing
        add(clearButton);
        add(Box.createRigidArea(new Dimension(0, 10))); // Adds some vertical spacing
        add(loadButton);
    }

    /**
     * Sets the GamePanel instance.
     * 
     * @param gamePanel The GamePanel instance to set.
     */
    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Gets the UI instance.
     * 
     * @return The UI instance.
     */
    public UI getGameUI() {
        return theUI;
    }

    /**
     * Toggles the game mode between flagging and guessing.
     */
    private void toggleMode() {
        flagMode = !flagMode;
        gamePanel.setFlagMode(flagMode);
        modeLabel.setText("Mode: " + (flagMode ? "Flag" : "Guess"));
        modeLabel.setForeground(flagMode ? Color.RED : Color.BLACK);
    }

    /**
     * Saves the game state to a file.
     */
    private void saveGame() {
        JFileChooser fileChooser = new JFileChooser(); // Creates a file chooser for selecting the save location
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) { // Opens the save dialog and checks if the user approves the save action
            File file = fileChooser.getSelectedFile(); // Gets the selected file from the file chooser
            saveGameToFile(file); // Calls the method to save the game state to the selected file
            JOptionPane.showMessageDialog(this, "Game saved successfully!"); // Displays a confirmation message to the user
        }
    }
    
    /**
     * Saves the game state to the specified file.
     * 
     * @param file The file to save the game state to.
     */
    public void saveGameToFile(File file) {
        // Utilises the FileHandler class to save the game state to the specified file
        FileHandler.saveGame(theGame, file.getAbsolutePath());
    }
    
    /**
     * Undoes the last move.
     */
    private void undoMove() {
        // Delegates the undo action to the UI class, which handles the undo functionality
        theUI.undoMove();
    }
    
    /**
     * Clears the game state.
     */
    private void clearGame() {
        // Delegates the clear action to the UI class, which handles clearing the game state
        theUI.clearGame();
    }
    
    /**
     * Loads the game state from a file.
     */
    private void loadGame() {
        JFileChooser fileChooser = new JFileChooser(); // Creates a file chooser for selecting the load file
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { // Opens the load dialog and checks if the user approves the load action
            File file = fileChooser.getSelectedFile(); // Gets the selected file from the file chooser
            // Utilises the FileHandler class to load the game state from the specified file
            FileHandler.loadGame(theGame, file.getAbsolutePath());
            JOptionPane.showMessageDialog(this, "Game loaded successfully!"); // Displays a confirmation message to the user
        }
    }

    /**
     * Updates the control panel display.
     */
    public void update() {
        livesLabel.setText("Lives: " + theGame.getLives());
        progressBar.setValue(getProgress());
    }

    /**
     * Calculates and returns the current progress of the game.
     * 
     * @return The current progress of the game.
     */
    private int getProgress() {
        int progress = 0;
        Slot[][] board = theGame.getBoard();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (!board[i][j].getState().equals("?")) {
                    progress++;
                }
            }
        }
        return progress;
    }
} // End of ControlPanel Class.
