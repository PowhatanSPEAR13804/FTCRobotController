package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Libraries.ButtonClick;
import org.firstinspires.ftc.teamcode.Libraries.ServoMotorDeclarations;

//controls all of our robot's subsystems

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp", group="TeleOp")

public class TeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ServoMotorDeclarations robot = new  ServoMotorDeclarations(hardwareMap);

        //reverse the right side motors; reverse left motors if you are using NeveRests
        robot.motorFrontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        robot.motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        robot.motorBackRight.setDirection(DcMotorSimple.Direction.FORWARD);
        robot.motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        //set servo directions
        robot.intakeLeft.setDirection((Servo.Direction.REVERSE));
        robot.intakeRight.setDirection((Servo.Direction.FORWARD));
        robot.hangingM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //throughput variables
        double throughputPosition = 0.5;
        boolean throughputForward = false;
        boolean throughputBackward = false;

        double intakePosition = 0.5;

        double fourBarPower = 0;

        //viper variables
        double viperPower = 0;
        int viperPosition = 0;

        //drone launch variables
        double launchPosition = 0;
        boolean launchServoOpen = false;

        //output variables
        double outputLinearPosition = 0;
        double outputServoPosition = 0.5;
        boolean linearOpen = false;
        boolean servoOpen = false;

        //hanging variables
        double hookDownPosition = robot.hook.getPosition();
        double hookUpPosition = hookDownPosition + 180.0/270.0;
        double hookPosition;
        boolean hookUp = false;

        //ButtonClick variables
        ButtonClick dPadLeft = new ButtonClick();
        ButtonClick dPadRight = new ButtonClick();
        ButtonClick gamepadB = new ButtonClick();
        ButtonClick gamepadX = new ButtonClick();
        ButtonClick gamepadY = new ButtonClick();
        ButtonClick BumperLeft = new ButtonClick();
        ButtonClick BumperRight = new ButtonClick();
        ButtonClick littleBroB = new ButtonClick();
        ButtonClick littleBroY = new ButtonClick();
        ButtonClick littleBroRB = new ButtonClick();



        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double x = -gamepad1.left_stick_x; //remember, this is reversed!
            double y = gamepad1.left_stick_y * 1.1; //counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            /*
            denominator is the largest motor power (absolute value) or 1

            this ensures all the powers maintain the same ratio, but only when at least one is out
            of the range [-1, 1]
             */
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            robot.motorFrontLeft.setPower(frontLeftPower);
            robot.motorBackLeft.setPower(backLeftPower);
            robot.motorFrontRight.setPower(frontRightPower);
            robot.motorBackRight.setPower(backRightPower);



            //throughput subsystem
            dPadRight.checkButton(gamepad1.dpad_right);
            dPadLeft.checkButton(gamepad1.dpad_left);

            if (dPadRight.getClickCount() > 0 && !throughputForward && !throughputBackward) {
                throughputForward = true;
                dPadRight.resetClickCount();
            } else if (dPadRight.getClickCount() > 0 && throughputForward) {
                throughputForward = false;
                dPadRight.resetClickCount();
            }

            if (dPadLeft.getClickCount() > 0 && !throughputBackward && !throughputForward) {
                throughputBackward = true;
                dPadLeft.resetClickCount();
            } else if (dPadLeft.getClickCount() > 0 && throughputBackward) {
                throughputBackward = false;
                dPadLeft.resetClickCount();
            }

            if (throughputForward) {
                throughputPosition = 1;
                dPadRight.resetClickCount();
            } else if (throughputBackward) {
                throughputPosition = 0;
                dPadLeft.resetClickCount();
            } else if (!throughputForward && !throughputBackward) {
                throughputPosition = 0.5;
                dPadLeft.resetClickCount();
                dPadRight.resetClickCount();
            }

            robot.throughput.setPosition(throughputPosition);
            telemetry.addLine("\nBackward: " + throughputBackward);
            telemetry.addLine("\nForward: " + throughputForward);
            telemetry.addLine("\nThroughput position: " + throughputPosition);



            //intake subsystem
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

            robot.intakeLeft.setPosition(intakePosition);
            robot.intakeRight.setPosition(intakePosition);
            telemetry.addLine("\nIntake position: " + intakePosition);



            //fourbar subsystem
            if (gamepad1.dpad_up || gamepad2.dpad_up) {
                fourBarPower = 1;
            } else if (gamepad1.dpad_down || gamepad2.dpad_down) {
                fourBarPower = -1;
            } else {
                fourBarPower = 0;
            }

            robot.fourBar.setPower(fourBarPower);
            telemetry.addLine("\nFour Bar Power: " + fourBarPower);
            telemetry.addLine("\nFour Bar Position: " + robot.fourBar.getCurrentPosition());



            //viper subsystem
            boolean viperOn;
            if (gamepad1.right_trigger > 0 && viperPosition > -4450) {
                viperOn = true;
                viperPower = Math.min(1, viperPower + 0.1);
            } else if (gamepad1.left_trigger > 0 && viperPosition > -4450) {
                viperOn = true;
                viperPower = Math.max(-1, viperPower - 0.1);
            } else {
                viperPower = 0.5;
                viperOn = false;
            }

            if(!viperOn) {
                if(viperPosition < -4450) {
                    viperPosition = -4450;
                }
                robot.viper.setTargetPosition(viperPosition);
                robot.viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            } else {
                robot.viper.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }

            robot.viper.setPower(viperPower);
            viperPosition = robot.viper.getCurrentPosition() + 1;
            telemetry.addLine("\nViper Position: " + robot.viper.getCurrentPosition());
            telemetry.addLine("\nViper Power: " + viperPower);



            //drone launch subsystem
            gamepadY.checkButton(gamepad1.y);
            littleBroY.checkButton(gamepad2.y);
            //transfer the state of the button to the ButtonClick class
            if (gamepadY.getClickCount() > 0 || littleBroY.getClickCount() > 0) {
                launchServoOpen = !launchServoOpen;
                gamepadY.resetClickCount();
                littleBroY.resetClickCount();
            }

            if (launchServoOpen) {
                launchPosition = 90.0/270.0;
            } else {
                launchPosition = 180.0/270.0;
            }

            robot.launch.setPosition(launchPosition);
            BumperLeft.checkButton(gamepad1.left_bumper);
            BumperRight.checkButton(gamepad1.right_bumper);
            telemetry.addLine("Servo Open: " + launchServoOpen);
            telemetry.addLine("Launch Servo Position: " + launchPosition);



            //output subsystem linear servo
            if (BumperLeft.getClickCount() > 0) {
                linearOpen = !linearOpen;
                BumperLeft.resetClickCount();
            }

            if (linearOpen) {
                outputLinearPosition = 1;
            } else {
                outputLinearPosition = 0;
            }

            robot.outputL.setPosition(outputLinearPosition);
            telemetry.addLine("Linear Open: " + linearOpen);
            telemetry.addLine("Output Linear Position: " + outputLinearPosition);



            //output subsystem positional servo
            if (BumperRight.getClickCount() > 0) {
                servoOpen = !servoOpen;
                BumperRight.resetClickCount();
            }

            if (servoOpen) {
                outputServoPosition = 135.0/270.0;
            } else {
                outputServoPosition = 100.0/270.0;
            }

            robot.outputS.setPosition(outputServoPosition);
            telemetry.addLine("Servo Open: " + servoOpen);
            telemetry.addLine("Output Servo Position: " + outputServoPosition);



            //hanging subsystem wench motor
            robot.hangingS.setPosition((-Math.abs(gamepad2.left_stick_y) + 1));
            if (gamepad1.a || gamepad2.a) {
                robot.hangingM.setPower(1);
            } else if (gamepad2.x) {
                robot.hangingM.setPower(-1);
            } else {
                robot.hangingM.setPower(0);
            }

            //hanging subsystem hook servo
            littleBroRB.checkButton(gamepad2.right_bumper);
            //transfer the state of the button to the ButtonClick class
            if (littleBroRB.getClickCount() > 0) {
                hookUp = !hookUp;
                littleBroRB.resetClickCount();
            }

            if (hookUp) {
                hookPosition = hookUpPosition;
            } else {
                hookPosition = hookDownPosition;
            }

            telemetry.addLine("\nHanging Motor Power: " + robot.hangingM.getPower());
            telemetry.addLine("\nHanging Servo Position: " + robot.hangingS.getPosition());
            telemetry.addLine("\nHook Servo Position: " + robot.hook.getPosition());

            telemetry.update();
        }
    }
}