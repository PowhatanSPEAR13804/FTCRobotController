package org.firstinspires.ftc.teamcode.subsystems;


import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake extends SubsystemBase {
    private final SimpleServo wristServo;
    private final SimpleServo fingerServo;

    public Intake(HardwareMap hardwareMap, String wristID, String fingerID) {
        wristServo = new SimpleServo(hardwareMap, wristID, 0, 270);
        fingerServo = new SimpleServo(hardwareMap, fingerID, 0, 180);

    }

    public void openFinger() { fingerServo.turnToAngle(25); }
    public void closeFinger() { fingerServo.turnToAngle(90); }
    public void straightWrist() { wristServo.turnToAngle(180); }
    public void sidewaysWrist() { wristServo.turnToAngle(270); }
}
