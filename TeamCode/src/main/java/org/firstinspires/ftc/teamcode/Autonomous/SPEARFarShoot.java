
/*
Hello :D my names mikey and i'm the head of software on team 21721. I was looking at the april tag sample code on the PP (pedro pathing) website and it kinda confused me or just wasn't
what I needed to do, so I decided to make my own! Before you worry about the code itself u need to know a bit about April tags. April tags are basically just QR codes; in the sense
that when you scan them they give u a numerical value. the april tag values for this season are as the following-

Blue Goal: 20
Motif GPP: 21
Motif PGP: 22
Motif : 23
Red Goal: 24

So basically, you lineup your robot in front of the motif april tag. It scans said April Tag and then gives you a value back. You then have three if/then statements where you pretty much
say "if the numeric value is 21, then run the GPP pathbuilder" and so on. Right now, though, the code just has movement. So whenever you get your shooting and intake mechanisms figured out, just add that code in the
designated function and call the function in whichever part of the pathbuilder it is needed. I hope this helps!
*/


package org.firstinspires.ftc.teamcode.Autonomous;

// FTC SDK

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.PedroPathing.Constants;

import dev.nextftc.hardware.impl.CRServoEx;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.impl.ServoEx;

@Autonomous(name = "FAR SHOOT", group = "AUTO")
@Configurable // Panels
@SuppressWarnings("FieldCanBeLocal") // Stop Android Studio from bugging about variables being predefined
public class SPEARFarShoot extends LinearOpMode {
    // Initialize elapsed timer
    private final ElapsedTime runtime = new ElapsedTime();

    // Initialize poses
    private final Pose startPose = new Pose(48, 18, Math.toRadians(90)); // Start Pose of our robot.
    private final Pose endPose = new Pose(48, 24, Math.toRadians(90)); // Highest (First Set) of Artifacts from the Spike Mark.

    // Initialize variables for paths
    private PathChain end;

    // Other variables
    private Pose currentPose; // Current pose of the robot
    private Follower follower; // Pedro Pathing follower
    private TelemetryManager panelsTelemetry; // Panels telemetry
    private int pathState;

    //subsystems
    private final MotorEx flywheel = new MotorEx("Hub2_Motor2").reversed();
    private final CRServoEx advancer = new CRServoEx("Hub1_Servo5");
    private final ServoEx angleAdjuster = new ServoEx("Hub1_Servo4");

    private final MotorEx FL = new MotorEx("Hub1_Motor3").reversed();
    private final MotorEx BL = new MotorEx("Hub1_Motor1").reversed();
    private final MotorEx FR = new MotorEx("Hub1_Motor2");
    private final MotorEx BR = new MotorEx("Hub1_Motor0");



    // Custom logging function to support telemetry and Panels
    private void log(String caption, Object... text) {
        if (text.length == 1) {
            telemetry.addData(caption, text[0]);
            panelsTelemetry.debug(caption + ": " + text[0]);
        } else if (text.length >= 2) {
            StringBuilder message = new StringBuilder();
            for (int i = 0; i < text.length; i++) {
                message.append(text[i]);
                if (i < text.length - 1) message.append(" ");
            }
            telemetry.addData(caption, message.toString());
            panelsTelemetry.debug(caption + ": " + message);
        }
    }

    public void shootArtifacts() {
        angleAdjuster.setPosition(1);
        flywheel.setPower(0.725);
        sleep(6000);
        advancer.setPower(-1);
        sleep(750);
        advancer.setPower(0);
        sleep(5500);
        advancer.setPower(-1);
        sleep(500);
        advancer.setPower(0);
        sleep(5500);
        advancer.setPower(-1);
        sleep(500);
        advancer.setPower(0);
        sleep(5500);
        advancer.setPower(-1);
    }


    @Override
    public void runOpMode() {
        // Initialize Panels telemetry
        panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();

        // Initialize Pedro Pathing follower
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);

        // Log completed initialization to Panels and driver station (custom log function)
        log("Status", "Initialized");
        telemetry.update(); // Update driver station after logging

        // Wait for the game to start (driver presses START)
        waitForStart();
        runtime.reset();

        setPathState(0);
        runtime.reset();

        while (opModeIsActive()) {
            // Update Pedro Pathing and Panels every iteration
            follower.update();
            panelsTelemetry.update();
            currentPose = follower.getPose(); // Update the current pose

            buildPaths();

            updateStateMachine();

            if(runtime.seconds() >= 20) {
                FL.setPower(-0.25);
                BL.setPower(-0.25);
                FR.setPower(0.25);
                BR.setPower(0.25);
            }
            if(runtime.seconds() >= 21) {
                FL.setPower(0);
                BL.setPower(0);
                FR.setPower(0);
                BR.setPower(0);
            }

            // Log to Panels and driver station (custom log function)
            log("Elapsed", runtime.toString());
            log("X", currentPose.getX());
            log("Y", currentPose.getY());
            log("Heading", currentPose.getHeading());
            telemetry.update(); // Update the driver station after logging
        }
    }


    public void buildPaths() {
        // basically just plotting the points for the lines that score
        end = follower.pathBuilder() //
                .addPath(new BezierLine(startPose, endPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), endPose.getHeading())
                .build();
    }
    //below is the state machine or each pattern

    public void updateStateMachine() {
        switch (pathState) {
            case 0:
                // Move to the scoring position from the start position
                shootArtifacts();
                /*FL.setPower(1);
                BL.setPower(1);
                FR.setPower(1);
                BR.setPower(1);*/
                //follower.followPath(end);
                setPathState(1);
                // Call the setter method
                break;
        }
    }

    // Setter methods for pathState variables placed at the class level
    void setPathState(int newPathState) {
        this.pathState = newPathState;
    }
}