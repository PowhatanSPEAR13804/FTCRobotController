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

        waitForStart();
        int i = 0;
        while(opModeIsActive()) {
            if(i < 1) {
                robot.right(0.5, 24);
                robot.forward(1, 80);
                robot.stop();
            }
            i++;
        }
    }
}