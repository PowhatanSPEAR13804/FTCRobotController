package org.firstinspires.ftc.teamcode;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.helperclasses.buttonClick;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

//@Disabled
//safety :)

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TicTacToe", group="TeleOp")
public class TicTacToe extends LinearOpMode {

    double  x=0;
    double y =0;

    private static final boolean USE_WEBCAM = true;  // Set true to use a webcam, or false for a phone camera
    private static final int DESIRED_TAG_ID = -1;     // Choose the tag you want to approach or set to -1 for ANY tag.
    private VisionPortal visionPortal;               // Used to manage the video source.
    private AprilTagProcessor aprilTag;              // Used for managing the AprilTag detection process.
    private AprilTagDetection desiredTag = null;     // Used to hold the data for a detected AprilTag


    static final char player = 'o';
    static final char opponent = 'x';

    double[] expectedPosOnCamX = {1, 2, 3,
                              4, 5, 6,
                              7, 8, 9};
    double[] expectedPosOnCamY = {1, 2, 3,
                               4, 5, 6,
                               7, 8, 9};
    double[] expectedPosOnMotorX = {1, 2, 3,
            4, 5, 6,
            7, 8, 9};
    double[] expectedPosOnMotorY = {1, 2, 3,
            4, 5, 6,
            7, 8, 9};

    int expectedDiveationX = 100;
    int expectedDiveationY = 100;

    int pos =-1;
    double baseX =0;
    double baseY =0;

    //true means not clicked



    @Override
    public void runOpMode() throws InterruptedException {

        initAprilTag();
        if (USE_WEBCAM)
            setManualExposure(6, 150);  // Use low exposure time to reduce motion blur

        //AnalogInput HomeSenorX = hardwareMap.analogInput.get("HomeSp0");
      //  DigitalChannel HomeSenorX = hardwareMap.digitalChannel.get("HomeSp0");




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

        pickupLinearServo.setPosition(0);
        sleep(4000);
        RotationServo.setPosition(0);
        TouchSensor HomeSenorY = hardwareMap.get(TouchSensor.class, "HomeSp0");
        TouchSensor HomeSenorX = hardwareMap.get(TouchSensor.class, "HomeSp2");


        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

          //  AprilTag_telemetry_for_Portal_1(board);

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


/*
            //Spins the pickup 90 degress
            if (gamepad1.y) {
                RotationServo.setPosition((85.0 / 270.0));
            } else if (gamepad1.b) {
                pickupLinearServo.setPosition(0);
                RotationServo.setPosition(0);
            }
            if (gamepad1.x) {
                //servo out
                pickupLinearServo.setPosition(0.48);
            } else if (gamepad1.a) {
                //servo in
                pickupLinearServo.setPosition(0);
            }

 */

            if (gamepad1.b) {
                homeMotors(xMotor,yMotor,HomeSenorX,HomeSenorY);
                resetCenter();
            }
            if (gamepad1.a) {
                resetMotorCenter(xMotor,yMotor);
                grabAPiece(xMotor,yMotor,RotationServo,pickupLinearServo,HomeSenorX,HomeSenorY);
            }
            if (gamepad1.x) {
                int i = getPos(board);
                pos = findBestMove(board);
                board[pos] = 'o';

            }
            if (gamepad1.y) {
                MoveToSpot(xMotor,yMotor,HomeSenorX,HomeSenorY,expectedPosOnMotorX[pos], expectedPosOnMotorY[pos]);
            }


            if (gamepad1.right_trigger>0) {
                //servo out
                pickupLinearServo.setPosition(pickupLinearServo.getPosition()+0.01);
            } else if (gamepad1.left_trigger>0) {
                //servo in
                pickupLinearServo.setPosition(pickupLinearServo.getPosition()-0.01);
            }



            //home placer
            if(gamepad1.right_bumper){
                homeMotors(xMotor,yMotor,HomeSenorX,HomeSenorY);
                resetCenter();
            }
            //grab a piece
            if(gamepad1.left_bumper){
                grabAPiece(xMotor,yMotor,RotationServo,pickupLinearServo,HomeSenorX,HomeSenorY);
             //   resetMotorCenter(xMotor,yMotor);
            }

            if(gamepad1.left_stick_button){
                homeMotors(xMotor,yMotor,HomeSenorX,HomeSenorY);
                int i = getPos(board);
                pos = findBestMove(board);
                MoveToSpot(xMotor,yMotor,HomeSenorX,HomeSenorY,expectedPosOnMotorX[pos], expectedPosOnMotorY[pos]);


             telemetry.addLine("Pos = "+i);
             telemetry.addLine("Current Cords ="+expectedPosOnCamX[i]+","+expectedPosOnCamY[i]);

            }
            if(gamepad1.right_stick_button){

              // resetCenter();
               // MoveToSpot(xMotor,yMotor,HomeSenorX,HomeSenorY,expectedPosOnMotorX[4], expectedPosOnMotorY[4]);
               // grabAPiece(xMotor,yMotor,RotationServo,pickupLinearServo,HomeSenorX,HomeSenorY);
                placePiece(xMotor,yMotor,pickupLinearServo);
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

            telemetry.addLine("x="+x);
            telemetry.addLine("y="+y);

            telemetry.addLine("BaseX="+baseX);
            telemetry.addLine("BaseY="+baseY);
            telemetry.addLine(Arrays.toString(expectedPosOnMotorX));
            telemetry.addLine(Arrays.toString(expectedPosOnMotorY));
            telemetry.addLine("Pos ="+pos);
            telemetry.addLine("Board ="+Arrays.toString(board));



            telemetry.update();

           // findBestMove(board);

        }
    }





