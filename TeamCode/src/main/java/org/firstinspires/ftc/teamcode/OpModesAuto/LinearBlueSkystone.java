package org.firstinspires.ftc.teamcode.OpModesAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Mechanisms.Robot;
import org.firstinspires.ftc.teamcode.Mechanisms.Lifecam;

import static org.firstinspires.ftc.teamcode.Util.Constants.*;

@Autonomous
public class LinearBlueSkystone extends LinearOpMode {
    private Robot robot = new Robot();
    private Lifecam lifecam = new Lifecam();

    private int step = 1;
    private double firstSkystoneDistance;
    private int skystonesGrabbed = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        /* <Initialization Stage> */
        robot.init(hardwareMap);
        lifecam.init(hardwareMap);

        robot.openClaw();
        robot.setRotatorPosition(0);

        while (opModeIsActive()) {
            /* <Main Stage> */
            robot.setLastHeading();
            switch (step) {
                case 1:
                    telemetry.addData("Auto Step", "0");
                    if (robot.checkSkystoneProximity() > SKYSTONE_CLOSE_DISTANCE_THRESHOLD) {
                        robot.driveForwardStraight(AUTO_FAST_SPEED_FORWARD);
                    } else {
                        robot.driveAll(0, 0, 0, telemetry);
                        step = 2;
                    }
                    break;
                case 2:
                    telemetry.addData("Auto Step", "1");
                    if (robot.checkSkystoneProximity() > SKYSTONE_CLOSE_DISTANCE_THRESHOLD) {
                        robot.driveForwardStraight(AUTO_SLOW_SPEED_FORWARD);
                    } else {
                        robot.driveAll(0, 0, 0, telemetry);
                        step = 3;
                    }
                    break;
//                Case 3 needs to be worked on, essentially it's supposed to say if skystone sensed left, strafe left,
//                else, strafe right to skystone
//                case 3:
//                    telemetry.addData("Auto Step", "3");
//                    if (robot.)
//                        step = 4;
//                    break;
                case 4:
                    telemetry.addData("Auto Step", "4");
                    robot.closeClaw();
                    step = 5;
                    break;
                case 5:
                    telemetry.addData("Auto Step", "5");
                    robot.setRotatorPosition(25);
                    step = 6;
                    break;
                case 6:
                    telemetry.addData("Auto Step", "6");
                    if (robot.checkSkystoneProximity() < SKYSTONE_CLOSE_DISTANCE_THRESHOLD) {
                        robot.driveForwardStraight(AUTO_FAST_SPEED_BACKWARD);
                    } else {
                        robot.driveAll(0, 0, 0, telemetry);
                        step = 7;
                    }
                    break;
                case 7:
                    telemetry.addData("Auto Step", "7");
                    if (robot.checkSkystoneProximity() < SKYSTONE_FAR_DISTANCE_THRESHOLD) {
                        robot.driveForwardStraight(AUTO_SLOW_SPEED_BACKWARD);
                    } else {
                        robot.driveAll(0, 0, 0, telemetry);
                        step = 8;
                    }
                    break;
                case 8:
                    telemetry.addData("Auto Step", "8");
                    if (robot.checkLeftDistance()> 150) {
                    robot.driveStrafe(AUTO_SLOW_SPEED_LEFT);
                    } else {
                        robot.driveAll(0, 0, 0, telemetry);
                        robot.openClaw();
                        step = 9;
                    }
                    break;
                case 9:
                    telemetry.addData("Auto Step", "9");
                    if (robot.checkRightDistance()> 150){
                        robot.driveStrafe(AUTO_SLOW_SPEED_RIGHT);
                    } else {
                    robot.driveAll(0, 0, 0, telemetry);
                    step = 10;
                    }
                    break;
                case 10:
                    telemetry.addData("Auto Step", "10");
                    if (robot.checkSkystoneProximity() > SKYSTONE_CLOSE_DISTANCE_THRESHOLD) {
                        robot.driveForwardStraight(AUTO_FAST_SPEED_FORWARD);
                    } else {
                        robot.driveAll(0, 0, 0, telemetry);
                        step = 11;
                    }
                    break;
                case 11:
                    telemetry.addData("Auto Step", "11");
                    if (robot.checkSkystoneProximity() > SKYSTONE_CLOSE_DISTANCE_THRESHOLD) {
                        robot.driveForwardStraight(AUTO_SLOW_SPEED_FORWARD);
                    } else {
                        robot.driveAll(0, 0, 0, telemetry);
                        step = 12;
                    }
                    break;
                case 12: //This is the only arbitrary value
                    telemetry.addData("Auto Step", "12");
                    robot.driveAll(0, 0, 0, telemetry);
                    step = 13;
                    break;
//                Case 13 needs to be worked on, essentially it's supposed to say if skystone sensed left, strafe left,
//                else, strafe right to skystone
//                case 3:
//                    telemetry.addData("Auto Step", "3");
//                    if (robot.)
//                        step = 14;
//                    break;
                case 14:
                    telemetry.addData("Auto Step", "14");
                    robot.closeClaw();
                    step = 5;
                    break;
                case 15:
                    telemetry.addData("Auto Step", "15");
                    robot.setRotatorPosition(25);
                    step = 16;
                    break;
                case 16:
                    telemetry.addData("Auto Step", "16");
                    if (robot.checkSkystoneProximity() < SKYSTONE_CLOSE_DISTANCE_THRESHOLD) {
                        robot.driveForwardStraight(AUTO_FAST_SPEED_BACKWARD);
                    } else {
                        robot.driveAll(0, 0, 0, telemetry);
                        step = 17;
                    }
                    break;
                case 17:
                    telemetry.addData("Auto Step", "17");
                    if (robot.checkSkystoneProximity() < SKYSTONE_FAR_DISTANCE_THRESHOLD) {
                        robot.driveForwardStraight(AUTO_SLOW_SPEED_BACKWARD);
                    } else {
                        robot.driveAll(0, 0, 0, telemetry);
                        step = 18;
                    }
                    break;
                case 18:
                    telemetry.addData("Auto Step", "18");
                    if (robot.checkLeftDistance()> 150) {
                        robot.driveStrafe(AUTO_SLOW_SPEED_LEFT);
                    } else {
                        robot.driveAll(0, 0, 0, telemetry);
                        step = 19;
                    }
                    break;
                }
            }
            /* <Termination Stage> */

        }
    }
