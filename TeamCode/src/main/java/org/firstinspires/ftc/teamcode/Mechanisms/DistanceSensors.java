package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class DistanceSensors {
    private DistanceSensor leftSensor;
    private DistanceSensor rightSensor;
    private DistanceSensor backSensor;
    void init(HardwareMap hwMap){
        leftSensor = hwMap.get(DistanceSensor.class, "left_distance_sensor");
        rightSensor = hwMap.get(DistanceSensor.class, "right_distance_sensor");
        backSensor = hwMap.get(DistanceSensor.class, "back_distance_sensor");


    }
    double checkLeftDistance() {
        return (leftSensor.getDistance(DistanceUnit.CM));
    }

    double checkRightDistance() {
        return (rightSensor.getDistance(DistanceUnit.CM));
    }

    double checkBackDistance() { return (rightSensor.getDistance(DistanceUnit.CM)); }

    void reportDistances(Telemetry telemetry) {
        telemetry.addData("Back Distance", backSensor.getDistance(DistanceUnit.CM));
        telemetry.addData("Left Distance", leftSensor.getDistance(DistanceUnit.CM));
        telemetry.addData("Right Distance", rightSensor.getDistance(DistanceUnit.CM));

    }
}
