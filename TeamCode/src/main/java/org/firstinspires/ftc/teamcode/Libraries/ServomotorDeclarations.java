package org.firstinspires.ftc.teamcode.Libraries;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

//object containing all motor and servo declarations

public class ServoMotorDeclarations {
    //drive train motors
    public DcMotor motorFL;
    public DcMotor motorBL;
    public DcMotor motorFR;
    public DcMotor motorBR;

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
        //Movement motors
        motorFL = hardwareMap.dcMotor.get("Hub1_Motor3");
        motorBL = hardwareMap.dcMotor.get("Hub1_Motor0");
        motorFR = hardwareMap.dcMotor.get("Hub2_Motor0");
        motorBR = hardwareMap.dcMotor.get("Hub2_Motor3");

        //Intake/Outtake motors
        fourBar = hardwareMap.dcMotor.get("Hub1_Motor2");
        viper = hardwareMap.dcMotor.get("Hub1_Motor1");
        hangingM = hardwareMap.dcMotor.get("Hub2_Motor1");

        //Intake and pixel positioning servos
        throughput = hardwareMap.servo.get("Hub2_Servo4");
        intakeLeft = hardwareMap.servo.get("Hub1_Servo0");
        intakeRight = hardwareMap.servo.get("Hub2_Servo0");

        //Extra objective subsystem servos
        launch = hardwareMap.servo.get("Hub1_Servo3");
        hangingS = hardwareMap.servo.get("Hub2_Servo1");
        hook = hardwareMap.servo.get("Hub2_Servo2");

        //Output servos
        outputL = hardwareMap.servo.get("Hub1_Servo4");
        outputS = hardwareMap.servo.get("Hub1_Servo5");
    }
}