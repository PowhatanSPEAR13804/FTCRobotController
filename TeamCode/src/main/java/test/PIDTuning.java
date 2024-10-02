package test;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.ViperSlide;

@TeleOp
public class PIDTuning extends CommandOpMode {
    @Override
    public void initialize() {
        ViperSlide leftSlide = new ViperSlide(hardwareMap, telemetry, "Hub2_Motor2", true);
        ViperSlide rightSlide = new ViperSlide(hardwareMap, telemetry, "Hub1_Motor3", false);

        register(leftSlide, rightSlide);

        new Trigger(() -> gamepad1.a).whenActive(() -> {
            leftSlide.setPosition(0.0);
            rightSlide.setPosition(0.0);
        });

        new Trigger(() -> gamepad1.b).whenActive(() -> {
            leftSlide.setPosition(2250.0);
            rightSlide.setPosition(2250.0);
        });

        schedule(new RunCommand(() -> {
            telemetry.update();
        }));
    }
}
