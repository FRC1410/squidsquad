package org.firstinspires.ftc.teamcode.OpModesAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Mechanisms.Robot;
import org.firstinspires.ftc.teamcode.Mechanisms.SkystoneSearch;

import static org.firstinspires.ftc.teamcode.Util.Constants.*;

@Autonomous
public class LinearBlueSkystone extends LinearOpMode {
    private Robot robot = new Robot();
    private SkystoneSearch lifecam = new SkystoneSearch();

    private int step = -1;
    private double firstSkystoneDistance;
    private int skystonesGrabbed = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        /* <Initialization Stage> */
        robot.init(hardwareMap);


        robot.openClaw();
        robot.setRotatorPosition(0);
        robot.setLastHeading();
        waitForStart();

        while (opModeIsActive()) {
            /* <Main Stage> */
            lifecam.vision(hardwareMap, telemetry);
            switch (step) {
                case -1:
                    telemetry.addData("Auto Step", "-1");
                    robot.driveAll(AUTO_FAST_SPEED_FORWARD, 0, 0, telemetry);
                    robot.waiting(300);
                    robot.driveAll(0, 0, 0, telemetry);
                    if (lifecam.vision(hardwareMap, telemetry)) {
                        step = 0;
                    } else {
                        robot.driveAll(0, -AUTO_SLOW_SPEED_RIGHT, 0, telemetry);
                    }
                    telemetry.update();
                    break;
                case 0:
                    telemetry.addData("Auto Step", "0");
                    if (lifecam.Y < SKYSTONE_Y_LOCATION) robot.driveAll(0,-AUTO_SLOW_SPEED_LEFT,0, telemetry);
                    else if (lifecam.Y > SKYSTONE_Y_LOCATION) robot.driveAll(0,-AUTO_SLOW_SPEED_RIGHT,0, telemetry);
                    else step = 1;
                    telemetry.update();
                    break;
                case 1:
                    telemetry.addData("Auto Step", "1");
                    if (robot.checkSkystoneProximity() > SKYSTONE_FAR_DISTANCE_THRESHOLD) {
                        robot.driveForwardStraight(AUTO_FAST_SPEED_FORWARD);
                    } else {
                        robot.driveAll(0, 0, 0, telemetry);
                        step = 2;
                    }
                    telemetry.update();
                    break;
                case 2:
                    telemetry.addData("Auto Step", "2");
                    if (robot.checkSkystoneProximity() > SKYSTONE_CLOSE_DISTANCE_THRESHOLD) {
                        robot.driveForwardStraight(AUTO_SLOW_SPEED_FORWARD);
                    } else {
                        robot.driveAll(0, 0, 0, telemetry);
                        step = 4;
                    }
                    telemetry.update();
                    break;
                case 4:
                    telemetry.addData("Auto Step", "4");
                    robot.closeClaw();
                    firstSkystoneDistance = robot.checkLeftDistance();
                    step = 7;
                    telemetry.update();
                    break;
//                case 5:
//                    telemetry.addData("Auto Step", "5");
//                    robot.setRotatorPosition(25);
//                    step = 6;
//                    break;
//                case 6:
//                    telemetry.addData("Auto Step", "6");
//                    if (robot.checkSkystoneProximity() < SKYSTONE_CLOSE_DISTANCE_THRESHOLD) {
//                        robot.driveForwardStraight(AUTO_FAST_SPEED_BACKWARD);
//                    } else {
//                        robot.driveAll(0, 0, 0, telemetry);
//                        step = 7;
//                    }
//                    break;
                case 7:
                    telemetry.addData("Auto Step", "7");
                    robot.driveForwardStraight(AUTO_FAST_SPEED_BACKWARD);
                    robot.waiting(400);
                    robot.driveAll(0, 0, 0, telemetry);
                    step = 8;
                    telemetry.update();
                    break;
                case 8:
                    telemetry.addData("Auto Step", "8");
                    if (robot.checkRightDistance() < 800) {
                        robot.driveAll(0, 0, 0, telemetry);
                        robot.openClaw();
                        step = 9;
                    } else {
                        robot.driveStrafe(-AUTO_FAST_SPEED_RIGHT);
                    }
                    telemetry.update();
                    break;
                case 9:
                    telemetry.addData("Auto Step", "9");
                    if (robot.checkLeftDistance() > firstSkystoneDistance) {
                        robot.driveStrafe(-AUTO_SLOW_SPEED_LEFT);
                    } else {
                        robot.driveAll(0, 0, 0, telemetry);
                        skystonesGrabbed++;
                        if (skystonesGrabbed < 3) {
                            step = 0;
                        } else {
                            step = 10;
                        }
                    }
                    telemetry.update();
                    break;
                case 10:
                    telemetry.addData("Auto Step", "10");
                    if (robot.checkLeftDistance() < 800) {
                        robot.driveAll(0, -AUTO_FAST_SPEED_RIGHT, 0, telemetry);
                    } else {
                        robot.driveAll(0, 0, 0, telemetry);
                        telemetry.addData("Auto Step", "Finished");
                        telemetry.update();
                    }
                    telemetry.update();
                    break;
            }
            /* <Termination Stage> */

        }
    }
}
