package org.firstinspires.ftc.teamcode.Archive.TwentyFiveTwentySix.SecondBot.Subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;

public class Flywheel implements Subsystem {
    public static final Flywheel INSTANCE = new Flywheel();
    private MotorEx shooter = new MotorEx("Hub2_Motor2").reversed();
    private boolean shooting;
    private double power;
    private double goal;
    private double lastError = 0.0;
    private double derivativeError = 0.0;
    private double integralError = 0.0;
    private int loopIndex = 0;
    private int lastLoopIndex = 0;

    private final double[] controller = {1, 0.0, 0.0};


    public Command toggle = new LambdaCommand()
            .setStart(() -> {
                if (!shooting) {
                    shooting = true;
                    goal = 1000.0;
                } else {
                    shooting = false;
                    goal = 0.0;
                }
            })
            .requires(this);

    public Command off = new LambdaCommand()
            .setStart(() -> {
                shooting = false;
                goal = 0.0;
            })
            .requires(this);

    public double calculateError(double currentGoal) {
        double velocity = -shooter.getVelocity();
        return (goal-velocity)/100;
    }

    public void setGoal(double newGoal) {
        goal = newGoal;
    }

    @Override
    public void periodic() {
        double error = calculateError(goal);
        double kp = controller[0];
        double ki = controller[1];
        double kd = controller[2];
        if(error > 0) {
            int change = loopIndex - lastLoopIndex;
            derivativeError = (error - lastError)/change;
            integralError += (error * change);

            power = (kp * error) + (ki * integralError) + (kd * derivativeError);
            lastError = error;
        }
        lastLoopIndex = loopIndex;
        loopIndex += 1;

        shooter.setPower(power);
    }

    //debuggin stuff
    public double getError() {
        return calculateError(goal);
    }

    public double getSpeed() {
        return -shooter.getVelocity();
    }

    public double getGoal() {
        return goal;
    }

    public double getPower() {
        return power;
    }
}