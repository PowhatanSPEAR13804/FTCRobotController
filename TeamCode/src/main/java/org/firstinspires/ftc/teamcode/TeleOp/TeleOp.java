package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


//@Disabled
//safety :)

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp", group="TeleOp")
public class TeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor FLMotor;
        DcMotor FRMotor;
        DcMotor BLMotor;
        DcMotor BRMotor;

        FLMotor = hardwareMap.dcMotor.get("Hub1_Motor2");
        FRMotor = hardwareMap.dcMotor.get("Hub2_Motor3");
        BLMotor = hardwareMap.dcMotor.get("Hub1_Motor0");
        BRMotor = hardwareMap.dcMotor.get("Hub2_Motor1");


        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            double x = gamepad1.left_stick_x;
            double y = gamepad1.left_stick_y;
            double rx = gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(x + y + rx), 1.0);

            double FLSpeed = (y + x + rx)/denominator;
            double FRSpeed = (y - x - rx)/denominator;
            double BLSpeed = (y - x + rx)/denominator;
            double BRSpeed = (y + x - rx)/denominator;


            FLMotor.setPower(FLSpeed);
            FRMotor.setPower(FRSpeed);
            BLMotor.setPower(BLSpeed);
            BRMotor.setPower(BRSpeed);

        }
    }
}
