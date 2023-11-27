package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.helperclasses.robotMove;

// TODO - stuff with pixels

@Autonomous(name="SimpleRight", group="Autonomous")

public class SimpleRight extends LinearOpMode{
    @Override
    public void runOpMode() {
        robotMove robot = new robotMove(hardwareMap);

        robot.stop();
        waitForStart();

        robot.right(0.5);

        long startTime = System.currentTimeMillis();
        long stopTime = startTime + 500;
        long currentTime = System.currentTimeMillis();

        while(currentTime < stopTime)
            currentTime = System.currentTimeMillis();

        robot.forward(0.5);

        startTime = System.currentTimeMillis();
        stopTime = startTime + 500;
        currentTime = System.currentTimeMillis();

        while(currentTime < stopTime)
            currentTime = System.currentTimeMillis();

        robot.stop();
    }
}