package org.firstinspires.ftc.teamcode.Teleop;


import static dev.nextftc.bindings.Bindings.range;
import static dev.nextftc.bindings.Bindings.variable;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

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

@TeleOp(name = "SPEAR TeleOp")
//@SuppressWarnings("FieldCanBeLocal")
public class SPEARTeleop extends NextFTCOpMode {
    private static final Logger log = LoggerFactory.getLogger(SPEARTeleop.class);

    public SPEARTeleop() {
        addComponents(
                /*new SubsystemComponent(/*Shooter.INSTANCE, Lifter.INSTANCE),*/
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
    private double flywheelGoal = 0.55;
    private final double advancerPower = -1.0;
    private boolean far = false;
    private double flywheelPower = 0.75;

    private double integralSum = 0;
    private double lastError = 0;
    ElapsedTime timer = new ElapsedTime();


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
            flywheelGoal = -1850;
            angleAdjuster.setPosition(1);
        }
        else {
            flywheelGoal = -1350;
            angleAdjuster.setPosition(0);
        }
        if (!flywheelOn) {
            flywheelGoal = 0;
        }

        double flywheelPower = PIDController(flywheelGoal);

        if ((Math.abs(flywheel.getVelocity()) <=20) && flywheelGoal == 0.0) {
            flywheelPower = 0;
        }
        flywheel.setPower(flywheelPower);

        //flywheel.setPower((flywheelOn) ? flywheelPower : 0.0);
        advancer.setPower((advancerOn) ? advancerPower : 0.0);

        rightLifter.setPower(gamepad1.right_trigger-gamepad1.left_trigger);
        leftLifter.setPower(gamepad1.right_trigger-gamepad1.left_trigger);
        telemetry.addData("advancer on", advancerOn);
        telemetry.addData("flywheel on", flywheelOn);
        telemetry.addData("flywheel velocity", flywheel.getVelocity());
        telemetry.addData("flywheel goal", flywheelGoal);
        telemetry.addData("flywheel power", flywheel.getPower());
        telemetry.update();
    }

    public void onStop() {
        BindingManager.reset();
    }

    public double PIDController(double goal) {
        double kP = 0.05;
        double kI = 0.0;
        double kD = 0.0;

        double flywheelVelocity = flywheel.getVelocity();
        double error = goal-flywheelVelocity;
        double derivative = error-lastError / timer.seconds();
        integralSum += (error*timer.seconds());
        lastError = error;
        timer.reset();
        double powerOutput = (kP * error) + (kI * integralSum) + (kD * derivative);
        return -powerOutput;
    }
}