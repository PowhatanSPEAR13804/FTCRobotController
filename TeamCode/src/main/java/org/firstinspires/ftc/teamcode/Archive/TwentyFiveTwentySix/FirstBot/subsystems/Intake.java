package org.firstinspires.ftc.teamcode.Archive.TwentyFiveTwentySix.FirstBot.subsystems;
/*
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;*/
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.HardwareMap;
@Disabled

public class Intake /*extends SubsystemBase*/{/*
    private Motor intake;
    private boolean in;
    private boolean out;

    public Intake(HardwareMap hardwareMap, String intakeID, boolean reverse) {
        intake = new Motor(hardwareMap, intakeID);

        intake.setInverted(reverse);

        intake.setRunMode(Motor.RunMode.RawPower);
    }

    public void toggle(String inOrOut) {
        if((inOrOut.equals("in") && in) || (inOrOut.equals("out") && out)) {
            intake.set(0);
            in = false;
            out = false;
        }
        else if(inOrOut.equals("out")) {
            intake.set(-1);
            in = false;
            out = true;
        }
        else if(inOrOut.equals("in")) {
            intake.set(1);
            in = true;
            out = false;
        }
    }*/
}
