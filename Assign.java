/**
 * Assign
 * This class handles the creation of all moves in the game.
 * 
 * This class is responsible for assigning moves to the game board and 
 * maintaining the state of each move.
 * 
 * @version 2.0
 * @authors Lauren Scott, Paul Carmody
 */
public class Assign {
    private int col, row; // The row and column being assigned
    private Minesweeper game; // The game instance
    private Slot[][] moves; // 2D Array to store the game's moves

    /**
     * Constructor for the Assign class.
     * This initialises the game move assignment and sets the state of the slot being assigned.
     * 
     * @param game   The game instance
     * @param row    The row the user has selected
     * @param col    The column the user has selected
     * @param number The state to be assigned to the slot
     */
    public Assign(Minesweeper game, int row, int col, String number) {
        this.game = game;
        this.col = col;
        this.row = row;
        this.moves = game.getBoard(); // Fetches the current game board from the Minesweeper instance
        assignMove(number);
    }

    /**
     * Assigns the move to the game board.
     * 
     * @param number The state to be assigned to the slot
     */
    public void assignMove(String number) {
        moves[row][col].setState(number); // Updates the state of the specific slot in the game board
    }

    /**
     * Returns the current row value for this move.
     * 
     * @return The row value
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the current column value for this move.
     * 
     * @return The column value
     */
    public int getCol() {
        return col;
    }

    /**
     * Returns the state value for this move.
     * 
     * @return The state value
     */
    public String getState() {
        return moves[row][col].getState(); // Retrieves the state of the specific slot
    }

    /**
     * Sets the state of the slot at the specified row and column.
     * 
     * @param row   The row of the slot
     * @param col   The column of the slot
     * @param state The state to set
     */
    public void setState(int row, int col, String state) {
        moves[row][col].setState(state); // Sets the state of the specific slot
    }

    /**
     * Returns the entire game board.
     * 
     * @return The game board
     */
    public Slot[][] getBoard() {
        return moves; // Returns the current game board
    }
}
