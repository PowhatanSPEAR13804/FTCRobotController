package org.firstinspires.ftc.teamcode;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.helperclasses.buttonClick;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

//@Disabled
//safety :)

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TicTacToe", group="TeleOp")
public class TicTacToe extends LinearOpMode {
    VisionPortal myVisionPortal_1;
    VisionPortal.Builder myVisionPortalBuilder;
    boolean USE_WEBCAM_1 = true;
    int Portal_1_View_ID;
    AprilTagProcessor myAprilTagProcessor_1;

    static final char player = 'o';
    static final char opponent = 'x';

    //true means not clicked



    @Override
    public void runOpMode() throws InterruptedException {

        //AnalogInput HomeSenorX = hardwareMap.analogInput.get("HomeSp0");
      //  DigitalChannel HomeSenorX = hardwareMap.digitalChannel.get("HomeSp0");



        initAprilTag();
        // Declare our motors
        // Make sure your ID's match your configuration


        char[] board = new char[]{'_', '_', '_',
                                  '_', '_', '_',
                                  '_', '_', '_'};

        //linear servo
        Servo pickupLinearServo = hardwareMap.servo.get("S4");
        Servo RotationServo = hardwareMap.servo.get("S5");

        // Reverse the right side motors
        // Reverse left motors if you are using NeveRests
        DcMotor yMotor = hardwareMap.dcMotor.get("M0");
        DcMotor xMotor = hardwareMap.dcMotor.get("M3");
        xMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        yMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        resetMotors(xMotor,yMotor);

        RotationServo.setPosition(0);
        TouchSensor HomeSenorY = hardwareMap.get(TouchSensor.class, "HomeSp0");
        TouchSensor HomeSenorX = hardwareMap.get(TouchSensor.class, "HomeSp2");


        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {


            if (gamepad1.dpad_left) {
                xMotor.setPower(-1);
            } else if (gamepad1.dpad_right ) {
                xMotor.setPower(1);
            } else {
                xMotor.setPower(0);
            }
            if (gamepad1.dpad_up ) {
                yMotor.setPower(1);
            } else if (gamepad1.dpad_down ) {
                yMotor.setPower(-1);
            } else {
                yMotor.setPower(0);
            }

            if (gamepad1.x) {
                pickupLinearServo.setPosition(1);
            } else if (gamepad1.a) {
                pickupLinearServo.setPosition(0);
            }

            if (gamepad1.y) {
                RotationServo.setPosition((85.0 / 270.0));
            } else if (gamepad1.b) {
                RotationServo.setPosition(0);
            }

            if(gamepad1.right_bumper){
                homeMotors(xMotor,yMotor,HomeSenorX,HomeSenorY);
            }


            telemetry.addLine("xMotor power: " + xMotor.getPower());
            telemetry.addLine("yMotor power: " + yMotor.getPower());
            telemetry.addLine("xMotor pos: " + xMotor.getCurrentPosition());
            telemetry.addLine("yMotor pos: " + yMotor.getCurrentPosition());

            //true means not clicked
            telemetry.addLine("HomeSenorY Power:"+HomeSenorY.isPressed());
            telemetry.addLine("HomeSenorX Power:"+HomeSenorX.isPressed());


            telemetry.addLine("pickupServo: " + pickupLinearServo.getPosition());
            telemetry.addLine("RotServo: " + RotationServo.getPosition());
            telemetry.addLine("pickupServo: " + pickupLinearServo.getPosition());
            telemetry.addLine("RotServo: " + RotationServo.getPosition());

            telemetry.update();

            findBestMove(board);

        }
    }



    public int[] findBestMove(char[] board) {
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

        int[] movePosition = moveToCoordinates(moveIndex);
        return movePosition;
    }

    public void  resetMotors(DcMotor x,DcMotor y){
        x.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        y.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        x.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        y.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void  homeMotors(DcMotor x,DcMotor y,TouchSensor TSX,TouchSensor TSY){
        while (TSX.isPressed()){
            if(isStopRequested()) return;
            x.setPower(-1);
            sleep(1);
        }
        x.setPower(0);
        while (TSY.isPressed()){
            if(isStopRequested()) return;
            y.setPower(1);
            sleep(1);
        }
        y.setPower(0);
        sleep(100);
        resetMotors(x,y);
    }

    public void grabAPiece(DcMotor x,DcMotor y,Servo RotationServo){
        while(y.getCurrentPosition()>-39000){
            if(isStopRequested()) return;
            y.setPower(-1);
            sleep(1);
        }
        RotationServo.setPosition((85.0 / 270.0));
        sleep(100);
        
    }

    public static Boolean isMovesLeft(char[] board) {
        for (int i = 0; i < 9; i++)
                if (board[i] == '_')
                    return true;
        return false;
    }


    public int[] moveToCoordinates(int moveIndex) {
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

    private void initAprilTag() {
        AprilTagProcessor.Builder myAprilTagProcessorBuilder;

        // First, create an AprilTagProcessor.Builder.
        myAprilTagProcessorBuilder = new AprilTagProcessor.Builder();
        // Create each AprilTagProcessor by calling build.
        myAprilTagProcessor_1 = myAprilTagProcessorBuilder.build();
        Make_first_VisionPortal();
    }

    private void Make_first_VisionPortal() {
        // Create a VisionPortal.Builder and set attributes related to the first camera.
        myVisionPortalBuilder = new VisionPortal.Builder();
        if (USE_WEBCAM_1) {
            // Use a webcam.
            myVisionPortalBuilder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        } else {
            // Use the device's back camera.
            myVisionPortalBuilder.setCamera(BuiltinCameraDirection.BACK);
        }
        // Manage USB bandwidth of two camera streams, by adjusting resolution from default 640x480.
        // Set the camera resolution.
        myVisionPortalBuilder.setCameraResolution(new Size(320, 240));
        // Manage USB bandwidth of two camera streams, by selecting Streaming Format.
        // Set the stream format.
        myVisionPortalBuilder.setStreamFormat(VisionPortal.StreamFormat.MJPEG);
        // Add myAprilTagProcessor to the VisionPortal.Builder.
        myVisionPortalBuilder.addProcessor(myAprilTagProcessor_1);
        // Add the Portal View ID to the VisionPortal.Builder
        // Set the camera monitor view id.
        myVisionPortalBuilder.setLiveViewContainerId(Portal_1_View_ID);
        // Create a VisionPortal by calling build.
        myVisionPortal_1 = myVisionPortalBuilder.build();
    }

}