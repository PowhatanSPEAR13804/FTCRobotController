
package org.firstinspires.ftc.teamcode.misc;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/*
    TEST
 */



@Disabled
@Autonomous(name="Tic_Tac_Toe_AI", group="Robot")

public class Tic_Tac_Toe_AI extends LinearOpMode
{


    @Override
    public void runOpMode() throws InterruptedException {
        while(true) {
            //detect when a piece is placed
            newPlayerPosition = 0;
            move += 1;
            updateBoardPositions();
        }
    }

    private DcMotor xMotor = hardwareMap.dcMotor.get("Hub1_motor0");
    private DcMotor yMotor = hardwareMap.dcMotor.get("Hub1_motor2");

    private double X_ROTATIONS_PER_INCH = 12.0;
    private double y_ROTATIONS_PER_INCH = 10/1.5;

    //move number
    int move = 0;

    //tells which space is occupied
    int[] boardPositions = {0, 0, 0,
            0, 0, 0,
            0, 0, 0};
    /*
    int[] r1 = {0, 1, 2};
    int[] r2 = {3, 4, 5};
    int[] r3 = {6, 7, 8};
    int[] c1 = {0, 3, 6};
    int[] c2 = {1, 4, 7};
    int[] c3 = {2, 5, 8};
    int[] d1 = {0, 4, 8};
    int[] d2 = {2, 4, 6};
    */

    //list of values for each space
    int[] boardValues = {0, 0, 0,
            0, 0, 0,
            0, 0, 0};

    //0-8 for each space on the board with top left being 0 and bottom right being 8
    int bestMove = 0;

    int newPlayerPosition = 0;

    // >:D -albert
    public void updateBoardPositions() {
        boardPositions[newPlayerPosition] = 1;
        placePiece();
    }

