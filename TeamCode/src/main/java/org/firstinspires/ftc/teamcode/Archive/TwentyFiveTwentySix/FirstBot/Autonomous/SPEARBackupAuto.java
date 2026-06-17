package org.firstinspires.ftc.teamcode.Archive.TwentyFiveTwentySix.FirstBot.Autonomous;
/*
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Archive.TwentyFiveTwentySix.FirstBot.Constants;
import org.firstinspires.ftc.teamcode.Archive.TwentyFiveTwentySix.FirstBot.subsystems.Intake;
import org.firstinspires.ftc.teamcode.Archive.TwentyFiveTwentySix.FirstBot.subsystems.Launcher;
import org.firstinspires.ftc.teamcode.Archive.TwentyFiveTwentySix.FirstBot.subsystems.Sorter;

@Disabled
@Autonomous(name = "BACKUP AUTO", group = "Examples")*/
public class SPEARBackupAuto /* extends OpMode */{
    /*
        private Follower follower;
        private Timer pathTimer, actionTimer, opmodeTimer;

        Intake intake;
        Launcher launcher;
        //Lifter lifter;
        Sorter sorter;

        private final Pose startPose = new Pose(0, 0, Math.toRadians(135)); // Start Pose of our robot.
        private final Pose scorePose = new Pose(0, 12, Math.toRadians(135)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.
        private final Pose pickup1Pose = new Pose(37, 121, Math.toRadians(0)); // Highest (First Set) of Artifacts from the Spike Mark.
        private final Pose pickup2Pose = new Pose(43, 130, Math.toRadians(0)); // Middle (Second Set) of Artifacts from the Spike Mark.
        private final Pose pickup3Pose = new Pose(49, 135, Math.toRadians(0)); // Lowest (Third Set) of Artifacts from the Spike Mark.

        private int pathState;

        private PathChain path;

        /** This method is called once at the init of the OpMode. **/
    /*@Override
    public void init() {
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        intake = new Intake(hardwareMap, "Hub1_Motor3", false);
        launcher = new Launcher(hardwareMap,
                "Hub1_Motor2", "Hub1_Motor0",
                false, false);
        //lifter = new Lifter(hardwareMap, "Hub1_Motor1", false);
        sorter = new Sorter(hardwareMap,
                "Hub2_Servo0", "Hub2_Servo1", "Hub2_Servo2",
                "Hub1_I2C0", "Hub2_I2C2", "Hub1_I2C2",
                "Hub1_I2C1", "Hub2_I2C3", "Hub1_I2C3");

        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);

        path = follower.pathBuilder()
                .addPath(new BezierLine(startPose, scorePose))
                .setLinearHeadingInterpolation(startPose.getHeading(), scorePose.getHeading())
                .build();
    }

    /** This is the main loop of the OpMode, it will run repeatedly after clicking "Play". **/
    /*@Override
    public void loop() {

        // These loop the movements of the robot, these must be called continuously in order to work
        follower.followPath(path);

        // Feedback to Driver Hub for debugging
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }

    /** This method is called continuously after Init while waiting for "play". **/
    /*@Override
    public void init_loop() {}

    /** This method is called once at the start of the OpMode.
     * It runs all the setup actions, including building paths and starting the path system **/
    /*@Override
    public void start() {
        opmodeTimer.resetTimer();
        launcher.toggle();
        follower.followPath(path);
    }

    /** We do not use this because everything should automatically disable **/
    /*@Override
    public void stop() {}

     */
}
