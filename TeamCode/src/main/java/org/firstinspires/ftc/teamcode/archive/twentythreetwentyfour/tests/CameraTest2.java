package org.firstinspires.ftc.teamcode.Archive.twentythreetwentyfour.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.SwitchableCamera;
import org.openftc.easyopencv.OpenCvSwitchableWebcam;


import java.util.List;
import java.util.concurrent.TimeUnit;
@Disabled
@TeleOp(name="CameraTest2", group="Tests")
public class CameraTest2 extends LinearOpMode {

    private static final boolean USE_WEBCAM = true;  // Set true to use a webcam, or false for a phone camera
    private static final int DESIRED_TAG_ID = 1;     // Choose the tag you want to approach or set to -1 for ANY tag.
    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTag;
   private boolean CameraChange =false;
    private AprilTagDetection desiredTag = null;     // Used to hold the data for a detected AprilTag
private   List<AprilTagDetection> currentDetections;
    @Override public void runOpMode() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        OpenCvSwitchableWebcam switchableWebcam;
        WebcamName webcam1;
        WebcamName webcam2;
        webcam1 = hardwareMap.get(WebcamName.class, "Webcam 1");
        webcam2 = hardwareMap.get(WebcamName.class, "Webcam 2");
        webcam1.isSwitchable();
        webcam2.isSwitchable();
        switchableWebcam = OpenCvCameraFactory.getInstance().createSwitchableWebcam(cameraMonitorViewId, webcam1, webcam2);


        initAprilTag();

        boolean targetFound = false;    // Set to true when an AprilTag target is detected
        if (USE_WEBCAM)
            setManualExposure(6, 250);  // Use low exposure time to reduce motion blur
        telemetry.addData("Camera preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()){

            if(CameraChange){


                switchableWebcam.setActiveCamera(webcam1);


                CameraChange = false;
            }
            else{

                switchableWebcam.setActiveCamera(webcam2);

                CameraChange = true;
            }



            targetFound = false;
            desiredTag  = null;

            //if we detect our obeject in a certain color and in a certain position, set DESIRED_TAG_ID equal to the apporiate tag



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
                    .addProcessor(aprilTag)
                    .build();
        } else {
            visionPortal = new VisionPortal.Builder()
                    .setCamera(BuiltinCameraDirection.BACK)
                    .addProcessor(aprilTag)
                    .build();
        }
    }

    /*
     Manually set the camera gain and exposure.
     This can only be called AFTER calling initAprilTag(), and only works for Webcams;
    */
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
    }/*
    private void MakeCamOne() {
        VisionPortal.Builder CamPort;
        CamPort = new VisionPortal.Builder();

        CamPort.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        CamPort.setCameraResolution();

        CamPort.setStreamFormat(VisionPortal.StreamFormat.MJPEG)

        CamPort.addProcessor(aprilTag);
        visionPortal =

        Camport.setCameraMonitorViewId





    }
    */

}
