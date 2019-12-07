package org.firstinspires.ftc.teamcode.Mechanisms;

import android.content.Context;
//TODO Insert a function to run thunder_sound_FX-Grant_Evens-1523270250.wav (Found in res :raw)
// as found in QQ
//import com.qualcomm.ftccommon.SoundPlayer;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import static org.firstinspires.ftc.teamcode.Util.Constants.*;

public class Robot {                                     //Initialize subsystems below, otherwise shit hits the fan
    private DriveTrain driveTrain = new DriveTrain();
    private Rotator rotator = new Rotator();
    private Claw claw = new Claw();
    private LightSensor colorSensor = new LightSensor();
    private DistanceSensors distanceSensors = new DistanceSensors();

    private Context appContext;

    public void init(HardwareMap hwMap) {
        driveTrain.init(hwMap);
        claw.init(hwMap);
        rotator.init(hwMap);
        colorSensor.init(hwMap);
        distanceSensors.init(hwMap);
        appContext = hwMap.appContext;
    }

    public void driveForward(double speed) {
        driveTrain.setSpeeds(speed, -speed, speed, -speed);
    }

    public void drifeForwardStraight (double speed) {

    }

    public void driveRotate(double speed) {
        driveTrain.setSpeeds(speed, speed, speed, speed);
    }

    public void driveForwardAndRotate(double fSpeed, double rSpeed) {                         //Checks for rotation direction, then applies both vectors via multiplication
        if (rSpeed > 0) {
            driveTrain.setSpeeds(-fSpeed, fSpeed * (1 - rSpeed), -fSpeed, fSpeed * (1 - rSpeed));
        } else if (rSpeed < 0) {
            driveTrain.setSpeeds(-fSpeed * (1 - rSpeed), fSpeed, -fSpeed * (1 - rSpeed), fSpeed);
        } else {
            driveTrain.setSpeeds(-fSpeed, fSpeed, -fSpeed, fSpeed);
        }
    }

    public void driveStrafe(double speed) {
        driveTrain.setSpeeds(-speed, -speed, speed, speed);                                        //Runs opposite angled wheels at each other
    }

    public void driveStrafeStraight(double speed) {

    }

    public void driveAll(double fSpeed, double sSpeed, double rSpeed) {
        driveTrain.setSpeeds((fSpeed + sSpeed + rSpeed), (-fSpeed - sSpeed + rSpeed), (fSpeed - sSpeed + rSpeed), (-fSpeed + sSpeed + rSpeed));
    }

    public void setRotator(int target) {
        rotator.setPosition(target);
    }

    public boolean checkYellow() {
        if (colorSensor.checkRed() > YELLOW_RED_THRESHOLD && colorSensor.checkGreen() > YELLOW_GREEN_THRESHOLD && colorSensor.checkBlue() < YELLOW_BLUE_THRESHOLD) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkBlack() {
        if (colorSensor.checkRed() < BLACK_RED_THRESHOLD && colorSensor.checkGreen() < BLACK_GREEN_THRESHOLD && colorSensor.checkBlue() < BLACK_BLUE_THRESHOLD) {
            return true;
        } else {
            return false;
        }
    }

    public void waiting(int millis){
        if(Thread.currentThread().isInterrupted()) return;
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public double checkSkystoneProximity() {
        return colorSensor.checkProximity() * 2;
    }

    public double checkLeftDistance() {
        return distanceSensors.checkLeftDistance();
    }

    public double checkRightDistance() {
        return distanceSensors.checkRightDistance();
    }

    //public double robotOrientation(){ return driveTrain.getHeading(); }

    public void openClaw() { claw.open(); }

    public void closeClaw() { claw.close(); }

    public void reportSensors(Telemetry telemetry) {
        colorSensor.reportColors(telemetry);
        colorSensor.reportProximity(telemetry);
        distanceSensors.reportDistances(telemetry);
        rotator.reportEncoders(telemetry);
        //driveTrain.reportHeading(telemetry);
    }
}
