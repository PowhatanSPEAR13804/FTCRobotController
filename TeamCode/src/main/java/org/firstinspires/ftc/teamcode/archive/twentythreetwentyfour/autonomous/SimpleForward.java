package org.firstinspires.ftc.teamcode.Archive.twentythreetwentyfour.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.teamcode.helperclasses.robotMove;

// TODO - stuff with pixels

@Autonomous(name="SimpleForward", group="Autonomous")

public class SimpleForward extends LinearOpMode{

    @Override
    public void runOpMode() {
        NormalizedColorSensor color = hardwareMap.get(NormalizedColorSensor.class, "Hub2_I2C_3");;
        robotMove robot = new robotMove(hardwareMap);

        waitForStart();

        robot.setMotors(-0.5, -0.5, -0.5, -0.5);

        NormalizedRGBA colors;
-
        boolean isRed = false;

        while(!isRed) {
            colors = color.getNormalizedColors();
            isRed = colors.red > 0.5;
            telemetry.addLine("red: " + colors.red);
        }

        stop();

      /*  intakeLeft.setPosition(0);
        intakeRight.setPosition(0);
        sleep(1000);
        intakeLeft.setPosition(0.5);
        intakeRight.setPosition(0.5);
        
       */

        // idk if the arm needs to be extended first or something
    }
}