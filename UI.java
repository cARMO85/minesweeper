import java.util.Scanner;
import java.util.Stack;

/**
 * TThe UI class provides a text-based user interface for the player to interact with the Minesweeper game. 
 * It handles user input through a console-based menu, allowing the player to make moves, 
 * undo moves, and clear the game board.
 * 
 * @version 2.0
 * @authors Lauren Scott, Paul Camrody
 */
public class UI {
    private Minesweeper theGame; // The game model
    private String menuChoice; // The user's choice from the menu
    private Scanner reader; // This scanner is used to read the terminal
    private Stack<Slot[][]> undoStack; // Stack to undo game states

    /**
     * Constructs the UI with a given Minesweeper game instance.
     * This allows the UI to interact with an existing game rather than creating a new one.
     * 
     * @param theGame The Minesweeper game instance.
     */
    public UI(Minesweeper theGame) {
        this.theGame = theGame; // Uses the provided Minesweeper game instance instead of creating a new one.
        reader = new Scanner(System.in);
        menuChoice = "";
        undoStack = new Stack<>(); // Initialises the undo stack to store game states.
        displayWelcomeMessage(); // Displays the welcome message to the user.
    }       

    /**
     * Default constructor for the UI class that initialises a new Minesweeper game.
     */
    public UI() {
        this(new Minesweeper()); // Calls the parameterised constructor with a new Minesweeper instance.
        startGame(); // Starts the game loop immediately for the default constructor.
    }

    /**
     * Starts the game and handles the game loop, user inputs, and game state updates.
     */
    public void startGame() {
        boolean continuePlaying = true; // Allows the user to choose to play again or quit at the end.
        while (continuePlaying) {
            while (!menuChoice.equalsIgnoreCase("Q") && theGame.checkWin().equals("continue")) {
                displayGame();
                menu();
                menuChoice = getChoice();
                if (theGame.getLives() <= 0) {
                    break;
                }
            }
            if (theGame.checkWin().equals("won")) {
                winningAnnouncement();
            } else if (theGame.getLives() <= 0) {
                livesAnnouncement();
            }
            continuePlaying = promptPlayAgain();
            if (continuePlaying) {
                theGame.reset();
                undoStack.clear();
            }
        }
    }

    /**
     * Prompts the user to decide whether to play again.
     * 
     * @return true if the user wants to play again, false otherwise.
     */
    public boolean promptPlayAgain() {
        System.out.print("Do you want to play again? (Y/N): ");
        String choice = reader.next().toUpperCase();
        return choice.equals("Y");
    }

    /**
     * Announces that the user has won the game.
     */
    public void winningAnnouncement() {
        System.out.println("\nWahoo, you solved the level");
    }

    /**
     * Announces that the user has lost the game due to running out of lives.
     */
    public void livesAnnouncement() {
        System.out.println("\nYou Lose! No more lives. The game is over");
    }

    /**
     * Displays the current state of the game board.
     */
    public void displayGame() {
        System.out.print("\n\nCol    ");
        for (int r = 0; r < theGame.getGameSize(); r++) {
            System.out.print(r + " ");
        }
        for (int i = 0; i < theGame.getGameSize(); i++) {
            System.out.print("\nRow  " + i);
            for (int c = 0; c < theGame.getGameSize(); c++) {
                System.out.print(" " + theGame.getCellState(i, c));
            }
        }
    }

    /**
     * Displays the menu options for the user.
     */
    public void menu() {
        System.out.println("\nPlease select an option: \n" +
                "[M] Flag a mine\n" +
                "[G] Guess a square\n" +
                "[S] Save game\n" +
                "[L] Load saved game\n" +
                "[U] Undo move\n" +
                "[C] Clear game\n" +
                "[Q] Quit game\n");
    }

