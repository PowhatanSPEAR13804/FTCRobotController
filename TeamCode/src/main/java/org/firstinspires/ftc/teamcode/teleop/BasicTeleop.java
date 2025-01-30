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

import java.util.concurrent.atomic.AtomicReference;

@TeleOp
public class BasicTeleop extends CommandOpMode {
    private GamepadEx gamepad;

    @Override
    public void initialize() {
        gamepad = new GamepadEx(gamepad1);

        Drivetrain drive = new Drivetrain(new MecanumOdometry(hardwareMap), false);
        ViperSlide leftSlide = new ViperSlide(hardwareMap, telemetry, "Hub2_Motor2", true);
        ViperSlide rightSlide = new ViperSlide(hardwareMap, telemetry, "Hub1_Motor3", false);
        Pivot pivot = new Pivot(hardwareMap, "Hub1_Motor1", "Hub1_Servo2", telemetry, true);
        Intake intake = new Intake(hardwareMap, "Hub1_Servo0", "Hub1_Servo1");

        double right_error = 0.99;

        AtomicReference<Double> multiplier = new AtomicReference<>(1.0);

        register(drive, leftSlide, rightSlide, pivot, intake);
        drive.setDefaultCommand(new MecanumDriveCommand(
                drive, () -> -gamepad.getLeftY() * multiplier.get(), () -> gamepad.getLeftX() * multiplier.get(), () -> gamepad.getRightX() * multiplier.get()
        ));

        new Trigger(() -> gamepad1.left_bumper || gamepad1.right_bumper)
                .whenActive(() -> {
                    multiplier.set(0.2);
                })
                .whenInactive(() -> {
                    multiplier.set(1.0);
                });

        new Trigger(() -> gamepad2.dpad_up).whenActive(new InstantCommand(() -> {
            leftSlide.setPosition(4260);
            rightSlide.setPosition(4260);
            pivot.rotateTo(-280);
        }, leftSlide, rightSlide, pivot));
        new Trigger(() -> gamepad2.dpad_left).whenActive(new InstantCommand(() -> {
            leftSlide.setPosition(0);
            rightSlide.setPosition(0);
            pivot.rotateTo(-140);
            intake.openFinger();
        }, leftSlide, rightSlide, pivot, intake));
        new Trigger(() -> gamepad2.dpad_right).whenActive(new InstantCommand(() -> {
            leftSlide.setPosition(1850);
            rightSlide.setPosition(1850);
            pivot.rotateTo(-420);
        }, leftSlide, rightSlide, pivot));
//        new Trigger(() -> gamepad2.dpad_down).whenActive(new InstantCommand(() -> {
//            leftSlide.setPosition(700);
//            rightSlide.setPosition(700);
//            pivot.rotateTo(-420);
//        }, leftSlide, rightSlide, pivot));
        new Trigger(() -> gamepad2.y).whenActive(new InstantCommand(() -> {
            leftSlide.togglePID();
            rightSlide.togglePID();
        }, leftSlide, rightSlide));

        //final double[] viperSpeedDown = {gamepad2.left_trigger};
        //final double[] viperSpeedUp = {gamepad2.right_trigger};

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
/*
        new Trigger(() -> gamepad1.left_trigger > 0.1 || gamepad2.left_stick_y > 0.1).whenActive(new RunCommand(() -> {
            if(gamepad1.left_trigger > 0.1) {
                pivot.extend(-gamepad1.left_trigger);
            } else {
                pivot.extend(-gamepad2.left_stick_y);
            }
        }, pivot)).whenInactive(new InstantCommand(() -> {
            pivot.extend(0);
        }, pivot));

        new Trigger(() -> gamepad1.right_trigger > 0.1 || gamepad2.left_stick_y < -0.1).whenActive(new RunCommand(() -> {
            if(gamepad1.left_stick_y < -0.1) {
                pivot.extend(gamepad1.right_trigger);
            } else {
                pivot.extend(-gamepad2.left_stick_y);
            }
        }, pivot)).whenInactive(new InstantCommand(() -> {
            pivot.extend(0);
        }, pivot));
*/
        new Trigger(() -> gamepad2.left_bumper)
                .whenActive(new RunCommand(() -> pivot.rotate(1), pivot))
                .whenInactive(new RunCommand(() -> pivot.rotateTo(pivot.getAngle()), pivot));

        new Trigger(() -> gamepad2.right_bumper)
                .whenActive(new RunCommand(() -> pivot.rotate(-1), pivot))
                .whenInactive(new RunCommand(() -> pivot.rotateTo(pivot.getAngle()), pivot));

        new Trigger(() -> gamepad2.x).whenActive(new InstantCommand(intake::toggleFinger, intake));
        new Trigger(() -> (gamepad2.b && !gamepad2.start)).toggleWhenActive(new RunCommand(intake::intake, intake), new RunCommand(intake::stop, intake));
        new Trigger(() -> (gamepad2.a && !gamepad2.start)).toggleWhenActive(new RunCommand(intake::outtake, intake), new RunCommand(intake::stop, intake));

        telemetry.addData("pivot position", pivot.getAngle());

        schedule(new RunCommand(() -> {
            drive.update();
            telemetry.update();

        }));
    }
}
