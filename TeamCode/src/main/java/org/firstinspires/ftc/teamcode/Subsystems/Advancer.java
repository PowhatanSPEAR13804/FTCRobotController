package org.firstinspires.ftc.teamcode.Subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.CRServoEx;
import dev.nextftc.hardware.powerable.SetPower;

public class Advancer implements Subsystem {
    public static final Advancer INSTANCE = new Advancer();
    private CRServoEx advancer = new CRServoEx("Hub1_Servo5");

    public Command on = new SetPower(advancer, 1).requires(this);
    public Command off = new SetPower(advancer, 0).requires(this);
    public Command out = new SetPower(advancer, -1).requires(this);

    public SequentialGroup next = new SequentialGroup(
            on.thenWait(0.5),
            off
    );
}