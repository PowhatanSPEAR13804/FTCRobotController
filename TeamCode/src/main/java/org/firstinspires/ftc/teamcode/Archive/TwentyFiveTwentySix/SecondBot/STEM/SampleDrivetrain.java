package org.firstinspires.ftc.teamcode.Archive.TwentyFiveTwentySix.SecondBot.STEM;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import dev.nextftc.core.commands.Command;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.hardware.driving.MecanumDriverControlled;
import dev.nextftc.hardware.impl.MotorEx;

@Disabled

@TeleOp(name = "NextFTC TeleOp Program Java")
public class SampleDrivetrain extends NextFTCOpMode{
    private final MotorEx frontleftmotor = new MotorEx("front_left").reversed();
    private final MotorEx frontrightmotor = new MotorEx("front_right");
    private final MotorEx backleftmotor = new MotorEx("back_left").reversed();
    private final MotorEx backrightmotor = new MotorEx("back_right");

    @Override
    public void onStartButtonPressed() {
        Command drivercontrolled = new MecanumDriverControlled(
              frontleftmotor,
                frontrightmotor,
                backleftmotor,
                backrightmotor,
                Gamepads.gamepad1().leftStickY().negate(),
                Gamepads.gamepad1().leftStickX(),
                Gamepads.gamepad1().rightStickX()

        );
        drivercontrolled.schedule();
    }
    public SampleDrivetrain() {
    }
}
