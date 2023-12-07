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

        robot.move(20, 0, 0);
        robot.wait(1000);
        robot.move(-20, 0, 0);
        robot.wait(1000);
        robot.move(0, -20, 0);
        robot.wait(1000);
        robot.move(0, 20, 0);
        robot.wait(1000);
        robot.stop();
    }
}