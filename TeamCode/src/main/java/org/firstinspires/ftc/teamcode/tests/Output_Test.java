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
       /*
        DcMotor motorFrontLeft = hardwareMap.dcMotor.get("H2M0");
        DcMotor motorBackLeft = hardwareMap.dcMotor.get("H2M1");
        DcMotor motorFrontRight = hardwareMap.dcMotor.get("H2M2");
        DcMotor motorBackRight = hardwareMap.dcMotor.get("H2M3");

        motorFrontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackRight.setDirection(DcMotorSimple.Direction.FORWARD);
        motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);*/
        /*
        Servo throughput =  hardwareMap.servo.get("H2S0");

        double throughputPosition = 0.5;
        boolean forward = false;
        boolean backward = false;
        boolean gamepadLeftPressedLastTime = false;
        boolean gamepadRightPressedLastTime = false;
        */
        /*
        Servo intakeLeft = hardwareMap.servo.get("H2S0");
        Servo intakeRight =  hardwareMap.servo.get("H2S1");

        intakeLeft.setDirection((Servo.Direction.REVERSE));
        intakeRight.setDirection((Servo.Direction.FORWARD));

        double intakePosition = 0.5;
        */
        /*
        DcMotor fourBar = hardwareMap.dcMotor.get("H2M2");

        double fourBarPower = 0;

         */
        /*
        DcMotor viper = hardwareMap.dcMotor.get("H2M1");

        double viperPower = 0;

         */
        /*
        Servo launch =  hardwareMap.servo.get("H2S3");

        double launchPosition = 0.5;

         */
        Servo outputS =  hardwareMap.servo.get("H2S0");
        Servo outputL =  hardwareMap.servo.get("H2S1");

        double outputSPosition = 0.5;
        double outputLPosition = 0;
        boolean outputDropIsTrigger = false;


        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()){
            /*
            double y = -gamepad1.left_stick_y; // Remember, this is reversed!
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            motorFrontLeft.setPower(frontLeftPower);
            motorBackLeft.setPower(backLeftPower);
            motorFrontRight.setPower(frontRightPower);
            motorBackRight.setPower(backRightPower);

            telemetry.addLine("Front left power: "+frontLeftPower);
            telemetry.addLine("Back left power: "+backLeftPower);
            telemetry.addLine("Front right power: "+frontRightPower); //why is this one negative at rest
            telemetry.addLine("Back right power: "+backRightPower);
             */
/*
            //need to make throughput and intake inputs more reliable
            //throughput
            if(gamepad1.dpad_right && !forward && !backward && !gamepadLeftPressedLastTime){
                forward = true;
            }else if(gamepad1.dpad_right && forward){
                forward = false;
            }
            if(gamepad1.dpad_left && !backward && !forward && !gamepadRightPressedLastTime){
                backward = true;
            }else if(gamepad1.dpad_left && backward){
                backward = false;
            }



            gamepadLeftPressedLastTime = gamepad1.dpad_left;
            gamepadRightPressedLastTime = gamepad1.dpad_right;

            if (forward)
                throughputPosition = 1;
            if (backward)
                throughputPosition = 0;
            if (!forward && !backward)
                throughputPosition = 0.5;
            throughput.setPosition(throughputPosition);

            telemetry.addLine("\nThroughput position: "+throughputPosition);
 */
/*
            //intake
            if (gamepad1.x && intakePosition == 0.5){
                intakePosition = 1;
            }else if(gamepad1.x && intakePosition != 0.5){
                intakePosition = 0.5;
            }
            if (gamepad1.b && intakePosition == 0.5){
                intakePosition = 0;
            }else if(gamepad1.b && intakePosition != 0.5){
                intakePosition = 0.5;
            }
            intakeLeft.setPosition(intakePosition);
            intakeRight.setPosition(intakePosition);



            telemetry.addLine("\nIntake position: "+intakePosition);

 */
            /*
            if (gamepad1.dpad_up || gamepad2.dpad_up)
                fourBarPower = Math.min(1, fourBarPower + 0.001);
            else if (gamepad1.dpad_down||gamepad2.dpad_down)
                fourBarPower = Math.max(-1, fourBarPower - 0.001);
            else{
                fourBarPower = 0;
            }
            fourBar.setPower(fourBarPower);

            telemetry.addLine("\nFour Bar Power: "+fourBarPower);

             */
            /*
            if (gamepad1.left_trigger > 0)
                viperPower = Math.min(1, viperPower + 0.001);
            else if (gamepad1.right_trigger > 0)
                viperPower = Math.max(-1, viperPower - 0.001);
            else{
                viperPower = 0;
            }
            viper.setPower(viperPower);

            telemetry.addLine("\nViper Power: "+viperPower);

             */
            /*
            if (gamepad1.y)
                launchPosition =1;
            else{
                launchPosition = 0.5;
            }
            launch.setPosition(launchPosition);

            telemetry.addLine("\nlaunch Position: "+launchPosition);

             */
            if(gamepad1.left_bumper){
                outputL.setPosition(1);
            }
            if(gamepad1.right_bumper&&!outputDropIsTrigger){
                outputDropIsTrigger = true;
                outputS.setPosition(1);
                Thread.sleep(50000);
                outputS.setPosition(0);
                Thread.sleep(500+00);
                outputS.setPosition(0.5);
                outputL.setPosition(0);
                outputDropIsTrigger=false;
            }

            telemetry.update();
        }
    }
}