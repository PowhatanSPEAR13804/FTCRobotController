package org.firstinspires.ftc.teamcode.Archive.TwentyFourTwentyFive.Auto;
/*
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Archive.TwentyFourTwentyFive.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Archive.TwentyFourTwentyFive.Subsystems.Pivot;
import org.firstinspires.ftc.teamcode.Archive.TwentyFourTwentyFive.Subsystems.ViperSlide;
import org.firstinspires.ftc.teamcode.commands.TrajectorySequenceFollowerCommand;
import org.firstinspires.ftc.teamcode.drive.MecanumOdometry;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Disabled
@Autonomous
public class AutonomousLeftSimple extends CommandOpMode {
    public void initialize() {
        Drivetrain drive = new Drivetrain(new MecanumOdometry(hardwareMap), false);
        Intake intake = new Intake(hardwareMap, "Hub1_Servo0", "Hub1_Servo1");
        ViperSlide leftSlide = new ViperSlide(hardwareMap, telemetry, "Hub2_Motor2", true);
        ViperSlide rightSlide = new ViperSlide(hardwareMap, telemetry, "Hub1_Motor3", false);
        Pivot pivot = new Pivot(hardwareMap, "Hub1_Motor1", "Hub1_Servo2", telemetry, true);

        // We want to start the bot at x: 10, y: -8, heading: 90 degrees
        Pose2d startPose = new Pose2d(-9, -63, Math.toRadians(270));

        intake.closeFinger();
        drive.setPoseEstimate(startPose);

        TrajectorySequence trajectorySequence = drive.trajectorySequenceBuilder(startPose)
                .forward(38)
                .strafeLeft(2)
                .addDisplacementMarker(30, () -> {
                    leftSlide.setPosition(4260);
                    rightSlide.setPosition(4260);
                })
                .addTemporalMarker(15, () -> {
                    pivot.rotateTo(-280);
                })
                .addTemporalMarker(21, intake::outtake)
                .addTemporalMarker(25, () -> {
                    pivot.rotateTo(-140);
                })
                .addTemporalMarker(27, () -> {
                    leftSlide.setPosition(0);
                    rightSlide.setPosition(0);
                })
                .build();


        schedule(new SequentialCommandGroup(
                new InstantCommand(() -> pivot.rotateTo(-146)),
                new TrajectorySequenceFollowerCommand(drive, trajectorySequence)
        ));
    }
}
*/