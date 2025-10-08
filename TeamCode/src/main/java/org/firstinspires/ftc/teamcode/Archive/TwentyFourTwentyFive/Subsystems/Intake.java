package org.firstinspires.ftc.teamcode.Archive.TwentyFourTwentyFive.Subsystems;


import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Intake extends SubsystemBase {
    private final CRServo intakeServo;
    private final SimpleServo fingerServo;
    private boolean fingerClosed = false;
    public Intake(HardwareMap hardwareMap, String intakeID, String fingerID) {
        intakeServo = new CRServo(hardwareMap, intakeID);
        fingerServo = new SimpleServo(hardwareMap, fingerID, 0, 300);
        intakeServo.setInverted(false);
    }

    public void openFinger() {
        fingerServo.turnToAngle(50);
        fingerClosed = false;
    }
    public void closeFinger() {
        fingerServo.turnToAngle(0);
        fingerClosed = true;
    }
    public void toggleFinger() {
        if (fingerClosed) {
            openFinger();
        } else {
            closeFinger();
        }
    }

    public void intake() {
        intakeServo.set(-1);
    }

    public void outtake() {
        intakeServo.set(1);
    }

    public void stop() {
        intakeServo.set(0);
    }

    public int getIntakeMode(){return intakeServo.getCurrentPosition();}
}
