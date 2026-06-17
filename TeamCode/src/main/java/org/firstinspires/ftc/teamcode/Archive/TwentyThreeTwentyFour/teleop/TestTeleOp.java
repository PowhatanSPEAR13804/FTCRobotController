package org.firstinspires.ftc.teamcode.Archive.TwentyThreeTwentyFour.teleop;
/*
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
//import org.firstinspires.ftc.teamcode.Libraries.ButtonClick;
//import org.firstinspires.ftc.teamcode.Libraries.ServoMotorDeclarations;
//import org.firstinspires.ftc.teamcode.Libraries.RobotMoveEx;

//controls all of our robot's subsystems

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TestTeleOp", group="TeleOp")
@Disabled
public class TestTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        RobotMoveEx robot = new RobotMoveEx(hardwareMap);

        waitForStart();

        if (isStopRequested()) return;

        double vMax = 20.0;
        double angleMax = 90.0;

        while (opModeIsActive()) {
            double x = -gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y;
            double r = gamepad1.right_stick_x;

            //convert the normalized r value to +- 90 degrees for the move function
            r = (r * angleMax);
            //convert the xy normalized velocity values
            x *= vMax;
            y *= vMax;
            robot.move(y * 5, x * 5, r * 5);

            if (isStopRequested()){
                robot.stop();
                return;
            }

            String a = "";
            a.format("x(%f) y(%f) r(%f)\n", x, y, r);
            telemetry.addLine(a);
            telemetry.update();
        }
    }
}*/