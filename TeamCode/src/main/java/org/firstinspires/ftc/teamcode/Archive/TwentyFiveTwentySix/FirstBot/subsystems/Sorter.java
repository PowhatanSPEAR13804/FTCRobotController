package org.firstinspires.ftc.teamcode.Archive.TwentyFiveTwentySix.FirstBot.subsystems;
/*
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Disabled
*/
public class Sorter /*extends SubsystemBase */ {
    /*
    private final ServoEx middleLifter;
    private final ServoEx leftLifter;
    private final ServoEx rightLifter;

    private final NormalizedColorSensor topMiddleSensor;
    private final NormalizedColorSensor topLeftSensor;
    private final NormalizedColorSensor topRightSensor;
    private final NormalizedColorSensor bottomMiddleSensor;
    private final NormalizedColorSensor bottomLeftSensor;
    private final NormalizedColorSensor bottomRightSensor;

    private final double[] emptyThreshold = {0.25,0.25,0.25};

    private boolean idle = true;

    //[left color, middle color, right color] 0 is empty, 1 is purple, 2 is green
    private int[] positions = {0, 0, 0};

    public Sorter(HardwareMap hardwareMap, String middleServoID, String leftServoID, String rightServoID, String topMiddleSensorID, String topLeftSensorID, String topRightSensorID, String bottomMiddleSensorID, String bottomLeftSensorID, String bottomRightSensorID) {
        middleLifter = new SimpleServo(hardwareMap, middleServoID, 0, 360);
        leftLifter = new SimpleServo(hardwareMap, leftServoID, 0, 270);
        rightLifter = new SimpleServo(hardwareMap, rightServoID, 0, 270);


        topMiddleSensor = hardwareMap.get(NormalizedColorSensor.class, topMiddleSensorID);
        topLeftSensor = hardwareMap.get(NormalizedColorSensor.class, topLeftSensorID);
        topRightSensor = hardwareMap.get(NormalizedColorSensor.class, topRightSensorID);
        bottomMiddleSensor = hardwareMap.get(NormalizedColorSensor.class, bottomMiddleSensorID);
        bottomLeftSensor = hardwareMap.get(NormalizedColorSensor.class, bottomLeftSensorID);
        bottomRightSensor = hardwareMap.get(NormalizedColorSensor.class, bottomRightSensorID);


        //TODO calibrate the gain for each color sensor once it is in the robot
        topMiddleSensor.setGain(12);
        topLeftSensor.setGain(12);
        topRightSensor.setGain(12);
        bottomMiddleSensor.setGain(12);
        bottomLeftSensor.setGain(12);
        bottomRightSensor.setGain(12);
    }


    public void updateColorPositions(int[] positions) {
        NormalizedRGBA[] colors = sense();

        for (int i = 0; i < colors.length; i+=2) {
            NormalizedRGBA sensor = colors[i];
            if(compareThreshold(sensor)){
                if(sensor.red <= 0.7) {
                    positions[i/2] = 1;
                } else {
                    positions[i/2] = 2;
                }
            }
            if(positions[i] == 0) {
                sensor = colors[i+1];
                if(compareThreshold(sensor)){
                    if(sensor.red <= 0.7) {
                        positions[i/2] = 1;
                    } else {
                        positions[i/2] = 2;
                    }
                }
            }
        }
    }

    private boolean compareThreshold(NormalizedRGBA values) {
        return values.red >= emptyThreshold[0] && values.green >= emptyThreshold[1] && values.blue >= emptyThreshold[2];
    }

    private NormalizedRGBA[] sense() {
        NormalizedRGBA topMiddleColors = topMiddleSensor.getNormalizedColors();
        NormalizedRGBA topLeftColors = topLeftSensor.getNormalizedColors();
        NormalizedRGBA topRightColors = topRightSensor.getNormalizedColors();
        NormalizedRGBA bottomMiddleColors = bottomMiddleSensor.getNormalizedColors();
        NormalizedRGBA bottomLeftColors = bottomLeftSensor.getNormalizedColors();
        NormalizedRGBA bottomRightColors = bottomRightSensor.getNormalizedColors();

        NormalizedRGBA[] colors = new NormalizedRGBA[6];

        colors[0] = topMiddleColors;
        colors[1] = topLeftColors;
        colors[2] = topRightColors;
        colors[3] = bottomMiddleColors;
        colors[4] = bottomLeftColors;
        colors[5] = bottomRightColors;


        return colors;
    }

    private void balls(ServoEx liftServo) {
        //TODO Use servo position finder to find position.
        liftServo.setPosition(100);
    }

    public void sendBall(String servo) {
        switch (servo) {
            case "left":
                balls(leftLifter);
            case "right":
                balls(rightLifter);
            case "middle":
                balls(middleLifter);
        }
    }

    public void periodic() {
        updateColorPositions(positions);
    }

    public void idle() {
        leftLifter.setPosition(1);
        rightLifter.setPosition(1);
        middleLifter.setPosition(1.0/3.0);
    }

    public void setPositions(double midPos, double leftPos, double rightPos) {
        middleLifter.setPosition(midPos);
        leftLifter.setPosition(leftPos);
        rightLifter.setPosition(rightPos);
    }

    public List<Float> getValues() {
        NormalizedRGBA[] colors = sense();
        List<Float> values = new ArrayList<>(colors.length * 3);
        for(NormalizedRGBA sensor:colors){
            values.add(sensor.red);
            values.add(sensor.green);
            values.add(sensor.blue);
        }
        return values;
    }

    public String[] getPositions() {
        String[] positionString = {"","",""};
        for(int i=0; i<positions.length; i++) {
            int num = positions[i];
            switch(num) {
                case 0:
                    positionString[i] = "NONE";
                case 1:
                    positionString[i] = "PURPLE";
                case 2:
                    positionString[i] = "GREEN";
            }
        }
        return positionString;
    }

    public List<Integer> getPosition() {
        ArrayList<Integer> positionsList = new ArrayList<>();
        for (int value : positions) {
            positionsList.add(value); // Autoboxing converts int to Integer
        }
        return positionsList;
    }*/
}

