package org.firstinspires.ftc.teamcode.Archive.TwentyFiveTwentySix.FirstBot.TeleOp;
/*
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Archive.TwentyFiveTwentySix.FirstBot.subsystems.Intake;
import org.firstinspires.ftc.teamcode.Archive.TwentyFiveTwentySix.FirstBot.subsystems.Launcher;
import org.firstinspires.ftc.teamcode.Archive.TwentyFiveTwentySix.FirstBot.subsystems.Sorter;
import org.firstinspires.ftc.teamcode.Archive.TwentyFiveTwentySix.FirstBot.Constants;
import org.firstinspires.ftc.teamcode.subsystems.*;
@Disabled

@Configurable
@TeleOp*/
public class SPEARTeleOp /*extends OpMode */{
    /*private Follower follower;
    public static Pose startingPose;

    Intake intake;
    Launcher launcher;
    //Lifter lifter;
    Sorter sorter;

    private TelemetryManager telemetryM;
    private boolean slowMode = false;
    private boolean launcherOn = false;
    private boolean launching = false;
    long start = System.nanoTime();
    long now = System.nanoTime();
    double launchStepTime = 4000;
    private double launcherPower = 0.5;


    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startingPose == null ? new Pose() : startingPose);
        follower.update();

        intake = new Intake(hardwareMap, "Hub1_Motor3", false);
        launcher = new Launcher(hardwareMap,
                "Hub1_Motor2", "Hub1_Motor0",
                false, false);
        //lifter = new Lifter(hardwareMap, "Hub1_Motor1", false);
        sorter = new Sorter(hardwareMap,
                "Hub2_Servo0", "Hub2_Servo1", "Hub2_Servo2",
                "Hub1_I2C0", "Hub2_I2C2", "Hub1_I2C2",
                "Hub1_I2C1", "Hub2_I2C3", "Hub1_I2C3");

        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
    }

    @Override
    public void start() {
        //The parameter controls whether the Follower should use break mode on the motors (using it is recommended).
        //In order to use float mode, add .useBrakeModeInTeleOp(true); to your Drivetrain Constants in Constant.java (for Mecanum)
        //If you don't pass anything in, it uses the default (false)
        follower.startTeleopDrive();
    }

    @Override
    public void loop() {
        //Call this once per loop
        follower.update();
        telemetryM.update();

        //Make the last parameter false for field-centric
        //In case the drivers want to use a "slowMode" you can scale the vectors
        //This is the normal version to use in the TeleOp
        double slowModeMultiplier = 0.5;
        if (!slowMode) follower.setTeleOpDrive(
                -gamepad1.left_stick_y,
                gamepad1.left_stick_x,
                -gamepad1.right_stick_x,
                true // Robot Centric
        );

        else follower.setTeleOpDrive(
                -gamepad1.left_stick_y * slowModeMultiplier,
                -gamepad1.left_stick_x * slowModeMultiplier,
                -gamepad1.right_stick_x * slowModeMultiplier,
                true // Robot Centric
        );

        //Slow Mode
        //if (gamepad1.rightBumperWasPressed()) {
        //    slowMode = !slowMode;
        //}

        //Launching
        /*
        if(gamepad1.aWasPressed() || gamepad2.aWasPressed()) {
            sorter.colorSort("purple");
            launcher.shoot(1);
        }
        if(gamepad1.bWasPressed() || gamepad2.bWasPressed()) {
            sorter.colorSort("green");
            launcher.shoot(1);
        }
        if(gamepad1.xWasPressed() || gamepad2.xWasPressed()) {
            //lift random guy
        }
*//*
        if (gamepad2.x) {
            sorter.setPositions(0.6, 0, 0.9);
        }
        if (gamepad2.b) {
            sorter.setPositions(0.6, 0.1, 1);
        }
        if (gamepad2.a) {
            sorter.setPositions(0, 0.1, 0.9);
        }
        if (gamepad2.y) {
            sorter.setPositions(1, 1, 0);
        }
        if (gamepad2.dpadLeftWasPressed()) {
            sorter.setPositions(1.0/3.0, 0.1, 0.9);
        }

        //robot lift
        /*
        lifter.lift(gamepad1.right_trigger - gamepad1.left_trigger);
        if(gamepad1.leftBumperWasPressed()) {
            lifter.liftToPosition(0); //TODO find min position
        }
        if(gamepad1.rightBumperWasPressed()) {
            lifter.liftToPosition(9999); //TODO find max position
        }
*/
        //intake
    /*
        if(gamepad1.dpadUpWasPressed() || gamepad2.left_trigger >= 0.5) {
            intake.toggle("out");
        }
        if(gamepad1.dpadDownWasPressed() || gamepad2.right_trigger >= 0.5) {
            intake.toggle("in");
        }
        
        if(gamepad2.dpadUpWasPressed()) {
            launcher.toggle();
        }

        telemetry.addLine("duration: " +  (double)(System.nanoTime() - start)/ 1_000_000.0);
        telemetry.addLine("positions: " + sorter.getPosition());
        telemetry.addLine("values: " + sorter.getValues());
        telemetryM.debug("position", follower.getPose());
        telemetryM.debug("velocity", follower.getVelocity());
    }*/
}