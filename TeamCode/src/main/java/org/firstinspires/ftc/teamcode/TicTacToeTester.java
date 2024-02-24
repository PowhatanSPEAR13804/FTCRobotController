package org.firstinspires.ftc.teamcode;

public class TicTacToeTester {
    static final char player = 'o';
    static final char opponent = 'x';


    public static void main(String[] args) {
        char board[] = {'x', 'o', 'x' ,
                        'o', 'o', 'x',
                        '_', '_', '_'};

        int bestMove = findBestMove(board);
        int[] movePosition = moveToCoordinates(bestMove);

        System.out.printf("The Optimal Move is: " + bestMove);
        System.out.printf("ROW: " + movePosition[0] + "COL: " + movePosition[1]);
    }

    public static int findBestMove(char[] board) {
        int moveIndex = -1;
        int moveVal = Integer.MIN_VALUE;
        int bestVal = Integer.MIN_VALUE;
        for(int i = 0; i < 9; i++){
            if(board[i] == '_') {
                // try a move
                board[i] = player;
                // check value
                moveVal = minimax(board, 0, true);
                // undo move
                board[i] = '_';
                if(moveVal > bestVal) {
                    moveIndex = i;
                }
            }
        }

        return moveIndex;
    }

    public static int[] moveToCoordinates(int moveIndex) {
        int[] movePosition = new int[2];
        switch (moveIndex) {
            //using temporary x and y values.
            case 0:
                movePosition = new int[]{0, 0};
            case 1:
                movePosition = new int[]{0, 1};
            case 2:
                movePosition = new int[]{0, 2};
            case 3:
                movePosition = new int[]{1, 0};
            case 4:
                movePosition = new int[]{1, 1};
            case 5:
                movePosition = new int[]{1, 2};
            case 6:
                movePosition = new int[]{2, 0};
            case 7:
                movePosition = new int[]{2, 1};
            case 8:
                movePosition = new int[]{2, 2};
            case 9:
                //no change found
                movePosition = new int[]{-1, -1};
        }
        return movePosition;
    }

    public static Boolean isMovesLeft(char[] board) {
        for (int i = 0; i < 9; i++)
            if (board[i] == '_')
                return true;
        return false;
    }

    public static int evaluate(char[] board) {
        //win for x is -10 and win for o is +10;
        for (int row = 0; row <= 6; row+=3)
        {
            if (board[row] == board[row + 1] && board[row + 1] == board[row + 2])
            {
                if (board[row] == 'x')
                    return -10;
                else if (board[row] == 'o')
                    return 10;
            }
        }
        for (int col = 0; col < 3; col++)
        {
            if (board[col] == board[col + 3] && board[col + 3] == board[col + 6]) {
                if (board[col] == 'x')
                    return -10;
                else if (board[col] == 'o')
                    return 10;
            }
        }
        if(board[2] == board[4] && board[4] == board[6]) {
            if(board[4] == 'x')
                return - 10;
            else if(board[4] == 'o')
                return 10;
        }

        //if there no wins return 0
        return 0;
    }

    static int minimax(char[] board, int depth, Boolean isMax) {
        int score = evaluate(board);
        //we win
        if(score == 10)
            return score;

        //opponent wins
        if(score == -10)
            return score;

        //no more moves and no one wins
        if(isMovesLeft(board) == false)
            return 0;

        if(isMax) {
            int best = -1000;

            for (int i = 0; i < 9; i++) {
                if (board[i] == '_') {
                    // try a move
                    board[i] = player;
                    // check value
                    best = Math.max(best, minimax(board, depth + 1, !isMax));
                    // undo move
                    board[i] = '_';
                }
            }
            return best;
        } else {
            int best = 1000;

            for (int i = 0; i < 9; i++) {
                if (board[i] == '_') {
                    // try a move
                    board[i] = opponent;
                    // check value
                    best = Math.min(best, minimax(board, depth + 1, !isMax));
                    // undo move
                    board[i] = '_';
                }
            }
            return best;
        }
    }
}