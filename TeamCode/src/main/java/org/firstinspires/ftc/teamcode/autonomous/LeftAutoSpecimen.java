package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.TrajectorySequenceFollowerCommand;
import org.firstinspires.ftc.teamcode.drive.MecanumOdometry;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Pivot;
import org.firstinspires.ftc.teamcode.subsystems.ViperSlide;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous
public class LeftAutoSpecimen extends CommandOpMode {
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
                //to red bar
                .lineTo(new Vector2d(-11.5, -32.5))
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
                .splineTo(new Vector2d(-35, -46), Math.toRadians(135))


                //pick up
                //TODO shorten time here, robot just sits still for a couple seconds
                .waitSeconds(1.5)
                .addTemporalMarker(7, ()->{
                    pivot.rotateTo(-490);
                })
                .addTemporalMarker(8, intake::intake)
                .addTemporalMarker(12, ()->{
                    pivot.rotateTo(-146);
                })
                .forward(8)

                //go to basket
                .splineTo(new Vector2d(-60, -62), Math.toRadians(225))
                .waitSeconds(1)

                //drop off block
                .addTemporalMarker(11, ()->{
                    leftSlide.setPosition(3600);
                    rightSlide.setPosition(3600);
                })
                .addTemporalMarker(12, ()->{
                    pivot.rotateTo(-180);
                })
                .addTemporalMarker(14,intake::outtake)
                .addTemporalMarker(16, ()->{
                    leftSlide.setPosition(100);
                    rightSlide.setPosition(100);
                })
                .addTemporalMarker(17, ()->{
                    pivot.rotateTo(-146);
                })
                .addTemporalMarker(17.5, intake::intake)

                //TODO add subsystem code
                //move right away from basket
                .lineToSplineHeading(new Pose2d(-39, -62, Math.toRadians(180)))
                //go to block
                .strafeRight(31.75)

                //pick up (pivot down and intake)
                //.waitSeconds()
                .addTemporalMarker(20, ()->{
                    pivot.rotateTo(-490);
                })
                .addTemporalMarker(22.5, ()->{
                    pivot.rotateTo(-146);
                })

                .forward(2)
                .splineTo(new Vector2d(-60, -62), Math.toRadians(225))

                //drop off
                //drop off block
                .addTemporalMarker(24, ()->{
                    leftSlide.setPosition(3600);
                    rightSlide.setPosition(3600);
                })
                .addTemporalMarker(25, ()->{
                    pivot.rotateTo(-180);
                })
                .addTemporalMarker(25.5, intake::outtake)
                .addTemporalMarker(27, ()->{
                    leftSlide.setPosition(100);
                    rightSlide.setPosition(100);
                })
                .addTemporalMarker(27.5, ()->{
                    pivot.rotateTo(-146);
                })


                .lineToSplineHeading(new Pose2d(-48, -62, Math.toRadians(180)))
                /*.strafeRight(38)
                //pick up
                .forward(2)
                .waitSeconds(3)
                .splineTo(new Vector2d(-60, -62), Math.toRadians(225))
                //drop off */
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
