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

public class RobotMoveEx {
    public ServoMotorDeclarations robot = null;

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



    public void move(double x, double y, double a) {
        double robotDim = robotWidth / 2 + robotLength / 2;

        //rotation in radians
        double r = a / 180 * Math.PI;

        //calculates individual motor velocities
        double m1 = (x - y - robotDim * r) * ticksPerInch;
        double m2 = (x + y + robotDim * r) * ticksPerInch;
        double m3 = (x + y - robotDim * r) * ticksPerInch;
        double m4 = (x - y + robotDim * r) * ticksPerInch;

        setVelocity(m1, m2, m3, m4);
    }
}