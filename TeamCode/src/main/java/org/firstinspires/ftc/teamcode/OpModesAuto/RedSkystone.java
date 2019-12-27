package org.firstinspires.ftc.teamcode.OpModesAuto;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Mechanisms.Robot;

import static org.firstinspires.ftc.teamcode.Util.Constants.*;
//TODO Remake the logic of this OpMode once done tuning logic on BlueAlliance

@Autonomous()
public class RedSkystone extends OpMode {
    private Robot robot = new Robot();

    private int step = 0;
    private double firstSkystoneDistance;
    private int skystonesGrabbed = 0;

    @Override
    public void init() {
        robot.init(hardwareMap);

        robot.openClaw();
    }

    @Override
    public void loop(){
        robot.reportSensors(telemetry);
        switch (step) {
            case 0:
                if (robot.checkSkystoneProximity() >  SKYSTONE_FAR_DISTANCE_THRESHOLD) {
                    robot.driveForward(AUTO_FAST_SPEED_FORWARD, telemetry);
                } else {
                    robot.driveForward(0, telemetry);
                    step = 1;
                }
                break;
            case 1:
                if (robot.checkSkystoneProximity() > SKYSTONE_CLOSE_DISTANCE_THRESHOLD) {
                    robot.driveForward(AUTO_SLOW_SPEED_FORWARD, telemetry);
                } else {
                    robot.driveForward(0, telemetry);
                    robot.closeClaw();
                    firstSkystoneDistance = robot.checkLeftDistance();
                    step = 2;
                }
                break;
            case 2: //This is the only arbitrary value
                robot.driveForward(AUTO_FAST_SPEED_BACKWARD, telemetry);
                robot.waiting(AUTO_WAIT_PERIOD);
                robot.driveForward(AUTO_STOP, telemetry);
                step = 3;
                break;
            case 3:
                if (robot.checkRightDistance() < FAR_DISTANCE_THRESHOLD){
                    robot.driveStrafe(AUTO_FAST_SPEED_RIGHT);
                } else {
                    robot.driveStrafe(0);
                    step = 4;
                }
                break;
            case 4:
                robot.openClaw();
                skystonesGrabbed++;
                step = 5;
                break;
            case 5:
                robot.driveStrafe(AUTO_FAST_SPEED_RIGHT);
                if (robot.checkLeftDistance() >= firstSkystoneDistance - SKYSTONE_OFFSET) {
                    robot.driveStrafe(AUTO_FAST_SPEED_RIGHT);
                } else {
                    robot.driveStrafe(0);
                    if (skystonesGrabbed <= 1) {
                        step = 6;
                    } else {
                        robot.driveAll(0, 0, 0, telemetry);
                        telemetry.addData("Auto", "Completed");
                    }
                }
                break;
            case 6:
                if (robot.checkSkystoneProximity() >  SKYSTONE_FAR_DISTANCE_THRESHOLD) {
                    robot.driveForward(AUTO_FAST_SPEED_FORWARD, telemetry);
                } else {
                    robot.driveForward(0, telemetry);
                    step = 7;
                }
                break;
            case 7:
                if (robot.checkBlack() == false){
                    robot.driveStrafe(AUTO_SLOW_SPEED_RIGHT);
                } else {
                    robot.driveStrafe(0);
                    step = 1;
                }
                break;

        }
    }

}