package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity AutonomousRedRight = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(30, 30, Math.toRadians(180), Math.toRadians(180), 15.5)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(12, -63, Math.toRadians(270)))
                        /*.back(28)
                        .waitSeconds(2)
                        .splineToConstantHeading(new Vector2d(24, -48), Math.toRadians(270))
                        .strafeLeft(2)*/
                        .strafeLeft(25)
                        .back(3)
                        .splineToConstantHeading(new Vector2d(36, -12), Math.toRadians(270))
                        .strafeLeft(8)
                        .forward(46)
                        .back(46)
                        .strafeLeft(10)
                        .forward(46)
                        .back(46)
                        .strafeLeft(7)
                        .forward(46)
                        .build());

        RoadRunnerBotEntity AutonomousRedLeftBucket = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(30, 30, Math.toRadians(270), Math.toRadians(270), 15.5)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(-38, -63, Math.toRadians(180)))
                        .strafeRight(1)
                        .forward(20)
                        .waitSeconds(2)
                        .lineTo(new Vector2d(-40, -55))
                        //go to yellow block
                        .splineToLinearHeading(new Pose2d(-35, -46, Math.toRadians(135)), Math.toRadians(0))
                        //go to basket
                        .splineTo(new Vector2d(-60, -62), Math.toRadians(225))
                        .waitSeconds(1)
                        //move right away from basket
                        .lineToSplineHeading(new Pose2d(-39, -62, Math.toRadians(180)))
                        //go to block
                        .strafeRight(31.75)
                        .forward(2)
                        .splineTo(new Vector2d(-60, -62), Math.toRadians(225))
                        .lineToSplineHeading(new Pose2d(-48, -62, Math.toRadians(180)))
                        .strafeRight(38)
                        //pick up
                        .forward(2)
                        .waitSeconds(3)
                        .splineTo(new Vector2d(-60, -62), Math.toRadians(225))
                        //drop off
                        .build());

        RoadRunnerBotEntity AutonomousRedLeft = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(30, 30, Math.toRadians(270), Math.toRadians(270), 15.5)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(-21, -63, Math.toRadians(270)))
                        .lineTo(new Vector2d(-11.5, -33))
                        .waitSeconds(0.5)
                        .forward(5)
                        .splineTo(new Vector2d(-39, -39), Math.toRadians(135))
                        .waitSeconds(3)
                        .waitSeconds(1)
                        .splineTo(new Vector2d(-60, -62), Math.toRadians(225))
                        .waitSeconds(1)
                        .lineToSplineHeading(new Pose2d(-35, -62, Math.toRadians(180)))
                        .strafeRight(38)
                        .forward(5)
                        .waitSeconds(3)
                        .splineTo(new Vector2d(-60, -62), Math.toRadians(225))
                        .lineToSplineHeading(new Pose2d(-48, -62, Math.toRadians(180)))
                        .strafeRight(38)
                        .forward(2)
                        .waitSeconds(3)
                        .splineTo(new Vector2d(-60, -62), Math.toRadians(225))
                        .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(AutonomousRedRight)
                .addEntity(AutonomousRedLeftBucket)
                .start();
    }
}