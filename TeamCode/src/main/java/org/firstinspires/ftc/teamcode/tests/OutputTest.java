package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.helperclasses.buttonClick;

@TeleOp(name="OutputTest", group="Tests")
public class OutputTest extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException{

        //motors
        DcMotor motorFR = hardwareMap.dcMotor.get("Hub2_Motor0");
        DcMotor motorFL = hardwareMap.dcMotor.get("Hub1_Motor3");
        DcMotor motorBR = hardwareMap.dcMotor.get("Hub2_Motor3");
        DcMotor motorBL = hardwareMap.dcMotor.get("Hub1_Motor0");
        DcMotor motorFourBar = hardwareMap.dcMotor.get("Hub2_Motor2");
        DcMotor motorV = hardwareMap.dcMotor.get("Hub1_Motor1");
        DcMotor motorH = hardwareMap.dcMotor.get("Hub2_Motor1");

        //continuous servos
        Servo servoIntakeLeft = hardwareMap.servo.get("Hub1_Servo0");
        Servo servoIntakeRight =  hardwareMap.servo.get("Hub2_Servo0");
        Servo servoThroughput =  hardwareMap.servo.get("Hub2_Servo4");

        //position servos
        Servo servoLaunch =  hardwareMap.servo.get("Hub2_Servo3");
        Servo servoOutput =  hardwareMap.servo.get("Hub2_Servo5");
        Servo servoHanging =  hardwareMap.servo.get("Hub2_Servo2");

        //linear servo
        Servo linearServoOutput = hardwareMap.servo.get("Hub1_Servo4");

        //maximum and minimum index of the motors and servos.
        final int minTest = 0;
        final int maxTest = 14;

        int test = 0; //index of each motor

        //create a button click object that will check the button state
        buttonClick b = new buttonClick();
        buttonClick a = new buttonClick();

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()){

            b.checkButton(gamepad1.b); //check the state of the B button

            if(b.getClickCount() > 0) /*get the amount of times the B button has been pressed*/{
                test--; //decrement the test variable
                b.resetClickCount(); //reset the B button's click count
            }

            a.checkButton(gamepad1.a); //check the state of the A button

            if(a.getClickCount() > 0) /*get the amount of times the A button has been pressed*/{
                test++; //increment the test variable
                a.resetClickCount(); // reset the A button's click count
            }

            if (test > maxTest) {
                test = minTest;
            }
            if (test < minTest) {
                test = maxTest;
            }

            switch (test) {
                case 0:
                    telemetry.addLine("testing front left motor.");
                    testMotor(motorFR);
                    break;
                case 1:
                    telemetry.addLine("testing back left motor.");
                    testMotor(motorFL);
                    break;
                case 2:
                    telemetry.addLine("testing front right motor.");
                    testMotor(motorBR);
                    break;
                case 3:
                    telemetry.addLine("testing back right motor.");
                    testMotor(motorBL);
                    break;
                case 4:
                    telemetry.addLine("testing four Bar motor.");
                    testMotor(motorFourBar);
                    break;
                case 5:
                    telemetry.addLine("testing viper motor.");
                    testMotor(motorV);
                    break;
                case 6:
                    telemetry.addLine("testing hanging motor.");
                    testMotor(motorH);
                    break;
                case 7:
                    telemetry.addLine("testing left intake servo.");
                    continuousTest(servoIntakeLeft);
                    break;
                case 8:
                    telemetry.addLine("testing right intake servo.");
                    continuousTest(servoIntakeRight);
                    break;
                case 9:
                    telemetry.addLine("testing throughput servo.");
                    continuousTest(servoThroughput);
                    break;
                case 10:
                    telemetry.addLine("testing the drone launch servo.");
                    positionalTest(servoLaunch);
                    break;
                case 11:
                    telemetry.addLine("testing the output servo");
                    positionalTest(servoOutput);
                    break;
                case 12:
                    telemetry.addLine("testing the hanging servo");
                    positionalTest(servoHanging);
                    break;
                case 13:
                    telemetry.addLine("testing the output linear servo.");
                    linearServoTest(linearServoOutput);
                    break;
                default:
                    telemetry.addLine("test unknown");
                    break;
            }
            telemetry.update();
        }
    }

    public void testMotor(DcMotor motor) {
        //set motor power to a value that is from -1 to 1 depending on the trigger positions
        motor.setPower(gamepad1.right_trigger - gamepad1.left_trigger);
    }

    public void continuousTest(Servo servo) {
        //get the combined values of the triggers (-1 to 1)
        double triggerValue = gamepad1.right_trigger - gamepad1.left_trigger;
        //turn that value into a double with the range 0 to 1 with 0.5 being nothing pressed
        double position = (triggerValue + 1)/2;
        //continuous servos will go backwards if position is below 0.5, forwards if position is above 0.5, and stop if position is 0.5
        servo.setPosition(position);
    }

    public void positionalTest(Servo servo) {
        double position = 90;
        if (gamepad1.left_trigger > 0.5) {
            position = 180;
        } else if (gamepad1.right_trigger > 0.5) {
            position = 0;
        }
        servo.setPosition(position);
    }

    public void linearServoTest(Servo servo) {
        servo.setPosition(gamepad1.left_trigger);
    }
}