package org.firstinspires.ftc.teamcode.helperclasses;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

// Object for controlling the 4 drive motors
// Expects the 4 drive motors connected to omni directional wheels
// See OmniDirectionWheels.jpg for how these work.
// TODO - recreate the JPG in ASCII and paste here.
// TODO - make this a singleton or static member so there can be only one set of drive motor objects.

public class robotMove {
    private DcMotor motorFR = null;
    private DcMotor motorFL = null;
    private DcMotor motorBR = null;
    private DcMotor motorBL = null;

    // Constructor - does all the initialization of the motors
    // this function gets called when you make a new object.
    public robotMove() {
        // Create the motor devices
        // Change this mapping to however your robot is setup.
        // TODO - this is setup to the 2023 robot map
        motorFR = hardwareMap.dcMotor.get("Hub2_Motor0");
        motorFL = hardwareMap.dcMotor.get("Hub1_Motor3");
        motorBR = hardwareMap.dcMotor.get("Hub2_Motor3");
        motorBL = hardwareMap.dcMotor.get("Hub1_Motor0");

        // Setup the motors to turn in the correct
        // direction to default to forward motion
        // Always set all 4 just in case their default
        // is NOT FORWARD.
        motorFR.setDirection(DcMotor.Direction.REVERSE);
        motorFL.setDirection(DcMotor.Direction.FORWARD);
        motorBR.setDirection(DcMotor.Direction.REVERSE);
        motorBL.setDirection(DcMotor.Direction.FORWARD);
        // Make sure we are stopped
        stop();
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
        motorFR.setPower(FRS);
        motorFL.setPower(FLS);
        motorBR.setPower(BRS);
        motorBL.setPower(BLS);
    }

    // Stop the robot
    // or something like that.  the breaking action
    // should stop the motors really quickly and
    // the non breaking mode will allow the motors
    // to coast a little.  What do we want?
    // TODO -there is a breaking mode and floating mode
    public void stop() {
        // Set all 4 motors to 0.0 speed
        setMotors(0.0, 0.0, 0.0, 0.0);
    }

    // Set the robot to move in the forward direction
    // Inputs:
    // speed = -1.0 to 1.0
    public void forward(double speed) {
        // All 4 motors go in the same direction
        setMotors(speed, speed, speed, speed);
    }

    // Set the robot to move in the backward direction
    // Inputs:
    // speed = -1.0 to 1.0
    public void backward(double speed) {
        // Reverse the direction of the motors
        forward(speed * -1.0);
    }

    // Set the robot to move in the right direction
    public void right(double speed) {
        // In order to move right we have to set the motors
        // to different directions because we have omni directional wheels.
        // FR = forward
        // FL = reverse
        // BR = reverse
        // BL = forward
        setMotors(speed,speed * -1.0,speed * -1.0, speed);
    }

    // Set the robot to move in the left direction
    public void left(double speed) {
        // In order to move left we have to set the motors
        // to different directions because we have omni directional wheels.
        // FR = reverse
        // FL = forward
        // BR = forward
        // BL = reverse
        setMotors(speed * -1.0,speed,speed, speed * -1.0);
    }

    // Set the robot to turn in the ClockWise direction
    public void clockwise(double speed) {
        // Turning is easier to think about with omni direction wheels.
        // To Turn ClockWise (right) we must reverse the right side motors
        setMotors(speed * -1.0, speed, speed * -1.0, speed);
    }

    // Set the robot to turn in the AntiClockWise direction
    public void anticlockwise(double speed) {
        // Turning is eaiser to think about with omni direction wheels.
        // To turn anticlockwise (left) we must reverse the left side motors
        // OR in other words just turn clockwise in reverse
        clockwise(-1.0);
    }
}
