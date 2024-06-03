import java.util.Observable;

/**
 * Slot
 * This class handles the state of each slot in the game. Extending observable in preparation for the addition of a GUI.
 * @author Lauren Scott
 * @version Student Sample Code
 */
public class Slot extends Observable{
    private String state;//The current state of the slot
    private int row, col;//The row and column number of the slot 
    /**
     * Constructor of the class slot when importing the level file
     * This creates the slot and denotes where it is placed in the game board.
     * 
     * @param col - the slot's column number
     * @param row - the slot's row number
     * @param number - the number that is in that cell
     * @param fillable - whether that cell can be filled  by the user, cells in the level file that already have numbers should not be changed.
     */
    public Slot (int col, int row, String status) {
        this.row = row;
        this.col = col;
        this.state = status;
    }
    /**
     * getState
     * This provides the current state of the slot
     * @return the current state of the slot
     */
    public String getState(){
        return state;  
    }
    /**
     * setState
     * This method sets the state of the slot
     * @param state - the new state of the slot
     */
    public void setState(String state) {
        this.state = state;
    }
}//End of class Slot

