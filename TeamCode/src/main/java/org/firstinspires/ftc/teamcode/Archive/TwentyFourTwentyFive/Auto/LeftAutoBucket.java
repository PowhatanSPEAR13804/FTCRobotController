package org.firstinspires.ftc.teamcode.Archive.TwentyFourTwentyFive.Auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
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
                //to basket
                .forward(14)
                .turn(Math.toRadians(20))
                .addTemporalMarker(0, ()->{
                    leftSlide.setPosition(3200);
                    rightSlide.setPosition(3200);
                    pivot.rotateTo(-146);
                })
                .addTemporalMarker(1, ()->{
                    pivot.rotateTo(-210);
                })
                .addTemporalMarker(1.75, intake::outtake)
                .addTemporalMarker(2.5, ()->{
                    pivot.rotateTo(-146);
                })
                .addTemporalMarker(3.5, ()->{
                    leftSlide.setPosition(135);
                    rightSlide.setPosition(135);
                })
                //to block
                .lineToLinearHeading(new Pose2d(-49, -53, Math.toRadians(90)))
                .addTemporalMarker(3, ()->{
                    pivot.rotateTo(-460);
                    intake.intake();
                })
                //pick up
                .forward(10)
                .addTemporalMarker(5, ()->{
                    pivot.rotateTo(-190);
                })
                .addTemporalMarker(5.5, ()->{
                    leftSlide.setPosition(3400);
                    rightSlide.setPosition(3400);
                })
                .addTemporalMarker(7.5, ()->{
                    //pivot.rotateTo(-186);
                })
                //to basket
                .back(2)
                .lineToSplineHeading(new Pose2d(-58, -57, Math.toRadians(225)))
                .back(5)
                .addTemporalMarker(7.75, intake::outtake)
                .addTemporalMarker(9.5, ()->{
                    leftSlide.setPosition(135);
                    rightSlide.setPosition(135);
                    pivot.rotateTo(-480);
                    intake.intake();
                })
                //to second block
                .lineToSplineHeading(new Pose2d(-59, -53.5, Math.toRadians(90)))
                //pick up
                .forward(10)
                //basket
                .addTemporalMarker(11.5, ()->{
                    pivot.rotateTo(-210);
                    leftSlide.setPosition(3400);
                    rightSlide.setPosition(3400);
                })
                .lineToSplineHeading(new Pose2d(-57, -56, Math.toRadians(225)))
                .back(5)
                .addTemporalMarker(13.6, intake::outtake)
                //block 3
                .lineToSplineHeading(new Pose2d(-43, -45, Math.toRadians(150)))
                .addTemporalMarker(14.75, ()->{
                    leftSlide.setPosition(135);
                    rightSlide.setPosition(135);
                })
                .addTemporalMarker(15.5, ()->{
                    intake.intake();
                    pivot.rotateTo(-480);
                })
                .addTemporalMarker(17, ()->{
                    pivot.rotateTo(-140);
                })
                //baskcet
                .forward(13)
                /*.turn(Math.toRadians(10))
                .turn(Math.toRadians(-20))
                .turn(Math.toRadians(10))*/
                .addTemporalMarker(19.5, ()->{
                    leftSlide.setPosition(3400);
                    rightSlide.setPosition(3400);
                })
                .addTemporalMarker(20.5, ()->{
                    pivot.rotateTo(-195);
                })
                .addTemporalMarker(20.5, intake::outtake)
                .back(1)
                .lineToSplineHeading(new Pose2d(-58.5, -61.5, Math.toRadians(225)))
                .back(6)
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
