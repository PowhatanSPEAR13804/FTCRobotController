package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Libraries.RobotMoveEx;

// TODO - stuff with pixels

@Autonomous(name="SimpleForward", group="Autonomous")

public class SimpleForward extends LinearOpMode{

    @Override
    public void runOpMode() {
        RobotMoveEx robot = new RobotMoveEx(hardwareMap);

        waitForStart();

        robot.forward(0.1, 80);
        robot.stop();
    }
}