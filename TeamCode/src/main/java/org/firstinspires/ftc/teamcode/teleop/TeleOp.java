package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.helperclasses.buttonClick;

//@Disabled
//safety :)

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp", group="TeleOp")
public class TeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor motorFrontLeft = hardwareMap.dcMotor.get("Hub1_Motor3");
        DcMotor motorBackLeft = hardwareMap.dcMotor.get("Hub1_Motor0");
        DcMotor motorFrontRight = hardwareMap.dcMotor.get("Hub2_Motor0");
        DcMotor motorBackRight = hardwareMap.dcMotor.get("Hub2_Motor3");

        DcMotor fourBar = hardwareMap.dcMotor.get("Hub1_Motor2");
        DcMotor viper =  hardwareMap.dcMotor.get("Hub1_Motor1");
        DcMotor hangingM = hardwareMap.dcMotor.get("Hub2_Motor1");

        //continuous servos
        Servo throughput =  hardwareMap.servo.get("Hub2_Servo4");
        Servo intakeLeft =  hardwareMap.servo.get("Hub1_Servo0");
        Servo intakeRight =  hardwareMap.servo.get("Hub2_Servo0");

        //Positional servos
        Servo launch =  hardwareMap.servo.get("Hub1_Servo3");
        Servo outputS =  hardwareMap.servo.get("Hub1_Servo5");
        Servo hangingS =  hardwareMap.servo.get("Hub2_Servo1");
        Servo hook = hardwareMap.servo.get("Hub2_Servo2");

        //linear servo
        Servo outputL =  hardwareMap.servo.get("Hub1_Servo4");

        // Reverse the right side motors
        // Reverse left motors if you are using NeveRests
        motorFrontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackRight.setDirection(DcMotorSimple.Direction.FORWARD);
        motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeLeft.setDirection((Servo.Direction.REVERSE));
        intakeRight.setDirection((Servo.Direction.FORWARD));

        hangingM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        double throughputPosition = 0.5;
        boolean forward = false;
        boolean backward = false;
        double intakePosition = 0.5;
        double fourBarPower = 0;
        double viperPower = 0;
        double launchPosition = 0;
        double outputSPosition = 0.5;
        double outputLPosition = 0;
        double hookDownPosition = hook.getPosition();
        double hookUpPosition = hookDownPosition + 180.0/270.0;
        double hookPosition;
        int viperPosition = 0;
        boolean linearOpen = false;
        boolean servoOpen = false;
        boolean launchServoOpen = false;
        boolean hookUp = false;

        buttonClick dPadLeft = new buttonClick();
        buttonClick dPadRight = new buttonClick();
        //buttonClick gamepadA = new buttonClick();
        buttonClick gamepadB = new buttonClick();
        buttonClick gamepadX = new buttonClick();
        buttonClick gamepadY = new buttonClick();
        buttonClick BumperLeft = new buttonClick();
        buttonClick BumperRight = new buttonClick();
        buttonClick littleBroB = new buttonClick();
        buttonClick littleBroY = new buttonClick();
        /*
        buttonClick littleBroA = new buttonClick();
        buttonClick littleBroDL = new buttonClick();
        buttonClick littleBroDR = new buttonClick();
        */
        buttonClick littleBroRB = new buttonClick();


        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            //x and y were switched :)
            double x = -gamepad1.left_stick_x; // Remember, this is reversed!
            double y = gamepad1.left_stick_y * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio, but only when
            // at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            motorFrontLeft.setPower(frontLeftPower);
            motorBackLeft.setPower(backLeftPower);
            motorFrontRight.setPower(frontRightPower);
            motorBackRight.setPower(backRightPower);

            //throughput
            dPadRight.checkButton(gamepad1.dpad_right);
            dPadLeft.checkButton(gamepad1.dpad_left);
            if (dPadRight.getClickCount() > 0 && !forward && !backward) {
                forward = true;
                dPadRight.resetClickCount();
            } else if (dPadRight.getClickCount() > 0 && forward) {
                forward = false;
                dPadRight.resetClickCount();
            }
            if (dPadLeft.getClickCount() > 0 && !backward && !forward) {
                backward = true;
                dPadLeft.resetClickCount();
            } else if (dPadLeft.getClickCount() > 0 && backward) {
                backward = false;
                dPadLeft.resetClickCount();
            }


            if (forward) {
                throughputPosition = 1;
                dPadRight.resetClickCount();
            } else if (backward) {
                throughputPosition = 0;
                dPadLeft.resetClickCount();
            } else if (!forward && !backward) {
                throughputPosition = 0.5;
                dPadLeft.resetClickCount();
                dPadRight.resetClickCount();
            }
            throughput.setPosition(throughputPosition);
            telemetry.addLine("\nBackward: " + backward);
            telemetry.addLine("\nForward: " + forward);
            telemetry.addLine("\nThroughput position: " + throughputPosition);


            //intake
            gamepadX.checkButton(gamepad1.x);
            gamepadB.checkButton(gamepad1.b);
            littleBroB.checkButton(gamepad2.b);
            if (gamepadX.getClickCount() > 0 && intakePosition == 0.5) {
                intakePosition = 1;
                gamepadX.resetClickCount();
            } else if (gamepadX.getClickCount() > 0 && intakePosition != 0.5) {
                intakePosition = 0.5;
                gamepadX.resetClickCount();
            }
            if ((gamepadB.getClickCount() > 0 || littleBroB.getClickCount() > 0) && intakePosition == 0.5) {
                intakePosition = 0;
                gamepadB.resetClickCount();
                littleBroB.resetClickCount();
            } else if ((gamepadB.getClickCount() > 0 || littleBroB.getClickCount() > 0) && intakePosition != 0.5) {
                intakePosition = 0.5;
                gamepadB.resetClickCount();
                littleBroB.resetClickCount();
            }
            intakeLeft.setPosition(intakePosition);
            intakeRight.setPosition(intakePosition);

            telemetry.addLine("\nIntake position: " + intakePosition);


            if (gamepad1.dpad_up || gamepad2.dpad_up) {
                fourBarPower = 1;
            } else if (gamepad1.dpad_down || gamepad2.dpad_down) {
                fourBarPower = -1;
            } else {
                fourBarPower = 0;
            }
            fourBar.setPower(fourBarPower);

            telemetry.addLine("\nFour Bar Power: " + fourBarPower);
            telemetry.addLine("\nFour Bar Position: " + fourBar.getCurrentPosition());

            boolean on;
            if (gamepad1.right_trigger > 0 && viperPosition > -4450) {
                on = true;
                viperPower = Math.min(1, viperPower + 0.1);
            } else if (gamepad1.left_trigger > 0 && viperPosition > -4450) {
                on = true;
                viperPower = Math.max(-1, viperPower - 0.1);
            } else {
                viperPower = 0.5;
                on = false;
            }
            if(!on) {
                if(viperPosition < -4450) {
                    viperPosition = -4450;
                }
                viper.setTargetPosition(viperPosition);
                viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            } else {
                viper.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }
            viper.setPower(viperPower);
            viperPosition = viper.getCurrentPosition() + 1;

            telemetry.addLine("\nViper Position: " + viper.getCurrentPosition());
            telemetry.addLine("\nViper Power: " + viperPower);

            //Launch Servo Movements
            {
                launchPosition = 0;
                gamepadY.checkButton(gamepad1.y);
                littleBroY.checkButton(gamepad2.y);
                // transfer the state of the button to the buttonclick class
                if (gamepadY.getClickCount() > 0 || littleBroY.getClickCount() > 0) {
                    launchServoOpen = !launchServoOpen;
                    gamepadY.resetClickCount();
                    littleBroY.resetClickCount();
                }
                if (launchServoOpen) {
                    launchPosition = 90.0/270.0;
                } else {
                    launchPosition = 0.0;
                }

                telemetry.addLine("Servo Open: " + launchServoOpen);
                telemetry.addLine("Launch Servo Position: " + launchPosition);

                launch.setPosition(launchPosition);
            }
            BumperLeft.checkButton(gamepad1.left_bumper);
            BumperRight.checkButton(gamepad1.right_bumper);

            //Linear Servo Movements
            {
                if (BumperLeft.getClickCount() > 0) {
                    linearOpen = !linearOpen;
                    BumperLeft.resetClickCount();
                }
                if (linearOpen) {
                    outputLPosition = 1;
                } else {
                    outputLPosition = 0;
                }

                telemetry.addLine("Linear Open: " + linearOpen);
                telemetry.addLine("Output Linear Position: " + outputLPosition);

                outputL.setPosition(outputLPosition);
            }
            //Output Servo Movements
            {
                if (BumperRight.getClickCount() > 0) {
                    servoOpen = !servoOpen;
                    BumperRight.resetClickCount();
                }
                if (servoOpen) {
                    outputSPosition = 135.0/270.0;
                } else {
                    outputSPosition = 100.0/270.0;
                }

                telemetry.addLine("Servo Open: " + servoOpen);
                telemetry.addLine("Output Servo Position: " + outputSPosition);

                outputS.setPosition(outputSPosition);
            }
            hangingS.setPosition((-Math.abs(gamepad2.left_stick_y) + 1));
            if (gamepad1.a || gamepad2.a) {
                hangingM.setPower(1);
            } else if (gamepad2.x) {
                hangingM.setPower(-1);
            } else {
                hangingM.setPower(0);
            }
            //Hanging Hook Servo Movements
            {
                littleBroRB.checkButton(gamepad2.right_bumper);
                // transfer the state of the button to the buttonclick class
                if (littleBroRB.getClickCount() > 0) {
                    hookUp = !hookUp;
                    littleBroRB.resetClickCount();
                }
                if (hookUp) {
                    hookPosition = hookUpPosition;
                } else {
                    hookPosition = hookDownPosition;
                }
            }
            //hook.setPosition(hookPosition);
            telemetry.addLine("\nHanging Motor Power: "+hangingM.getPower());
            telemetry.addLine("\nHanging Servo Position: " + hangingS.getPosition());
            telemetry.addLine("\nHook Servo Position: " + hook.getPosition());
            telemetry.update();
        }
    }
}