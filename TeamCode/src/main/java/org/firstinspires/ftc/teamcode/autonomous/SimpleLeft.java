package org.firstinspires.ftc.teamcode.Autonomous;

import org.firstinspires.ftc.teamcode.Libraries.RobotMove;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

//TODO - stuff with pixels

@Autonomous(name="SimpleLeft", group="Autonomous")

public class SimpleLeft extends LinearOpMode{
    @Override
    public void runOpMode() {
        RobotMove robot = new RobotMove(hardwareMap);

        waitForStart();

        robot.left(0.5, 24);
        robot.forward(1, 80);
        robot.stop();
    }
}