package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.MecanumDriveCommand;
import org.firstinspires.ftc.teamcode.drive.MecanumOdometry;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

/**
 * This is an additional test made specifically for FTCLib's quickstart.
 * It tests the field-centric mode of the {@link Drivetrain}.
 * The robot should always drive in the direction in which the left stick is
 * being pushed. This should be run after your {@link LocalizationTest}
 * to ensure proper tuning for the localization's heading estimate.
 *
 * @author Jackson
 */
@Config
@TeleOp
public class RobotCentricTest extends CommandOpMode {

    private GamepadEx gamepad;
    private Drivetrain drive;

    @Override
    public void initialize() {
        gamepad = new GamepadEx(gamepad1);

        drive = new Drivetrain(new MecanumOdometry(hardwareMap), false);

        register(drive);
        drive.setDefaultCommand(new MecanumDriveCommand(
                drive, () -> -gamepad.getLeftY(), gamepad::getLeftX, gamepad::getRightX
        ));

        schedule(new RunCommand(() -> {
            telemetry.update();
        }));
    }

}
