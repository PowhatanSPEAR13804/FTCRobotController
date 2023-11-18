package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="SimpleForward", group="Autonomous")

public class SimpleForward extends LinearOpMode{
    private DcMotor frontRightDrive;
    private DcMotor frontLeftDrive;
    private DcMotor backRightDrive;
    private DcMotor backLeftDrive;

    @Override
    public void runOpMode() {
        frontRightDrive  = hardwareMap.get(DcMotor.class, "H2:M0");
        frontLeftDrive  = hardwareMap.get(DcMotor.class, "H1:M3");
        backRightDrive  = hardwareMap.get(DcMotor.class, "H2:M3");
        backLeftDrive  = hardwareMap.get(DcMotor.class, "H1:M0");

        frontLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        frontRightDrive.setDirection(DcMotor.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        backRightDrive.setDirection(DcMotor.Direction.REVERSE);

        frontRightDrive.setPower(0.25);
        frontLeftDrive.setPower(0.25);
        backRightDrive.setPower(0.25);
        backLeftDrive.setPower(0.25);

        sleep(3000);

        frontRightDrive.setPower(0);
        frontLeftDrive.setPower(0);
        backRightDrive.setPower(0);
        backLeftDrive.setPower(0);
    }
}

