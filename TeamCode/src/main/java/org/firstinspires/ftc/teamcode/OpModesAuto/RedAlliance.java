package org.firstinspires.ftc.teamcode.OpModesAuto;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Mechanisms.Robot;

import static org.firstinspires.ftc.teamcode.Util.Constants.*;

@Autonomous()
public class RedAlliance extends OpMode {
    private Robot robot = new Robot();
    private ElapsedTime runtime = new ElapsedTime();

    private int step = -1;
    private double firstSkystoneDistance;
    private int skystonesGrabbed = 0;

    @Override
    public void init() {
        robot.init(hardwareMap);
        runtime.reset();

        robot.openClaw();
    }

    @Override
    public void loop(){
        robot.reportSensors(telemetry);
        robot.getHeadingAbsolute(telemetry);
        telemetry.addData("Auto", "Run Time: " + runtime.toString());

        switch (step) {
            case -1: //What it Does:
                telemetry.addData("Auto Step", "-1");
                if (robot.checkSkystoneProximity() >  SKYSTONE_FAR_DISTANCE_THRESHOLD) {
                    robot.driveForward(AUTO_FAST_SPEED_FORWARD, telemetry);
                } else {
                    robot.driveForward(0, telemetry);
                    step = 0;
                }
                break;
            case 0:
                telemetry.addData("Auto Step", "0");
                if (robot.checkBlack() == false){
                    robot.driveAll(0, AUTO_SLOW_SPEED_LEFT, 0, telemetry);
                } else {
                    robot.driveAll(0, 0, 0, telemetry);
                    step = 1;
                }
            case 1: //What it Does:
                telemetry.addData("Auto Step", "1");
                if (robot.checkSkystoneProximity() < SKYSTONE_CLOSE_DISTANCE_THRESHOLD) {
                    robot.driveForward(0, telemetry);
                    robot.closeClaw();
                    firstSkystoneDistance = robot.checkRightDistance();
                    step = 2;
//                    robot.driveForward(AUTO_SLOW_SPEED_FORWARD, telemetry);
                } else {
//                    robot.driveForward(0, telemetry);
//                    robot.closeClaw();
//                    firstSkystoneDistance = robot.checkRightDistance();
//                    step = 2;
                    robot.driveForward(AUTO_SLOW_SPEED_FORWARD, telemetry);
                }
                break;
            case 2: //This is the only arbitrary value
                telemetry.addData("Auto Step", "2");
                robot.driveForward(AUTO_FAST_SPEED_BACKWARD, telemetry);
                robot.waiting(AUTO_WAIT_PERIOD);
                robot.driveForward(AUTO_STOP, telemetry);
                step = 3;
                break;
            case 3:
                telemetry.addData("Auto Step", "3");
                if (robot.checkLeftDistance() == 819.0){                              //robot.checkRightDistance() < SKYSTONE_DROP_POINT
                    step = 4;                                                                       //robot.checkRightDistance() >= DISTANCE_THRESHOLD
                } else {
                    robot.driveAll(0,AUTO_FAST_SPEED_LEFT,0, telemetry);
                    telemetry.addData("Test", "Success");
                }
                break;
            case 4:
                telemetry.addData("Auto Step", "4");
                if (robot.checkRightDistance() < CLOSE_DISTANCE_THRESHOLD) {
                    robot.driveAll(0,0,0,telemetry);
                    robot.openClaw();
                    skystonesGrabbed++;
                    step = 5;
                } else {
                    robot.driveAll(0,AUTO_FAST_SPEED_LEFT,0,telemetry);
                }
                break;
            case 5:
                telemetry.addData("Auto Step", "5");
                if (robot.checkRightDistance() < FAR_DISTANCE_THRESHOLD){                              //robot.checkRightDistance() < SKYSTONE_DROP_POINT
                    robot.driveAll(0,AUTO_FAST_SPEED_RIGHT,0, telemetry);
                    telemetry.addData("Test", "Success");
                } else {
                    robot.driveAll(0,AUTO_FAST_SPEED_RIGHT,0, telemetry);
                    step = 6;
                }
                break;
            case 6:
                telemetry.addData("Auto Step", "6");
                if (robot.checkLeftDistance() >= firstSkystoneDistance - SKYSTONE_OFFSET) {
                    robot.driveAll(0,AUTO_SLOW_SPEED_RIGHT,0, telemetry);
                } else {
                    robot.driveAll(0,0,0,telemetry);
                    if (skystonesGrabbed <= 2) {
                        step = 7;
                    } else {
                        robot.driveAll(0, 0, 0, telemetry);
                        telemetry.addData("Auto", "Skystone Complete");
                    }
                }
                break;
            case 7:
                telemetry.addData("Auto Step", "7");
                if (robot.checkSkystoneProximity() >  SKYSTONE_FAR_DISTANCE_THRESHOLD) {
                    robot.driveForward(AUTO_FAST_SPEED_FORWARD, telemetry);
                } else {
                    robot.driveAll(0,0,0, telemetry);
                    step = 8;
                }
                break;
            case 8:
                telemetry.addData("Auto Step", "8");
                if (robot.checkBlack() == false){
                    robot.driveAll(0, AUTO_SLOW_SPEED_LEFT, 0, telemetry);
                } else {
                    robot.driveAll(0, 0, 0, telemetry);
                    step = 1;
                }
                break;
        }
    }

}