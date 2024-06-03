import java.util.Scanner;

public class UI {
    private Minesweeper thegame; // this is the game model
    private String menuChoice; // this is the user's choice from the menu
    private Scanner reader; // this scanner is used to read the terminal
    private Assign assign; // Assign instance to manage moves

    /**
     * Constructor for the class UI
     */
    public UI() {
        this(new Minesweeper(), new Scanner(System.in));
    }

    /**
     * Constructor for the class UI with Minesweeper and Scanner
     */
    public UI(Minesweeper game, Scanner scanner) {
        this.thegame = game;
        this.assign = new Assign(game); // Initialize Assign with the game instance
        this.reader = scanner;
        this.menuChoice = "";
        while (!menuChoice.equalsIgnoreCase("Q") && thegame.checkWin().equals("continue")) {
            displayGame();
            menu();
            menuChoice = getChoice();
        }
        if (thegame.checkWin().equals("won")) {
            winningAnnouncement();
        } else if (thegame.checkWin().equals("lives")) {
            livesAnnouncement();
        }
    }

    /**
     * Method that outputs an announcement when the user has won the game
     */
    public void winningAnnouncement() {
        System.out.println("\nCongratulations, you solved the level");
    }

    /**
     * Method that outputs an announcement when the user has lost due to lack of lives
     */
    public void livesAnnouncement() {
        System.out.println("\nYou have run out of lives, the game is over");
    }

    /**
     * Method that displays the game for the user to play
     */
    public void displayGame() {
        System.out.print("\n\nCol    ");
        for (int r = 0; r < thegame.getGameSize(); r++) {
            System.out.print(r + " ");
        }
        for (int i = 0; i < thegame.getGameSize(); i++) {
            System.out.print("\nRow  " + i);
            for (int c = 0; c < thegame.getGameSize(); c++) {
                System.out.print(" " + thegame.getCellState(i, c));
            }
        }
        System.out.println();
    }

    /**
     * Method that displays the menu to the user
     */
    public void menu() {
        System.out.println("\nPlease select an option: \n"
            + "[M] Flag a mine\n"
            + "[G] Guess a square\n"
            + "[S] Save game\n"
            + "[L] Load saved game\n"
            + "[U] Undo move\n"
            + "[C] Clear game\n"
            + "[Q] Quit game\n");
    }

    /**
     * Method that gets the user's choice from the menu and conducts the activities
     * accordingly
     * @return the choice the user has selected
     */
    public String getChoice() {
        if (!reader.hasNext()) {
            return "Q";
        }
        String choice = reader.next();
        if (choice.equalsIgnoreCase("M") || choice.equalsIgnoreCase("G")) {
            handleMove(choice);
        } else if (choice.equalsIgnoreCase("S")) {
            saveGame();
        } else if (choice.equalsIgnoreCase("U")) {
            undoMove();
        } else if (choice.equalsIgnoreCase("L")) {
            loadGame();
        } else if (choice.equalsIgnoreCase("C")) {
            clearGame();
        } else if (choice.equalsIgnoreCase("Q")) {
            System.exit(0);
        }
        return choice;
    }

    public void handleMove(String choice) {
        int row = -1, col = -1;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.print("Which row is the cell you wish to " + (choice.equalsIgnoreCase("M") ? "flag" : "guess") + "?  ");
                if (!reader.hasNextInt()) {
                    System.out.println("Invalid input. Please enter numeric values for row and column.");
                    reader.next(); // Clear invalid input
                    continue;
                }
                row = reader.nextInt();
                System.out.print("Which column is the cell you wish to " + (choice.equalsIgnoreCase("M") ? "flag" : "guess") + "?  ");
                if (!reader.hasNextInt()) {
                    System.out.println("Invalid input. Please enter numeric values for row and column.");
                    reader.next(); // Clear invalid input
                    continue;
                }
                col = reader.nextInt();
                if (row >= 0 && row < thegame.getGameSize() && col >= 0 && col < thegame.getGameSize()) {
                    validInput = true;
                } else {
                    System.out.println("Invalid input. Please enter a valid row and column within the game board.");
                }
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
                reader.next(); // Clear invalid input
            }
        }
        System.out.print(thegame.makeMove(String.valueOf(row), String.valueOf(col), choice));
    }

    /**
     * Method to clear the game and reset the board
     */
    public void clearGame() {
        thegame = new Minesweeper(); // Create a new instance to reset the game
        assign = new Assign(thegame); // Reinitialize Assign with the new game instance
        System.out.println("The game has been cleared.");
    }

    /**
     * Method to save the game state
     */
    public void saveGame() {
        assign.saveGameState();
    }

    /**
     * Method to undo the last move
     */
    public void undoMove() {
        assign.undoMove();
    }

    /**
     * Method to load a previously saved game state
     */
    public void loadGame() {
        assign.loadGame("saved_game.txt");
    }

    /**
     * The main method within the Java application. It's the core method of the program and calls all others
     */
    public static void main(String[] args) {
        UI thisUI = new UI();
    }
}
