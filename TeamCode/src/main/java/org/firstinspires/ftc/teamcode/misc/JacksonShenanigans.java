package org.firstinspires.ftc.teamcode.misc;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@Disabled
@TeleOp(name = "JacksonShenanigans", group = "Misc")


public class JacksonShenanigans extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException{
        DcMotor Motor1 = hardwareMap.dcMotor.get("Hub1_Motor0");
    }
}

