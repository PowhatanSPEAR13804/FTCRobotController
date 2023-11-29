package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
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

        double throughputPosition = 0.5;
        boolean forward = false;
        boolean backward = false;
        double intakePosition = 0.5;
        double fourBarPower = 0;
        double viperPower = 0;
        double launchPosition = 0.5;
        double outputSPosition = 0.5;
        double outputLPosition = 0;
        buttonClick dPadLeft = new buttonClick();
        buttonClick dPadRight = new buttonClick();
        buttonClick gamepadA = new buttonClick();
        buttonClick gamepadB = new buttonClick();
        buttonClick gamepadX = new buttonClick();
        buttonClick gamepadY = new buttonClick();
        buttonClick BumperLeft = new buttonClick();
        buttonClick BumperRight = new buttonClick();
        int position = 0;


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


            if (forward)
                throughputPosition = 1;
                dPadRight.resetClickCount();
            if (backward)
                throughputPosition = 0;
                dPadLeft.resetClickCount();
            if (!forward && !backward)
                throughputPosition = 0.5;
                dPadLeft.resetClickCount();
                dPadRight.resetClickCount();
            throughput.setPosition(throughputPosition);

            telemetry.addLine("\nThroughput position: " + throughputPosition);


            //intake
            gamepadX.checkButton(gamepad1.x);
            gamepadB.checkButton(gamepad1.b);
            if (gamepadX.getClickCount() > 0 && intakePosition == 0.5) {
                intakePosition = 1;
                gamepadX.resetClickCount();
            } else if (gamepadX.getClickCount() > 0 && intakePosition != 0.5) {
                intakePosition = 0.5;
                gamepadX.resetClickCount();
            }
            if (gamepadB.getClickCount() > 0 && intakePosition == 0.5) {
                intakePosition = 0;
                gamepadB.resetClickCount();
            } else if (gamepadB.getClickCount() > 0 && intakePosition != 0.5) {
                intakePosition = 0.5;
                gamepadB.resetClickCount();
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
            if (gamepad1.right_trigger > 0) {
                on = true;
                viperPower = Math.min(1, viperPower + 0.1);
            } else if (gamepad1.left_trigger > 0) {
                on = true;
                viperPower = Math.max(-1, viperPower - 0.1);
            } else {
                viperPower = 0.5;
                on = false;    
            }
            if(!on) {
                viper.setTargetPosition(position);
                viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            } else {
                viper.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }
            viper.setPower(viperPower);
            position = viper.getCurrentPosition();

            telemetry.addLine("\nViper Power: "+viperPower);

            //need to add button click helper class
            gamepadY.checkButton(gamepad1.y);
            if (gamepadY.getClickCount() > 0) {
                launchPosition = 1;
                gamepadY.resetClickCount();
            } else {
                launchPosition = 0.5;
            }
            launch.setPosition(launchPosition);

            telemetry.addLine("\nlaunch Position: "+launchPosition);


            BumperLeft.checkButton(gamepad1.left_bumper);
            BumperRight.checkButton(gamepad1.right_bumper);
            boolean linearOpen = false;
            boolean servoOpen = false;
            if (BumperLeft.getClickCount() > 0) {
                int outputPosition;
                if (linearOpen) {
                    outputPosition = 1;
                    linearOpen = false;
                    BumperLeft.resetClickCount();
                } else {
                    outputPosition = 0;
                    linearOpen = true;
                    BumperLeft.resetClickCount();
                }
                telemetry.addLine("Linear Open: " + linearOpen);
                telemetry.addLine("Output Position: " + outputPosition);

                outputL.setPosition(outputPosition);
            }
            if (BumperRight.getClickCount() > 0) {
                int outputPosition;
                if (servoOpen) {
                    outputPosition = 0;
                    servoOpen = false;
                    BumperRight.resetClickCount();
                } else {
                    outputPosition = 1;
                    servoOpen = true;
                    BumperLeft.resetClickCount();
                }
                telemetry.addLine("Servo Open: " + servoOpen);
                telemetry.addLine("Output Position: " + outputPosition);
                outputS.setPosition(outputPosition);
            }


            hangingS.setPosition(gamepad2.left_stick_x + 0.5);
            if (gamepad1.a) {
                hangingM.setPower(1);
            } else {
                hangingM.setPower(0);
            }
            telemetry.addLine("Hanging Servo Position: " + hangingS.getPosition());
            telemetry.update();
        }
    }
}