    //call this before you use board values
    public void updateBoardValues()
    {

        for(int boardSpace : boardValues)
        {
            boardValues[boardSpace] = 0;
            // :/ - fredrick(has no idea why you are doing whatever you are currently doing hence the facial expression he was given because he is confused why you just did what you did so he thinks he will murder jackson now, maybe monroe if he doesn't fix whatever he did.
            if(boardPositions[boardSpace] >= 0)
            {
                boardValues[boardSpace] -= 1000000;
                continue;//takes me back to the start of the current loop
            }

            if (boardSpace == 0)
            {
                if (((boardPositions[boardSpace + 1]) == (boardPositions[boardSpace + 2])) ||
                        ((boardPositions[boardSpace + 4]) == (boardPositions[boardSpace + 8])) ||
                        ((boardPositions[boardSpace + 3]) == (boardPositions[boardSpace + 6])))
                {
                    if (((boardPositions[boardSpace + 1]) == 1) ||
                            ((boardPositions[boardSpace + 4]) == 1) ||
                            ((boardPositions[boardSpace + 3]) == 1))
                    {
                        boardValues[boardSpace] -= 1000000;
                    }
                    else if (((boardPositions[boardSpace + 1]) == 2) ||
                            ((boardPositions[boardSpace + 4]) == 2) ||
                            ((boardPositions[boardSpace + 3]) == 2))
                    {
                        boardValues[boardSpace] += 1000000;
                    }
                }
            }
            else if (boardSpace == 1)
            {
                if (((boardPositions[boardSpace + 1]) == (boardPositions[boardSpace -1])) ||
                        ((boardPositions[boardSpace + 3]) == (boardPositions[boardSpace + 6])))
                {
                    if (((boardPositions[boardSpace + 1]) == 1) ||
                            ((boardPositions[boardSpace + 3]) == 1))
                    {
                        boardValues[boardSpace] -= 1000000;
                    }
                    else if (((boardPositions[boardSpace + 1]) == 2) ||
                            ((boardPositions[boardSpace + 3]) == 2))
                    {
                        boardValues[boardSpace] += 1000000;
                    }
                }
            }
            else if (boardSpace == 2)
            {
                if (((boardPositions[boardSpace - 1]) == (boardPositions[boardSpace - 2])) ||
                        ((boardPositions[boardSpace + 2]) == (boardPositions[boardSpace + 4])) ||
                        ((boardPositions[boardSpace + 3]) == (boardPositions[boardSpace + 6])))
                {
                    if (((boardPositions[boardSpace - 1]) == 1) ||
                            ((boardPositions[boardSpace + 2]) == 1) ||
                            ((boardPositions[boardSpace + 3]) == 1))
                    {
                        boardValues[boardSpace] -= 1000000;
                    }
                    else if (((boardPositions[boardSpace - 1]) == 2) ||
                            ((boardPositions[boardSpace + 2]) == 2) ||
                            ((boardPositions[boardSpace + 3]) == 2))
                    {
                        boardValues[boardSpace] += 1000000;
                    }
                }
            }
            else if (boardSpace == 3)
            {
                if (((boardPositions[boardSpace + 1]) == (boardPositions[boardSpace + 2])) ||
                        ((boardPositions[boardSpace + 3]) == (boardPositions[boardSpace - 3])))
                {
                    if (((boardPositions[boardSpace + 1]) == 1) ||
                            ((boardPositions[boardSpace + 3]) == 1))
                    {
                        boardValues[boardSpace] -= 1000000;
                    }
                    else if (((boardPositions[boardSpace + 1]) == 2) ||
                            ((boardPositions[boardSpace + 3]) == 2))
                    {
                        boardValues[boardSpace] += 1000000;
                    }
                }
            }
            else if (boardSpace == 4)
            {
                if (((boardPositions[boardSpace + 1]) == (boardPositions[boardSpace - 1])) ||
                        ((boardPositions[boardSpace + 4]) == (boardPositions[boardSpace - 4])) ||
                        ((boardPositions[boardSpace + 2]) == (boardPositions[boardSpace -2])) ||
                        ((boardPositions[boardSpace + 3]) == (boardPositions[boardSpace -3])))
                {
                    if (((boardPositions[boardSpace + 1]) == 1) ||
                            ((boardPositions[boardSpace + 4]) == 1) ||
                            ((boardPositions[boardSpace + 2]) == 1) ||
                            ((boardPositions[boardSpace + 3]) == 1))
                    {
                        boardValues[boardSpace] -= 1000000;
                    }
                    else if (((boardPositions[boardSpace + 1]) == 2) ||
                            ((boardPositions[boardSpace + 4]) == 2) ||
                            ((boardPositions[boardSpace + 2]) == 2) ||
                            ((boardPositions[boardSpace + 3]) == 2))
                    {
                        boardValues[boardSpace] += 1000000;
                    }
                }
            }
            else if (boardSpace == 5)
            {
                if (((boardPositions[boardSpace - 1]) == (boardPositions[boardSpace - 2])) ||
                        ((boardPositions[boardSpace + 4]) == (boardPositions[boardSpace + 8])))
                {
                    if (((boardPositions[boardSpace - 1]) == 1) ||
                            ((boardPositions[boardSpace + 4]) == 1))
                    {
                        boardValues[boardSpace] -= 1000000;
                    }
                    else if (((boardPositions[boardSpace - 1]) == 2) ||
                            ((boardPositions[boardSpace + 4]) == 2))
                    {
                        boardValues[boardSpace] += 1000000;
                    }
                }
            }
            else if (boardSpace == 6)
            {
                if (((boardPositions[boardSpace + 1]) == (boardPositions[boardSpace + 2])) ||
                        ((boardPositions[boardSpace - 2]) == (boardPositions[boardSpace - 4])) ||
                        ((boardPositions[boardSpace - 3]) == (boardPositions[boardSpace - 6])))
                {
                    if (((boardPositions[boardSpace + 1]) == 1) ||
                            ((boardPositions[boardSpace - 2]) == 1) ||
                            ((boardPositions[boardSpace - 3]) == 1))
                    {
                        boardValues[boardSpace] -= 1000000;
                    }
                    else if (((boardPositions[boardSpace + 1]) == 2) ||
                            ((boardPositions[boardSpace - 2]) == 2) ||
                            ((boardPositions[boardSpace - 3]) == 2))
                    {
                        boardValues[boardSpace] += 1000000;
                    }
                }
            }
            else if (boardSpace == 7)
            {
                if (((boardPositions[boardSpace + 1]) == (boardPositions[boardSpace - 1])) ||
                        ((boardPositions[boardSpace +   3]) == (boardPositions[boardSpace -3])))
                {
                    if (((boardPositions[boardSpace + 1]) == 1) ||
                            ((boardPositions[boardSpace + 4]) == 1))
                    {
                        boardValues[boardSpace] -= 1000000;
                    }
                    else if (((boardPositions[boardSpace + 1]) == 2) ||
                            ((boardPositions[boardSpace + 4]) == 2))
                    {
                        boardValues[boardSpace] += 1000000;
                    }
                }
            }
            // > : ) Albertia - Alberts wife
            else if (boardSpace == 8)
            {
                if (((boardPositions[boardSpace - 1]) == (boardPositions[boardSpace - 2])) ||
                        ((boardPositions[boardSpace - 4]) == (boardPositions[boardSpace - 8])) ||
                        ((boardPositions[boardSpace - 3]) == (boardPositions[boardSpace - 6])))
                {
                    if (((boardPositions[boardSpace - 1]) == 1) ||
                            ((boardPositions[boardSpace - 4]) == 1) ||
                            ((boardPositions[boardSpace - 3]) == 1))
                    {
                        boardValues[boardSpace] -= 1000000;
                    }
                    else if (((boardPositions[boardSpace - 1]) == 2) ||
                            ((boardPositions[boardSpace - 4]) == 2) ||
                            ((boardPositions[boardSpace - 3]) == 2))
                    {
                        boardValues[boardSpace] += 1000000;
                    }
                }
            }

            //if the board space is odd, then it is a side
            if  ((boardSpace % 2) ==  1)
            {
                if (!(move == 3))
                {
                    boardValues[boardSpace] += 100;
                }
                else if ((boardPositions[0] == 1) && (boardPositions[8] == 1)
                        || (boardPositions[2] == 1) && (boardPositions[6] == 1))
                {
                    boardValues[boardSpace] += 1000;
                }
            }

            //if it is even, then it is a corner or the middle
            if ((boardSpace % 2) == 0)
            {
                if (boardSpace == 4)
                {
                    boardValues[boardSpace] += 1000;
                }
                else
                {
                    boardValues[boardSpace] += 500;
                }
            }

            boardValues[boardSpace] += boardSpace;
        }

    }
    //call this before you use best move
    public void findBestMove() {

        updateBoardValues();

        int max = boardValues[0];

        for(int boardValue : boardValues) {
            if (boardValue > max) {
                max = boardValue;
            }
        }
        bestMove = max;
    }

    public void placePiece() {
        int xTarget;
        int yTarget;

        findBestMove();

        if (bestMove<3)
        {
            xTarget = 0;
            yTarget = bestMove;
        }
        else if (bestMove<6)
        {
            xTarget = 1;
            yTarget = bestMove-3;
        }
        else
        {
            xTarget = 2;
            yTarget = bestMove-6;
        }



        move += 1;
        //move motors to best move and place

    }

}
 // : \ Fredricka - Fredricks girlfrie- sister.........              both?