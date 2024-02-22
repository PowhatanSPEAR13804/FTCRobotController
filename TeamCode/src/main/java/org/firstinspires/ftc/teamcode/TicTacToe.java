package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.helperclasses.buttonClick;

//@Disabled
//safety :)

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TicTacToe", group="TeleOp")
public class TicTacToe extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor xMotor = hardwareMap.dcMotor.get("M0");
        DcMotor yMotor = hardwareMap.dcMotor.get("M3");


        //linear servo
        Servo pickupLinearServo =  hardwareMap.servo.get("S4");
        Servo RotationServo =  hardwareMap.servo.get("S5");

        // Reverse the right side motors
        // Reverse left motors if you are using NeveRests

        xMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        yMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        xMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        yMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        xMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        yMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        RotationServo.setPosition(0);





        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {



            if(gamepad1.dpad_left&&yMotor.getCurrentPosition()>=1000){
                yMotor.setPower(-1);
            } else if (gamepad1.dpad_right&&yMotor.getCurrentPosition()<=20000) {
                yMotor.setPower(1);
            }
            else{
                yMotor.setPower(0);
            }
            if(gamepad1.dpad_up&&xMotor.getCurrentPosition()<=40000){
                xMotor.setPower(1);
            } else if (gamepad1.dpad_down&&xMotor.getCurrentPosition()>=1000) {
                xMotor.setPower(-1);
            }
            else{
                xMotor.setPower(0);
            }

            if(gamepad1.x){
                pickupLinearServo.setPosition(1);
            } else if (gamepad1.a) {
                pickupLinearServo.setPosition(0);
            }

            if(gamepad1.y){
                RotationServo.setPosition((85.0/270.0));
            } else if (gamepad1.b) {
                RotationServo.setPosition(0);
            }


            telemetry.addLine("xMotor power: " + xMotor.getPower());
            telemetry.addLine("yMotor power: " + yMotor.getPower());
            telemetry.addLine("xMotor pos: " + xMotor.getCurrentPosition());
            telemetry.addLine("yMotor pos: " + yMotor.getCurrentPosition());

            telemetry.addLine("pickupServo: " + pickupLinearServo.getPosition());
            telemetry.addLine("RotServo: " + RotationServo.getPosition());
            telemetry.addLine("pickupServo: " + pickupLinearServo.getPosition());
            telemetry.addLine("RotServo: " + RotationServo.getPosition());

        telemetry.update();
        }
    }
}