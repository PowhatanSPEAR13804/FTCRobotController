package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.commands.TrajectoryFollowerCommand;
import org.firstinspires.ftc.teamcode.drive.MecanumOdometry;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

/**
 * This is a simple routine to test translational drive capabilities.
 *
 * NOTE: this has been refactored to use FTCLib's command-based
 */
@Config
@Disabled
@Autonomous(group = "drive")
public class StrafeTest extends CommandOpMode {

    public static double DISTANCE = 60; // in

    private Drivetrain drive;
    private TrajectoryFollowerCommand strafeFollower;

    @Override
    public void initialize() {
        drive = new Drivetrain(new MecanumOdometry(hardwareMap), false);
        strafeFollower = new TrajectoryFollowerCommand(drive,
                drive.trajectoryBuilder(new Pose2d())
                    .strafeRight(DISTANCE)
                    .build()
        );
        new Trigger(() -> gamepad1.y).whenActive(() -> {
            Pose2d poseEstimate = drive.getPoseEstimate();
            telemetry.addData("finalX", poseEstimate.getX());
            telemetry.addData("finalY", poseEstimate.getY());
            telemetry.addData("finalHeading", poseEstimate.getHeading());
            telemetry.update();
        });
        schedule(new WaitUntilCommand(this::isStarted).andThen(strafeFollower));
    }

}
