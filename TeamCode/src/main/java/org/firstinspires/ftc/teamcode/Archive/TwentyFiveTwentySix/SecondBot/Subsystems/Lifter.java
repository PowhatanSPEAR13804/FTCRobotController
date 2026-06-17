package org.firstinspires.ftc.teamcode.Archive.TwentyFiveTwentySix.SecondBot.Subsystems;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;

public class Lifter implements Subsystem {
    public static final Lifter INSTANCE = new Lifter();
    private Lifter() { }

    private MotorEx rightMotor = new MotorEx("Hub2_Motor0");
    private MotorEx leftMotor = new MotorEx("Hub2_Motor1");

    private ControlSystem rightControlSystem = ControlSystem.builder()
            .posPid(0.005, 0, 0)
            .elevatorFF(0)
            .build();
    private ControlSystem leftControlSystem = ControlSystem.builder()
            .posPid(0.005, 0, 0)
            .elevatorFF(0)
            .build();

    public Command down = new LambdaCommand()
            .setStart(()-> {
                rightControlSystem.setGoal(rightMotor.getState().minus(new KineticState(10)));
            })
            .requires(this);
    public Command up = new LambdaCommand()
            .setStart(()-> {
                rightControlSystem.setGoal(rightMotor.getState().plus(new KineticState(10)));
            })
            .requires(this);

    @Override
    public void periodic() {
        rightMotor.setPower(rightControlSystem.calculate(rightMotor.getState()));
        leftMotor.setPower(leftControlSystem.calculate(leftMotor.getState()));
    }
}