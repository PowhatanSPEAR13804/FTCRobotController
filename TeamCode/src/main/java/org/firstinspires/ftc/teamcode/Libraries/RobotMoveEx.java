package org.firstinspires.ftc.teamcode.Libraries;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

//object for controlling the 4 drive motors

/*
expects the 4 drive motors connected to omnidirectional wheels; see OmniDirectionWheels.jpg for
how these work.
 */

/*
TODO - recreate the JPG in ASCII and paste here.
 - make this a singleton or static member so there can be only one set of drive motor objects.
 */

// RobotPos class object
// used to store a snapshot of the robot position
// based on motor encoder values
class RobotPos
{
    public double x;    // x position
    public double y;    // y position
    public double dist; // distance
    public double dir;  // angle degrees
    public double a;    // anglular position
    public RobotPos()
    {
        x = 0.0;
        y = 0.0;
        dist = 0.0;
        dir = 0.0;
        a = 0.0;
    }
}
public class RobotMoveEx {
    public ServoMotorDeclarations robot = null;

    // Debugging string that is set in various functions
    // so that the owner of the object can print it out
    public String debugString;

    //encoder ticks per inch
    public double ticksPerInch = 0.0;

    public double robotWidth = 14.0;
    public double robotLength = 11.5;

    //constructor - does all the initialization of the motors
    //this function gets called when you make a new object.
    public RobotMoveEx(HardwareMap hardwareMap) {
        robot = new ServoMotorDeclarations(hardwareMap);

        //setup the motors to turn in the correct direction to default to forward motion
        //always set all 4 just in case their default is NOT FORWARD.
        robot.motorFR.setDirection(DcMotor.Direction.FORWARD);
        robot.motorFL.setDirection(DcMotor.Direction.REVERSE);
        robot.motorBR.setDirection(DcMotor.Direction.FORWARD);
        robot.motorBL.setDirection(DcMotor.Direction.REVERSE);

        //setup the motors so that they are speed and not power based...  it uses the encoders to control the speed.
        setModeStopAndReset();
        setModeRunUsingEncoder();

        //calculate the encoder ticks per inch
        double wheelDiameterInches = 96.0 / 25.4; //https://www.gobilda.com/3606-series-mecanum-wheel-set-bearing-supported-rollers-100mm-diameter/
        double wheelCircumference = wheelDiameterInches * Math.PI;
        double ticksPerRevolution = 384.5;  //from https://www.gobilda.com/5202-series-yellow-jacket-planetary-gear-motor-13-7-1-ratio-435-rpm-3-3-5v-encoder/
        ticksPerInch = ticksPerRevolution / wheelCircumference;

        //make sure we are stopped
        stop();
    }



