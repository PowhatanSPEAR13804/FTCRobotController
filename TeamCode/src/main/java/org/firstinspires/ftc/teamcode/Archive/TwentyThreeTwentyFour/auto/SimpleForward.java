package org.firstinspires.ftc.teamcode.Archive.TwentyThreeTwentyFour.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Libraries.RobotMove;

//moves the robot forward
//TODO - stuff with pixels

@Autonomous(name="SimpleForward", group="Autonomous")
@Disabled
public class SimpleForward extends LinearOpMode{
    @Override
    public void runOpMode() {
        //store an instance of the RobotMove class in the robot object
        RobotMove robot = new RobotMove(hardwareMap);

        waitForStart();

        //move forward at full speed 80 inches, then stop
        robot.forward(1, 80);
        robot.stop();
    }
}