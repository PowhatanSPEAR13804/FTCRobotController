package org.firstinspires.ftc.teamcode.Subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.subsystems.SubsystemGroup;

public class Shooter extends SubsystemGroup {
    public static final Shooter INSTANCE = new Shooter();

    private Shooter() {
        super(
                Flywheel.INSTANCE,
                Advancer.INSTANCE
        );
    }

    public SequentialGroup shoot = new SequentialGroup(
            Flywheel.INSTANCE.toggle.thenWait(0.67),
            Advancer.INSTANCE.on
    );

    public Command off = new ParallelGroup(
            Flywheel.INSTANCE.off,
            Advancer.INSTANCE.off
    );
}