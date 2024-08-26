package org.firstinspires.ftc.teamcode.Archive.TwentyOneTwentyTwo;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Disabled


@TeleOp

public class TeleOp_21_22 extends LinearOpMode

{
    private ElapsedTime timer = new ElapsedTime();

    private DcMotor forearm = hardwareMap.get(DcMotor.class, "Hub2_motor1");
    private DcMotor Rotation = hardwareMap.get(DcMotor.class, "Hub2_motor2");
    private Servo box1 = hardwareMap.get(Servo.class, "Hub1_servo0");
    private Servo box2 = hardwareMap.get(Servo.class, "Hub1_servo1");

    @Override
    public void runOpMode() throws InterruptedException
    {
        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor motorFrontLeft = hardwareMap.dcMotor.get("Hub2_motor0");
        DcMotor motorBackLeft = hardwareMap.dcMotor.get("Hub2_motor2");
        DcMotor motorFrontRight = hardwareMap.dcMotor.get("Hub1_motor0");
        DcMotor motorBackRight = hardwareMap.dcMotor.get("Hub1_motor2");
        DcMotor upperArm = hardwareMap.dcMotor.get("Hub2_motor1");
        DcMotor foreArm = hardwareMap.dcMotor.get("Hub1_motor1");
        DcMotor intake = hardwareMap.dcMotor.get("Hub2_motor3");
        DcMotor duckMotor = hardwareMap.dcMotor.get("Hub1_motor3");
        Servo finger = hardwareMap.servo.get("Hub1_Servo_2");
        CRServo wrist = hardwareMap.crservo.get("Hub1_Servo_4");

        upperArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        foreArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Reverse the right side motors
        //Reverse left motors if you are using NeveRests
        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);
        //motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //motorFrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //upperArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //foreArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //duckMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER); */

        waitForStart();

        if (isStopRequested()) return;

        int level = 2;

        //upperArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //foreArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, this is reversed!
            int x = (int)(gamepad1.left_stick_x * 1.1); // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio, but only when
            // at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), .8);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            motorFrontLeft.setPower(frontLeftPower);
            motorBackLeft.setPower(backLeftPower);
            motorFrontRight.setPower(frontRightPower);
            motorBackRight.setPower(backRightPower);

            int collectionUpper = -150;//level 0
            int collectionFore = -1700;
            double collectionWrist = 1;

            int travelPositionUpper = 100;//level 1
            int travelPositionFore = -230;
            double travelPositionWrist = -1;

            int topLevelUpper = 200;//level 2
            int topLevelFore = 2600;
            double topLevelWrist = -1;

            //int midLevelUpper = 450;//level 3
            //int midLevelFore = 3000;
            //double midLevelWrist = 0;

            //int bottomLevelUpper = 500;//level 4
            //int bottomLevelFore = 3500;
            //double bottomLevelWrist = 0;

            int fifthArmUpper = 230;//shared hub level level 5
            int fifthArmFore = 4000;//shared hub level

            //UpperArm and ForeArm controls
            if(opModeIsActive() && gamepad2.left_bumper )
            {
                timer.reset();
                if(level==0)//collection position->travel position
                {
                    foreArm.setTargetPosition(travelPositionFore);
                    upperArm.setTargetPosition(travelPositionUpper);
                    foreArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    upperArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    telemetry.addData("travel position",level);
                    telemetry.update();
                    //wrist.setPosition(travelPositionWrist);
                    foreArm.setPower(-0.2);
                    upperArm.setPower(-0.2);
                    level = 1;
                }

                else if(level==1)//travel position->top level
                {
                    foreArm.setTargetPosition(topLevelFore);
                    upperArm.setTargetPosition(topLevelUpper);
                    foreArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    upperArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    telemetry.addData("top position",level);
                    telemetry.update();
                    //wrist.setPosition(topLevelWrist);
                    foreArm.setPower(-0.3);
                    upperArm.setPower(-0.3);
                    level = 2;
                }
            /*else if(level==2)//top level->middle level
            {
               foreArm.setTargetPosition(midLevelFore);
               upperArm.setTargetPosition(midLevelUpper);
               foreArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
               upperArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
               telemetry.addData("middle position",level);
               telemetry.update();
               //wrist.setPosition(midLevelWrist);
               foreArm.setPower(-0.3);
               upperArm.setPower(-0.3);
               level = 3;
            }*/
            /*else if(level==3)//middle level->bottom level
            {
               foreArm.setTargetPosition(bottomLevelFore);
               upperArm.setTargetPosition(bottomLevelUpper);
               foreArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
               upperArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
               telemetry.addData("bottom position",level);
               telemetry.update();
               //wrist.setPosition(bottomLevelWrist);
               foreArm.setPower(-0.3);
               upperArm.setPower(-0.3);
               level = 4;
            }*/
                else if(level==2)//top level->shared level
                {
                    foreArm.setTargetPosition(fifthArmFore);
                    upperArm.setTargetPosition(fifthArmUpper);
                    foreArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    upperArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    telemetry.addData("shared position",level);
                    telemetry.update();
                    foreArm.setPower(-0.3);
                    upperArm.setPower(-0.3);
                    level = 5;
                }
                else if(level==5)
                {
                    telemetry.addData("shared position",level);
                    telemetry.update();
                }

                while(timer.seconds()<.5)
                {
                    //do nothing, just wait
                }

            }
            else if(opModeIsActive() && gamepad2.right_bumper)
            {
                timer.reset();
                if(level==0)//collection position
                {
                    //foreArm.setTargetPosition(100);
                    //upperArm.setTargetPosition(100);
                    //foreArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    //upperArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    telemetry.addData("collection position",level);
                    telemetry.update();
                    //foreArm.setPower(-0.3);
                    //upperArm.setPower(-0.3);
                }

                else if(level==1)//travel position->collection position
                {
                    foreArm.setTargetPosition(collectionFore);
                    upperArm.setTargetPosition(collectionUpper);
                    //upperArm.setTargetPosition(200);
                    foreArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    upperArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    telemetry.addData("collection position",level);
                    telemetry.update();
                    //wrist.setPosition(collectionWrist);
                    foreArm.setPower(-0.1);
                    upperArm.setPower(-0.1);
                    level = 0;
                }
                else if(level==2)//top position->travel position
                {
                    foreArm.setTargetPosition(travelPositionFore);
                    upperArm.setTargetPosition(travelPositionUpper);
                    foreArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    upperArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    telemetry.addData("travel position",level);
                    telemetry.update();
                    //wrist.setPosition(travelPositionWrist);
                    foreArm.setPower(-0.3);
                    upperArm.setPower(-0.3);
                    level = 1;
                }
            /*else if(level==3)//middle position->top position
            {
               foreArm.setTargetPosition(topLevelFore);
               upperArm.setTargetPosition(topLevelUpper);
               foreArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
               upperArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
               telemetry.addData("top position",level);
               telemetry.update();
               //wrist.setPosition(topLevelWrist);
               foreArm.setPower(-0.3);
               upperArm.setPower(-0.3);
               level = 2;
            }*/
            /*else if(level==4)//bottom position->middle position
            {
               foreArm.setTargetPosition(midLevelFore);
               upperArm.setTargetPosition(midLevelUpper);
               foreArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
               upperArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
               telemetry.addData("middle position",level);
               telemetry.update();
               //wrist.setPosition(midLevelWrist);
               foreArm.setPower(-0.3);
               upperArm.setPower(-0.3);
               level = 3;
            }*/
                else if(level==5)//fifth position->top position
                {
                    foreArm.setTargetPosition(topLevelFore);
                    upperArm.setTargetPosition(topLevelUpper);
                    foreArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    upperArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    telemetry.addData("top position",level);
                    telemetry.update();
                    foreArm.setPower(-0.3);
                    upperArm.setPower(-0.3);
                    level = 2;
                }



                while(timer.seconds()<.5)
                {
                    //do nothing, just wait
                }
            }


