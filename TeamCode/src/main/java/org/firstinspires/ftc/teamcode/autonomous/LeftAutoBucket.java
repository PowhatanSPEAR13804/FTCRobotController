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
public class LeftAutoBucket extends CommandOpMode {
    public void initialize() {

        Drivetrain drive = new Drivetrain(new MecanumOdometry(hardwareMap), false);
        Intake intake = new Intake(hardwareMap, "Hub1_Servo0", "Hub1_Servo1");
        ViperSlide leftSlide = new ViperSlide(hardwareMap, telemetry, "Hub2_Motor2", true);
        ViperSlide rightSlide = new ViperSlide(hardwareMap, telemetry, "Hub1_Motor3", false);
        Pivot pivot = new Pivot(hardwareMap, "Hub1_Motor1", "Hub1_Servo2", telemetry, true);

        // We want to start the bot at x: 10, y: -8, heading: 90 degrees
        Pose2d startPose = new Pose2d(-38, -63, Math.toRadians(180));

        intake.intake();
        drive.setPoseEstimate(startPose);
        TrajectorySequence trajectorySequence = drive.trajectorySequenceBuilder(startPose)
                .strafeRight(1)
                .forward(15)
                .turn(Math.toRadians(20))
                .addTemporalMarker(0.2, ()->{
                    leftSlide.setPosition(3400);
                    rightSlide.setPosition(3400);
                    pivot.rotateTo(-146);
                })
                .addTemporalMarker(2, ()->{
                    pivot.rotateTo(-210);
                })
                .addTemporalMarker(2.2, intake::outtake)
                .addTemporalMarker(4, ()->{
                    pivot.rotateTo(-146);
                })
                .addTemporalMarker(4, ()->{
                    leftSlide.setPosition(100);
                    rightSlide.setPosition(100);
                })

                .lineTo(new Vector2d(-40, -55))

                //go to yellow block
                .splineToLinearHeading(new Pose2d(-27, -41, Math.toRadians(150)), Math.toRadians(0))
                //pick up
                .addTemporalMarker(7, ()->{
                    pivot.rotateTo(-470);
                })
                .addTemporalMarker(7, intake::intake)
                .addTemporalMarker(10, ()->{
                    pivot.rotateTo(-180);
                })
                .forward(6)

                //go to basket
                .splineTo(new Vector2d(-55, -61), Math.toRadians(225))
                //.waitSeconds(1)

                //drop off block
                .addTemporalMarker(9.5, ()->{
                    leftSlide.setPosition(3400);
                    rightSlide.setPosition(3400);
                })
                .addTemporalMarker(12,intake::outtake)
                .addTemporalMarker(14, ()->{
                    leftSlide.setPosition(100);
                    rightSlide.setPosition(100);
                })
                .addTemporalMarker(14, ()->{
                    pivot.rotateTo(-146);
                })
                .addTemporalMarker(16, intake::intake)
                //.waitSeconds(0.25)
                //move right away from basket
                .lineToSplineHeading(new Pose2d(-35, -30, Math.toRadians(180)))
                //go to block
                //pick up (pivot down and intake)
                .waitSeconds(0.25)
                .addTemporalMarker(16, ()->{
                    pivot.rotateTo(-470);
                })
                .addTemporalMarker(17.5, ()->{
                    pivot.rotateTo(-146);
                })

                .forward(2)
                .splineTo(new Vector2d(-52, -62), Math.toRadians(225))

                //drop off
                //drop off block
                .waitSeconds(0.5)
                .addTemporalMarker(19.5, ()->{
                    leftSlide.setPosition(3600);
                    rightSlide.setPosition(3600);
                })
                .addTemporalMarker(21, ()->{
                    pivot.rotateTo(-200);
                })
                .addTemporalMarker(21, intake::outtake)
                .addTemporalMarker(23, ()->{
                    leftSlide.setPosition(100);
                    rightSlide.setPosition(100);
                })
                .addTemporalMarker(23.5, ()->{
                    pivot.rotateTo(0);
                })


                .lineToSplineHeading(new Pose2d(-43, -31, Math.toRadians(180)))
                //pick up
                //.forward(2)
                //.splineTo(new Vector2d(-58, -62), Math.toRadians(225))
                //drop off
                .build();

        schedule(new SequentialCommandGroup(
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
