package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.helperclasses.TestTensorFlowObjectDetection;
import org.firstinspires.ftc.teamcode.helperclasses.robotMove;

// TODO - stuff with pixels

@Autonomous(name="CameraAutonomusBlueClose", group="Autonomous")

public class CameraAutonomusBlueClose extends LinearOpMode{


    @Override
    public void runOpMode() {
        Servo intakeLeft =  hardwareMap.servo.get("Hub1_Servo0");
        Servo intakeRight =  hardwareMap.servo.get("Hub2_Servo0");
        DcMotor fourBar = hardwareMap.dcMotor.get("Hub1_Motor2");
        DcMotor viper =  hardwareMap.dcMotor.get("Hub1_Motor1");

        intakeLeft.setDirection((Servo.Direction.REVERSE));
        intakeRight.setDirection((Servo.Direction.FORWARD));

        Servo outputS =  hardwareMap.servo.get("Hub1_Servo5");
        Servo outputL =  hardwareMap.servo.get("Hub1_Servo4");

        robotMove robot = new robotMove(hardwareMap);
        TestTensorFlowObjectDetection Scanner = new TestTensorFlowObjectDetection();


        waitForStart();

        double x = Scanner.objectPositionX(0);
        double y = Scanner.objectPositionY(0);
        while(x==0){
            x = Scanner.objectPositionX(0);
            y = Scanner.objectPositionY(0);
        }
        robot.forward(0.5, 29.5);
        if(x>550){
            //right spike
            robot.right(0.5, 11.5);
            intakeLeft.setPosition(0);
            intakeRight.setPosition(0);
            sleep(1000);
            intakeLeft.setPosition(0.5);
            intakeRight.setPosition(0.5);
            robot.left(0.5, 11.5);

        }
        else if(x<150){
            //left spike
            robot.left(0.5, 11.5);
            intakeLeft.setPosition(0);
            intakeRight.setPosition(0);
            sleep(1000);
            intakeLeft.setPosition(0.5);
            intakeRight.setPosition(0.5);
            robot.right(0.5, 11.5);
        }
        else{
            //center spike
            intakeLeft.setPosition(0);
            intakeRight.setPosition(0);
            sleep(1000);
            intakeLeft.setPosition(0.5);
            intakeRight.setPosition(0.5);
        }
        robot.left(0.5, 40);

        //turn robot right 90 degrees
        robot.runMotorsForDistance(-0.5, 0.5, -0.5, 0.5, 0.5*Math.PI*6.25);

        fourBar.setPower(0.5);
        sleep(1000);
        fourBar.setPower(0);
        viper.setPower(0.5);
        sleep(250);
        viper.setPower(0);

        if(x>550){
            //right spike

            robot.left(0.5, 11.5);
            outputL.setPosition(0);
            outputS.setPosition(45);
            sleep(1000);
            robot.right(0.5, 11.5);
        }
        else if(x<150){
            //left spike
            robot.right(0.5, 11.5);
            outputL.setPosition(0);
            outputS.setPosition(45);
            sleep(1000);
            robot.left(0.5, 11.5);
        }
        else{
            outputL.setPosition(0);
            outputS.setPosition(45);
        }
        robot.right(0.5, 23);









        robot.forward(0.5, 60);
        robot.stop();

        // idk if the arm needs to be extended first or something

    }
}