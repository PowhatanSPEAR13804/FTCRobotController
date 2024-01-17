package org.firstinspires.ftc.teamcode.helperclasses;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

// Object for controlling the 4 drive motors
// Expects the 4 drive motors connected to omni directional wheels
// See OmniDirectionWheels.jpg for how these work.
// TODO - recreate the JPG in ASCII and paste here.
// TODO - make this a singleton or static member so there can be only one set of drive motor objects.

public class robotMove {
    public DcMotor motorFR = null;
    public DcMotor motorFL = null;
    public DcMotor motorBR = null;
    public DcMotor motorBL = null;
    public double ticksPerInch = 0.0;   // Encoder ticks per inch

    // Constructor - does all the initialization of the motors
    // this function gets called when you make a new object.
    public robotMove(HardwareMap hardwareMap) {
        // Create the motor devices
        // TODO - this is setup to the 2023 robot map
        // Change this mapping to however your robot is setup.

        // test bot motors
        // motorFL = hardwareMap.dcMotor.get("Hub1_Motor3");
        // motorBL = hardwareMap.dcMotor.get("Hub1_Motor0");
        // motorFR = hardwareMap.dcMotor.get("Hub2_Motor0");
        // motorBR = hardwareMap.dcMotor.get("Hub2_Motor3");

        // comp bot motors
        motorFL = hardwareMap.dcMotor.get("Hub1_Motor3");
        motorBL = hardwareMap.dcMotor.get("Hub1_Motor0");
        motorFR = hardwareMap.dcMotor.get("Hub2_Motor0");
        motorBR = hardwareMap.dcMotor.get("Hub2_Motor3");

        // Setup the motors to turn in the correct
        // direction to default to forward motion
        // Always set all 4 just in case their default
        // is NOT FORWARD.
        motorFR.setDirection(DcMotor.Direction.REVERSE);
        motorFL.setDirection(DcMotor.Direction.FORWARD);
        motorBR.setDirection(DcMotor.Direction.REVERSE);
        motorBL.setDirection(DcMotor.Direction.FORWARD);

        // Setup the motors so that they are speed and not
        // power based...  it uses the encoders to control the speed.
        setModeStopAndReset();
        setModeRunUsingEncoder();

        double CompBotDiam = 96.0;
        double TestBotDiam = 100.0;


        // Calculate the encoder ticks per inch
        double wheelDiameterInches = TestBotDiam / 25.4; // https://www.gobilda.com/3606-series-mecanum-wheel-set-bearing-supported-rollers-100mm-diameter/

        double wheelCircumference = wheelDiameterInches * Math.PI;
        double ticksPerRevolution = 384.5;  // From https://www.gobilda.com/5202-series-yellow-jacket-planetary-gear-motor-13-7-1-ratio-435-rpm-3-3-5v-encoder/
        ticksPerInch = ticksPerRevolution / wheelCircumference;

        // Make sure we are stopped
        stop();
    }

    public void setMode(DcMotor.RunMode mode) {
        motorFR.setMode(mode);
        motorFL.setMode(mode);
        motorBR.setMode(mode);
        motorBL.setMode(mode);
    }

