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

        double throughputPosition = 0.5;
        boolean forward = false;
        boolean backward = false;
        boolean gamepadLeftPressedLastTime = false;
        boolean gamepadRightPressedLastTime = false;
        double intakePosition = 0.5;
        double fourBarPower = 0;
        double viperPower = 0;
        double launchPosition = 0.5;
        double outputSPosition = 0.5;
        double outputLPosition = 0;
        boolean outputDropIsTrigger = false;

        buttonClick dPadLeft = new buttonClick();
        buttonClick dPadRight = new buttonClick();
        buttonClick gamepadX = new buttonClick();
        buttonClick gamepadB = new buttonClick();

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, this is reversed!
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
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

            //need to make throughput and intake inputs more reliable


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
                fourBarPower = Math.min(1, fourBarPower + 0.001);
            } else if (gamepad1.dpad_down || gamepad2.dpad_down) {
                fourBarPower = Math.max(-1, fourBarPower - 0.001);
            } else {
              //  fourBarPower = 0;
            }
            fourBar.setPower(fourBarPower);

            telemetry.addLine("\nFour Bar Power: " + fourBarPower);


            if (gamepad1.left_trigger > 0) {
                viperPower = Math.min(1, viperPower + 0.001);
            } else if (gamepad1.right_trigger > 0) {
                viperPower = Math.max(-1, viperPower - 0.001);
            } else {
                viperPower = 0;
            }
            viper.setPower(viperPower);

            telemetry.addLine("\nViper Power: "+viperPower);


            if (gamepad1.y) {
                launchPosition = 1;
            } else {
                launchPosition = 0.5;
            }
            launch.setPosition(launchPosition);

            telemetry.addLine("\nlaunch Position: "+launchPosition);


            if (gamepad1.left_bumper) {
                outputL.setPosition(1);
            }
            if (gamepad1.right_bumper && !outputDropIsTrigger) {
                outputDropIsTrigger = true;
                outputS.setPosition(1);
                Thread.sleep(4000);
                outputS.setPosition(0);
                Thread.sleep(4000);
                outputS.setPosition(0.5);
                outputL.setPosition(0);
                outputDropIsTrigger=false;
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