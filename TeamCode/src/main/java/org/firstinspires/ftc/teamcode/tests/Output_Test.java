package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.hardware.Servo;

//@Disabled
//safety :)

@TeleOp
public class Output_Test extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor motorFrontLeft = hardwareMap.dcMotor.get("Hub2_Motor0");
        DcMotor motorBackLeft = hardwareMap.dcMotor.get("Hub2_Motor1");
        DcMotor motorFrontRight = hardwareMap.dcMotor.get("Hub2_Motor2");
        DcMotor motorBackRight = hardwareMap.dcMotor.get("Hub2_Motor3");

        motorFrontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackRight.setDirection(DcMotorSimple.Direction.FORWARD);
        motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, this is reversed!
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            motorFrontLeft.setPower(frontLeftPower);
            motorBackLeft.setPower(backLeftPower);
            motorFrontRight.setPower(frontRightPower);
            motorBackRight.setPower(backRightPower);

            telemetry.addLine(""+frontLeftPower);
            telemetry.addLine(""+backLeftPower);
            telemetry.addLine(""+frontRightPower);
            telemetry.addLine(""+backRightPower);
        }
    }
}