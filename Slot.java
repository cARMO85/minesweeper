import java.util.Observable;

/**
 * Slot
 * This class handles the state of each slot in the game. Extending Observable for use in the GUI version of the game.
 * 
 * @version 2.0
 * @authors Lauren Scott, Paul Carmody
 */
public class Slot extends Observable {
    private String state; // The current state of the slot
    private int row, col; // The row and column number of the slot 

    /**
     * Constructor of the class Slot when importing the level file.
     * This creates the slot and denotes where it is placed in the game board.
     * 
     * @param col - the slot's column number
     * @param row - the slot's row number
     * @param state - the state that is in that cell
     */
    public Slot(int col, int row, String state) {
        this.row = row;
        this.col = col;
        this.state = state; // changed status to state for consistent coding purposes.
    }

    /**
     * This provides the current state of the slot.
     * 
     * @return the current state of the slot
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the state of the slot and notifies observers of the change.
     * 
     * @param state - the new state of the slot
     */
    public void setState(String state) {
        this.state = state;
        setChanged();
        notifyObservers(); // Notify observers about the change in state
    }
}//End of class Slot
