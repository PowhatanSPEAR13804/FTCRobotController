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

package org.firstinspires.ftc.teamcode.Archive.twentythreetwentyfour.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.Helperclasses.robotMove;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

/*
 * This OpMode illustrates the basics of TensorFlow Object Detection,
 * including Java Builder structures for specifying Vision parameters.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list.
 */

@Autonomous(name="TestBlueAuto", group="Test")
public class
TestBlueAuto extends LinearOpMode {

    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    // TFOD_MODEL_ASSET points to a model file stored in the project Asset location,
    // this is only used for Android Studio when using models in Assets.
    private static final String TFOD_MODEL_ASSET = "model_20231202_083115.tflite";
     // Defines the labels recognized in the model for TFOD (must be in training order!)
    private static final String[] LABELS = {
      "Blue Cube"
    };

    /**
     * The variable to store our instance of the TensorFlow Object Detection processor.
     */

    //the variable that stores the image reconiection program
    private TfodProcessor tfod;

   // private TfodProcessor tfod2;

   // private AprilTagProcessor aprilTag;

   // int Portal_1_View_ID;
  //  int Portal_2_View_ID;
    //int Portal_3_View_ID;




    /**
     * The variable to store our instance of the vision portal.
     */
    //the screen that displays the video
    private VisionPortal visionPortal;
    //private VisionPortal visionPortal2;

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
    @Override
    public void runOpMode() {

      Servo intakeLeft =  hardwareMap.servo.get("Hub1_Servo0");
         Servo intakeRight =  hardwareMap.servo.get("Hub2_Servo0");
        DcMotor fourBar = hardwareMap.dcMotor.get("Hub1_Motor2");
        DcMotor viper =  hardwareMap.dcMotor.get("Hub1_Motor1");



        Servo outputS =  hardwareMap.servo.get("Hub1_Servo5");

        robotMove  robot =  new robotMove(hardwareMap);



        intakeLeft.setDirection((Servo.Direction.REVERSE));
        intakeRight.setDirection((Servo.Direction.FORWARD));

        initTfod();




        waitForStart();

       // double distanceMove = 21.5;

        if (opModeIsActive()) {
            while (opModeIsActive()) {





              //  robot.forward(0.5,24);
                //robot.stop();

                double x = 0;
                double y;
                //Checks the list of recognitions and moves the robot forward slightly until it detects an object
                //or it has moved forward enough to rule out the other spots
                List<Recognition> currentRecognitions = tfod.getRecognitions();
                telemetry.addLine("\ncurrent recognitions: " + currentRecognitions);
                while(currentRecognitions.size() == 0) {
                    if(isStopRequested()) return;
                    //distanceMove-=0.1;
                  //  robot.forward(0.1, 0.1);
                    currentRecognitions = tfod.getRecognitions();
                    x=-1;


                }

                //Gets the central "x" corodinate of the object
                telemetry.addData("x = ", x);

                for (Recognition recognition : currentRecognitions) {
                    x = (recognition.getLeft() + recognition.getRight()) / 2 ;
                    y = (recognition.getTop()  + recognition.getBottom()) / 2 ;
                    telemetry.addLine("\nx and y:" + x + " " + y);
                    telemetry.update();
                }

                telemetry.addLine("\nnew code");
                //robot.backward(0.5,28);
                robot.forward(0.5,24);
                robot.stop();
                telemetry.addLine("Distanced Moved = "+robot.getOdomDistance());
                telemetry.update();


                if(x>200&&x<400){
                    robot.stop();


                    //center spike
                    telemetry.addLine("\ncenter spike");
                    telemetry.update();

                    intakeLeft.setPosition(0);
                    intakeRight.setPosition(0);
                    sleep(1000);
                    intakeLeft.setPosition(0.5);
                    intakeRight.setPosition(0.5);                    //robot.forward(0.5,5);
                    robot.backward(0.5,5);
                    robot.stop();
                }
                else if(x<=200){ //if the object is on the left side of the screen the robot moves to the left spike

                    robot.stop();

                    //left spike

                    //turns robot left 90 degrees


                    //robot.runMotorsForDistance(-0.5, 0.5, -0.5, 0.5, 11.314*Math.PI/2.0);
                    robot.runMotorsForDistance(0.5, -0.5, 0.5, -0.5, 8);
                    robot.stop();
                    //robot.backward(0.5,2);
                    robot.forward(0.5,1);
                   robot.stop();

                    telemetry.addLine("\nleft spike");
                    telemetry.update();
                    //robot.left(0.5, 11.5);
                    intakeLeft.setPosition(0);
                    intakeRight.setPosition(0);
                    sleep(1000);
                    intakeLeft.setPosition(0.5);
                    intakeRight.setPosition(0.5);                  //  robot.backward(0.5,5);
                    //robot.right(0.5, 11.5);
                    //turns robot right 90 degrees
                    // robot.runMotorsForDistance(-0.5, 0.5, -0.5, 0.5, 0.5*Math.PI*6.25);
                    //robot.forward(0.5,2);
                   robot.backward(0.5,2.5);

                    robot.stop();
                    //robot.runMotorsForDistance(0.5, -0.5, 0.5, -0.5, 11.314*Math.PI/2.0);
                     robot.runMotorsForDistance(-0.5, 0.5, -0.5, 0.5, 8);
                    robot.stop();

                }
                else if(x>=400){ //if the object is not found it is assumed to be on the right spike, see line 144
                    robot.stop();

                    //robot.runMotorsForDistance(0.5, -0.5, 0.5, -0.5, 11.314*Math.PI/2.0);
                    robot.runMotorsForDistance(-0.5, 0.5, -0.5, 0.5, 8);
                    telemetry.addLine("Distanced Moved = "+robot.getOdomDistance());
                    telemetry.update();
                    robot.stop();


                    //right spike
                    telemetry.addLine("\nright spike");
                    telemetry.update();
                    //robot.backward(0.5,1);
                    robot.forward(0.5,0.5);
                    robot.stop();
                    intakeLeft.setPosition(0);
                    intakeRight.setPosition(0);
                    sleep(1000);
                    intakeLeft.setPosition(0.5);
                    intakeRight.setPosition(0.5);
                    //robot.forward(0.5,1);
                    robot.stop();
                    robot.backward(0.5,2.5);

                    robot.stop();
                    //robot.runMotorsForDistance(-0.5, 0.5, -0.5, 0.5, 11.314*Math.PI/2.0);
                    robot.runMotorsForDistance(0.5, -0.5, 0.5, -0.5,8 );
                    telemetry.addLine("Distanced Moved = "+robot.getOdomDistance());
                    telemetry.update();
                    robot.stop();
                }
                //robot.forward(0.5,20);
                 robot.backward(0.5,10);
                robot.stop();
                robot.left(0.5,37);/*
                //robot.left(0.5,40);
                robot.stop();
                robot.forward(0.5,27);
               // robot.runMotorsForDistance(-0.5, 0.5, -0.5, 0.5, 12*Math.PI/2.0);
                robot.stop();
                robot.left(0.5,20);
                robot.stop();/*

  fourBar.setPower(1);
    sleep(1000);
    fourBar.setPower(0);
    viper.setPower(-1);
    sleep(950);
    viper.setPower(0);
    fourBar.setPower(1);
    sleep(1000);
    fourBar.setPower(0);
    //  robot.backward(0.5,2);
    // robot.stop();
    outputS.setPosition(0.8);
    sleep(1000);
    outputS.setPosition(0.65);
    robot.forward(0.5,4);
    robot.stop();
    viper.setPower(1);
    sleep(2000);
    viper.setPower(0);
    fourBar.setPower(-1);
    sleep(1000);
    fourBar.setPower(0);
    viper.setPower(1);
    sleep(2000);
    viper.setPower(0);
    fourBar.setPower(-1);
    sleep(1000);
    fourBar.setPower(0);

                //makes the robot strafe right

                //turns robot left 90 degrees
               // robot.runMotorsForDistance(0.5, -0.5, 0.5, -0.5, 0.5*Math.PI*6.25);

               /*
               //puts the the output mechanism up and into position
               fourBar.setPower(0.5);
                sleep(1000);
                fourBar.setPower(0);
                viper.setPower(0.5);
                sleep(250);
                viper.setPower(0);

                //the next three ifs check to see where on the screen the object was then moves to the correct apriltag and then outputs the pixel
                if(x>550){
                    //center spike
                    outputL.setPosition(0);
                    outputS.setPosition(45);

                }
                else if(x>=0){


                    //left spike
                    robot.right(0.5, 11.5);
                    outputL.setPosition(0);
                    outputS.setPosition(45);
                    sleep(1000);
                    robot.left(0.5, 11.5);
                }
                else{
                    //right spike

                    robot.left(0.5, 11.5);
                    outputL.setPosition(0);
                    outputS.setPosition(45);
                    sleep(1000);
                    robot.right(0.5, 11.5);
                }

                */
                //parks the robot
               // robot.left(0.5, 23);

              //  robot.backward(0.5, 20);
              //  robot.stop();

              //  telemetryTfod();


                // Push telemetry to the Driver Station.
                telemetry.update();

               /* // Save CPU resources; can resume streaming when needed.
                if (gamepad1.dpad_down) {
                    visionPortal.stopStreaming();
                } else if (gamepad1.dpad_up) {
                    visionPortal.resumeStreaming();
                }

                */

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
       // builder.setCameraResolution(new Size(800, 600));

        // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
        //builder.enableLiveView(true);

        // Set the stream format; MJPEG uses less bandwidth than default YUY2.
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

        // Create the TensorFlow processor by using a builder.

        /*
        tfod2 = new TfodProcessor.Builder()
                .setModelAssetName(TFOD_MODEL_ASSET)
                .setModelLabels(LABELS)
                .build();

        VisionPortal.Builder builder2 = new VisionPortal.Builder();

        // Set the camera (webcam vs. built-in RC phone camera).
        if (USE_WEBCAM) {
            builder2.setCamera(hardwareMap.get(WebcamName.class, "Webcam 3"));
            telemetry.addLine("Camera 2 Built");
        } else {
            builder2.setCamera(BuiltinCameraDirection.BACK);
        }
        builder2.setLiveViewContainerId(Portal_2_View_ID);

        builder2.addProcessor(tfod2);

        // Build the Vision Portal, using the above settings.
        visionPortal2 = builder2.build();

         */

        /*

        aprilTag = new AprilTagProcessor.Builder().build();



        VisionPortal.Builder builder3 = new VisionPortal.Builder();

        // Set the camera (webcam vs. built-in RC phone camera).
        if (USE_WEBCAM) {
            builder3.setCamera(hardwareMap.get(WebcamName.class, "Webcam 3"));
            telemetry.addLine("Camera 3 Built");
        } else {
            builder3.setCamera(BuiltinCameraDirection.BACK);
        }

        builder3.addProcessor(aprilTag);
        builder3.setLiveViewContainerId(Portal_3_View_ID);

        // Build the Vision Portal, using the above settings.
        visionPortalAprilTag = builder3.build();

         */

    }   // end method initTfod()

    /**
     * Add telemetry about TensorFlow Object Detection (TFOD) recognitions.
     */
    /*
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

     */
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
/*
    public double objectPositionX2 (double x){


        List<Recognition> currentRecognitions = tfod2.getRecognitions();
        for (Recognition recognition : currentRecognitions) {
            x = (recognition.getLeft() + recognition.getRight()) / 2 ;
        }

        return(x);
    }
    public double objectPositionY2 (double y){


        List<Recognition> currentRecognitions = tfod2.getRecognitions();
        for (Recognition recognition : currentRecognitions) {
            y = (recognition.getTop()  + recognition.getBottom()) / 2 ;
        }
        return(y);
    }

    private void initMultiPortals() {
        List myPortalsList;

        myPortalsList = JavaUtil.makeIntegerList(VisionPortal.makeMultiPortalView(2, VisionPortal.MultiPortalLayout.HORIZONTAL));
        Portal_1_View_ID = ((Integer) JavaUtil.inListGet(myPortalsList, JavaUtil.AtMode.FROM_START, 0, false)).intValue();
        Portal_2_View_ID = ((Integer) JavaUtil.inListGet(myPortalsList, JavaUtil.AtMode.FROM_START, 1, false)).intValue();
       // Portal_3_View_ID = ((Integer) JavaUtil.inListGet(myPortalsList, JavaUtil.AtMode.FROM_START, 2, false)).intValue();
        telemetry.addData("Portal 1 View ID (index 0 of myPortalsList)", Portal_1_View_ID);
        telemetry.addData("Portal 2 View ID (index 1 of myPortalsList)", Portal_2_View_ID);
        //telemetry.addData("Portal 3 View ID (index 1 of myPortalsList)", Portal_3_View_ID);
        telemetry.addLine("");
        telemetry.addLine("Press Y to continue");
        telemetry.update();
       while (!gamepad1.y && opModeInInit()) {
            // Loop until gamepad Y button is pressed.
        }


    }

 */



}   // end class