    public void setMode(DcMotor.RunMode mode) {
        robot.motorFR.setMode(mode);
        robot.motorFL.setMode(mode);
        robot.motorBR.setMode(mode);
        robot.motorBL.setMode(mode);
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



    //set the power individually on each motor as a group
    //you can call this by itself or use one of the other functions to call it for you.
    public void setMotors(double FRS, double FLS, double BRS, double BLS) {
        //the DcMotor.setPower function takes in a power value from -1.0 to 1.0
        //0.0 is stop / no motion
        //-1.0 is full reverse
        //1.0 is full forward

        /*
        note:  if you configure the motor direction to REVERSE then -1.0 will be the opposite direction that a
        motor configured for FORWARD

        this can help make all motors turn their wheels in the direction you want them to and then the speed
        is the same for all 4 motors.
         */

        // See the constructor for the directions the motors are set to.

        /*
        note:  the order we set the power in does not matter for steering offsets. the bot always veers off in
        the same direction.
         */
        robot.motorFR.setPower(FRS);
        robot.motorFL.setPower(FLS);
        robot.motorBR.setPower(BRS);
        robot.motorBL.setPower(BLS);
    }



    //set the Velocity individually on each motor as a group
    //you can call this by itself or use one of the other functions to call it for you.
    //FRS, FLS, BRS, BLS are in ticks per second
    public void setVelocity(double FRS, double FLS, double BRS, double BLS) {
        /*
        the DcMotorEx.setVelocity function takes in a value from -ticks per second to +ticks per second to set
        the velocity of the DC motor.
         */

        //0.0 is stop / no motion
        //-X.0 is full reverse
        //X.0 is full forward

        /*
        note:  if you configure the motor direction to REVERSE then -X.0 will be the opposite direction that
        a motor configured for FORWARD

        this can help make all motors turn their wheels in the direction you want them to and then the speed
        is the same for all 4 motors.

        see the constructor for the directions the motors are set to.
         */

        /*
        note:  the order we set the power in does not matter for steering offsets. the bot always veers
        off in the same direction.
         */
        ((DcMotorEx)robot.motorFR).setVelocity(FRS);
        ((DcMotorEx)robot.motorFL).setVelocity(FLS);
        ((DcMotorEx)robot.motorBR).setVelocity(BRS);
        ((DcMotorEx)robot.motorBL).setVelocity(BLS);
    }



    public void wait(int milliseconds) {
        long startTime = System.currentTimeMillis();
        long stopTime = startTime + milliseconds;
        long currentTime = System.currentTimeMillis();

        while(currentTime < stopTime)
            currentTime = System.currentTimeMillis();
    }



    //stop the robot

    /*
    TODO -there is a breaking mode and floating mode or something like that. the breaking action
     should stop the motors really quickly and the non breaking mode will allow the motors to coast a little.
     What do we want?
     */
    public void stop() {
        //set all 4 motors to 0.0 speed
        setMotors(0.0, 0.0, 0.0, 0.0);
        resetEncoders();
    }

    public void forward(double velocity)
    {
        // Forward is 0.0 degrees
        move(velocity, 0.0, 0.0);
    }

    public void backward(double velocity)
    {
        // Backward is -180.0 degrees
        move(velocity, -180.0, 0.0);
    }

    public void left(double velocity)
    {
        // Left is -90.0
        move(velocity, -90.0, 0.0);
    }

    public void right(double velocity)
    {
        // Right is 90.0
        move(velocity, 90.0, 0.0);
    }

    //triangle shaped motion profile that gives you velocity depending on the distance traveled
    //vMin and vMax can be normalized values used to scale actual speed inputs to the motors
    public double triangleMotionProfile(double vMin, double vMax, double totalDist, double currDist) {
        double halfDist = totalDist / 2.0;
        double vDiff = vMax - vMin;
        double vCurr = vMin;
        if (currDist < halfDist) {
            vCurr = (currDist / halfDist)  * vDiff + vMin;
        } else {
            vCurr = (totalDist - currDist) / halfDist * vDiff + vMin;
        }

        return vCurr;
    }

    // Get the robot position in cartesian coordinate space
    // referenced to the robot's frame
    // Found this in the paper:
    // Kinematic Model of a Four Mecanum Wheeled Mobile Robot
    // equation 23,23,24,25,26
    public void getRobotPos(RobotPos pos)
    {
        double robotDim = robotWidth / 2 + robotLength / 2;

        double m1 = robot.motorFR.getCurrentPosition();
        double m2 = robot.motorFL.getCurrentPosition();
        double m3 = robot.motorBR.getCurrentPosition();
        double m4 = robot.motorBL.getCurrentPosition();

        // Get the forward and sideways position
        double y = (m1 + m2 + m3 + m4) / ticksPerInch / 4;
        double x = (-m1 + m2 + m3 - m4) / ticksPerInch / 4;
        // Calculate the distance a^2 + b^ = c^2
        double dist = Math.sqrt(x * x + y * y);
        // Get the angular position
        double a = (-m1 + m2 - m3 + m4) * (1.0 / robotDim) / Math.PI * 180.0;

        // Use some trig to turn this into a direction of travel
        double dir = Math.atan(y / x);

        // Set the position object's values so we can transfer them out of
        // this function.  Java really does not have pass by reference!
        // So we pass in an object and set the member variables.
        pos.x = x;
        pos.y = y;
        pos.dist = dist;
        pos.dir = dir;
        pos.a = a;
    }

    public double getRobotDist()
    {
        double robotDim = robotWidth / 2 + robotLength / 2;

        double m1 = robot.motorFR.getCurrentPosition();
        double m2 = robot.motorFL.getCurrentPosition();
        double m3 = robot.motorBR.getCurrentPosition();
        double m4 = robot.motorBL.getCurrentPosition();

        // Get the forward and sideways position
        // note:  x and y are reversed.. in the paper X is forward/backward
        double y = (m1 + m2 + m3 + m4) / ticksPerInch / 4;
        double x = (-m1 + m2 + m3 - m4) / ticksPerInch / 4;
        // Calculate the distance a^2 + b^ = c^2
        double dist = Math.sqrt(x * x + y * y);
        // Get the angular position
        double a = (-m1 + m2 - m3 + m4) * (1.0 / robotDim) / Math.PI * 180.0;

        // Use some trig to turn this into a direction of travel
        double dir = Math.atan(y / x);

        return dist;
    }

    // Move the robot a certain distance (inches) at vMax velocity (ips) in the direction (degrees)
    // The angular velocity causes the robot to rotate about it's center.
    public void moveTo(double vMax, double distance, double direction, double angleVelocity)
    {
        RobotPos newPos = new RobotPos();
        RobotPos startPos = new RobotPos();

        // Get the starting position
        getRobotPos(startPos);

        double currDist = Math.abs(newPos.dist - startPos.dist);

        double currVel = triangleMotionProfile(5, vMax, distance, currDist);

        // Start moving in that direction
        move(currVel, direction, angleVelocity);

        while (currDist < distance)
        {
            // Calculate the new velocity based on our motion profile
            currVel = triangleMotionProfile(5, vMax, distance, currDist);
            // Move the robot
            move(currVel, direction, angleVelocity);
            // Get the new position
            getRobotPos(newPos);
            // Calculate the current distance
            currDist = Math.abs(newPos.dist - startPos.dist);
        }

        stop();
    }

    // Move the robot in the direction at the velocity given
    // the robot will rotate at the angularVelocity
    public void move(double velocity, double direction, double angleVelocity)
    {
        // Convert the compass degrees (0 degrees is forward, -180 is left, 180 is right)
        // to math degrees on a unit circle (0 degrees is right, 90 is forward, 180 is left)
        double directionMathDegrees = 360.0 - direction + 90.0;

        // Convert the direction and velocity into the X and Y velocity using a little bit of trig
        double directionRadians = directionMathDegrees / 180.0 * Math.PI;
        double vX = Math.cos(directionRadians) * velocity;
        double vY = Math.sin(directionRadians) * velocity;

        moveXYA(vX, vY, angleVelocity);
    }

    // Moves the robot at the velocity defined by x,y
    // and rotate at the speed a
    // y is the forward velocity in inches per second
    // x is left/right velocity in inches per second
    // a is the rotational angle velocity in degrees per second
    public void moveXYA(double x, double y, double a) {
        double robotDim = robotWidth / 2 + robotLength / 2;

        //rotation in radians
        double r = a / 180 * Math.PI;

        // From the research paper:
        // Kinematic Model of a Four Mecanum Wheeled Mobile Robot
        // equation 20
        //calculates individual motor velocities
        double m1 = (y - x - robotDim * r) * ticksPerInch;
        double m2 = (y + x + robotDim * r) * ticksPerInch;
        double m3 = (y + x - robotDim * r) * ticksPerInch;
        double m4 = (y - x + robotDim * r) * ticksPerInch;

        //debugString = String.format("m1(%f) m2(%f) m3(%f) m4(%f)", m1, m2, m3, m4);

        setVelocity(m1, m2, m3, m4);
    }
}