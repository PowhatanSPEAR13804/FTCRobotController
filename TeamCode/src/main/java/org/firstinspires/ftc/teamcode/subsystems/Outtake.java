package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Outtake extends SubsystemBase{
    private Motor outtakeLeft;
    private Motor outtakeRight;

    public Outtake(HardwareMap hardwareMap, String leftMotorID, String rightMotorID, boolean leftReverse, boolean rightReverse) {
        outtakeLeft = new Motor(hardwareMap, leftMotorID);
        outtakeRight = new Motor(hardwareMap, rightMotorID);

        outtakeLeft.setInverted(leftReverse);
        outtakeRight.setInverted(rightReverse);

        outtakeLeft.setRunMode(Motor.RunMode.RawPower);
    }

    public void shoot(float power) {
        outtakeLeft.set(power);
    }
}
