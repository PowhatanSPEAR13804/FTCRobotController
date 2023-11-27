package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.helperclasses.robotMove;

// TODO - stuff with pixels

@Autonomous(name="SimpleLeft", group="Autonomous")

public class SimpleLeft extends LinearOpMode{
    @Override
    public void runOpMode() {
        robotMove robot = new robotMove(hardwareMap);

        waitForStart();

        robot.left(0.5);
        robot.wait(1000);
        robot.forward(0.5);
        robot.wait(3000);
        robot.stop();
    }
}