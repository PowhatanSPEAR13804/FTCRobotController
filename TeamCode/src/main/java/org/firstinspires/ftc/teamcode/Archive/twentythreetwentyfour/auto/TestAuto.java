/* Copyright (c) 2019 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.Archive.twentythreetwentyfour.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.Libraries.RobotMove;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;
import org.firstinspires.ftc.vision.VisionPortal;

import java.util.List;

/*
 * This OpMode illustrates the basics of TensorFlow Object Detection,
 * including Java Builder structures for specifying Vision parameters.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list.
 */

@Autonomous(name="TestAuto", group="Autonomous")
public class TestAuto extends LinearOpMode {

    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    // TFOD_MODEL_ASSET points to a model file stored in the project Asset location,
    // this is only used for Android Studio when using models in Assets.
    private static final String TFOD_MODEL_ASSET = "model_20231202_083115.tflite";
    // TFOD_MODEL_FILE points to a model file stored onboard the Robot Controller's storage,
    // this is used when uploading models directly to the RC using the model upload interface.
    private static final String TFOD_MODEL_FILE = "/Internal shared storage/Download/RedObjectIdentification.tflite";
    // Define the labels recognized in the model for TFOD (must be in training order!)
    private static final String[] LABELS = {
       "Red Cube","Blue Cube"
    };

    /**
     * The variable to store our instance of the TensorFlow Object Detection processor.
     */
    private TfodProcessor tfod;

    /**
     * The variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;

    /*
    public TestTensorFlowObjectDetection() {
        initTfod();
    }
     */

    //@Override
    // runOpMode will never ever run using new!
    // linearopmode is a "special" java class
    // you want a regular java class to contain things.
    // This function is not running and that is why = new TestTensorFlowObjectDetection is NULL!
    // Make this into a normal java class like the buttonclick or robotMove class
    // This means they need a constructor which is a function
    // that has the same name as the class.

    public void runOpMode() {

        initTfod();

        Servo intakeLeft =  hardwareMap.servo.get("Hub1_Servo0");
        Servo intakeRight =  hardwareMap.servo.get("Hub2_Servo0");
        DcMotor fourBar = hardwareMap.dcMotor.get("Hub1_Motor2");
        DcMotor viper =  hardwareMap.dcMotor.get("Hub1_Motor1");

        intakeLeft.setDirection((Servo.Direction.REVERSE));
        intakeRight.setDirection((Servo.Direction.FORWARD));

        Servo outputS =  hardwareMap.servo.get("Hub1_Servo5");
        Servo outputL =  hardwareMap.servo.get("Hub1_Servo4");

        RobotMove robot = new RobotMove(hardwareMap);
        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {

                double x = 0;
                double y;
                List<Recognition> currentRecognitions = tfod.getRecognitions();
                telemetry.addLine("\ncurrent recognitions: " + currentRecognitions);
                while(currentRecognitions.size() == 0) {
                    robot.forward(0.1, 0.1);
                    currentRecognitions = tfod.getRecognitions();
                }
                telemetry.addData("x = ", x);
//                while(x == 0){
                    for (Recognition recognition : currentRecognitions) {
                        x = (recognition.getLeft() + recognition.getRight()) / 2 ;
                        y = (recognition.getTop()  + recognition.getBottom()) / 2 ;
                        telemetry.addLine("\nx and y:" + x + " " + y);
                    }
//                }

                robot.forward(0.5, 29.5);
                if(x>550){
                    //right spike
                    robot.right(0.5, 11.5);
                    intakeLeft.setPosition(0);
                    intakeRight.setPosition(0);
                    sleep(1000);
                    intakeLeft.setPosition(0.5);
                    intakeRight.setPosition(0.5);
                    robot.left(0.5, 11.5);

                }
                else if(x<150){
                    //left spike
                    robot.left(0.5, 11.5);
                    intakeLeft.setPosition(0);
                    intakeRight.setPosition(0);
                    sleep(1000);
                    intakeLeft.setPosition(0.5);
                    intakeRight.setPosition(0.5);
                    robot.right(0.5, 11.5);
                }
                else{
                    //center spike
                    intakeLeft.setPosition(0);
                    intakeRight.setPosition(0);
                    sleep(1000);
                    intakeLeft.setPosition(0.5);
                    intakeRight.setPosition(0.5);
                }
                robot.right(0.5, 40);

                //turn robot left 90 degrees
                robot.runMotorsForDistance(0.5, -0.5, 0.5, -0.5, 0.5*Math.PI*6.25);

                fourBar.setPower(0.5);
                sleep(1000);
                fourBar.setPower(0);
                viper.setPower(0.5);
                sleep(250);
                viper.setPower(0);

                if(x>550){
                    //right spike

                    robot.left(0.5, 11.5);
                    outputL.setPosition(0);
                    outputS.setPosition(45);
                    sleep(1000);
                    robot.right(0.5, 11.5);
                }
                else if(x<150){
                    //left spike
                    robot.right(0.5, 11.5);
                    outputL.setPosition(0);
                    outputS.setPosition(45);
                    sleep(1000);
                    robot.left(0.5, 11.5);
                }
                else{
                    outputL.setPosition(0);
                    outputS.setPosition(45);
                }
                robot.left(0.5, 23);

                robot.forward(0.5, 60);
                robot.stop();

                telemetryTfod();


                // Push telemetry to the Driver Station.
                telemetry.update();

                // Save CPU resources; can resume streaming when needed.
                if (gamepad1.dpad_down) {
                    visionPortal.stopStreaming();
                } else if (gamepad1.dpad_up) {
                    visionPortal.resumeStreaming();
                }

                // Share the CPU.
                while (opModeIsActive()) {
                    sleep(10);
                }
            }
        }

