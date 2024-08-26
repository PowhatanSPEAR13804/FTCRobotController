package org.firstinspires.ftc.teamcode.Archive.twentythreetwentyfour.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Helperclasses.robotMove;

// TODO - stuff with pixels

@Autonomous(name="SimpleLeft", group="Autonomous")

public class SimpleLeft extends LinearOpMode{
    @Override
    public void runOpMode() {
        robotMove robot = new robotMove(hardwareMap);

        waitForStart();

        robot.left(0.5, 24);
        robot.forward(1, 80);
        robot.stop();
    }
}