    public void resetCenter(){
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        for (AprilTagDetection detection : currentDetections) {
            // Look to see if we have size info on this tag.
            if (detection.metadata != null) {
                x =detection.rawPose.x;
                y =detection.rawPose.y;
            } else {
                // This tag is NOT in the library, so we don't have enough information to track to it.
                telemetry.addData("Unknown", "Tag ID %d is not in TagLibrary", detection.id);
            }
        }
        baseX =x;
        baseY =y;
        expectedPosOnCamX[0] = baseX+2.5;  expectedPosOnCamX[1] = baseX;  expectedPosOnCamX[2] = baseX-2.5;
        expectedPosOnCamY[0] = baseY-3.5;  expectedPosOnCamY[1] = baseY-3.5;  expectedPosOnCamY[2] = baseY-3.5;

        expectedPosOnCamX[3] = baseX+2.5;  expectedPosOnCamX[4] = baseX;  expectedPosOnCamX[5] = baseX-2.5;
        expectedPosOnCamY[3] = baseY;  expectedPosOnCamY[4] = baseY;  expectedPosOnCamY[5] = baseY;

        expectedPosOnCamX[6] = baseX+2.5;  expectedPosOnCamX[7] = baseX;  expectedPosOnCamX[8] = baseX-2.5;
        expectedPosOnCamY[6] = baseY+2.5;  expectedPosOnCamY[7] = baseY+2.5;  expectedPosOnCamY[8] = baseY+2.5;
    }
    public void resetMotorCenter(DcMotor x,DcMotor y){
        baseX =x.getCurrentPosition();
        baseY =y.getCurrentPosition();
        expectedPosOnMotorX[0] = baseX-8000;  expectedPosOnMotorX[1] = baseX;  expectedPosOnMotorX[2] = baseX+8000;
        expectedPosOnMotorY[0] = baseY+13000;  expectedPosOnMotorY[1] = baseY+13000;  expectedPosOnMotorY[2] = baseY+13000;

        expectedPosOnMotorX[3] = baseX-8000;  expectedPosOnMotorX[4] = baseX;  expectedPosOnMotorX[5] = baseX+8000;
        expectedPosOnMotorY[3] = baseY;  expectedPosOnMotorY[4] = baseY;  expectedPosOnMotorY[5] = baseY;

        expectedPosOnMotorX[6] = baseX-8000;  expectedPosOnMotorX[7] = baseX;  expectedPosOnMotorX[8] = baseX+8000;
        expectedPosOnMotorY[6] = baseY-13000;  expectedPosOnMotorY[7] = baseY-13000;  expectedPosOnMotorY[8] = baseY-13000;
    }
    public int getPos(char[] board){
        int place=-1;

        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        for (AprilTagDetection detection : currentDetections) {
            // Look to see if we have size info on this tag.
            if (detection.metadata != null) {
                x =detection.rawPose.x;
                y =detection.rawPose.y;
                for(int i =0;i<9;i++){
                    if(expectedPosOnCamX[i]+1>x&&x>expectedPosOnCamX[i]-1){
                        if(expectedPosOnCamY[i]+1>y&&y>expectedPosOnCamY[i]-1){
                            place = i;
                        }
                    }
                }
                if(place>-1){
                    board[place] = 'x';
                }
            } else {
                // This tag is NOT in the library, so we don't have enough information to track to it.
                telemetry.addData("Unknown", "Tag ID %d is not in TagLibrary", detection.id);
            }
        }

      return (place);
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

    public void grabAPiece(DcMotor x,DcMotor y,Servo RotationServo, Servo LinearServo,TouchSensor TSX,TouchSensor TSY){
        homeMotors(x,y,TSX,TSY);
        LinearServo.setPosition(0);
        sleep(2000);
        while(y.getCurrentPosition()>-33000){
            if(isStopRequested()) return;
            y.setPower(-1);
            sleep(1);
        }
        y.setPower(0);
        RotationServo.setPosition((85.0 / 270.0));
        sleep(100);
        LinearServo.setPosition(0.84);
        sleep(3000);
        while(y.getCurrentPosition()<-25500){
            if(isStopRequested()) return;
            y.setPower(1);
            sleep(1);
        }
        y.setPower(0);
        RotationServo.setPosition((65.0 / 270.0));
        sleep(2000);
        LinearServo.setPosition(0.6);
        sleep(3000);
        RotationServo.setPosition(0);
        sleep(100);
        LinearServo.setPosition(0.40);
        homeMotors(x,y,TSX,TSY);

    }

    public void placePiece(DcMotor x,DcMotor y,Servo LS){
        LS.setPosition(1);
        sleep(10000);
        LS.setPosition(0.7);
        sleep(10000);
        double tempY = y.getCurrentPosition();
        while (tempY-5000<y.getCurrentPosition()){
            y.setPower(-0.5);
            sleep(1);
        }
        y.setPower(0);
        LS.setPosition(0);
    }

    public  void MoveToSpot(DcMotor x,DcMotor y,TouchSensor TSX,TouchSensor TSY,double posX, double posY){
        homeMotors(x,y,TSX,TSY);
        while(y.getCurrentPosition()>posY){
            if(isStopRequested()) return;
            y.setPower(-0.5);
            sleep(1);
        }
        y.setPower(0);
        while(x.getCurrentPosition()<posX){
            if(isStopRequested()) return;
            x.setPower(0.5);
            sleep(1);
        }
        x.setPower(0);

    }

    public static Boolean isMovesLeft(char[] board) {
        for (int i = 0; i < 9; i++)
                if (board[i] == '_')
                    return true;
        return false;
    }

    public int findBestMove(char[] board) {
        int moveIndex = -1;
        int moveVal = Integer.MIN_VALUE;
        int bestVal = Integer.MIN_VALUE;
        for(int i = 0; i < 9; i++){
            if(board[i] == '_') {
                // try a move
                board[i] = player;
                // check value
                moveVal = minimax(board, 0, false);
                // undo move
                board[i] = '_';
                if(moveVal > bestVal) {
                    moveIndex = i;
                }
            }
        }

        int[] movePosition = moveToCoordinates(moveIndex);
        board[moveIndex] = player;
        return moveIndex;
    }
    public int[] moveToCoordinates(int moveIndex) {
        int[] movePosition = new int[2];
        switch (moveIndex) {
            //using temporary x and y values.
            case 0:
                movePosition = new int[]{0, 0};
                break;
            case 1:
                movePosition = new int[]{0, 1};
                break;
            case 2:
                movePosition = new int[]{0, 2};
                break;
            case 3:
                movePosition = new int[]{1, 0};
                break;
            case 4:
                movePosition = new int[]{1, 1};
                break;
            case 5:
                movePosition = new int[]{1, 2};
                break;
            case 6:
                movePosition = new int[]{2, 0};
                break;
            case 7:
                movePosition = new int[]{2, 1};
                break;
            case 8:
                movePosition = new int[]{2, 2};
                break;
            case 9:
                //no change found
                movePosition = new int[]{-1, -1};
                break;
        }
        return movePosition;
    }

    public static int evaluate(char[] board, int depth) {
        //win for x is -10 and win for o is +10;
        for (int row = 0; row <= 6; row+=3)
        {
            if (board[row] == board[row + 1] && board[row + 1] == board[row + 2])
            {
                if (board[row] == 'x')
                    return -10 + depth;
                else if (board[row] == 'o')
                    return 10 - depth;
            }
        }
        for (int col = 0; col < 3; col++)
        {
            if (board[col] == board[col + 3] && board[col + 3] == board[col + 6]) {
                if (board[col] == 'x')
                    return -10 + depth;
                else if (board[col] == 'o')
                    return 10 - depth;
            }
        }
        if(board[2] == board[4] && board[4] == board[6]) {
            if(board[4] == 'x')
                return - 10 + depth;
            else if(board[4] == 'o')
                return 10 - depth;
        }

        //if there no wins return 0
        return 0;
    }

    static int minimax(char[] board, int depth, Boolean isMax) {
        int score = evaluate(board, depth);
        //we win
        if(score == 10)
            return score;

        //opponent wins
        if(score == -10)
            return score;

        //no more moves and no one wins
        if(!isMovesLeft(board))
            return 0;

        int best;
        if(isMax) {
            best = Integer.MIN_VALUE;

            for (int i = 0; i < 9; i++) {
                if (board[i] == '_') {
                    // try a move
                    board[i] = player;
                    // check value
                    best = Math.max(best, minimax(board, depth + 1, false));
                    // undo move
                    board[i] = '_';
                }
            }
        } else {
            best = Integer.MAX_VALUE;

            for (int i = 0; i < 9; i++) {
                if (board[i] == '_') {
                    // try a move
                    board[i] = opponent;
                    // check value
                    best = Math.min(best, minimax(board, depth + 1, true));
                    // undo move
                    board[i] = '_';
                    }
            }
        }
        return best;
    }

    private void initAprilTag() {
        // Create the AprilTag processor by using a builder.
        aprilTag = new AprilTagProcessor.Builder().build();

        // Adjust Image Decimation to trade-off detection-range for detection-rate.
        // eg: Some typical detection data using a Logitech C920 WebCam
        // Decimation = 1 ..  Detect 2" Tag from 10 feet away at 10 Frames per second
        // Decimation = 2 ..  Detect 2" Tag from 6  feet away at 22 Frames per second
        // Decimation = 3 ..  Detect 2" Tag from 4  feet away at 30 Frames Per Second
        // Decimation = 3 ..  Detect 5" Tag from 10 feet away at 30 Frames Per Second
        // Note: Decimation can be changed on-the-fly to adapt during a match.
        aprilTag.setDecimation(3);

        // Create the vision portal by using a builder.
        if (USE_WEBCAM) {
            visionPortal = new VisionPortal.Builder()
                    .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                    .addProcessor(aprilTag)
                    .build();
        } else {
            visionPortal = new VisionPortal.Builder()
                    .setCamera(BuiltinCameraDirection.BACK)
                    .addProcessor(aprilTag)
                    .build();
        }
    }

    /*
     Manually set the camera gain and exposure.
     This can only be called AFTER calling initAprilTag(), and only works for Webcams;
    */
    private void    setManualExposure(int exposureMS, int gain) {
        // Wait for the camera to be open, then use the controls

        if (visionPortal == null) {
            return;
        }

        // Make sure camera is streaming before we try to set the exposure controls
        if (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {
            telemetry.addData("Camera", "Waiting");
            telemetry.update();
            while (!isStopRequested() && (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING)) {
                sleep(20);
            }
            telemetry.addData("Camera", "Ready");
            telemetry.update();
        }

        // Set camera controls unless we are stopping.
        if (!isStopRequested())
        {
            ExposureControl exposureControl = visionPortal.getCameraControl(ExposureControl.class);
            if (exposureControl.getMode() != ExposureControl.Mode.Manual) {
                exposureControl.setMode(ExposureControl.Mode.Manual);
                sleep(50);
            }
            exposureControl.setExposure((long)exposureMS, TimeUnit.MILLISECONDS);
            sleep(20);
            GainControl gainControl = visionPortal.getCameraControl(GainControl.class);
            gainControl.setGain(gain);
            sleep(20);
        }
    }
    public void ComparePosOnCam(double x,double y,char[] board){
        for(int i =0;i<expectedPosOnCamX.length;i++){
            if(x<expectedPosOnCamX[i]+expectedDiveationX&&x>expectedPosOnCamX[i]-expectedDiveationX){
                if(y<expectedPosOnCamY[i]+expectedDiveationY&&y>expectedPosOnCamY[i]-expectedDiveationY){
                board[i] = opponent;
                }
            }
        }
    }
}