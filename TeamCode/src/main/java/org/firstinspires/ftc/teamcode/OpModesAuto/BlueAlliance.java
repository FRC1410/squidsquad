package org.firstinspires.ftc.teamcode.OpModesAuto;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Mechanisms.Robot;

import static org.firstinspires.ftc.teamcode.Util.Constants.*;

@Autonomous()
public class BlueAlliance extends OpMode {
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
        switch (step) {
            case 0: //What it Does:
                if (robot.checkSkystoneProximity() >  SKYSTONE_FAR_DISTANCE_THRESHOLD) {
                    robot.driveForward(AUTO_FAST_SPEED_FORWARD);
                } else {
                    robot.driveForward(0);
                    step = 1;
                }
            break;
            case 1: //What it Does:
                if (robot.checkSkystoneProximity() > SKYSTONE_CLOSE_DISTANCE_THRESHOLD) {
                    robot.driveForward(AUTO_SLOW_SPEED_FORWARD);
                } else {
                    robot.driveForward(0);
                    robot.closeClaw();
                    firstSkystoneDistance = robot.checkRightDistance();
                    step = 2;
                }
            break;
            case 2: //This is the only arbitrary value
                robot.driveForward(AUTO_FAST_SPEED_BACKWARD);
                robot.waiting(AUTO_WAIT_PERIOD);
                robot.driveForward(AUTO_STOP);
                step = 3;
            break;
            case 3:
                if (robot.checkRightDistance() < SKYSTONE_DROP_POINT){
                    robot.strafe(AUTO_FAST_SPEED_LEFT);
                } else {
                    robot.strafe(0);
                    step = 4;
                }
            break;
            case 4:
                robot.openClaw();
                skystonesGrabbed++;
                step = 5;
            break;
            case 5:
                robot.strafe(AUTO_FAST_SPEED_RIGHT);
                if (robot.checkRightDistance() >= firstSkystoneDistance - SKYSTONE_OFFSET) {
                    robot.strafe(AUTO_FAST_SPEED_RIGHT);
                } else {
                    robot.strafe(0);
                    if (skystonesGrabbed <= 1) {
                        step = 6;
                    } else {
                        robot.driveAll(0, 0, 0);
                        telemetry.addData("Auto", "Completed");
                    }
                }
            break;
            case 6:
                if (robot.checkSkystoneProximity() >  SKYSTONE_FAR_DISTANCE_THRESHOLD) {
                    robot.driveForward(AUTO_FAST_SPEED_FORWARD);
                } else {
                    robot.driveForward(0);
                    step = 7;
                }
            break;
            case 7:
                if (robot.checkBlack() == false){
                    robot.strafe(AUTO_SLOW_SPEED_LEFT);
                } else {
                    robot.strafe(0);
                    step = 1;
                }
            break;

        }
    }

}
