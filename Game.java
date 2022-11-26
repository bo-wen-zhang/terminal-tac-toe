import java.util.*;

public class Game {
    private Character[] currentGameState;
    private boolean stillPlaying;
    private Scanner inputScanner;
    private Set<Character> set;
    private int[] forwardDiagonal;
    private int[] backwardDiagonal;


    public Game() {
        currentGameState = new Character[]{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        stillPlaying = true;
        inputScanner = new Scanner(System.in);
        set = new HashSet<Character>();
        forwardDiagonal = new int[]{2,4,6};
        backwardDiagonal = new int[]{0,4,8};
    }

    public Character[] getGameState() {
        return currentGameState;
    }

    public boolean isWon(Character[] board, int movePos) {
        int row = movePos / 3;
        set.clear();
        for (int index = 0; index < board.length; index++) {
            if ((index / 3) == row) {
                set.add(board[index]);
            }
        }
        //System.out.println(set.toString());
        if (set.size() == 1) {return true;}
        set.clear();
        int col = movePos % 3;
        for (int index = 0; index < board.length; index++) {
            if ((index % 3) == col) {
                set.add(board[index]);
            }
        }
        //System.out.println(set.toString());
        if (set.size() == 1) {return true;}
        if (movePos % 4 == 0) {
            set.clear();
            for (int index: backwardDiagonal) {
                set.add(board[index]);
            }
            //System.out.println(set.toString());
            if (set.size() == 1) {return true;}
        }
        if (movePos == 2 || movePos == 4 || movePos == 6) {
            set.clear();
            for (int index: forwardDiagonal) {
                set.add(board[index]);
            }
            //System.out.println(set.toString());
            if (set.size() == 1) {return true;}
        }
        return false;
    }

    public int miniMax(Character[] board, Character player, int movePos) {
        boolean gameWon = isWon(board, movePos);
        if (gameWon && player == 'X') {
            return -1;
        }
        else if (gameWon && player == 'O') {
            return 1;
        }
         else if (boardFull(board)) {
             return 0;
        }

        player = changePlayer(player);
        int res = 0;
        // for (int index = 0; index < board.length; index++) {
        //     if (board[index] == ' ') {
        //         board[index] = player;
        //         res = res + miniMax(board, player, index);
        //         board[index] = ' ';
        //     }
        // }
        int bestIndex = 0;
        Double bestScore;
        if (player == 'O') {
            bestScore = Double.NEGATIVE_INFINITY;
        }
        else {
            bestScore = Double.POSITIVE_INFINITY;
        }
        for (int index = 0; index < 9; index++) {
            if (board[index] == ' ') {
                board[index] = player;
                res = miniMax(board, player, index);
                if (player == 'O') {
                    if (res > bestScore) {
                        bestIndex = index;
                        bestScore = Double.valueOf(res);
                    }
                }
                else {
                    if (res < bestScore) {
                        bestIndex = index;
                        bestScore = Double.valueOf(res);
                    }
                }
                board[index] = ' ';
            }
        }

        return bestScore.intValue();
    }

    public Character changePlayer(Character player) {
        if (player == 'X') {
            return 'O';
        }
        return 'X';
    }

    public boolean boardFull(Character[] board) {
        for (Character space: board) {
            if (space == ' ') {return false;}
        }
        return true;
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

    public int makePlayerMove() {
        int move = getUserInput();
        currentGameState[move] = 'X';
        return move;
    }

    public void playGame() {
        while (stillPlaying) {
            displayBoard();
            int playerMove = makePlayerMove();    
            if (isWon(currentGameState, playerMove)) {
                System.out.println("Congratulations, you have won!");
                stillPlaying = !stillPlaying;
                break;
            }
            if (boardFull(currentGameState)) {
                System.out.println("You tied.");
                stillPlaying = !stillPlaying;
                break;
            }
            Character[] boardCopy;
            int bestIndex = 0;
            Double bestScore = Double.NEGATIVE_INFINITY;
            for (int index = 0; index < 9; index++) {
                if (currentGameState[index] == ' ') {
                    boardCopy = currentGameState.clone();
                    boardCopy[index] = 'O';
                    int res =  miniMax(boardCopy, 'O', index);
                    if (res > bestScore) {
                        bestIndex = index;
                        bestScore = Double.valueOf(res);
                    }
                }
            }
            currentGameState[bestIndex] = 'O';
            if (isWon(currentGameState, bestIndex)) {
                System.out.println("The AI Won.");
                stillPlaying = !stillPlaying;
                break;
            }
            if (boardFull(currentGameState)) {
                System.out.println("You tied.");
                stillPlaying = !stillPlaying;
                break;
            }
            System.out.println("Next turn.");
        }
        
    }

    public static void main(String[] args) {
        Game newGame = new Game();
        System.out.println("Welcome to AI Tic-Tac-Toe.");
        newGame.playGame();
    }
}