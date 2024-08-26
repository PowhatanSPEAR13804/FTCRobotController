package org.firstinspires.ftc.teamcode.Helperclasses;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

//object for controlling the 4 drive motors
//expects the 4 drive motors connected to omni directional wheels
//see OmniDirectionWheels.jpg for how these work.
//TODO - recreate the JPG in ASCII and paste here.
//TODO - make this a singleton or static member so there can be only one set of drive motor objects.

public class robotMove {
    public DcMotor motorFR = null;
    public DcMotor motorFL = null;
    public DcMotor motorBR = null;
    public DcMotor motorBL = null;

    public  DcMotor motorX =null ;
    public  DcMotor motorY = null;

    public double ticksPerInch = 0.0;   // Encoder ticks per inch

    public  double ODOM_INCHES_PER_COUNT   = (2*24/25.4*Math.PI)/2000;   //  GoBilda Odometry Pod (1/226.8)


    //initialization
    public robotMove(HardwareMap hardwareMap) {
        motorFL = hardwareMap.dcMotor.get("Hub1_Motor3");
        motorBL = hardwareMap.dcMotor.get("Hub1_Motor0");
        motorFR = hardwareMap.dcMotor.get("Hub2_Motor0");
        motorBR = hardwareMap.dcMotor.get("Hub2_Motor3");

        motorY = hardwareMap.dcMotor.get("Hub2_Motor2");
        motorX = hardwareMap.dcMotor.get("Hub2_Motor1");

        //set motor direction
        motorFR.setDirection(DcMotor.Direction.REVERSE);
        motorFL.setDirection(DcMotor.Direction.FORWARD);
        motorBR.setDirection(DcMotor.Direction.REVERSE);
        motorBL.setDirection(DcMotor.Direction.FORWARD);

        //MotorGroup motorF = new MotorGroup(); //to be added for shrinking of code

        //setup the motors so that they are speed based; encoders control the speed.
        setModeStopAndReset();
        setModeRunUsingEncoder();

        double compBotDiameter = 96.0;

        //calculate the encoder ticks per inch
        double wheelDiameterInches = compBotDiameter / 25.4; // https://www.gobilda.com/3606-series-mecanum-wheel-set-bearing-supported-rollers-100mm-diameter/

        double wheelCircumference = wheelDiameterInches * Math.PI;
        double ticksPerRevolution = 384.5;  // From https://www.gobilda.com/5202-series-yellow-jacket-planetary-gear-motor-13-7-1-ratio-435-rpm-3-3-5v-encoder/
        ticksPerInch = ticksPerRevolution / wheelCircumference;

        //stop the bot
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
        motorX.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorY.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public  double getOdometryDistance(){
        double x = motorX.getCurrentPosition();
        double y = motorY.getCurrentPosition();
        double D = Math.sqrt(x*x+y*y);
        return (D*ODOM_INCHES_PER_COUNT);

    }

    public void setMotors(double FRS, double FLS, double BRS, double BLS) {
        //0.0 to 1.0, negative is opposite direction
        motorFR.setPower(FRS);
        motorFL.setPower(FLS);
        motorBR.setPower(BRS);
        motorBL.setPower(BLS);
    }

    public void setVelocity(double FRS, double FLS, double BRS, double BLS) {
        //ticks are similar in degrees in that it is a rotational coordinate system, however it doesn't count to 360
        //this function uses ticks per second to set velocity
        //0.0 to X.0 values, negative is opposite direction (add proper values when can be found)
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

    public void stop() {
        // Set all 4 motors to 0.0 speed
        setMotors(0.0, 0.0, 0.0, 0.0);
        resetEncoders();
    }

    // Runs the motors using the given speeds for the desired distance
    // Inputs:
    // FRS, FLS, BRS, BLS = speed for each motor 1.0 to -1.0
    // distance = distance in inches to travel
    public void runMotorsForDistanceOdom(double FRS, double FLS, double BRS, double BLS, double distance) {

        // Store the starting position of the motor encoder
        // Use the Front Right motor for right now...
        double dStartDistance = getOdometryDistance();


        double vMin = 0.15;
        double vMax = 1.0;
        double aMax = 1.0;

        // Calculate the starting velocity using the motion profile
        double dCurrentDistance = getOdometryDistance();
        double dVelMul = triangleMotionProfile(vMin, vMax, distance, dCurrentDistance);

        // Multiply the vMax for each motor by the starting velocity (scalar)
        setMotors(FRS * dVelMul, FLS * dVelMul, BRS * dVelMul, BLS * dVelMul);

        // Wait until the encoder says we have traveled the desired distance
        // Eventually the ticks will roll over or something like that.
        // However the autonomous code only needs to run for 30 seconds or so
        // Use the absolute distance traveled from dStartTicks to currentPosition
        //double dTicks = Math.abs(motorFR.getCurrentPosition() - dStartTicks);
        while (dCurrentDistance < distance)
        {
            // Calculate the current velocity scalar using the current distance traveled
            dVelMul = triangleMotionProfile(vMin, vMax, distance, dCurrentDistance);
            // Multiply the vMax for each motor by the starting velocity (scalar)
            setMotors(FRS * dVelMul, FLS * dVelMul, BRS * dVelMul, BLS * dVelMul);

            dCurrentDistance = getOdometryDistance();

        }

        //calculate the final scalar velocity (distance?)
        dVelMul = triangleMotionProfile(vMin, vMax, distance, dCurrentDistance);
        //multiply the vMax for each motor by the starting velocity (scalar) (distance?)
        setMotors(FRS * dVelMul, FLS * dVelMul, BRS * dVelMul, BLS * dVelMul);
    }

    public void runMotorsForDistance(double FRS, double FLS, double BRS, double BLS, double distance) {
        //convert the distance from inches to ticks
        distance *= ticksPerInch;

        //store the starting position of the motor encoder
        //use the Front Right motor for right now...
        double dStartTicks = motorFR.getCurrentPosition();

        double vMin = 0.15;
        double vMax = 1.0;
        double aMax = 1.0;

        //calculate the starting velocity using the motion profile
        double dTicks = Math.abs(motorFR.getCurrentPosition() - dStartTicks);
        double dVelMul = triangleMotionProfile(vMin, vMax, distance, dTicks);

        //multiply the vMax for each motor by the starting velocity (scalar)
        setMotors(FRS * dVelMul, FLS * dVelMul, BRS * dVelMul, BLS * dVelMul);

        //wait until the encoder says we have traveled the desired distance
        //eventually the ticks will roll over or something like that.
        //however the autonomous code only needs to run for 30 seconds or so
        //use the absolute distance traveled from dStartTicks to currentPosition
        //double dTicks = Math.abs(motorFR.getCurrentPosition() - dStartTicks);
        while (dTicks < distance)
        {
            //calculate the current velocity scalar using the current distance traveled
            dVelMul = triangleMotionProfile(vMin, vMax, distance, dTicks);
            //multiply the vMax for each motor by the starting velocity (scalar)
            setMotors(FRS * dVelMul, FLS * dVelMul, BRS * dVelMul, BLS * dVelMul);

            dTicks = Math.abs(motorFR.getCurrentPosition() - dStartTicks);
        }

        //calculate the final velocity scalar
        dVelMul = triangleMotionProfile(vMin, vMax, distance, dTicks);
        //multiply the vMax for each motor by the starting velocity (scalar)
        setMotors(FRS * dVelMul, FLS * dVelMul, BRS * dVelMul, BLS * dVelMul);
    }

    //triangle shaped motion profile that gives you velocity depending on the distance traveled
    //vMin and vMax can be normalized values used to scale actual speed inputs to the motors
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

    //TODO - needs to work in encoder ticks and not a relative or normalized 0 to 1
    double trapezoidalMotionProfile(double vMin, double vMax, double aMax, double totalDist, double currDist)
    {
        double maxAccelTime = vMax / aMax;
        double maxAccelDist = 0.5 * aMax * maxAccelTime * maxAccelTime;
        double halfDist = totalDist / 2.0;

        if (halfDist < maxAccelDist)
        {
            //adjust maxAccelDist because there will be no room for a const velocity period
            maxAccelDist = halfDist;
            //recalculate maxAccelTime based on the new maxAccelDist
            maxAccelTime = Math.sqrt(maxAccelDist / (0.5 * aMax));
            //recalculate vMax
            vMax = maxAccelDist / maxAccelTime * 2.0;
        }

        double cruiseDist = totalDist - (maxAccelDist * 2.0);
        double vDiff = vMax - vMin;
        double vCurr = vMin;

        if (currDist < maxAccelDist)
        {
            //accelerating
            vCurr = (currDist / maxAccelDist) *  vDiff + vMin;
        }
        else if (currDist < (maxAccelDist + cruiseDist))
        {
            //constant speed
            vCurr = vMax;
        }
        else if (currDist < totalDist)
        {
            //decelerating
            vCurr = ((totalDist - currDist) / maxAccelDist) * vDiff + vMin;
        }
        else {
            //overshot
            vCurr = vMin;
        }
        return vCurr;
    }

    //set the robot to move in the forward direction
    //inputs:
    //speed = -1.0 to 1.0
    //distance = distance in inches to travel before exiting
    public void forward(double speed, double distance) {
        //all 4 motors go in the same direction
        runMotorsForDistance(speed, speed, speed, speed, distance);
    }

    //set the robot to move in the backward direction
    //inputs:
    //speed = -1.0 to 1.0
    //distance = distance in inches to travel before exiting
    public void backward(double speed, double distance) {
        // Reverse the direction of the motors
        forward(speed * -1.0, distance);
    }

    //set the robot to move in the right direction
    public void right(double speed, double distance) {
        //in order to move right we have to set the motors
        //to different directions because we have omni directional wheels.
        //note:  this looks different than the chart
        //FR = reverse
        //FL = forward
        //BR = forward
        //BL = reverse

        runMotorsForDistance(speed * -1.0,speed,speed, speed * -1.0, distance);
    }

    //set the robot to move in the left direction
    public void left(double speed, double distance) {
        //in order to move left we have to set the motors
        //to different directions because we have omni directional wheels.
        //note:  this looks different than the chart
        //FR = forward
        //FL = reverse
        //BR = reverse
        //BL = forward

        runMotorsForDistance(speed,speed * -1.0,speed * -1.0, speed, distance);
    }

    //set the robot to turn in the ClockWise direction
    //TODO - figure out how to use the encoders to determine the angle to move to
    public void clockwise(double speed) {
        //turning is easier to think about with omni direction wheels.
        //to Turn ClockWise (right) we must reverse the right side motors
        setMotors(speed * -1.0, speed, speed * -1.0, speed);
    }

    //set the robot to turn in the AntiClockWise direction
    //TODO - figure out how to use the encoders to determine the angle to move to
    public void anticlockwise(double speed) {
        //turning is easier to think about with omni direction wheels.
        //to turn anticlockwise (left) we must reverse the left side motors
        //OR in other words just turn clockwise in reverse
        clockwise(speed * -1.0);
    }
}