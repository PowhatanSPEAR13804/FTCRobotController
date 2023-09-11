package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class teleop extends LinearOpMode {
    public void runOpMode() throws InterruptedException {
        //declare and set motor variables
        DcMotor frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;
        frontLeftMotor = hardwareMap.get(DcMotor.class,"Hub1_Motor0");
        frontRightMotor = hardwareMap.dcMotor.get("Hub1_Motor1");
        backLeftMotor = hardwareMap.dcMotor.get("Hub1_Motor2");
        backRightMotor = hardwareMap.dcMotor.get("Hub1_Motor3");

        //declare gamepad axes variables
        double x, y, rx;

    }
}
