package org.firstinspires.ftc.teamcode.subsystems;


import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake extends SubsystemBase {
    private final CRServo intakeServo;

    public Intake(HardwareMap hardwareMap, String servoID) {
        intakeServo = new CRServo(hardwareMap, servoID);
        intakeServo.setInverted(false);
    }

    public void rotateIn() {
        intakeServo.set(1.0);
    }
    public void rotateOut() {
        intakeServo.set(-1.0);
    }
}
