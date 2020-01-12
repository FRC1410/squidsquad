package org.firstinspires.ftc.teamcode.OpModesAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Mechanisms.Robot;
import org.firstinspires.ftc.teamcode.Mechanisms.Lifecam;

import static org.firstinspires.ftc.teamcode.Util.Constants.*;

@Autonomous
//Goes to the left
public class LinearBlueFoundation extends LinearOpMode {
    private Robot robot = new Robot();

    private int step = 2;

    @Override
    public void runOpMode() throws InterruptedException {
        /* <Initialization Stage> */
        robot.init(hardwareMap);
        waitForStart();

        while (opModeIsActive()) {
            /* <Main Stage> */
            switch (step) {
                case 0:
                    telemetry.addData("Step", "0");
                    robot.driveForwardStraight(AUTO_FAST_SPEED_BACKWARD);
                    robot.waiting(300);
                    telemetry.update();
                    step = 1;
                    break;
                case 1:
                    telemetry.addData("Step", "1");
                    if (robot.checkRightDistance() > 50) {
                        robot.driveAll(0,AUTO_FAST_SPEED_LEFT,0, telemetry);
                    } else {
                        robot.driveAll(0,0,0, telemetry);
                        step = 2;
                    }
                    telemetry.update();
                    break;
                case 2:
                    telemetry.addData("Step", "2");
                    robot.driveForwardStraight(AUTO_FAST_SPEED_BACKWARD);
                    robot.waiting(2000);
                    robot.driveAll(0,0,0, telemetry);
                    robot.closeFoundationClaw();
                    step = 3;
                    telemetry.update();
                    break;
                case 3:
                    telemetry.addData("Step", "3");
                    robot.driveForwardStraight(AUTO_FAST_SPEED_FORWARD);
                    robot.waiting(500);
                    robot.driveAll(0,0,0, telemetry);
                    step = 4;
                    telemetry.update();
                    break;
                case 4:
                    telemetry.addData("Step", "4");
                    if (robot.getHeadingAbsolute(telemetry) < 280 && robot.getHeadingAbsolute(telemetry) > 260) {
                        robot.driveAll(0,0,0, telemetry);
                        robot.openFoundationClaw();
                        step = 5;
                    } else {
                        robot.driveAll(0, -AUTO_SLOW_SPEED_LEFT, AUTO_ROTATE_LEFT, telemetry);
                    }
                    telemetry.update();
                    break;
                case 5:
                    telemetry.addData("Step", "5");
                    if (robot.getHeadingAbsolute(telemetry) < 190 && robot.getHeadingAbsolute(telemetry) > 170){
                        robot.driveAll(0,0,0, telemetry);
                        step = 6;
                    } else {
                        robot.driveAll(0,0,AUTO_SLOW_SPEED_LEFT, telemetry);
                    }
                    telemetry.update();
                    break;
                case 6:
                    telemetry.addData("Step", "6");
                    if (robot.checkLeftDistance() < 800) {
                        robot.driveForwardStraight(-AUTO_FAST_SPEED_RIGHT);
                    } else {
                        robot.driveAll(0,0,0, telemetry);
                    }
                    telemetry.update();
                    break;
            }
        }
        /* <Termination Stage> */

    }
}