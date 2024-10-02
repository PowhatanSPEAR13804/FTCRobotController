package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ViperSlide extends SubsystemBase {
	private static final double kP = 0.02;
	private static final double kD = 0.0001;

	private final Motor motor;
	private final String motorId;
	private final Telemetry telemetry;

	private Double setpointPosition;
	private final PIDController pid = new PIDController(kP, 0.0, kD);

	public ViperSlide(HardwareMap hardwareMap, Telemetry tl, String motorId, boolean reverse) {
		motor = new Motor(hardwareMap, motorId);
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

		if (getPosition() > 4260) {
			setpointPosition = 4260d;
		}

		if (getPosition() < 0) {
			setpointPosition = 0d;
		}
	}

	public void setPosition(double position) {
		if (position > 4260) {
			setpointPosition = 4260d;
		} else if (position < 0) {
			setpointPosition = 0d;
		} else {
			setpointPosition = position;
		}
	}

	public double getPosition() {
		return motor.getCurrentPosition();
	}

	public void setSpeed(double speed) {
		setpointPosition = null;
		motor.set(speed);
	}
}
