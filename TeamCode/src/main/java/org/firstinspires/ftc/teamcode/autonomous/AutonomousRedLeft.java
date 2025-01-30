package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

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
public class AutonomousRedLeft extends CommandOpMode {
    public void initialize() {

        Drivetrain drive = new Drivetrain(new MecanumOdometry(hardwareMap), false);
        Intake intake = new Intake(hardwareMap, "Hub1_Servo0", "Hub1_Servo1");
        ViperSlide leftSlide = new ViperSlide(hardwareMap, telemetry, "Hub2_Motor2", true);
        ViperSlide rightSlide = new ViperSlide(hardwareMap, telemetry, "Hub1_Motor3", false);
        Pivot pivot = new Pivot(hardwareMap, "Hub1_Motor1", "Hub1_Servo2", telemetry, true);

        // We want to start the bot at x: 10, y: -8, heading: 90 degrees
        Pose2d startPose = new Pose2d(-21, -63, Math.toRadians(270));

        intake.closeFinger();
        drive.setPoseEstimate(startPose);
        TrajectorySequence trajectorySequence = drive.trajectorySequenceBuilder(startPose)
                .lineTo(new Vector2d(-11.5, -33))
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
                .addTemporalMarker(2.65, intake::openFinger)
                .addTemporalMarker(4, () -> {
                    leftSlide.setPosition(100);
                    rightSlide.setPosition(100);
                })
                .forward(5)
                .splineTo(new Vector2d(-39, -39), Math.toRadians(135))
                .waitSeconds(3)
                .addTemporalMarker(10, ()->{
                    pivot.rotateTo(-490);
                })
                .addTemporalMarker(11, intake::intake)
                .addTemporalMarker(12, ()->{
                    pivot.rotateTo(-146);
                })
                .waitSeconds(1)
                .splineTo(new Vector2d(-60, -62), Math.toRadians(225))
                .waitSeconds(1)
                .addTemporalMarker(14, ()->{
                    leftSlide.setPosition(3600);
                    rightSlide.setPosition(3600);
                })
                .addTemporalMarker(15, ()->{
                    pivot.rotateTo(-180);
                })
                .addTemporalMarker(17,intake::outtake)
                .addTemporalMarker(22, ()->{
                    pivot.rotateTo(-146);
                })
                .addTemporalMarker(19, ()->{
                    leftSlide.setPosition(100);
                    rightSlide.setPosition(100);
                })
                .back(20)
                .addTemporalMarker(23, ()->{
                    pivot.rotateTo(-490);
                })
                .addTemporalMarker(23, intake::intake)
                .turn(Math.toRadians(-90))
                .forward(3)
                .waitSeconds(3)
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
                })*/
}
