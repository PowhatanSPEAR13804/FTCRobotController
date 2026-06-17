package org.firstinspires.ftc.teamcode.Archive.TwentyFourTwentyFive.Auto;
/*
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Archive.TwentyFourTwentyFive.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Archive.TwentyFourTwentyFive.Subsystems.Pivot;
import org.firstinspires.ftc.teamcode.Archive.TwentyFourTwentyFive.Subsystems.ViperSlide;
import org.firstinspires.ftc.teamcode.commands.TrajectorySequenceFollowerCommand;
import org.firstinspires.ftc.teamcode.drive.MecanumOdometry;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous
public class RightAutoSpecimen extends CommandOpMode {
    public void initialize() {

        Drivetrain drive = new Drivetrain(new MecanumOdometry(hardwareMap), false);
        Intake intake = new Intake(hardwareMap, "Hub1_Servo0", "Hub1_Servo1");
        ViperSlide leftSlide = new ViperSlide(hardwareMap, telemetry, "Hub2_Motor2", true);
        ViperSlide rightSlide = new ViperSlide(hardwareMap, telemetry, "Hub1_Motor3", false);
        Pivot pivot = new Pivot(hardwareMap, "Hub1_Motor1", "Hub1_Servo2", telemetry, true);

        // We want to start the bot at x: 10, y: -8, heading: 90 degrees
        Pose2d startPose = new Pose2d(10, -63, Math.toRadians(270));

        intake.closeFinger();
        drive.setPoseEstimate(startPose);
        TrajectorySequence trajectorySequence = drive.trajectorySequenceBuilder(startPose)
                //to red bar
                .lineTo(new Vector2d(10, -32.5))
                //vipers up

                .addDisplacementMarker(10, () -> {
                    leftSlide.setPosition(1870);
                    rightSlide.setPosition(1870);
                })
                .waitSeconds(0.5)
                //down and release finger
                .addTemporalMarker(2.5, () -> {
                    leftSlide.setPosition(1050);
                    rightSlide.setPosition(1050);
                    pivot.rotateTo(0);
                })
                .addTemporalMarker(2.65, intake::openFinger)
                //all the way down
                .addTemporalMarker(4, () -> {
                    leftSlide.setPosition(100);
                    rightSlide.setPosition(100);
                })

                //move away from bar
                .forward(5)
                //go to yellow block
                .lineTo(new Vector2d(20, -35))
                .splineTo(new Vector2d(37, -12), Math.toRadians(-90))
                .lineTo(new Vector2d(47, -12))
                .lineTo(new Vector2d(47, -57))
                .lineTo(new Vector2d(47, -12))
                .back(10)
                .lineTo(new Vector2d(57, -57))
                .lineTo(new Vector2d(57, -12))
                .back(7)
                .lineTo(new Vector2d(61, -57))
                .build();

        schedule(new SequentialCommandGroup(
                new InstantCommand(() -> pivot.rotateTo(-146)),
                new TrajectorySequenceFollowerCommand(drive, trajectorySequence),
                new InstantCommand(() -> {
                    pivot.rotateTo(0);
                    leftSlide.setPosition(0);
                    rightSlide.setPosition(0);
                })
        ));
    }

    /*.addTemporalMarker(33, ()->{
                    leftSlide.setPosition(0);
                    rightSlide.setPosition(0);
                    pivot.rotateTo(0);
                })*//*
}*/
