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
public class Test extends CommandOpMode {
    public void initialize() {
        Drivetrain drive = new Drivetrain(new MecanumOdometry(hardwareMap), false);
        Intake intake = new Intake(hardwareMap, "Hub1_Servo0", "Hub1_Servo1");
        ViperSlide leftSlide = new ViperSlide(hardwareMap, telemetry, "Hub2_Motor2", true);
        ViperSlide rightSlide = new ViperSlide(hardwareMap, telemetry, "Hub1_Motor3", false);
        Pivot pivot = new Pivot(hardwareMap, "Hub1_Motor1", "Hub1_Servo2", telemetry, true);

        // We want to start the bot at x: 10, y: -8, heading: 90 degrees
        Pose2d startPose = new Pose2d(-38, -63, Math.toRadians(180));

        intake.closeFinger();
        drive.setPoseEstimate(startPose);

        TrajectorySequence trajectorySequence = drive.trajectorySequenceBuilder(startPose)
                .strafeRight(1)
                //to basket
                .forward(14)
                .turn(Math.toRadians(20))
                //to block
                .lineToLinearHeading(new Pose2d(-51, -40, Math.toRadians(90)))
                //pick up
                //to basket
                .lineToSplineHeading(new Pose2d(-55, -61, Math.toRadians(225)))
                //to second block
                .lineToSplineHeading(new Pose2d(-58, -40, Math.toRadians(90)))
                //pick up
                .forward(2)
                //basket
                .lineToSplineHeading(new Pose2d(-51, -63, Math.toRadians(225)))
                //block 3
                .lineToSplineHeading(new Pose2d(-54, -40, Math.toRadians(150)))
                //baskce
                .back(1)
                .lineToSplineHeading(new Pose2d(-51, -68, Math.toRadians(180)))
                .splineTo(new Vector2d(-12, -12), Math.toRadians(0))
                .build();


        schedule(new SequentialCommandGroup(
                new InstantCommand(() -> pivot.rotateTo(-146)),
                new TrajectorySequenceFollowerCommand(drive, trajectorySequence)
        ));
    }
}
*/