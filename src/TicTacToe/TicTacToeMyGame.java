package TicTacToe;

import java.util.Random;
import java.util.Scanner;

public class TicTacToeMyGame {
    private static final char EMPTY = ' ';

    private static final char USER = 'X';

    private static final char COMPUTER = '0';

    private static final char[][] BOARD = {
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY}
    };

    public static void main(String[] args) {
        printGameRules();
        if (new Random().nextBoolean()) {
            makeComputerProgress();
            printGameBoard();
        }
        while (true) {
            int number = readUserInput();
            makeUserProgress(number);
            if (checkWinner(USER)) {
                System.out.println("User WiN!");
                break;
            }
            if (checkDraw()) {
                System.out.println("It's a draw!");
                break;
            }
            makeComputerProgress();
            printGameBoard();
            if (checkWinner(COMPUTER)) {
                System.out.println("COMPUTER WiN!");
                break;
            }
            if (checkDraw()) {
                System.out.println("It's a draw!");
                break;
            }
        }
        printGameBoard();
        System.out.println("Game Over!");
    }

    private static void printGameRules() {
        System.out.println("RULES FOR TIC-TAC-TOE");
        System.out.println("1. The game is played on a grid that's 3 squares by 3 squares.");
        System.out.println("2. You are X, your friend (or the computer in this case) is O. Players take turns putting their marks in empty squares.");
        System.out.println("3. The first player to get 3 of her marks in a row (up, down, across, or diagonally) is the winner.");
        System.out.println("4. When all 9 squares are full, the game is over. If no player has 3 marks in a row, the game ends in a tie.");
        System.out.println();
        char[][] data = {
                {'7', '8', '9'},
                {'4', '5', '6'},
                {'1', '2', '3'}
        };
        buildGameBoard(data);
    }

    private static int readUserInput() {
        while (true) {
            System.out.println("Please type number between 1 and 9:");
            var userInput = new Scanner(System.in).nextLine();
            if (userInput.length() == 1) {
                var ch = userInput.charAt(0);
                if (ch >= '1' && ch <= '9') {
                    var number = Integer.parseInt(userInput);
                    if (isCellFree(number)) {
                        return number;
                    } else {
                        System.out.println("Cell with number " + number + " is not free!");
                    }
                }
            }
        }
    }

    private static boolean checkWinner(char ch) {
        for (int i = 0; i < 3; i++) {
            if (BOARD[i][0] == BOARD[i][1] &&
                    BOARD[i][1] == BOARD[i][2] &&
                    BOARD[i][1] == ch) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (BOARD[0][i] == BOARD[1][i] &&
                    BOARD[1][i] == BOARD[2][i] &&
                    BOARD[1][i] == ch) {
                return true;
            }
        }
        if (BOARD[0][0] == BOARD[1][1] &&
                BOARD[1][1] == BOARD[2][2] &&
                BOARD[1][1] == ch) {
            return true;
        } else if (BOARD[2][0] == BOARD[1][1] &&
                BOARD[1][1] == BOARD[0][2] &&
                BOARD[1][1] == ch) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean checkDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (BOARD[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void makeComputerProgress() {
        int[] bestMove = checkCanItWin(COMPUTER);
        if (bestMove[0] != -1) {
            BOARD[bestMove[0]][bestMove[1]] = COMPUTER;
            return;
        }
        //prevent USER from winning
        bestMove = checkCanItWin(USER);
        if (bestMove[0] != -1) {
            BOARD[bestMove[0]][bestMove[1]] = COMPUTER;
            return;
        }
        bestMove = tryToWin(COMPUTER);
        if (bestMove[0] != -1) {
            BOARD[bestMove[0]][bestMove[1]] = COMPUTER;
            return;
        }
        makeRandomComputerProgress();
    }

    private static int[] checkCanItWin(char ch) {
        return checkCanItWinDiagonals(ch);
    }

    private static int[] checkCanItWinDiagonals(char ch) {
        if ((BOARD[0][0] == ch && BOARD[2][2] == ch && BOARD[1][1] == ' ') ||
                (BOARD[2][0] == ch && BOARD[0][2] == ch && BOARD[1][1] == ' ')) {
            return new int[]{1, 1};
        } else if (BOARD[1][1] == ch) {
            if (BOARD[0][0] == ch && BOARD[2][2] == ' ') {
                return new int[]{2, 2};
            } else if (BOARD[2][2] == ch && BOARD[0][0] == ' ') {
                return new int[]{0, 0};
            } else if (BOARD[0][2] == ch && BOARD[2][0] == ' ') {
                return new int[]{2, 0};
            } else if (BOARD[2][0] == ch && BOARD[0][2] == ' ') {
                return new int[]{0, 2};
            }
        }
        return checkCanItWinRows(ch);
    }

    private static int[] checkCanItWinRows(char ch) {
        for (int i = 0; i < 3; i++) {
            if (BOARD[i][0] == ch && BOARD[i][1] == ch && BOARD[i][2] == ' ') {
                return new int[]{i, 2};
            } else if (BOARD[i][1] == ch && BOARD[i][2] == ch && BOARD[i][0] == ' ') {
                return new int[]{i, 0};
            } else if (BOARD[i][0] == ch && BOARD[i][2] == ch && BOARD[i][1] == ' ') {
                return new int[]{i, 1};
            }
        }
        return checkCanItWinCols(ch);
    }

    private static int[] checkCanItWinCols(char ch) {
        for (int i = 0; i < 3; i++) {
            if (BOARD[0][i] == ch && BOARD[1][i] == ch && BOARD[2][i] == ' ') {
                return new int[]{2, i};
            } else if (BOARD[1][i] == ch && BOARD[2][i] == ch && BOARD[0][i] == ' ') {
                return new int[]{0, i};
            } else if (BOARD[0][i] == ch && BOARD[2][i] == ch && BOARD[1][i] == ' ') {
                return new int[]{1, i};
            }
        }
        return new int[]{-1, -1};
    }

    private static int[] tryToWin(char ch) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (BOARD[i][j] == ch) {
                    int[][] candidates = getCandidates(i, j);
                    for (int[] candidate : candidates) {
                        if (BOARD[candidate[0]][candidate[1]] == EMPTY) {
                            return new int[]{candidate[0], candidate[1]};
                        }
                    }
                }
            }
        }
        return new int[]{-1, -1};
    }

    private static int[][] getCandidates(int i, int j) {
        if (i == 0) {
            if (j == 0) {
                return new int[][]{{0, 1}, {1, 1}, {1, 0}};
            } else if (j == 1) {
                return new int[][]{{0, 2}, {1, 1}, {0, 0}};
            } else {
                return new int[][]{{1, 2}, {1, 1}, {0, 1}};
            }
        } else if (i == 1) {
            if (j == 0) {
                return new int[][]{{0, 0}, {1, 1}, {2, 0}};
            } else if (j == 1) {
                return new int[][]{{0, 1}, {0, 2}, {1, 2}, {2, 2}, {2, 1}, {2, 0}, {1, 0}, {0, 0}};
            } else {
                return new int[][]{{2, 2}, {1, 1}, {0, 2}};
            }
        } else {
            if (j == 0) {
                return new int[][]{{1, 0}, {1, 1}, {2, 1}};
            } else if (j == 1) {
                return new int[][]{{1, 1}, {2, 2}, {2, 0}};
            } else {
                return new int[][]{{1, 2}, {2, 1}, {1, 1}};
            }
        }
    }


    private static void makeRandomComputerProgress() {
        Random random = new Random();
        int choice;
        do {
            choice = random.nextInt(9);
        } while (BOARD[choice / 3][choice % 3] != ' ');
        BOARD[choice / 3][choice % 3] = COMPUTER;
    }


    private static boolean isCellFree(int number) {
        int[] indexes = toIndexes(number);

        var row = indexes[0];
        var col = indexes[1];
        return BOARD[row][col] == EMPTY;
    }

    private static void makeUserProgress(int number) {
        updateGameBoard(number);
    }

    private static void updateGameBoard(int number) {
        var indexes = toIndexes(number);
        BOARD[indexes[0]][indexes[1]] = USER;
    }

    private static int[] toIndexes(int number) {
        int[][] values = {
                {2, 0},
                {2, 1},
                {2, 2},
                {1, 0},
                {1, 1},
                {1, 2},
                {0, 0},
                {0, 1},
                {0, 2}
        };
        return values[number - 1];
    }

    private static void printGameBoard() {
        buildGameBoard(BOARD);
    }

    private static void buildGameBoard(char[][] data) {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(data[i][j] + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }
}
