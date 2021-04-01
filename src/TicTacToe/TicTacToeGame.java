package TicTacToe;

import java.util.Random;
import java.util.Scanner;

public class TicTacToeGame {

    private static final char EMPTY = ' ';

    private static final char USER = 'X';

    private static final char COMPUTER = '0';

    private static final char[][] GAME_TABLE = {
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY}
    };

    public static void main(String[] args) {
        printGameRules();
        while (true) {
            int number = readUserInput();
            makeUserProgress(number);
            if (isWinner(USER)) {
                System.out.println("YOU WIN!");
            }
            if (isDraw()) {
                System.out.println("SORRY, DRAW!");
                break;
            }
            makeComputerProgress();
            printGameTable();
            if (isWinner(USER)) {
                System.out.println("YOU WIN!");
            }
            if (isDraw()) {
                System.out.println("SORRY, DRAW!");
                break;
            }
        }

        printGameTable();
        System.out.println("GAME OVER!");
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
        printData(data);
    }

    private static void printGameTable() {
        printData(GAME_TABLE);
    }

    private static void printData(char[][] data) {
        for (int i = 0; i < 3; i++) {
            printHorizontalLine();
            for (int j = 0; j < 3; j++) {
                System.out.print("| " + data[i][j] + " ");
                if (j == 2) {
                    System.out.println("|");
                }
            }
        }
        printHorizontalLine();
    }

    private static void printHorizontalLine() {
        for (int i = 0; i < 3; i++) {
            System.out.print("----");
        }
        System.out.println("-");
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
                    makeUserProgress(number);
                }
            }
        }
    }

    private static boolean isCellFree(int number) {
        int[] indexes = toIndexes(number);
        return GAME_TABLE[indexes[0]][indexes[1]] == EMPTY;
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


    private static void makeUserProgress(int number) {
        /*while(true) {
            if (number == 7) {
                GAME_TABLE[0][0] = USER;
            } else if (number == 8) {
                GAME_TABLE[0][1] = USER;
            } else if (number == 9) {
                GAME_TABLE[0][2] = USER;
            } else if (number == 4) {
                GAME_TABLE[1][0] = USER;
            } else if (number == 5) {
                GAME_TABLE[1][1] = USER;
            } else if (number == 6) {
                GAME_TABLE[1][2] = USER;
            } else if (number == 1) {
                GAME_TABLE[2][0] = USER;
            } else if (number == 2) {
                GAME_TABLE[2][1] = USER;
            } else if (number == 3) {
                GAME_TABLE[2][2] = USER;
            }

         */
        var indexes = toIndexes(number);
        GAME_TABLE[indexes[0]][indexes[1]] = USER;
    }

    private static void makeComputerProgress() {
        while (true) {
            int number = new Random().nextInt(9) + 1;
            if (isCellFree(number)) {
                var indexes = toIndexes(number);
                GAME_TABLE[indexes[0]][indexes[1]] = COMPUTER;
                break;
            }
        }
    }

    private static boolean isWinner(char ch) {
        return false;
    }

    private static boolean isDraw() {
        return false;
    }
}
