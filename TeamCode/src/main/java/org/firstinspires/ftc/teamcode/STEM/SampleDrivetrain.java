package org.firstinspires.ftc.teamcode.STEM;

import static dev.nextftc.bindings.Bindings.range;
import static dev.nextftc.bindings.Bindings.variable;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.Subsystems.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.nextftc.bindings.BindingManager;
import dev.nextftc.bindings.Range;
import dev.nextftc.bindings.Variable;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.driving.FieldCentric;
import dev.nextftc.hardware.driving.MecanumDriverControlled;
import dev.nextftc.hardware.impl.CRServoEx;
import dev.nextftc.hardware.impl.Direction;
import dev.nextftc.hardware.impl.IMUEx;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.impl.ServoEx;

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
