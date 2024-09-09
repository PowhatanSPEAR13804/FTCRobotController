package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.arcrobotics.ftclib.kinematics.HolonomicOdometry;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drivetrain extends SubsystemBase {
	private static final double TRACKWIDTH = 0.0;
	private static final double CENTER_WHEEL_OFFSET = 0.0;
	private static final double TICKS_TO_INCHES = 0.0;

	private final MotorEx leftEncoder;
	private final MotorEx rightEncoder;
	private final MotorEx perpendicularEncoder;

	private final HolonomicOdometry odometry;

	private final MecanumDrive m_drive;

	public Drivetrain(HardwareMap hardwareMap) {
		Motor frontLeft = hardwareMap.get(Motor.class, "");
		Motor frontRight = hardwareMap.get(Motor.class, "");
		Motor backLeft = hardwareMap.get(Motor.class, "");
		Motor backRight = hardwareMap.get(Motor.class, "");

		rightEncoder = hardwareMap.get(MotorEx.class, "");
		leftEncoder = hardwareMap.get(MotorEx.class, "");
		perpendicularEncoder = hardwareMap.get(MotorEx.class, "");

		leftEncoder.setDistancePerPulse(TICKS_TO_INCHES);
		rightEncoder.setDistancePerPulse(TICKS_TO_INCHES);
		perpendicularEncoder.setDistancePerPulse(TICKS_TO_INCHES);

		odometry = new HolonomicOdometry(
				  leftEncoder::getDistance,
				  rightEncoder::getDistance,
				  perpendicularEncoder::getDistance,
				  TRACKWIDTH, CENTER_WHEEL_OFFSET
		);

		odometry.updatePose(new Pose2d());

		m_drive = new MecanumDrive(frontLeft, frontRight, backLeft, backRight);
	}

	public void drive(float forward, float strafe, float theta) {
		m_drive.driveRobotCentric(forward, strafe, theta);
	}

	@Override
	public void periodic() {
		odometry.updatePose();
	}

	public Pose2d getPosition() {
		return odometry.getPose();
	}

	public com.acmerobotics.roadrunner.geometry.Pose2d getPositionRoadRunner() {
		Pose2d pose = odometry.getPose();
		return new com.acmerobotics.roadrunner.geometry.Pose2d(pose.getX(), pose.getY(), pose.getHeading());
	}
}