            //servo controls
            //if(opModeIsActive() && gamepad2.left_bumper){
            //   lobsterClaw.setPosition(30);
            //}

            //Intake controls
            if(opModeIsActive() && gamepad1.right_bumper)
            {
                intake.setPower(-1);
            }
            if(opModeIsActive() && gamepad1.left_bumper)
            {
                intake.setPower(0);
            }
            if(opModeIsActive() && gamepad1.b)
            {
                intake.setPower(1);
            }

            //blue duckmotor controls
            if(opModeIsActive()&& gamepad1.right_trigger >-0.2)
            {
                duckMotor.setPower(gamepad1.right_trigger);
            }
            if(opModeIsActive() && gamepad1.a)
            {
                duckMotor.setPower(-.4);
            /*for (int i = 0; i <8; i++)
            {
               timer.reset();
               while(timer.seconds()<1.5)
               {
                  duckMotor.setPower(-.5);
               }
               timer.reset();
               while(timer.seconds()<.15)
               {
                  duckMotor.setPower(0);
               }
               timer.reset();
               while(timer.seconds()<1);
               {
                  duckMotor.setPower(.5);
               }
               timer.reset();*/


            }
            //red duckmotor controls
            if(opModeIsActive()&& gamepad1.left_trigger >.2)
            {
                duckMotor.setPower(gamepad1.left_trigger);
            }
            if(opModeIsActive() && gamepad1.y)
            {
                duckMotor.setPower(.4);
            /*for (int i = 0; i <8; i++)
            {
               timer.reset();
               while(timer.seconds()<1.5)
               {
                  duckMotor.setPower(-.25);
               }
               timer.reset();
               while(timer.seconds()<.15)
               {
                  duckMotor.setPower(-.15);
               }
               timer.reset();
               while(timer.seconds()<3.5);
               {
                duckMotor.setPower(0.1);
               }
               timer.reset(); */

            }
            if(opModeIsActive() && gamepad1.x)
            {
                duckMotor.setPower(0);
            }

            if(opModeIsActive()&&gamepad2.right_trigger>0)
            {
                finger.setPosition(.85);
            }
            if(opModeIsActive()&&gamepad2.left_trigger>0)
            {
                finger.setPosition(.95);
            }
            if(opModeIsActive()&&gamepad2.y)
            {
                wrist.setPower(0.2);
            }
            if(opModeIsActive()&&gamepad2.a)
            {
                wrist.setPower(-0.2);
            }
            if(opModeIsActive()&&gamepad2.b)
            {
                wrist.setPower(0);
            }
            if(opModeIsActive()&&gamepad2.x)
            {

                finger.setPosition(0.70);
            }

            //Up
            if(opModeIsActive() && gamepad1.right_trigger > 0.2) {
                encoderDrive(0.2, 5);
            }

            //Down
            if(opModeIsActive() && gamepad1.left_trigger > -0.2) {
                encoderDrive(0.2, -5);
            }

        }

    }

    public void encoderDrive(double speed, double height) {

    }


}









