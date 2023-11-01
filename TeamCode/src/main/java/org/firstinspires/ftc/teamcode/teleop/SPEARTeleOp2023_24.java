package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

//@Disabled

//safety :)

@TeleOp
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

        Servo throughput = /*(CRServo)*/ hardwareMap.servo.get("Hub1_Servo4");
        DcMotor viper = hardwareMap.dcMotor.get("Hub1_Motor1");

        Servo intakeLeft = hardwareMap.servo.get("Hub1_Servo0");
        Servo intakeRight = hardwareMap.servo.get("Hub1_Servo1");
        // Work in progress CRServo output = (CRServo) hardwareMap.servo.get("Hub1_Servo3");

        Servo droneLaunch = hardwareMap.servo.get("Hub2_Servo0");

        // Reverse the right side motors
        // Reverse left motors if you are using NeveRests
        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorFrontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotorSimple.Direction.FORWARD);

        double fourBarPower = 0;

        double throughputPower = 0.5;
        boolean forward = false;
        boolean backward = false;

        double viperPower = 0;

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

            //four bar
            if (gamepad1.dpad_up || gamepad2.dpad_up)
                fourBarPower = Math.min(1, fourBarPower + 0.001);
            if (gamepad1.dpad_down||gamepad2.dpad_down)
                fourBarPower = Math.max(1, fourBarPower - 0.001);
            fourBar.setPower(fourBarPower);

            //throughput
            if (gamepad2.dpad_right && !forward)
                forward = true;
            if (gamepad2.dpad_right && forward && !backward)
                forward = false;
            if (gamepad2.dpad_left && !backward)
                backward = true;
            if (gamepad2.dpad_left && backward && !forward)
                backward = false;

            if (forward)
                throughputPower = 1;
            if (backward)
                throughputPower = 0;
            if (!forward && !backward)
                throughputPower = 0.5;
            throughput.setPosition(throughputPower);

            //viper slide
            if (gamepad1.left_trigger > 0)
                viperPower = Math.min(1, viperPower + 0.001);
            if (gamepad1.right_trigger > 0)
                viperPower = Math.max(1, viperPower - 0.001);
            viper.setPower(viperPower);

            //telemetry.addData("I see" ,gamepad2.dpad_up);

            //intake
            if (gamepad1.x) {
                intakeLeft.setPosition(1);
                intakeRight.setPosition(1);
            }
            else {
                intakeLeft.setPosition(0.5);
                intakeRight.setPosition(0.5);
            }

            //droneLaunch
            if (gamepad1.a || gamepad2.a) {
                droneLaunch.setPosition(1);
                Thread.sleep(1000);
                droneLaunch.setPosition(-1);
            }
        }
    }
}