    public void setModeRunUsingEncoder() {
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setModeStopAndReset() {
        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void resetEncoders() {
        setModeStopAndReset();
        setModeRunUsingEncoder();
    }

    // Set the power individually on each motor as a group
    // You can call this by itself or use one of the
    // other functions to call it for you.
    public void setMotors(double FRS, double FLS, double BRS, double BLS) {
        // the DcMotor.setPower function takes in a power value from -1.0 to 1.0
        // 0.0 is stop / no motion
        // -1.0 is full reverse
        // 1.0 is full forward
        // Note:  if you configure the motor direction to REVERSE then -1.0 will be the
        // opposite direction that a motor configured for FORWARD
        // This can help make all motors turn their wheels in the direction
        // you want them to and then the speed is the same for all 4 motors.
        // See the constructor for the directions the motors are set to.
        // note:  the order we set the power in does not matter for
        // steering offsets.  the bot always veers off in the same direction.
        motorFR.setPower(FRS);
        motorFL.setPower(FLS);
        motorBR.setPower(BRS);
        motorBL.setPower(BLS);
    }

    // Set the Velocity individually on each motor as a group
    // You can call this by itself or use one of the
    // other functions to call it for you.
    // FRS, FLS, BRS, BLS are in ticks per second
    public void setVelocity(double FRS, double FLS, double BRS, double BLS) {
        // the DcMotorEx.setVelocity function takes in a value from -ticks per second
        // to +ticks per second to set the velocity of the DC motor.
        // 0.0 is stop / no motion
        // -X.0 is full reverse
        // X.0 is full forward
        // Note:  if you configure the motor direction to REVERSE then -X.0 will be the
        // opposite direction that a motor configured for FORWARD
        // This can help make all motors turn their wheels in the direction
        // you want them to and then the speed is the same for all 4 motors.
        // See the constructor for the directions the motors are set to.
        // note:  the order we set the power in does not matter for
        // steering offsets.  the bot always veers off in the same direction.
        ((DcMotorEx)motorFR).setVelocity(FRS);
        ((DcMotorEx)motorFL).setVelocity(FLS);
        ((DcMotorEx)motorBR).setVelocity(BRS);
        ((DcMotorEx)motorBL).setVelocity(BLS);
    }

    public void wait(int milliseconds){
        long startTime = System.currentTimeMillis();
        long stopTime = startTime + milliseconds;
        long currentTime = System.currentTimeMillis();

        while(currentTime < stopTime)
            currentTime = System.currentTimeMillis();
    }

    // Stop the robot
    // TODO -there is a breaking mode and floating mode
    // or something like that.  the breaking action
    // should stop the motors really quickly and
    // the non breaking mode will allow the motors
    // to coast a little.  What do we want?
    public void stop() {
        // Set all 4 motors to 0.0 speed
        setMotors(0.0, 0.0, 0.0, 0.0);
        resetEncoders();
    }

    // Runs the motors using the given speeds for the desired distance
    // Inputs:
    // FRS, FLS, BRS, BLS = speed for each motor 1.0 to -1.0
    // distance = distance in inches to travel
    public void runMotorsForDistance(double FRS, double FLS, double BRS, double BLS, double distance) {
        // Convert the distance from inches to ticks
        distance *= ticksPerInch;

        // Store the starting position of the motor encoder
        // Use the Front Right motor for right now...
        double dStartTicks = motorFR.getCurrentPosition();

        double vMin = 0.15;
        double vMax = 1.0;
        double aMax = 1.0;

        // Calculate the starting velocity using the motion profile
        double dTicks = Math.abs(motorFR.getCurrentPosition() - dStartTicks);
        double dVelMul = triangleMotionProfile(vMin, vMax, distance, dTicks);

        // Multiply the vMax for each motor by the starting velocity (scalar)
        setMotors(FRS * dVelMul, FLS * dVelMul, BRS * dVelMul, BLS * dVelMul);

        // Wait until the encoder says we have traveled the desired distance
        // Eventually the ticks will roll over or something like that.
        // However the autonomous code only needs to run for 30 seconds or so
        // Use the absolute distance traveled from dStartTicks to currentPosition
        //double dTicks = Math.abs(motorFR.getCurrentPosition() - dStartTicks);
        while (dTicks < distance)
        {
            // Calculate the current velocity scalar using the current distance traveled
            dVelMul = triangleMotionProfile(vMin, vMax, distance, dTicks);
            // Multiply the vMax for each motor by the starting velocity (scalar)
            setMotors(FRS * dVelMul, FLS * dVelMul, BRS * dVelMul, BLS * dVelMul);

            dTicks = Math.abs(motorFR.getCurrentPosition() - dStartTicks);
        }

        // Calculate the final velocity scalar
        dVelMul = triangleMotionProfile(vMin, vMax, distance, dTicks);
        // Multiply the vMax for each motor by the starting velocity (scalar)
        setMotors(FRS * dVelMul, FLS * dVelMul, BRS * dVelMul, BLS * dVelMul);
    }

    // Triangle shaped motion profile that gives you velocity depending on the distance traveled
    // vMin and vMax can be normalized values used to scale actual speed inputs to the motors
    double triangleMotionProfile(double vMin, double vMax, double totalDist, double currDist)
    {
        double halfDist = totalDist / 2.0;
        double vDiff = vMax - vMin;
        double vCurr = vMin;
        if (currDist < halfDist)
        {
            vCurr = (currDist / halfDist)  * vDiff + vMin;
        }
        else
        {
            vCurr = (totalDist - currDist) / halfDist * vDiff + vMin;
        }

        return vCurr;
    }

    // TODO - needs to work in encoder ticks and not a relative or normalized 0 to 1
    double trapezoidalMotionProfile(double vMin, double vMax, double aMax, double totalDist, double currDist)
    {
        double maxAccelTime = vMax / aMax;
        double maxAccelDist = 0.5 * aMax * maxAccelTime * maxAccelTime;
        double halfDist = totalDist / 2.0;

        if (halfDist < maxAccelDist)
        {
            // Adjust maxAccelDist because there will be no room for a const velocity period
            maxAccelDist = halfDist;
            // Recalculate maxAccelTime based on the new maxAccelDist
            maxAccelTime = Math.sqrt(maxAccelDist / (0.5 * aMax));
            // Recalculate vMax
            vMax = maxAccelDist / maxAccelTime * 2.0;
        }

        double cruiseDist = totalDist - (maxAccelDist * 2.0);
        double vDiff = vMax - vMin;
        double vCurr = vMin;

        if (currDist < maxAccelDist)
        {
            // We are accelerating
            vCurr = (currDist / maxAccelDist) *  vDiff + vMin;
        }
        else if (currDist < (maxAccelDist + cruiseDist))
        {
            // Ware at constant velocity cruising
            vCurr = vMax;
        }
        else if (currDist < totalDist)
        {
            // We are decelerating
            vCurr = ((totalDist - currDist) / maxAccelDist) * vDiff + vMin;
        }
        else
        {
            // We should not be here
            // Apparently we overshot out mark
            vCurr = vMin;
        }

        return vCurr;
    }

    // Set the robot to move in the forward direction
    // Inputs:
    // speed = -1.0 to 1.0
    // distance = distance in inches to travel before exiting
    public void forward(double speed, double distance) {
        // All 4 motors go in the same direction
        runMotorsForDistance(speed, speed, speed, speed, distance);
    }

    // Set the robot to move in the backward direction
    // Inputs:
    // speed = -1.0 to 1.0
    // distance = distance in inches to travel before exiting
    public void backward(double speed, double distance) {
        // Reverse the direction of the motors
        forward(speed * -1.0, distance);
    }

    // Set the robot to move in the right direction
    public void right(double speed, double distance) {
        // In order to move right we have to set the motors
        // to different directions because we have omni directional wheels.
        // note:  this looks different than the chart
        // FR = reverse
        // FL = forward
        // BR = forward
        // BL = reverse

        runMotorsForDistance(speed * -1.0,speed,speed, speed * -1.0, distance);
    }

    // Set the robot to move in the left direction
    public void left(double speed, double distance) {
        // In order to move left we have to set the motors
        // to different directions because we have omni directional wheels.
        // note:  this looks different than the chart
        // FR = forward
        // FL = reverse
        // BR = reverse
        // BL = forward

        runMotorsForDistance(speed,speed * -1.0,speed * -1.0, speed, distance);
    }

    // Set the robot to turn in the ClockWise direction
    // TODO - figure out how to use the encoders to determine the angle to move to
    public void clockwise(double speed) {
        // Turning is easier to think about with omni direction wheels.
        // To Turn ClockWise (right) we must reverse the right side motors
        setMotors(speed * -1.0, speed, speed * -1.0, speed);
    }

    // Set the robot to turn in the AntiClockWise direction
    // TODO - figure out how to use the encoders to determine the angle to move to
    public void anticlockwise(double speed) {
        // Turning is easier to think about with omni direction wheels.
        // To turn anticlockwise (left) we must reverse the left side motors
        // OR in other words just turn clockwise in reverse
        clockwise(speed * -1.0);
    }
}