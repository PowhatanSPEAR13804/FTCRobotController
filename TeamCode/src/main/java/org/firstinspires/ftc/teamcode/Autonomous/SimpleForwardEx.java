package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Libraries.RobotMoveEx;

// TODO - stuff with pixels

@Autonomous(name="SimpleForwardEx", group="Autonomous")

public class SimpleForwardEx extends LinearOpMode{

    @Override
    public void runOpMode() {
        RobotMoveEx robot = new RobotMoveEx(hardwareMap);

        waitForStart();

        robot.moveXYA(0, 20, 0);
        robot.wait(1000);
        robot.stop();
    }
}