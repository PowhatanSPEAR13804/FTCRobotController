package org.firstinspires.ftc.teamcode.teleop;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.MecanumDriveCommand;
import org.firstinspires.ftc.teamcode.drive.MecanumOdometry;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Pivot;
import org.firstinspires.ftc.teamcode.subsystems.ViperSlide;

@TeleOp
public class BasicTeleop extends CommandOpMode {
    private GamepadEx gamepad;

    @Override
    public void initialize() {
        gamepad = new GamepadEx(gamepad1);

        Drivetrain drive = new Drivetrain(new MecanumOdometry(hardwareMap), false);
        ViperSlide leftSlide = new ViperSlide(hardwareMap, telemetry, "Hub2_Motor2", true);
        ViperSlide rightSlide = new ViperSlide(hardwareMap, telemetry, "Hub1_Motor3", false);
        Pivot pivot = new Pivot(hardwareMap, "Hub1_Motor1", telemetry, false);
        Intake intake = new Intake(hardwareMap, "Hub1_Servo5", "Hub1_Servo4");


        register(drive, leftSlide, rightSlide, pivot, intake);
        drive.setDefaultCommand(new MecanumDriveCommand(
                drive, () -> -gamepad.getLeftY(), gamepad::getLeftX, gamepad::getRightX
        ));

        new Trigger(() -> gamepad2.dpad_up).whenActive(new InstantCommand(() -> {
            leftSlide.setPosition(4260);
            rightSlide.setPosition(4260);
        }, leftSlide, rightSlide));
        new Trigger(() -> gamepad2.dpad_left).whenActive(new InstantCommand(() -> {
            leftSlide.setPosition(2250);
            rightSlide.setPosition(2250);
        }, leftSlide, rightSlide));
        new Trigger(() -> gamepad2.dpad_right).whenActive(new InstantCommand(() -> {
            leftSlide.setPosition(2050);
            rightSlide.setPosition(2050);
        }, leftSlide, rightSlide));
        new Trigger(() -> gamepad2.dpad_down).whenActive(new InstantCommand(() -> {
            leftSlide.setPosition(700);
            rightSlide.setPosition(700);
        }, leftSlide, rightSlide));
        new Trigger(() -> gamepad2.y).whenActive(new InstantCommand(() -> {
            leftSlide.setPosition(0);
            rightSlide.setPosition(0);
        }, leftSlide, rightSlide));
        
        new Trigger(() -> gamepad2.left_trigger > 0.1).whenActive(new RunCommand(() -> {
            leftSlide.setSpeed(-gamepad2.left_trigger);
            rightSlide.setSpeed(-gamepad2.left_trigger);
        }, leftSlide, rightSlide)).whenInactive(new InstantCommand(() -> {
            leftSlide.setPosition(leftSlide.getPosition());
            rightSlide.setPosition(rightSlide.getPosition());
        }, leftSlide, rightSlide));

        new Trigger(() -> gamepad2.right_trigger > 0.1 && leftSlide.getPosition() < 4260).whenActive(new RunCommand(() -> {
            leftSlide.setSpeed(gamepad2.right_trigger);
            rightSlide.setSpeed(gamepad2.right_trigger);
        }, leftSlide, rightSlide)).whenInactive(new InstantCommand(() -> {
            leftSlide.setPosition(leftSlide.getPosition());
            rightSlide.setPosition(rightSlide.getPosition());
        }, leftSlide, rightSlide));

        new Trigger(() -> gamepad2.left_bumper).whenActive(new RunCommand(() -> pivot.rotate(-1), pivot)).whenInactive(new RunCommand(() -> pivot.rotate(0), pivot));

        new Trigger(() -> gamepad2.right_bumper).whenActive(new RunCommand(() -> pivot.rotate(1), pivot)).whenInactive(new RunCommand(() -> pivot.rotate(0), pivot));

        new Trigger(() -> gamepad2.a && !gamepad2.start).toggleWhenActive(new RunCommand(intake::openFinger, intake), new RunCommand(intake::closeFinger, intake));
        new Trigger(() -> gamepad2.b && !gamepad2.start).toggleWhenActive(new RunCommand(intake::sidewaysWrist, intake), new RunCommand(intake::straightWrist, intake));

        schedule(new RunCommand(() -> {
            drive.update();
            telemetry.update();
        }));
    }
}