        // Save more CPU resources when camera is no longer needed.
        visionPortal.close();

    }   // end runOpMode()
    /**
     * Initialize the TensorFlow Object Detection processor.
     */
    private void initTfod() {

        // Create the TensorFlow processor by using a builder.
        tfod = new TfodProcessor.Builder()

            // With the following lines commented out, the default TfodProcessor Builder
            // will load the default model for the season. To define a custom model to load, 
            // choose one of the following:
            //   Use setModelAssetName() if the custom TF Model is built in as an asset (AS only).
            //   Use setModelFileName() if you have downloaded a custom team model to the Robot Controller.
            .setModelAssetName(TFOD_MODEL_ASSET)
           // .setModelFileName(TFOD_MODEL_FILE)

            // The following default settings are available to un-comment and edit as needed to 
            // set parameters for custom models.
            .setModelLabels(LABELS)
            //.setIsModelTensorFlow2(true)
            //.setIsModelQuantized(true)
            //.setModelInputSize(300)
            //.setModelAspectRatio(16.0 / 9.0)

            .build();

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Set the camera (webcam vs. built-in RC phone camera).
        if (USE_WEBCAM) {
            builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        } else {
            builder.setCamera(BuiltinCameraDirection.BACK);
        }

        // Choose a camera resolution. Not all cameras support all resolutions.
        //builder.setCameraResolution(new Size(640, 480));

        // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
        //builder.enableLiveView(true);

        // Set the stream StandardFormatStyleInstructions; MJPEG uses less bandwidth than default YUY2.
        //builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);

        // Choose whether or not LiveView stops if no processors are enabled.
        // If set "true", monitor shows solid orange screen if no processors enabled.
        // If set "false", monitor shows camera view without annotations.
        //builder.setAutoStopLiveView(false);

        // Set and enable the processor.
        builder.addProcessor(tfod);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

        // Set confidence threshold for TFOD recognitions, at any time.
        //tfod.setMinResultConfidence(0.75f);

        // Disable or re-enable the TFOD processor at any time.
        //visionPortal.setProcessorEnabled(tfod, true);

    }   // end method initTfod()

    /**
     * Add telemetry about TensorFlow Object Detection (TFOD) recognitions.
     */
    private void telemetryTfod() {

        List<Recognition> currentRecognitions = tfod.getRecognitions();
        telemetry.addData("# Objects Detected", currentRecognitions.size());

        // Step through the list of recognitions and display info for each one.
        for (Recognition recognition : currentRecognitions) {
            double x = (recognition.getLeft() + recognition.getRight()) / 2 ;
            double y = (recognition.getTop()  + recognition.getBottom()) / 2 ;

            telemetry.addData(""," ");
            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
            telemetry.addData("- Position", "%.0f / %.0f", x, y);
            telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
        }   // end for() loop

    }   // end method telemetryTfod()
    public double objectPositionX (double x){


        List<Recognition> currentRecognitions = tfod.getRecognitions();
        for (Recognition recognition : currentRecognitions) {
             x = (recognition.getLeft() + recognition.getRight()) / 2 ;

            //telemetry.addData(""," ");
            //telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
            //telemetry.addData("- Position", "%.0f / %.0f", x, y);
            //telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
        }

        return(x);
    }
    public double objectPositionY (double y){


        List<Recognition> currentRecognitions = tfod.getRecognitions();
        for (Recognition recognition : currentRecognitions) {
            y = (recognition.getTop()  + recognition.getBottom()) / 2 ;

            //telemetry.addData(""," ");
            //telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
            //telemetry.addData("- Position", "%.0f / %.0f", x, y);
            //telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
        }

        return(y);
    }

}   // end class
