package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.controller.wpilibcontroller.ArmFeedforward;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Pivot extends SubsystemBase {
    private static final double TICKS_TO_RADIANS = 0.0;
    private static final double DEGREES_TO_RADIANS = Math.PI/180;
    private final CRServo pivotServo;
    private final PIDController pid = new PIDController(0.0, 0.0, 0.0);
    private final ArmFeedforward ff = new ArmFeedforward(0.0, 0.0, 0.0);

    private final Telemetry telemetry;

    private Double setpoint;

    public Pivot(HardwareMap hardwareMap, String servoID, Telemetry tl) {
        pivotServo = new CRServo(hardwareMap, servoID);
        telemetry = tl;
    }

    public void rotateWithPID(double position) {
        setpoint = position * DEGREES_TO_RADIANS;
    }

    public void rotate(double speed) {
        setpoint = null;
        pivotServo.set(speed);
    }

    @Override
    public void periodic() {
        if(setpoint != null) {
            pivotServo.set(ff.calculate(setpoint, 0.0) + pid.calculate(pivotServo.getCurrentPosition() * TICKS_TO_RADIANS, setpoint));
        }
        telemetry.addData("Arm angle ", pivotServo.getCurrentPosition());
    }
}