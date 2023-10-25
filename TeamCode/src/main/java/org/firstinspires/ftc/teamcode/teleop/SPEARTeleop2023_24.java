package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

//@Disabled

@TeleOp
public class SPEARTeleop2023_24 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor motorFrontLeft = hardwareMap.dcMotor.get("Hub2_Motor3");
        DcMotor motorBackLeft = hardwareMap.dcMotor.get("Hub2_Motor0");
        DcMotor motorFrontRight = hardwareMap.dcMotor.get("Hub1_Motor0");
        DcMotor motorBackRight = hardwareMap.dcMotor.get("Hub1_Motor3");

        DcMotor fourBar = hardwareMap.dcMotor.get("Hub1_Motor2");

        CRServo throughput = (CRServo) hardwareMap.servo.get("Hub2_Servo0");

        // Reverse the right side motors
        // Reverse left motors if you are using NeveRests
        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorFrontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotorSimple.Direction.FORWARD);

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

            double power = 0;
            if (gamepad1.dpad_up || gamepad2.dpad_up)
                fourBar.setPower(Math.min(1,(power += 0.001)));
            if(gamepad1.dpad_down||gamepad2.dpad_down)
                fourBar.setPower(Math.max(0, (power -=0.001)));

            boolean forward = false;
            //throughput forward (into the robot)
            if (gamepad2.dpad_left && !forward) {
                forward = true;
                throughput.setPower(0.2);
            }
            if (gamepad2.dpad_left && forward) {
                forward = false;
                throughput.setPower(0);
            }

            boolean backward = false;
            //throughput backward (out of the robot)
            if (gamepad2.dpad_right && !backward) {
                backward = true;
                throughput.setPower(-0.2);
            }
            if (gamepad2.dpad_right && backward) {
                backward = false;
                throughput.setPower(0);


            }

            telemetry.addData("I see" ,gamepad2.dpad_up);


        }
    }
}
