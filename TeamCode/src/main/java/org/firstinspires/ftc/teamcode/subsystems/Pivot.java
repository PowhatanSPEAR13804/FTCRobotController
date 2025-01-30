package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.controller.wpilibcontroller.ArmFeedforward;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotState;

@Config
public class Pivot extends SubsystemBase {
    private enum Mode {
        FLAT,
        DOWN,
        CUSTOM
    }
    private final Motor motor;
    //private final CRServo extender;
    private final PIDController pid = new PIDController(0.03, 0.0, 0.001);

    private final String motorID;
    //private final String extenderID;
    private final Telemetry telemetry;

    private Double setpoint;
    private Mode currentMode = Mode.CUSTOM;

    private final double initialPosition;

    public Pivot(HardwareMap hardwareMap, String motorID, String servoID, Telemetry tl, boolean reverse) {

        telemetry = tl;

        // Create the motor object
        this.motorID = motorID;
        //extenderID = servoID;
        motor = new Motor(hardwareMap, motorID);
        //extender = new CRServo(hardwareMap, extenderID);
        // Initialize the motor
        motor.setInverted(reverse);
        motor.resetEncoder();
        initialPosition = 0;
    }

    public void rotateTo(double position) {
        currentMode = Mode.CUSTOM;
        setpoint = position;
    }

    public void nextMode() {
        if (currentMode == Mode.CUSTOM || currentMode == Mode.DOWN) {
            currentMode = Mode.FLAT;
            setpoint = -390.;
        } else {
            currentMode = Mode.DOWN;
            setpoint = -450.;
        }
    }

    public void rotate(double speed) {
        setpoint = null;
        motor.set(speed);
        telemetry.addData("Pivot motor" + motorID + " speed", speed);
    }
    @Override
    public void periodic() {
        if(setpoint != null) {
            motor.set(pid.calculate(getAngle(), setpoint));
        }
        RobotState.armPosition = getAngle();
        telemetry.addData("Arm angle ", getAngle());
    }

    /*public void extend(double speed) {
           extender.set(speed);
    }*/

    public double getAngle() {
        return initialPosition + motor.getCurrentPosition();
    }
}