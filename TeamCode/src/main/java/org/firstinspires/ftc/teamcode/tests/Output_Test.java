package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

//@Disabled
//safety :)

@TeleOp
public class Output_Test extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException{

        DcMotor motorFL = hardwareMap.dcMotor.get("H2M0");
        DcMotor motorBL = hardwareMap.dcMotor.get("H2M1");
        DcMotor motorFR = hardwareMap.dcMotor.get("H2M2");
        DcMotor motorBR = hardwareMap.dcMotor.get("H2M3");
        DcMotor motorFB = hardwareMap.dcMotor.get("H2M2");
        DcMotor motorV = hardwareMap.dcMotor.get("H2M1");
        DcMotor motorH = hardwareMap.dcMotor.get("H2M0");

        //motorFL.setDirection(DcMotorSimple.Direction.FORWARD);
        //motorBL.setDirection(DcMotorSimple.Direction.REVERSE);
        //motorFR.setDirection(DcMotorSimple.Direction.FORWARD);
        //motorBR.setDirection(DcMotorSimple.Direction.REVERSE);

        //continuous servo
        Servo throughput =  hardwareMap.servo.get("H2S0");
        Servo intakeLeft = hardwareMap.servo.get("H2S0");
        Servo intakeRight =  hardwareMap.servo.get("H2S1");

        Servo launch =  hardwareMap.servo.get("H2S3");
        Servo outputS =  hardwareMap.servo.get("H2S0");
        Servo outputL =  hardwareMap.servo.get("H2S1");

        //position servo
        Servo hangingS =  hardwareMap.servo.get("H2S0");


        final int minTest = 0;
        final int maxTest = 11;
        int test = 0;

        buttonClick b = new buttonClick();
        buttonClick a = new buttonClick();

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()){

            b.sample(gamepad1.b);

            if(b.getClickCount() > 0) {
                test--;
                b.resetClickCount();
            }

            a.sample(gamepad1.a);

            if(a.getClickCount() > 0) {
                test++;
                a.resetClickCount();
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
                    testMotor(motorFL);
                    break;
                case 1:
                    telemetry.addLine("testing back left motor.");
                    testMotor(motorBL);
                    break;
                case 2:
                    telemetry.addLine("testing front right motor.");
                    testMotor(motorFR);
                    break;
                case 3:
                    telemetry.addLine("testing back right motor.");
                    testMotor(motorBR);
                    break;
                case 4:
                    telemetry.addLine("testing four Bar motor.");
                    testMotor(motorFB);
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
                    telemetry.addLine("test: " + test);
                    break;
                case 8:
                    telemetry.addLine("test: " + test);
                    break;
                case 9:
                    telemetry.addLine("test: " + test);
                    break;
                case 10:
                    telemetry.addLine("test: " + test);
                    break;
                case 11:
                    telemetry.addLine("test: " + test);
                    break;
                default:
                    telemetry.addLine("test unknown");
                    break;
            }
            telemetry.update();
        }
    }

    public void testMotor(DcMotor motor) {
        motor.setPower(gamepad1.right_trigger - gamepad1.left_trigger);
    }
}