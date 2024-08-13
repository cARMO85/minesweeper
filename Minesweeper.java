import java.io.File;
import java.io.FileNotFoundException;
import java.util.Observable; // 
import java.util.Scanner;

/**
 * This class represents the Minesweeper game, handling the core game logic, 
 * managing the game board, player moves, and win conditions.
 * It extends Observable to notify the GUI of changes in the game state.
 * 
 * @authors: Lauren Scott & Paul Carmody
 * @version 2.0
 */
public class Minesweeper extends Observable {
    private String[][] gameBoard; // Array to store the board currently displayed to the game
    private Slot[][] playerBoard; // Array to store the player's moves
    private Scanner reader; // Scanner to read the game and level files
    private int gameSize; // The size of the game
    private String level = "Levels/em1.txt"; // The level file, changeable for different difficulties
    private int lives = 3; // The number of lives the player has

    /**
     * Constructs a Minesweeper game instance by initialising the game board,
     * player board, and reading the level file.
     */
    public Minesweeper() {
        try {
            reader = new Scanner(new File(level));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        gameSize = calculateGameSize();
        gameBoard = new String[gameSize][gameSize];
        playerBoard = new Slot[gameSize][gameSize];
        readLevelFile(); // Populate the game and player boards by reading the level file
    }

    /**
     * Returns the entire set of player moves for the game.
     * 
     * @return The current state of the player's moves as a 2D array of Slots.
     */
    public Slot[][] getMoves() {
        return playerBoard;
    }

    /**
     * Returns the state of an individual cell.
     * 
     * @param row the row of the cell.
     * @param col the column of the cell.
     * @return The state of the specified cell.
     */
    public String getIndividualMove(int row, int col) {
        return playerBoard[row][col].getState();
    }

    /**
     * Calculates and returns the size of the game board by reading from the level file.
     * 
     * @return The size of the game board.
     */
    public int calculateGameSize() {
        return Integer.parseInt(reader.next());
    }

    /**
     * Provides access to the game size.
     * 
     * @return The size of the game board.
     */
    public int getGameSize() {
        return gameSize;
    }

    /**
     * Reads the level file to populate the game board and initialise player moves.
     * 
     * @return The game board populated with the level's data.
     */
    public String[][] readLevelFile() {
        while (reader.hasNext()) {
            int row = Integer.parseInt(reader.next());
            int col = Integer.parseInt(reader.next());
            String move = reader.next();

            gameBoard[row][col] = move;
            playerBoard[row][col] = new Slot(row, col, "?");
        }
        return gameBoard;
    }

    /**
     * Checks whether the game has been won, lost, or is still ongoing.
     * 
     * @return "won" if the player has won, "lives" if the player has run out of lives, 
     * or "continue" if the game is still ongoing.
     */
    public String checkWin() {
        if (lives <= 0) {
            return "lives"; // Check if the player has run out of lives, solving the issue of negative lives continuing the game
        }
        for (int i = 0; i < gameSize; i++) {
            for (int c = 0; c < gameSize; c++) {
                if (!playerBoard[i][c].getState().equals(gameBoard[i][c]) && !gameBoard[i][c].equals("M")) {
                    return "continue";
                }
            }
        }
        return "won";
    }

    /**
     * Allows a user to make a move in the game.
     * 
     * @param row   the row of the move.
     * @param col   the column of the move.
     * @param guess the player's guess, either "M" for mine or "G" for guess.
     * @return A message indicating the result of the move.
     */
    /**
 * Allows a user to make a move in the game.
 * 
 * @param row   the row of the move.
 * @param col   the column of the move.
 * @param guess the player's guess, either "M" for mine or "G" for guess.
 * @return A message indicating the result of the move.
 */
public String makeMove(int row, int col, String guess) {
    // Check if the guess is to flag a mine
    if (guess.equals("M")) {
        playerBoard[row][col].setState("M"); // Set the cell state to "M" indicating a flagged mine
        setChanged();
        notifyObservers(); // Notify the observers (GUI) to update the board
        return "You have flagged a tile."; // Return a message indicating the tile has been flagged
    } else if (guess.equals("G")) { // Check if the guess is to reveal a cell
        if (gameBoard[row][col].equals("M")) { // If the guessed cell contains a mine
            lives -= 1; // Decrease the player's lives by one
            playerBoard[row][col].setState("M"); // Update the player board to show the mine
            setChanged();
            notifyObservers(); // Notify the observers (GUI) to update the board
            return "Oh no! You have lost one life. \nNew life total: " + lives; // Return a message indicating the player has lost a life
        } else if (gameBoard[row][col].equals("-")) { // If the guessed cell is empty
            playerBoard[row][col].setState("-"); // Set the cell state to empty
        } else {
            playerBoard[row][col].setState(gameBoard[row][col]); // Otherwise, set the cell state to the corresponding game board value
        }
        setChanged();
        notifyObservers(); // Notify the observers (GUI) to update the board
        return "Phew! You survived this time!"; // Return a message indicating the player has survived
    } else {
        return "Nope - this was not a mine"; // Return a message for an invalid guess
    }
}


    /**
     * Gets the current state of an individual cell.
     * 
     * @param row the row of the cell.
     * @param col the column of the cell.
     * @return The state of the specified cell.
     */
    public String getCellState(int row, int col) {
        return playerBoard[row][col].getState();
    }
    
    // The following methods were added and deemed necessary to improve encapsulation, state management, 
    // and to support GUI updates.
    
    /**
     * Returns the current player board.
     * 
     * @return The player's board as a 2D array of Slots.
     */
    public Slot[][] getBoard() {
        return playerBoard;
    }

    /**
     * Sets the player board to a new state.
     * 
     * @param board The new state of the player board.
     */
    public void setBoard(Slot[][] board) {
        this.playerBoard = board;
        setChanged();
        notifyObservers();
    }

    /**
     * Returns the number of lives the player has.
     * 
     * @return The current number of lives.
     */
    public int getLives() {
        return lives;
    }

    /**
     * Sets the number of lives the player has.
     * 
     * @param lives The new number of lives.
     */
    public void setLives(int lives) {
        this.lives = lives;
        setChanged();
        notifyObservers();
    }

    /**
     * Resets the game by restoring the initial number of lives and resetting the board.
     */
    public void reset() {
        lives = 3;
        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < gameSize; j++) {
                playerBoard[i][j].setState("?");
            }
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Returns a specific slot on the game board.
     * 
     * @param row the row of the slot.
     * @param col the column of the slot.
     * @return The Slot at the specified position.
     */
    public Slot getSlot(int row, int col) {
        return playerBoard[row][col];
    }
}// End of Minesweeper Class
