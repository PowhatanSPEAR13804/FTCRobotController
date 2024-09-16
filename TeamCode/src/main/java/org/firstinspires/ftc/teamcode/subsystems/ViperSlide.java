package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ViperSlide extends SubsystemBase {
	private static final double kP = 0.0;
	private static final double kD = 0.0;
	private static final double kF = 0.0;

	private final MotorEx motor;
	private final String motorId;
	private final Telemetry telemetry;

	private Double setpointPosition;
	private final PIDFController pid = new PIDFController(kP, 0.0, kD, kF);

	public ViperSlide(HardwareMap hardwareMap, Telemetry tl, String motorId, boolean reverse) {
		motor = hardwareMap.get(MotorEx.class, motorId);
		telemetry = tl;
		this.motorId = motorId;

		motor.setInverted(reverse);
		motor.resetEncoder();
	}

	@Override
	public void periodic() {
		telemetry.addData("Elevator motor " + motorId + " position", motor.getCurrentPosition());
		if (setpointPosition != null) {
			motor.set(pid.calculate(motor.getCurrentPosition(), setpointPosition));
		}
	}

	public void setPosition(double position) {
		setpointPosition = position;
	}

	public void setSpeed(double speed) {
		setpointPosition = null;
		motor.set(speed);
	}
}
