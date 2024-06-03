import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Assign {
    private Minesweeper game;
    private ArrayList<String> moveHistory; // ArrayList to keep track of moves

    /**
     * Constructor for Assign class.
     * @param game - the game instance
     */
    public Assign(Minesweeper game) {
        this.game = game;
        moveHistory = new ArrayList<>();
    }

    public void saveGameState() {
        try {
            PrintStream print = new PrintStream(new File("saved_game.txt"));

            // Write game size
            print.println(game.getGameSize());

            // Write game board
            for (int i = 0; i < game.getGameSize(); i++) {
                for (int j = 0; j < game.getGameSize(); j++) {
                    print.print(game.getIndividualMove(i, j) + " ");
                }
                print.println();
            }

            // Write player board
            for (int i = 0; i < game.getGameSize(); i++) {
                for (int j = 0; j < game.getGameSize(); j++) {
                    print.print(game.getCellState(i, j) + " ");
                }
                print.println();
            }
            print.close();

            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the game.");
            e.printStackTrace();
        }
    }

    /**
     * Method to load game state
     */
    public void loadGame(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            int gameSize = Integer.parseInt(scanner.nextLine());
            String[][] gameBoard = new String[gameSize][gameSize];
            Slot[][] playerBoard = new Slot[gameSize][gameSize];

            // Read game board
            for (int i = 0; i < gameSize; i++) {
                for (int j = 0; j < gameSize; j++) {
                    gameBoard[i][j] = scanner.next();
                }
            }

            // Read player board
            for (int i = 0; i < gameSize; i++) {
                for (int j = 0; j < gameSize; j++) {
                    playerBoard[i][j] = new Slot(i, j, scanner.next());
                }
            }

            // Directly set the game state
            game.setGameBoard(gameBoard);
            game.setPlayerBoard(playerBoard);

            System.out.println("Game loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while loading the game.");
            e.printStackTrace();
        }
    }

    private void setGameBoard(String[][] gameBoard) {
        for (int i = 0; i < game.getGameSize(); i++) {
            for (int j = 0; j < game.getGameSize(); j++) {
                game.makeMove(String.valueOf(i), String.valueOf(j), gameBoard[i][j]);
            }
        }
    }

    private void setPlayerBoard(Slot[][] playerBoard) {
        for (int i = 0; i < game.getGameSize(); i++) {
            for (int j = 0; j < game.getGameSize(); j++) {
                game.getMoves()[i][j] = playerBoard[i][j];
            }
        }
    }

    /**
     * Method to assign a move and save it to history
     */
    public String assignMove(String row, String col, String guess) {
        int enteredRow = Integer.parseInt(row);
        int enteredCol = Integer.parseInt(col);
        String prevState = game.getCellState(enteredRow, enteredCol);

        // Save the previous state and the new move to history
        moveHistory.add(enteredRow + "," + enteredCol + "," + prevState + "," + guess);

        return game.makeMove(row, col, guess);
    }

    /**
     * Method to undo the last move
     */
    public void undoMove() {
        if (!moveHistory.isEmpty()) {
            String lastMove = moveHistory.remove(moveHistory.size() - 1);
            String[] parts = lastMove.split(",");
            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);
            String prevState = parts[2];

            // Restore the previous state
            game.getMoves()[row][col].setState(prevState);

            System.out.println("Last move undone.");
        } else {
            System.out.println("No moves to undo.");
        }
    }
}