    /**
     * Handles the user's menu choice and executes the corresponding actions.
     * 
     * @return The choice selected by the user.
     */
    public String getChoice() {
        String choice = reader.next().toUpperCase(); // Added to handle lowercase inputs to prevent terminal crashes.
        if (choice.equalsIgnoreCase("M") || choice.equalsIgnoreCase("G")) {
            try {
                System.out.print("Which row is the cell you wish to select?  ");
                String rowInput = reader.next();
                int row = Integer.parseInt(rowInput);

                System.out.print("Which column is the cell you wish to select?  ");
                String colInput = reader.next();
                int col = Integer.parseInt(colInput);

                if (row >= 0 && row < theGame.getGameSize() && col >= 0 && col < theGame.getGameSize()) {
                    pushCurrentStateToUndoStack(); // Saves the current state before making a move.
                    Assign assignMove = new Assign(theGame, row, col, choice);
                    System.out.print(theGame.makeMove(row, col, choice));
                } else {
                    System.out.println("Error: Row and column must be within the board boundaries.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid input. Please enter numeric values for row and column.");
            }
        } else if (choice.equalsIgnoreCase("S")) {
            System.out.print("Enter the filename to save the game: ");
            String filename = reader.next();
            FileHandler.saveGame(theGame, filename);
        } else if (choice.equalsIgnoreCase("U")) {
            undoMove();
        } else if (choice.equalsIgnoreCase("L")) {
            System.out.print("Enter the filename to load the game: ");
            String filename = reader.next();
            FileHandler.loadGame(theGame, filename);
        } else if (choice.equalsIgnoreCase("C")) {
            clearGame();
        } else if (choice.equalsIgnoreCase("Q")) {
            System.exit(0);
        } else {
            System.out.println("Invalid option. Please select a valid menu option.");
        }
        return choice;
    }

    /**
     * Pushes the current state of the game to the undo stack.
     * This method creates a deep copy of the current game board state.
     */
    public void pushCurrentStateToUndoStack() {
        Slot[][] currentState = theGame.getMoves();
        Slot[][] copyState = new Slot[currentState.length][currentState.length];

        for (int i = 0; i < currentState.length; i++) {
            for (int j = 0; j < currentState[i].length; j++) {
                copyState[i][j] = new Slot(j, i, currentState[i][j].getState());
            }
        }

        undoStack.push(copyState);
    }

    /**
     * Undoes the last move made by the player.
     */
    public void undoMove() {
        if (!undoStack.isEmpty()) {
            Slot[][] previousState = undoStack.pop(); // Retrieves the previous state from the stack.
            theGame.setBoard(previousState);
            System.out.println("Undo successful.");
        } else {
            System.out.println("No more moves to undo.");
        }
    }

    /**
     * Clears the game board and resets the game state.
     */
    public void clearGame() {
        theGame.reset();
        undoStack.clear();
        System.out.println("Game cleared and reset. Go again!");
    }

    /**
     * Displays the welcome message and game rules to the player.
     */
    private void displayWelcomeMessage() {
        System.out.println("        _"); // Added to make more viusally intersting to play and explain the rules.
        System.out.println("  /\\/\\ (_)_ __   ___  _____      _____  ___ _ __   ___ _ __");
        System.out.println(" /    \\| | '_ \\ / _ \\/ __\\ \\ /\\ / / _ \\/ _ \\ '_ \\ / _ \\ '__|");
        System.out.println("/ /\\/\\ \\ | | | |  __/\\__ \\\\ V  V /  __/  __/ |_) |  __/ |");
        System.out.println("\\/    \\/_|_| |_|\\___||___/ \\_/\\_/ \\___|\\___| .__/ \\___|_|");
        System.out.println("                                     NU |_| EDITION");
        System.out.println();
        System.out.println("Welcome to Minesweeper!");
        System.out.println("Rules:");
        System.out.println("1. There are hidden mines on the board.");
        System.out.println("2. Your objective is to guess all the squares without hitting 3 mines.");
        System.out.println("3. Use the commands given in the menu:");
        System.out.println("Good luck!\n");
    }

    /**
     * The main method to start the UI and the Minesweeper game.
     * 
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new UI();
    }
} //End of UI Class
