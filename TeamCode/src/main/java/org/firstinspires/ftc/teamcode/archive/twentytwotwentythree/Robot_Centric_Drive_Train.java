package org.firstinspires.ftc.teamcode.Archive.twentytwotwentythree;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
@Disabled

@TeleOp
public class Robot_Centric_Drive_Train extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor motorFrontLeft = hardwareMap.dcMotor.get("Hub2_motor0");
        DcMotor motorBackLeft = hardwareMap.dcMotor.get("Hub2_motor2");
        DcMotor motorFrontRight = hardwareMap.dcMotor.get("Hub1_motor0");
        DcMotor motorBackRight = hardwareMap.dcMotor.get("Hub1_motor2");

        DcMotor forearm = hardwareMap.get(DcMotor.class, "Hub2_motor1");
        DcMotor rotation = hardwareMap.get(DcMotor.class, "Hub1_motor1");
        Servo finger = hardwareMap.get(Servo.class, "Hub1_servo0");
        Servo wrist = hardwareMap.get(Servo.class, "Hub1_servo1");

        double openPosition = 0.6;
        double closedPosition = 0.39;


        // Reverse the right side motors
        // Reverse left motors if you are using NeveRests
        motorFrontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackRight.setDirection(DcMotorSimple.Direction.FORWARD);
        motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        forearm.setDirection(DcMotorSimple.Direction.FORWARD);

        double forearmPower = 0;

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
            forearm.setPower(forearmPower);

            //finger controls
            if (gamepad2.left_bumper) {
                finger.setPosition(closedPosition);
            } else if (gamepad2.right_bumper) {
                finger.setPosition(openPosition);
            }

            //arm controls
            //up
            if (gamepad2.right_trigger > 0.1)
            {
                forearmPower = -gamepad2.right_trigger * 1;
            }
            //down
            else if (gamepad2.left_trigger > 0.1)
            {
                forearmPower = gamepad2.left_trigger * 1;
            }
            //nothing pressed
            else
            {
                forearmPower = 0;
            }

            //wrist controls
            if (gamepad2.dpad_left) {
                wrist.setPosition(0.05);
            }

            if (gamepad2.dpad_right) {
                wrist.setPosition(0.85);
            }
        }
    }
}
