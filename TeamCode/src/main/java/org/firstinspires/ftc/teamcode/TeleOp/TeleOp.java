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

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

        }
    }
}
