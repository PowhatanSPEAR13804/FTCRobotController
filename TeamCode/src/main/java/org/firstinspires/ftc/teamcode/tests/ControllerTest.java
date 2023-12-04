package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.Locale;

//@Disabled
//safety :)

@TeleOp(name="ControllerTest", group="Tests")
public class ControllerTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();

        if (isStopRequested()) return;

        String sDpad;
        String sLeftStick;
        String sRightStick;
        String sFaceButtons;
        String sSB;
        String sBumpers;
        String sTriggers;

        while (opModeIsActive()) {

            telemetry.addLine("Press mode button to swap dpad and left stick directional inputs, and up on a stick is negative.");
            sDpad = String.format(Locale.US, "dpad: up(%d), down(%d), left(%d), right(%d)",
                    gamepad1.dpad_up ? 1 : 0,
                    gamepad1.dpad_down ? 1 : 0,
                    gamepad1.dpad_left ? 1 : 0,
                    gamepad1.dpad_right ? 1 : 0);
            telemetry.addLine("\n"+sDpad);

            sLeftStick = String.format(Locale.US, "left stick: button(%d), xAxis(%.1f), yAxis(%.1f)",
                    gamepad1.left_stick_button ? 1 : 0,
                    gamepad1.left_stick_x,
                    gamepad1.left_stick_y);
            telemetry.addLine("\n"+sLeftStick);

            sRightStick = String.format(Locale.US, "right stick: button(%d), xAxis(%.1f), yAxis(%.1f)",
                    gamepad1.right_stick_button ? 1 : 0,
                    gamepad1.right_stick_x,
                    gamepad1.right_stick_y);
            telemetry.addLine("\n"+sRightStick);

            sFaceButtons = String.format(Locale.US, "face buttons: a(%d), b(%d), x(%d), y(%d)",
                    gamepad1.a ? 1 : 0,
                    gamepad1.b ? 1 : 0,
                    gamepad1.x ? 1 : 0,
                    gamepad1.y ? 1 : 0);
            telemetry.addLine("\n"+sFaceButtons);

            sSB = String.format(Locale.US, "start(%d), back(%d)",
                    gamepad1.start ? 1 : 0,
                    gamepad1.back ? 1 : 0);
            telemetry.addLine("\n"+sSB);

            sBumpers = String.format(Locale.US, "bumpers: lb(%d), rb(%d)",
                    gamepad1.left_bumper ? 1 : 0,
                    gamepad1.right_bumper ? 1 : 0);
            telemetry.addLine("\n"+sBumpers);

            sTriggers = String.format(Locale.US, "triggers: lt(%.1f), rt(%.1f)",
                    gamepad1.left_trigger,
                    gamepad1.right_trigger);
            telemetry.addLine("\n"+sTriggers);
            telemetry.update();
        }
    }
}
