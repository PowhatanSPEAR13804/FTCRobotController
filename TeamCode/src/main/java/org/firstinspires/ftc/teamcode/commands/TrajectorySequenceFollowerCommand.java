package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequenceRunner;

public class TrajectorySequenceFollowerCommand extends CommandBase {
    private final Drivetrain drive;
    private final TrajectorySequence sequence;

    public TrajectorySequenceFollowerCommand(Drivetrain drive, TrajectorySequence sequence) {
        this.drive = drive;
        this.sequence = sequence;

        addRequirements(drive);
    }

    @Override
    public void initialize() {
        drive.followTrajectorySequence(sequence);
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            drive.stop();
        }
    }

    @Override
    public boolean isFinished() {
        return Thread.currentThread().isInterrupted() || !drive.isBusy();
    }
}
