package org.firstinspires.ftc.teamcode.tests;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;
import java.util.concurrent.TimeUnit;
@TeleOp(name="CameraTest", group="Tests")
public class CameraTest extends LinearOpMode {
    private static final boolean USE_WEBCAM = true;  // Set true to use a webcam, or false for a phone camera
    private static final int DESIRED_TAG_ID = 1;     // Choose the tag you want to approach or set to -1 for ANY tag.
    private VisionPortal visionPortal;               // Used to manage the video source.
    private AprilTagProcessor aprilTag;              // Used for managing the AprilTag detection process.
    private AprilTagDetection desiredTag = null;     // Used to hold the data for a detected AprilTag
private   List<AprilTagDetection> currentDetections;
    @Override public void runOpMode() {
        initAprilTag();

        boolean targetFound = false;    // Set to true when an AprilTag target is detected
        if (USE_WEBCAM)
            setManualExposure(6, 250);  // Use low exposure time to reduce motion blur
        telemetry.addData("Camera preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()){

            targetFound = false;
            desiredTag  = null;

            //if we detect our obecject in a certain color and in a certain position, set DESIRED_TAG_ID equal to the apporiate tag



            // Step through the list of detected tags and look for a matching tag
         if(aprilTag!=null){

         }


            currentDetections = aprilTag.getDetections();
            for (AprilTagDetection detection : currentDetections) {
                if ((detection.metadata != null) &&
                        ((DESIRED_TAG_ID < 0) || (detection.id == DESIRED_TAG_ID))  ){
                    targetFound = true;
                    desiredTag = detection;
                    telemetry.addLine("\nHi");
                    telemetry.addLine("\nx = " +  detection.rawPose.x);
                    telemetry.addLine("\ny = " +  detection.rawPose.y);
                    telemetry.addLine("\nz = " +  detection.rawPose.z);

                    break;  // don't look any further.
                } else {
                  //  telemetry.addData("Unknown Target", "Tag ID %d is not in TagLibrary\n", detection.id);
                }
            }
            // Tell the driver what we see, and what to do.
            if (targetFound) {
             /*   telemetry.addData(">","HOLD Left-Bumper to Drive to Target\n");
                telemetry.addData("Target", "ID %d (%s)", desiredTag.id, desiredTag.metadata.name);
                telemetry.addData("Range",  "%5.1f inches", desiredTag.ftcPose.range);
                telemetry.addData("Bearing","%3.0f degrees", desiredTag.ftcPose.bearing);
                telemetry.addData("Yaw","%3.0f degrees", desiredTag.ftcPose.yaw);

              */
            } else {
               // telemetry.addData(">","Drive using joysticks to find valid target\n");
            }
            telemetry.addLine("\nBye");
            telemetry.update();


            sleep(10);
        }

    }
    private void initAprilTag() {
        // Create the AprilTag processor by using a builder.
        aprilTag = new AprilTagProcessor.Builder().build();

        // Create the vision portal by using a builder.
        if (USE_WEBCAM) {


            visionPortal = new VisionPortal.Builder()
                    .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                    .setCamera(hardwareMap.get(WebcamName.class, "Webcam 2"))
                    .addProcessor(aprilTag)
                    .build();


        } else {
            visionPortal = new VisionPortal.Builder()
                    .setCamera(BuiltinCameraDirection.BACK)
                    .addProcessor(aprilTag)
                    .build();
        }
    }
    private void    setManualExposure(int exposureMS, int gain) {
        // Wait for the camera to be open, then use the controls

        if (visionPortal == null) {
            return;
        }

        // Make sure camera is streaming before we try to set the exposure controls
        if (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {
            telemetry.addData("Camera", "Waiting");
            telemetry.update();
            while (!isStopRequested() && (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING)) {
                sleep(20);
            }
            telemetry.addData("Camera", "Ready");
            telemetry.update();
        }

        // Set camera controls unless we are stopping.
        if (!isStopRequested())
        {
            ExposureControl exposureControl = visionPortal.getCameraControl(ExposureControl.class);
            if (exposureControl.getMode() != ExposureControl.Mode.Manual) {
                exposureControl.setMode(ExposureControl.Mode.Manual);
                sleep(50);
            }
            exposureControl.setExposure((long)exposureMS, TimeUnit.MILLISECONDS);
            sleep(20);
            GainControl gainControl = visionPortal.getCameraControl(GainControl.class);
            gainControl.setGain(gain);
            sleep(20);
        }
    }

}
