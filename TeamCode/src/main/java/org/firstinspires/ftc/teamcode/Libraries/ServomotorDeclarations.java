package org.firstinspires.ftc.teamcode.Libraries;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class ServomotorDeclarations {
    public void servoMotorDeclaration(HardwareMap hardwareMap) {
        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor motorFrontLeft = hardwareMap.dcMotor.get("Hub1_Motor3");
        DcMotor motorBackLeft = hardwareMap.dcMotor.get("Hub1_Motor0");
        DcMotor motorFrontRight = hardwareMap.dcMotor.get("Hub2_Motor0");
        DcMotor motorBackRight = hardwareMap.dcMotor.get("Hub2_Motor3");

        DcMotor fourBar = hardwareMap.dcMotor.get("Hub1_Motor2");
        DcMotor viper = hardwareMap.dcMotor.get("Hub1_Motor1");
        DcMotor hangingM = hardwareMap.dcMotor.get("Hub2_Motor1");

        //continuous servos
        Servo throughput = hardwareMap.servo.get("Hub2_Servo4");
        Servo intakeLeft = hardwareMap.servo.get("Hub1_Servo0");
        Servo intakeRight = hardwareMap.servo.get("Hub2_Servo0");

        //Positional servos
        Servo launch = hardwareMap.servo.get("Hub1_Servo3");
        Servo outputS = hardwareMap.servo.get("Hub1_Servo5");
        Servo hangingS = hardwareMap.servo.get("Hub2_Servo1");
        Servo hook = hardwareMap.servo.get("Hub2_Servo2");

        //linear servo
        Servo outputL = hardwareMap.servo.get("Hub1_Servo4");
    }
}
