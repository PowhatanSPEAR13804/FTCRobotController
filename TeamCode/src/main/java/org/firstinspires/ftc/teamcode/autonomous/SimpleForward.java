package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Libraries.robotMove;

// TODO - stuff with pixels

@Autonomous(name="SimpleForward", group="Autonomous")

public class SimpleForward extends LinearOpMode{

    @Override
    public void runOpMode() {
        Servo outputS =  hardwareMap.servo.get("Hub1_Servo5");
        Servo outputL =  hardwareMap.servo.get("Hub1_Servo4");
        Servo intakeLeft =  hardwareMap.servo.get("Hub1_Servo0");
        Servo intakeRight =  hardwareMap.servo.get("Hub2_Servo0");
        intakeLeft.setDirection((Servo.Direction.REVERSE));
        intakeRight.setDirection((Servo.Direction.FORWARD));

        robotMove robot = new robotMove(hardwareMap);

        waitForStart();

        outputL.setPosition(0);

        robot.forward(0.1, 80);
        robot.stop();

      /*  intakeLeft.setPosition(0);
        intakeRight.setPosition(0);
        sleep(1000);
        intakeLeft.setPosition(0.5);
        intakeRight.setPosition(0.5);
        
       */

        // idk if the arm needs to be extended first or something
        outputS.setPosition(45);
    }
}