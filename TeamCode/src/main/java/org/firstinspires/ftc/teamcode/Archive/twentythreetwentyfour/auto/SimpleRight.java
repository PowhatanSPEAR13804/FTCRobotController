package org.firstinspires.ftc.teamcode.Archive.twentythreetwentyfour.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Libraries.RobotMove;

//moves the robot right and then forward
//TODO - stuff with pixels

@Autonomous(name="SimpleRight", group="Autonomous")

public class SimpleRight extends LinearOpMode{
    @Override
    public void runOpMode() {
        RobotMove robot = new RobotMove(hardwareMap);

        waitForStart();

        robot.right(0.5, 24);
        robot.forward(1, 80);
        robot.stop();
    }
}