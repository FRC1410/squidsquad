package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import static org.firstinspires.ftc.teamcode.Util.Constants.*;

public class LightSensor {
    
    private ColorSensor colorSensor;
    private DistanceSensor distanceSensor;

    void init(HardwareMap hwMap) {
        colorSensor = hwMap.get(ColorSensor.class, "color_sensor");

        distanceSensor = hwMap.get(DistanceSensor.class, "color_sensor");

        colorSensor.red();   // Red channel value
        colorSensor.green(); // Green channel value
        colorSensor.blue();  // Blue channel value
        colorSensor.alpha();
        distanceSensor.getDistance(DistanceUnit.CM);  //Distance in CM
    }

    void reportColors(Telemetry telemetry) {
        telemetry.addData("Red", colorSensor.red());
        telemetry.addData("Green", colorSensor.green());
        telemetry.addData("Blue", colorSensor.blue());
        telemetry.addData("Alpha", colorSensor.alpha());
    }

    void reportProximity(Telemetry telemetry) {
        telemetry.addData("Proximity", distanceSensor.getDistance(DistanceUnit.CM));
    }

    double checkProximity() {
        return distanceSensor.getDistance(DistanceUnit.CM);
    }

    double checkRed() {
        return (colorSensor.red() * COLOR_SCALE_VALUE);
    }

    double checkGreen() {
        return (colorSensor.green() * COLOR_SCALE_VALUE);
    }

    double checkBlue() {
        return (colorSensor.blue() * COLOR_SCALE_VALUE);
    }

    double checkAlpha() {
        return (colorSensor.alpha() * COLOR_SCALE_VALUE);
    }
}




