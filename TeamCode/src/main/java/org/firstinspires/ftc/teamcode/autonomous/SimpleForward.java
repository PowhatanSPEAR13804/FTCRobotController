package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.helperclasses.robotMove;

// TODO - stuff with pixels

@Autonomous(name="SimpleForward", group="Autonomous")

public class SimpleForward extends LinearOpMode{

    @Override
    public void runOpMode() {
        Servo outputS =  hardwareMap.servo.get("Hub1_Servo5");
        Servo outputL =  hardwareMap.servo.get("Hub1_Servo4");

        robotMove robot = new robotMove(hardwareMap);

        waitForStart();

        outputL.setPosition(0);

        robot.forward(0.5, 60);
        robot.stop();

        // idk if the arm needs to be extended first or something
        outputS.setPosition(45);
    }
}