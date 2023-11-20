package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.helperclasses.robotMove;

// TODO - stuff with pixels

@Autonomous(name="SimpleForward", group="Autonomous")

public class SimpleForward extends LinearOpMode{
    private robotMove robot = null;

    @Override
    public void runOpMode() {
        robot = new robotMove();

        robot.stop();
        waitForStart();

        robot.forward(0.5);

        long startTime = System.currentTimeMillis();
        long stopTime = startTime + 3000;
        long currentTime = System.currentTimeMillis();

        while(currentTime < stopTime)
            currentTime = System.currentTimeMillis();

        robot.stop();
    }
}