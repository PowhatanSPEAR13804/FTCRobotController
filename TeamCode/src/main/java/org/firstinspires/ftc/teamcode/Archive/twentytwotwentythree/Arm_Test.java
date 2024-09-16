package org.firstinspires.ftc.teamcode.Archive.TwentyTwoTwentyFour;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
@Disabled
@Autonomous(name="Arm_Test", group="Robot")

public class Arm_Test extends LinearOpMode {
    private DcMotor         frontLeftDrive   = null;
    private DcMotor         frontRightDrive  = null;
    private DcMotor         backLeftDrive   = null;
    private DcMotor         backRightDrive  = null;
    private NormalizedColorSensor leftColor;
    private NormalizedColorSensor rightColor;
    private NormalizedColorSensor groundLeftColor;
    private NormalizedColorSensor groundRightColor;
    private BNO055IMU       IMU = null;
    private BNO055IMU.Parameters imuParameters = null;
    private DcMotor         forearm;
    private DcMotor         rotation;
    private Servo           finger;
    private Servo           wrist;
    private double          openPosition = 0;
    private double          closedPosition = 0;
    //private ColorSensor     sensor;

    private String          parkColor = "";

    private double             red=0;// = rightColor.red();
    private double             blue=0;// = rightColor.blue();
    private double             green=0;// = rightColor.green();
    private double []          colorValues = new double [] {red, green, blue};
    //private int             max = colorValues[0];

    //double correction;
    //Orientation             lastAngles = new Orientation();
    //double                  globalAngle;

    double UpDownSpeed = 0.5;

