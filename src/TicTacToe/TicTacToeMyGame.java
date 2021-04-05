package TicTacToe;

import java.util.Random;
import java.util.Scanner;

public class TicTacToeMyGame {
    private static final char EMPTY = ' ';

    private static final char USER = 'X';

    private static final char COMPUTER = '0';

    private static final char[][] board = {
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
            if (board[i][0] == board[i][1] &&
                    board[i][1] == board[i][2] &&
                    board[i][1] == ch) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == board[1][i] &&
                    board[1][i] == board[2][i] &&
                    board[1][i] == ch) {
                return true;
            }
        }
        if (board[0][0] == board[1][1] &&
                board[1][1] == board[2][2] &&
                board[1][1] == ch) {
            return true;
        } else if (board[2][0] == board[1][1] &&
                board[1][1] == board[0][2] &&
                board[1][1] == ch) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean checkDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void makeComputerProgress() {
        int choice;
        int[] coordinate = new int[2];
        boolean choiceMade = false;

        int[] bestMove = checkCanItWin(COMPUTER);
        if (bestMove[0] != -1) {
            choiceMade = true;
            coordinate = bestMove;
        }

        // check if COMPUTER winning
        //if COMPUTER can't win, try to prevent USER from winning

        if (!choiceMade) {
            bestMove = checkCanItWin(USER);
            if (bestMove[0] != -1) {
                choiceMade = true;
                coordinate = bestMove;
            }
        }

        // if we can't do winning move, do random move

        if (!choiceMade) {
            Random random = new Random();

            do {
                choice = random.nextInt(9);
            } while (board[choice / 3][choice % 3] != ' ');

            board[choice / 3][choice % 3] = COMPUTER;
        } else {
            board[coordinate[0]][coordinate[1]] = COMPUTER;
        }
    }

    private static int[] checkCanItWin(char ch) {
        int row = -1;
        int column = -1;

        // check for diagonals
        if ((board[0][0] == ch && board[2][2] == ch && board[1][1] == ' ') || (board[2][0] == ch && board[0][2] == ch && board[1][1] == ' ')) {
            row = 1;
            column = 1;
        } else if (board[1][1] == ch) {
            if (board[0][0] == ch && board[2][2] == ' ') {
                row = 2;
                column = 2;
            } else if (board[2][2] == ch && board[0][0] == ' ') {
                row = 0;
                column = 0;
            } else if (board[0][2] == ch && board[2][0] == ' ') {
                row = 2;
                column = 0;
            } else if (board[2][0] == ch && board[0][2] == ' ') {
                row = 0;
                column = 2;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (board[i][0] == ch && board[i][1] == ch && board[i][2] == ' ') {
                // check for rows
                row = i;
                column = 2;
            } else if (board[i][1] == ch && board[i][2] == ch && board[i][0] == ' ') {
                row = i;
                column = 0;
            } else if (board[i][0] == ch && board[i][2] == ch && board[i][1] == ' ') {
                row = i;
                column = 1;
            } else if (board[0][i] == ch && board[1][i] == ch && board[2][i] == ' ') {
                // check for columns
                row = 2;
                column = i;
            } else if (board[1][i] == ch && board[2][i] == ch && board[0][i] == ' ') {
                row = 0;
                column = i;
            } else if (board[0][i] == ch && board[2][i] == ch && board[1][i] == ' ') {
                row = 1;
                column = i;
            }
        }

        return new int[]{row, column};
    }

    private static boolean isCellFree(int number) {
        int[] indexes = toIndexes(number);

        var row = indexes[0];
        var col = indexes[1];
        return board[row][col] == EMPTY;
    }

    private static void makeUserProgress(int number) {
        updateGameBoard(number, USER);
    }

    private static void updateGameBoard(int number, char ch) {
        var indexes = toIndexes(number);
        board[indexes[0]][indexes[1]] = ch;
    }

    private static int[] toIndexes(int number) {
        if (number == 1) {
            return new int[]{2, 0};
        } else if (number == 2) {
            return new int[]{2, 1};
        } else if (number == 3) {
            return new int[]{2, 2};
        } else if (number == 4) {
            return new int[]{1, 0};
        } else if (number == 5) {
            return new int[]{1, 1};
        } else if (number == 6) {
            return new int[]{1, 2};
        } else if (number == 7) {
            return new int[]{0, 0};
        } else if (number == 8) {
            return new int[]{0, 1};
        } else {
            return new int[]{0, 2};
        }
    }

    private static void printGameBoard() {
        buildGameBoard(board);
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
