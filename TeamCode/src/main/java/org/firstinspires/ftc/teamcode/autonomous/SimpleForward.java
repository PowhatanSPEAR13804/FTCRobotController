package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.helperclasses.robotMove;

// TODO - stuff with pixels

@Autonomous(name="SimpleForward", group="Autonomous")

public class SimpleForward extends LinearOpMode{

    @Override
    public void runOpMode() {
        robotMove robot = new robotMove(hardwareMap);

        waitForStart();

        robot.forward(0.5);
        robot.wait(3000);
        robot.stop();
    }
}