    private ElapsedTime     runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 384.5 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // No External Gearing.
    static final double     WHEEL_DIAMETER_INCHES   = 3.5 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);

    @Override
    public void runOpMode() {

        IMU = hardwareMap.get(BNO055IMU.class, "imu");

        //sensor = hardwareMap.get()

        frontLeftDrive  = hardwareMap.get(DcMotor.class, "Hub2_motor0");
        frontRightDrive  = hardwareMap.get(DcMotor.class, "Hub1_motor0");
        backLeftDrive  = hardwareMap.get(DcMotor.class, "Hub2_motor2");
        backRightDrive  = hardwareMap.get(DcMotor.class, "Hub1_motor2");

        forearm = hardwareMap.get(DcMotor.class, "Hub2_motor1");
        rotation = hardwareMap.get(DcMotor.class, "Hub1_motor1");
        finger = hardwareMap.get(Servo.class, "Hub1_servo0");
        wrist = hardwareMap.get(Servo.class, "Hub1_servo1");

        openPosition = 0.6;
        closedPosition = 0.435;

        leftColor = hardwareMap.get(NormalizedColorSensor.class, "Hub2_I2C_2");
        rightColor = hardwareMap.get(NormalizedColorSensor.class, "Hub1_I2C_2");
        groundLeftColor = hardwareMap.get(NormalizedColorSensor.class, "Hub2_I2C_1");
        groundRightColor = hardwareMap.get(NormalizedColorSensor.class, "Hub1_I2C_1");

        /*red = rightColor.red;
        green = rightColor.green;
        blue = rightColor.blue;*/

        //frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.REVERSE);
        //backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backRightDrive.setDirection(DcMotor.Direction.REVERSE);

        frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode                = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled      = false;

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        IMU = hardwareMap.get(BNO055IMU.class, "imu");

        IMU.initialize(parameters);

        rightColor.setGain(12);
        NormalizedRGBA colors = rightColor.getNormalizedColors();
        //double correctiveValue=(double)0.02;
        red = colors.red;
        green = colors.green;
        blue = colors.blue;

        waitForStart();

        // Step through each leg of the path,

        //Forward
        //encoderDrive(2, 100, 100, 100, 100, 3);
        //Backward
        //encoderDrive(2, -100, -100, -100, -100, 3);
        //Strafe Left
        //encoderDrive(2, 100, -100, -100, 100, 3);
        //Strafe Right
        //encoderDrive(2, -100, 100, 100, -100, 3);
        //encoderDrive(Speed:FL,FR,BL,BR,Inches:FL,FR,BL,BR,Timeout Seconds,Step);
        //armAndWrist(pickup,dropOff,Arm Height(number of rotations to get there),Arm Speed,Wrist Position("front" or "back"), Timeout Seconds)
        //step 1 (ForwardToCone)
        for(double i = 1; i < 100; i+=1) {
            armAndWrist(true, false, i , 0.75, "front", 2);
            String armheight=Double.toString(i);
            telemetry.addData("armheight = ",armheight);
            telemetry.update();
            sleep(50);
        }
        /*encoderDrive(0.60, 0.60, 0.60, 0.60, -22, 17, 17, -22, 3, 1);
        armAndWrist(true, false, 0.125 , 0.75, "front", 2);
        //sense the color(Sense)
        sense(true);
        //step2(BackToWall)
        encoderDrive(0.60, 0.60, 0.60, 0.60, 20, -15, -15, 20, 3, 2);
        //step 2.5(MoveOverAWittle)
        encoderDrive(0.80, 0.80, 0.80, 0.80, 18, 13, 13, 18, 1, 3);
        //step 2.625(Adjustment)
        //encoderDrive(0.60, -1, 64, 64, -0, 3, 3, false, false, false, 0.2, 0.75, "front");
        //step 2.75(DriveToJunction)
        //encoderDrive(0.60, 0.60, 0.60, 0.60, -64, 58, 58, -64, 3, 3, true, false, 0.2, 0.75, "front");
        double c = 1.06;
        encoderDrive(0.60, 0.60, 0.60, 0.60, -64*c, 58*c, 58*c, -64*c, 3, 4);
        //step 4 (ArmUp)
        armAndWrist(true, false, 1.5, 1, "front", 2);
        //step 3(TowardJunction)
        encoderDrive(0.40, 0.40, -0.40, 0.40, -8.5*c, -8.5, -8.5, -8.5*c, 1.5, 5);
        //step 4(DropCone)
        armAndWrist(false, true, 0, 0, "front", 0.3);
        //arm back up
        armAndWrist(false, true, 1.5, 1, "front", 0.5);
        //4.1927563716552736451726
        encoderDrive(0, 0, 0, 0, 0, 0, 0, 0, 1, 6);
        //wait
        encoderDrive(0, 0, 0, 0, 0, 0, 0, 0, 1, 6);
        //step 4.5 (AwayFromJunction)
        encoderDrive(0.60, 0.60, 0.60, 0.60, 7, 7, 7, 7, 3, 6);
        //arm down
        armAndWrist(true, false, 0, 0, "front", 0.5);
        //step5 (park)
        encoderDrive(0.60, 0.60, 0.60, 0.60, 13*c, -8*c, -8*c, 13*c, 3, 7);
        /*
        for (int i=0; i<1;i++) {
            //step 4
            encoderDrive(0.75, -15, -10, -10, -15, 3, 4, false, false, false, 0, 0, "front");
            //sense the ground
            sense(false);
            //move to the cones
            encoderDrive(0.75, -15, -1, -1, -15, 3, 4, false, false, false, 0, 0, "front");
            //step 5
            encoderDrive(0.75, 19, 14, 14, 19, 3, 5, false, false, false, 0, 0, "back");
            //step 6
            encoderDrive(0.75, -10, 5, 5, -10, 3, 6, false, false, false, 0, 0, "back");
            //step 7
            encoderDrive(0.75, 10, -5, -5, 10, 3, 7, false, false, false, 0, 0, "back");
        }
        */

        switch (parkColor)
        {
            case "red":
                //forward
                encoderDrive(0.60, 0.60, 0.60, 0.60, -44, -46, -44, -46, 3, 8);
                break;
            case "green":
                //backward
                encoderDrive(0.60, 0.60, 0.60, 0.60, -22, -24, -22, -24, 3, 8);
                break;
            //case blue
            //stay still
        }


    }

    public void armAndWrist(boolean pickup,
                            boolean dropOff,
                            double armHeight,
                            double armSpeed,
                            String wristPosition,
                            double timeoutS)
    {
        if (isStopRequested()) return;

        // Ensure that the opmode is still active
        if (opModeIsActive())
        {
            double newArmTarget;

            newArmTarget = (forearm.getCurrentPosition() + (COUNTS_PER_MOTOR_REV * armHeight));

            forearm.setTargetPosition((int)newArmTarget);

            forearm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            forearm.setPower(Math.abs(armSpeed));

            while (opModeIsActive() && (runtime.seconds() < timeoutS))
            {

            }

            //forearm.setPower(0);

            //forearm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //wrist
            if (wristPosition == "front") {
                wrist.setPosition(0.05);
            }

            if (wristPosition == "back") {
                wrist.setPosition(0.8);
            }

            if (pickup == true)
            {
                finger.setPosition(closedPosition);
            }
            else if (dropOff == true)
            {
                finger.setPosition(openPosition);
            }
        }
    }

    public void encoderDrive(double frontLeftSpeed,
                             double frontRightSpeed,
                             double backLeftSpeed,
                             double backRightSpeed,
                             double frontLeftInches,
                             double frontRightInches,
                             double backLeftInches,
                             double backRightInches,
                             double timeoutS,
                             int step) {

        if (isStopRequested()) return;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            //correction = checkDirection();

            int newFrontLeftTarget;
            int newFrontRightTarget;
            int newBackLeftTarget;
            int newBackRightTarget;

            // Determine new target position, and pass to motor controller
            newFrontLeftTarget = frontLeftDrive.getCurrentPosition() + (int)(frontLeftInches * COUNTS_PER_INCH);
            newFrontRightTarget = frontRightDrive.getCurrentPosition() + (int)(frontRightInches * COUNTS_PER_INCH);
            newBackLeftTarget = backLeftDrive.getCurrentPosition() + (int)(backLeftInches * COUNTS_PER_INCH);
            newBackRightTarget = backRightDrive.getCurrentPosition() + (int)(backRightInches * COUNTS_PER_INCH);

            frontLeftDrive.setTargetPosition(newFrontLeftTarget);
            frontRightDrive.setTargetPosition(newFrontRightTarget);
            backLeftDrive.setTargetPosition(newBackLeftTarget);
            backRightDrive.setTargetPosition(newBackRightTarget);

            // Turn On RUN_TO_POSITION
            frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            //    double currentFrontLeftSpeed = 0;
            //    double currentFrontRightSpeed = 0;
            //    double currentBackLeftSpeed = 0;
            //    double currentBackRightSpeed = 0;
            //    while(currentFrontLeftSpeed < frontLeftSpeed &&
            //          currentFrontRightSpeed < frontRightSpeed &&
            //          currentBackLeftSpeed < backLeftSpeed &&
            //          currentBackRightSpeed < backRightSpeed)
            //    {
            frontLeftDrive.setPower(Math.abs(frontLeftSpeed));
            frontRightDrive.setPower(Math.abs(frontRightSpeed));
            backLeftDrive.setPower(Math.abs(backLeftSpeed));
            backRightDrive.setPower(Math.abs(backRightSpeed));

            //currentFrontLeftSpeed += UpDownSpeed;
            //currentFrontRightSpeed += UpDownSpeed;
            //currentBackLeftSpeed += UpDownSpeed;
            //currentBackRightSpeed += UpDownSpeed;
            //    }


            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() && (runtime.seconds() < timeoutS)
                                        /*|| frontLeftDrive.isBusy() || frontRightDrive.isBusy()
                                        || backLeftDrive.isBusy() || backRightDrive.isBusy()*/)
            {

                // Display it for the driver.
                //opModeIsActive() && (runtime.seconds() < timeoutS) &&
                //telemetry.addData("Running to ", newFrontLeftTarget);
                //telemetry.addData("Running to ", newFrontRightTarget);
                //telemetry.addData("Currently at ", frontLeftDrive.getCurrentPosition());
                /*telemetry.addData("P C: and m c", parkColor, " ", max);
                telemetry.addData("step ", step);
                telemetry.addData("R: ", rightColor.red());
                telemetry.addData("G: ", rightColor.green());
                telemetry.addData("B: ", rightColor.blue());
                telemetry.update();*/
            }

            // Stop all motion;
            frontLeftDrive.setPower(0);
            frontRightDrive.setPower(0);
            backLeftDrive.setPower(0);
            backRightDrive.setPower(0);

            //forearm.setPower(0);

            // Turn off RUN_TO_POSITION
            frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //sleep(50);   // optional pause after each move.
        }
    }
    /*
        private double getAngle()
    {
        // We experimentally determined the Z axis is the axis we want to use for heading angle.
        // We have to process the angle because the imu works in euler angles so the Z axis is
        // returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
        // 180 degrees. We detect this transition and track the total cumulative angle of rotation.

        Orientation angles = IMU.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;

        globalAngle += deltaAngle;

        lastAngles = angles;

        return globalAngle;
    }

    private void resetAngle()
    {
        lastAngles = IMU.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        globalAngle = 0;
    }


    private double checkDirection()
    {
        // The gain value determines how sensitive the correction is to direction changes.
        // You will have to experiment with your robot to get small smooth direction changes
        // to stay on a straight line.
        double correction, angle, gain = .10;

        angle = getAngle();

        if (angle == 0)
            correction = 0;             // no adjustment.
        else
            correction = -angle;        // reverse sign of angle for correction.

        correction = correction * gain;

        return correction;
    }

    /**
     * Rotate left or right the number of degrees. Does not support turning more than 180 degrees.
     * @param degrees Degrees to turn, + is left - is right
     */
     /*
        private void rotate(int degrees)
    {
        double  leftPower, rightPower;

        // restart imu movement tracking.
        resetAngle();

        // getAngle() returns + when rotating counter clockwise (left) and - when rotating
        // clockwise (right).

        if (degrees < 0)
        {   // turn right.
            leftPower = 2;
            rightPower = -2;
        }
        else if (degrees > 0)
        {   // turn left.
            leftPower = -2;
            rightPower = 2;
        }
        else return;

        // set power to rotate.
        frontLeftDrive.setPower(leftPower);
        frontRightDrive.setPower(rightPower);

        // rotate until turn is completed.
        if (degrees < 0)
        {
            // On right turn we have to get off zero first.
            while (opModeIsActive() && getAngle() == 0) {}

            while (opModeIsActive() && getAngle() > degrees) {}
        }
        else    // left turn.
            while (opModeIsActive() && getAngle() < degrees) {}

        // turn the motors off.
        frontRightDrive.setPower(0);
        frontLeftDrive.setPower(0);

        // wait for rotation to stop.
        sleep(1000);

        // reset angle tracking on new heading.
        resetAngle();
    }
    */
    public void sense (boolean cone)
    {
        //if color sensor is on the left
            /*if (cone = true) {
                if (leftColor.red() > 0.9 && leftColor.blue() < 0.3 && leftColor.green() < 0.3)
                {
                    parkColor = "red";
                }
                else if (leftColor.green() > 0.9 && leftColor.blue() < 0.3 && leftColor.red() < 0.3)
                {
                    parkColor = "green";
                }
                else if (leftColor.blue() > 0.9 && leftColor.red() < 0.3 && leftColor.green() < 0.3)
                {
                    parkColor = "blue";
                }
            }*/

        //if color sensor is on the right
        if (cone == true) {

            if (red > blue && red > green)
            {
                parkColor = "red";
                telemetry.addData("I see" ," red");
            }
            else if (green > blue && green > red)
            {
                parkColor = "green";
                telemetry.addData("I see" ," green");
            }
            else if (blue > red && blue > green)
            {
                parkColor = "blue";
                telemetry.addData("I see" ," blue");
            }
            else
            {
                telemetry.addData("We ain't see ","nothin");
            }
            telemetry.addData("R; ",red);
            telemetry.addData("G; ",green);
            telemetry.addData("B; ",blue);
            telemetry.update();

            //temporary
            parkColor = "green";

                /*if (rightColor.red() > 0.9 && rightColor.blue() < 0.3 && rightColor.green() < 0.3)
                {
                    parkColor = "red";
                }
                else if (rightColor.green() > 0.9 && rightColor.blue() < 0.3 && rightColor.red() < 0.3)
                {
                    parkColor = "green";
                }
                else if (rightColor.blue() > 0.9 && rightColor.red() < 0.3 && rightColor.green() < 0.3)
                {
                    parkColor = "blue";
                }*/
        }

        if (cone = false)
        {

        }
    }
}
