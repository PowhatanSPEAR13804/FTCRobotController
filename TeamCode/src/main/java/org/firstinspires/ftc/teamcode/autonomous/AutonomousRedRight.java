package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Libraries.GoBildaPinpointDriver;
import org.firstinspires.ftc.teamcode.commands.TrajectoryFollowerCommand;
import org.firstinspires.ftc.teamcode.commands.TrajectorySequenceFollowerCommand;
import org.firstinspires.ftc.teamcode.drive.MecanumOdometry;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Pivot;
import org.firstinspires.ftc.teamcode.subsystems.ViperSlide;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequenceBuilder;

@Autonomous
public class AutonomousRedRight extends CommandOpMode {
    public void initialize() {

        Drivetrain drive = new Drivetrain(new MecanumOdometry(hardwareMap), false);
        Intake intake = new Intake(hardwareMap, "Hub1_Servo0", "Hub1_Servo1");
        ViperSlide leftSlide = new ViperSlide(hardwareMap, telemetry, "Hub2_Motor2", true);
        ViperSlide rightSlide = new ViperSlide(hardwareMap, telemetry, "Hub1_Motor3", false);
        Pivot pivot = new Pivot(hardwareMap, "Hub1_Motor1", "Hub1_Servo2", telemetry, true);

        // We want to start the bot at x: 10, y: -8, heading: 90 degrees
        Pose2d startPose = new Pose2d(9, -63, Math.toRadians(270));

        intake.closeFinger();
        drive.setPoseEstimate(startPose);

        TrajectorySequence trajectorySequence = drive.trajectorySequenceBuilder(startPose)
                /*.back(31)
                .addDisplacementMarker(10, () -> {
                    leftSlide.setPosition(1870);
                    rightSlide.setPosition(1870);
                })
                .waitSeconds(0.5)
                .addTemporalMarker(2.5, () -> {
                    leftSlide.setPosition(1050);
                    rightSlide.setPosition(1050);
                    pivot.rotateTo(0);
                })
                .addTemporalMarker(2.75, intake::openFinger)
                .addTemporalMarker(4, () -> {
                    leftSlide.setPosition(0);
                    rightSlide.setPosition(0);
                })*/
                .strafeLeft(25)
                .back(3)
                .lineTo(new Vector2d(24, -48))
                .strafeLeft(8)
                .splineToConstantHeading(new Vector2d(36, -12), Math.toRadians(270))
                .strafeLeft(16)
                .forward(42)
                .back(42)
                .strafeLeft(18)
                .forward(42)
                //.back(42)
                //.strafeLeft(16)
                //.forward(42)
                .build();

        schedule(new SequentialCommandGroup(
                new InstantCommand(() -> pivot.rotateTo(-146)),
                new TrajectorySequenceFollowerCommand(drive, trajectorySequence)
        ));
    }
}
