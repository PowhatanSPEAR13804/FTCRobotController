
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

@Autonomous(name = "RED FAR", group = "AUTO")
@Configurable // Panels
@SuppressWarnings("FieldCanBeLocal") // Stop Android Studio from bugging about variables being predefined
public class SPEARRedAutoFar extends LinearOpMode {
    // Initialize elapsed timer
    private final ElapsedTime runtime = new ElapsedTime();

    // Initialize poses
    private final Pose startPose = new Pose(144-48, 18, Math.toRadians(90)); // Start Pose of our robot.
    private final Pose transitionPose = new Pose(144-48, 120, Math.toRadians(45)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.
    private final Pose scorePose = new Pose(144-38, 125, Math.toRadians(30)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.
    private final Pose endPose = new Pose(144-48, 120, Math.toRadians(90)); // Highest (First Set) of Artifacts from the Spike Mark.

    // Initialize variables for paths
    private PathChain transition;
    private PathChain score;
    private PathChain end;

    // Other variables
    private Pose currentPose; // Current pose of the robot
    private Follower follower; // Pedro Pathing follower
    private TelemetryManager panelsTelemetry; // Panels telemetry
    private int pathState;

    //subsystems
    private MotorEx flywheel = new MotorEx("Hub2_Motor2").reversed();
    private CRServoEx advancer = new CRServoEx("Hub1_Servo5");

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
        flywheel.setPower(0.55);
        sleep(2000);
        advancer.setPower(-1);
        sleep(500);
        advancer.setPower(0);
        sleep(5000);
        advancer.setPower(-1);
        sleep(500);
        advancer.setPower(0);
        sleep(3000);
        advancer.setPower(-1);
        sleep(500);
        advancer.setPower(0);
        sleep(2000);
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

        transition = follower.pathBuilder() //
                .addPath(new BezierLine(startPose, transitionPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), transitionPose.getHeading())
                .build();

        score = follower.pathBuilder()
                .addPath(new BezierLine(transitionPose, scorePose))
                .setLinearHeadingInterpolation(transitionPose.getHeading(), scorePose.getHeading())
                .build();

        // Move to the ending pose from the scoring pose
        end = follower.pathBuilder()
                .addPath(new BezierLine(scorePose, endPose))
                .setLinearHeadingInterpolation(scorePose.getHeading(), endPose.getHeading())
                .build();
    }
    //below is the state machine or each pattern

    public void updateStateMachine() {
        switch (pathState) {
            case 0:
                if(runtime.time() > 10.5) {
                    // Move to the scoring position from the start position
                    follower.followPath(transition);
                    setPathState(1);
                    // Call the setter method
                }
                break;
            case 1:
                // Wait until we have passed all path constraints
                if (!follower.isBusy()) {
                    // Move to the first artifact pickup location from the scoring position
                    follower.followPath(score);
                    setPathState(2);
                }
            case 2:
                if(!follower.isBusy()) {
                    shootArtifacts();
                    sleep(800);
                    //follower.followPath(end);
                    setPathState(-1);
                }
                break;
        }
    }

    // Setter methods for pathState variables placed at the class level
    void setPathState(int newPathState) {
        this.pathState = newPathState;
    }
}