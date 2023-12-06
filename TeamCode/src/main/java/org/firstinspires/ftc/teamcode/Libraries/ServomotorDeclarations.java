package org.firstinspires.ftc.teamcode.Libraries;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoMotorDeclarations {
    
    public DcMotor motorFrontLeft;
    public DcMotor motorBackLeft;
    public DcMotor motorFrontRight;
    public DcMotor motorBackRight;

    public DcMotor fourBar;
    public DcMotor viper;
    public DcMotor hangingM;

    public Servo throughput;
    public Servo intakeLeft;
    public Servo intakeRight;
    
    public Servo launch;
    public Servo outputS;
    public Servo hangingS;
    public Servo hook;
    
    public Servo outputL;
    
    public void servoMotorDeclaration(HardwareMap hardwareMap) {
        // Declare our motors
        // Make sure your ID's match your configuration
        motorFrontLeft = hardwareMap.dcMotor.get("Hub1_Motor3");
        motorBackLeft = hardwareMap.dcMotor.get("Hub1_Motor0");
        motorFrontRight = hardwareMap.dcMotor.get("Hub2_Motor0");
        motorBackRight = hardwareMap.dcMotor.get("Hub2_Motor3");

        fourBar = hardwareMap.dcMotor.get("Hub1_Motor2");
        viper = hardwareMap.dcMotor.get("Hub1_Motor1");
        hangingM = hardwareMap.dcMotor.get("Hub2_Motor1");

        //continuous servos
        throughput = hardwareMap.servo.get("Hub2_Servo4");
        intakeLeft = hardwareMap.servo.get("Hub1_Servo0");
        intakeRight = hardwareMap.servo.get("Hub2_Servo0");

        //Positional servos
        launch = hardwareMap.servo.get("Hub1_Servo3");
        outputS = hardwareMap.servo.get("Hub1_Servo5");
        hangingS = hardwareMap.servo.get("Hub2_Servo1");
        hook = hardwareMap.servo.get("Hub2_Servo2");

        //linear servo
        outputL = hardwareMap.servo.get("Hub1_Servo4");
    }
}
