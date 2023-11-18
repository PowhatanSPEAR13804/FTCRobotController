package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

//@Disabled
//safety :)

@TeleOp(name="TeleOp2023_24", group="TeleOp")
public class SPEARTeleOp2023_24 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor motorFrontLeft = hardwareMap.dcMotor.get("Hub1_Motor3");
        DcMotor motorBackLeft = hardwareMap.dcMotor.get("Hub1_Motor0");
        DcMotor motorFrontRight = hardwareMap.dcMotor.get("Hub2_Motor0");
        DcMotor motorBackRight = hardwareMap.dcMotor.get("Hub2_Motor3");

        DcMotor fourBar = hardwareMap.dcMotor.get("Hub1_Motor2");

        Servo throughput =  hardwareMap.servo.get("Hub2_Servo4");

        DcMotor viper =  hardwareMap.dcMotor.get("Hub1_Motor1");

        Servo intakeLeft =  hardwareMap.servo.get("Hub1_Servo0");
        Servo intakeRight =  hardwareMap.servo.get("Hub2_Servo0");

        Servo launch =  hardwareMap.servo.get("Hub2_Servo3");

        Servo outputS =  hardwareMap.servo.get("Hub1_Servo5");
        Servo outputL =  hardwareMap.servo.get("Hub1_Servo4");

        Servo hangingS =  hardwareMap.servo.get("Hub1_Servo2");
        DcMotor hangingM = hardwareMap.dcMotor.get("Hub1_Motor2");

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
            if (gamepad1.dpad_right && !forward && !backward && !gamepadLeftPressedLastTime) {
                forward = true;
            } else if (gamepad1.dpad_right && forward) {
                forward = false;
            }
            if (gamepad1.dpad_left && !backward && !forward && !gamepadRightPressedLastTime) {
                backward = true;
            } else if (gamepad1.dpad_left && backward) {
                backward = false;
            }

            gamepadLeftPressedLastTime = gamepad1.dpad_left;
            gamepadRightPressedLastTime = gamepad1.dpad_right;


            if (forward)
                throughputPosition = 1;
            if (backward)
                throughputPosition = 0;
            if (!forward && !backward)
                throughputPosition = 0.5;
            throughput.setPosition(throughputPosition);

            telemetry.addLine("\nThroughput position: " + throughputPosition);


            //intake
            if (gamepad1.x && intakePosition == 0.5) {
                intakePosition = 1;
            } else if (gamepad1.x && intakePosition != 0.5) {
                intakePosition = 0.5;
            }
            if (gamepad1.b && intakePosition == 0.5) {
                intakePosition = 0;
            } else if (gamepad1.b && intakePosition != 0.5) {
                intakePosition = 0.5;
            }
            intakeLeft.setPosition(intakePosition);
            intakeRight.setPosition(intakePosition);

            telemetry.addLine("\nIntake position: " + intakePosition);


            if (gamepad1.dpad_up || gamepad2.dpad_up) {
                fourBarPower = Math.min(1, fourBarPower + 0.001);
            } else if (gamepad1.dpad_down || gamepad2.dpad_down) {
                fourBarPower = Math.max(-1, fourBarPower - 0.001);
            } else {
                fourBarPower = 0;
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
                Thread.sleep(5000);
                outputS.setPosition(0);
                Thread.sleep(5000);
                outputS.setPosition(0.5);
                outputL.setPosition(0);
                outputDropIsTrigger=false;
            }


            hangingS.setPosition(gamepad1.left_stick_y+0.5);


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