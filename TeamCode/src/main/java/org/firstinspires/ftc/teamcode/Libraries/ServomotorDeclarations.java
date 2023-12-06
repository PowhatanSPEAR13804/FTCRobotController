package org.firstinspires.ftc.teamcode.Libraries;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

//declare all of our motors and servos in one place

public class ServoMotorDeclarations {
    //drive train motors
    public DcMotor motorFrontLeft;
    public DcMotor motorBackLeft;
    public DcMotor motorFrontRight;
    public DcMotor motorBackRight;

    //subsystem motors
    public DcMotor fourBar;
    public DcMotor viper;
    public DcMotor hangingM;

    //continuous servos
    public Servo throughput;
    public Servo intakeLeft;
    public Servo intakeRight;

    //positional servos
    public Servo launch;
    public Servo outputS;
    public Servo hangingS;
    public Servo hook;

    //linear servo
    public Servo outputL;
    
    public ServoMotorDeclarations(HardwareMap hardwareMap) {
        motorFrontLeft = hardwareMap.dcMotor.get("Hub1_Motor3");
        motorBackLeft = hardwareMap.dcMotor.get("Hub1_Motor0");
        motorFrontRight = hardwareMap.dcMotor.get("Hub2_Motor0");
        motorBackRight = hardwareMap.dcMotor.get("Hub2_Motor3");

        fourBar = hardwareMap.dcMotor.get("Hub1_Motor2");
        viper = hardwareMap.dcMotor.get("Hub1_Motor1");
        hangingM = hardwareMap.dcMotor.get("Hub2_Motor1");

        throughput = hardwareMap.servo.get("Hub2_Servo4");
        intakeLeft = hardwareMap.servo.get("Hub1_Servo0");
        intakeRight = hardwareMap.servo.get("Hub2_Servo0");

        launch = hardwareMap.servo.get("Hub1_Servo3");
        outputS = hardwareMap.servo.get("Hub1_Servo5");
        hangingS = hardwareMap.servo.get("Hub2_Servo1");
        hook = hardwareMap.servo.get("Hub2_Servo2");

        outputL = hardwareMap.servo.get("Hub1_Servo4");

        motorFrontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackRight.setDirection(DcMotorSimple.Direction.FORWARD);
        motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeLeft.setDirection((Servo.Direction.REVERSE));
        intakeRight.setDirection((Servo.Direction.FORWARD));

        hangingM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}