import java.util.Scanner;

public class Game {
    private Character[] currentGameState;
    private boolean stillPlaying;
    private Scanner inputScanner;

    public Game() {
        currentGameState = new Character[]{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        stillPlaying = true;
        inputScanner = new Scanner(System.in);
    }

    public Character[] getGameState() {
        return currentGameState;
    }

    public void displayBoard() {
        System.out.println("-------");
        for (int i = 0; i < currentGameState.length; i = i + 3) {
            String line = "|";
            for (int j = 0; j < 3; j++) {
                line = line + currentGameState[i+j] + "|";
            }
            System.out.println(line);
            System.out.println("-------");
        }
    }

    public int getUserInput() {
        String userInput = " ";
        while (!validateInput(userInput)) {
            System.out.println("Please enter your move or 'q' to quit: ");
            userInput = inputScanner.nextLine();
        }
        return Integer.parseInt(userInput);
    }

    public boolean isNumeric(String s) {
        if (s == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public boolean validateInput(String userInput) {
        if (userInput == "q") {
            System.out.println("Thank you for playing.");
            System.exit(0);
        }
        if (!isNumeric(userInput)) {
            return false;
        }
        int number = Integer.parseInt(userInput);
        if (0 > number || number > 8) {
            return false;
        }
        return true;
    }

    public void makePlayerMove() {
        int move = getUserInput();
        currentGameState[move] = 'x';
    }

    public void playGame() {
        while (stillPlaying) {
            displayBoard();
            makePlayerMove();           
        }
        
    }

    public static void main(String[] args) {
        Game newGame = new Game();
        System.out.println("Welcome to AI Tic-Tac-Toe.");
        newGame.playGame();
    }
}