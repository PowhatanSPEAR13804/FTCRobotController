package org.firstinspires.ftc.teamcode.Drive.OpMode;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.RunCommand;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

@TeleOp
public class DriveRobotRelative extends CommandOpMode {
	Drivetrain drivetrain = new Drivetrain(hardwareMap);

	@Override
	public void initialize() {
		drivetrain.setDefaultCommand(new RunCommand(() -> {
			drivetrain.drive(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
		}));
	}
}
