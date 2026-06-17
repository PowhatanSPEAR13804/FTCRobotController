package org.firstinspires.ftc.teamcode.Teleop;


import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Flywheel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.nextftc.bindings.BindingManager;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.driving.MecanumDriverControlled;
import dev.nextftc.hardware.impl.CRServoEx;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.impl.ServoEx;



@TeleOp(name = "SPEAR TeleOp 2")
//@SuppressWarnings("FieldCanBeLocal")
public class SPEARTeleopFLYWHEEL extends NextFTCOpMode {
    private static final Logger log = LoggerFactory.getLogger(SPEARTeleopFLYWHEEL.class);

    public SPEARTeleopFLYWHEEL() {
        addComponents(
                new SubsystemComponent(Flywheel.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    // change the names and directions to suit your robot
    private final MotorEx frontLeftMotor = new MotorEx("Hub1_Motor3").reversed();
    private final MotorEx frontRightMotor = new MotorEx("Hub1_Motor2");
    private final MotorEx backLeftMotor = new MotorEx("Hub1_Motor1").reversed();
    private final MotorEx backRightMotor = new MotorEx("Hub1_Motor0");

    private final MotorEx flywheel = new MotorEx("Hub2_Motor2").reversed();
    private final CRServoEx advancer = new CRServoEx("Hub1_Servo5");
    private final ServoEx angleAdjuster = new ServoEx("Hub1_Servo4");
    private final MotorEx rightLifter = new MotorEx("Hub2_Motor0");
    private final MotorEx leftLifter = new MotorEx("Hub2_Motor1");

    //private final VoltageSensor battery = hardwareMap.get(VoltageSensor.class, "control hub");
    //private double voltage = battery.getVoltage();

    //private final IMUEx imu = new IMUEx("Hub2_I2C0", Direction.UP, Direction.FORWARD).zeroed();

    private boolean flywheelOn = false;
    private boolean advancerOn = false;
    private double flywheelSpeed = 1000;
    private final double advancerPower = -1.0;
    private boolean far = false;

    @Override
    public void onInit() {
        //new InstantCommand(Lifter.INSTANCE.idle);
    }

    @Override
    public void onStartButtonPressed() {
        Command driverControlled = new MecanumDriverControlled(
                frontLeftMotor,
                frontRightMotor,
                backLeftMotor,
                backRightMotor,
                Gamepads.gamepad1().leftStickY().negate(),
                Gamepads.gamepad1().leftStickX(),
                Gamepads.gamepad1().rightStickX()
                //new FieldCentric(imu)
        );
        Command slowControl = new MecanumDriverControlled(
                frontLeftMotor,
                frontRightMotor,
                backLeftMotor,
                backRightMotor,
                Gamepads.gamepad1().leftStickY().negate().mapToRange(value -> value * 0.25),
                Gamepads.gamepad1().leftStickX().mapToRange(value -> value * 0.25),
                Gamepads.gamepad1().rightStickX().mapToRange(value -> value * 0.25)
                //new FieldCentric(imu)
        );
        driverControlled.schedule();


        Gamepads.gamepad1().a()
                .whenBecomesTrue(()->{
                    flywheelOn = !flywheelOn;
                });

        Gamepads.gamepad1().y()
                        .whenBecomesTrue(() ->{
                            far = !far;
                        });

        Gamepads.gamepad1().rightBumper()
                .whenTrue(()->advancerOn = true)
                .whenFalse(()->advancerOn = false);

        Gamepads.gamepad1().leftBumper()
                .whenBecomesTrue(()->{
                   driverControlled.cancel();
                   slowControl.schedule();
                })
                .whenBecomesFalse(()->{
                   driverControlled.schedule();
                   slowControl.cancel();
                });
    }

    @Override
    public void onUpdate() {
        /*if(voltage >= 14) {
            voltageMultiplier = 0.725;
        } else if(voltage >= 12.5) {
            voltageMultiplier = 0.75;
        } else if(voltage >= 12) {
            voltageMultiplier = 0.775;
        } else {
            voltageMultiplier = 0.8;
        }

        */
        BindingManager.update();
        if(far) {
            flywheelSpeed = 1750;
            angleAdjuster.setPosition(1);
        }
        else {
            flywheelSpeed = 1250;
            angleAdjuster.setPosition(0);
        }
        Flywheel.INSTANCE.setGoal((flywheelOn) ? flywheelSpeed : 0.0);
        advancer.setPower((advancerOn) ? advancerPower : 0.0);

        rightLifter.setPower(gamepad1.right_trigger-gamepad1.left_trigger);
        leftLifter.setPower(gamepad1.right_trigger-gamepad1.left_trigger);
        telemetry.addData("advancer on", advancerOn);
        telemetry.addData("flywheel error", Flywheel.INSTANCE.getError());
        telemetry.addData("flywheel speed", Flywheel.INSTANCE.getSpeed());
        telemetry.addData("flywheel goal", Flywheel.INSTANCE.getGoal());
        telemetry.addData("flywheel power", Flywheel.INSTANCE.getPower());
        telemetry.update();
    }

    public void onStop() {
        BindingManager.reset();
    }

}