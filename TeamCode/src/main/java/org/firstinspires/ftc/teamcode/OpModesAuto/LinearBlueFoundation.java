package org.firstinspires.ftc.teamcode.OpModesAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Mechanisms.Robot;

import static org.firstinspires.ftc.teamcode.Util.Constants.*;

@Autonomous
//Goes to the left
public class LinearBlueFoundation extends LinearOpMode {
    private Robot robot = new Robot();

    private int step = 0;

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
                    robot.waiting(300); //Is a variable, change when nessisary
                    robot.driveAll(AUTO_FAST_SPEED_FORWARD,0,0, telemetry);
                    robot.waiting(100);
                    robot.driveAll(0,0,0, telemetry);
                    step = 1;
                    telemetry.update();
                    break;
                case 1:
                    telemetry.addData("Step", "1");
                    robot.closeFoundationClaw();
                    if (robot.getHeadingAbsolute(telemetry) < 280 && robot.getHeadingAbsolute(telemetry) > 260) {
                        robot.driveAll(0,0,0, telemetry);
                        robot.openFoundationClaw();
                        step = 2;
                    } else {
                        robot.driveAll(0, 0, AUTO_ROTATE_RIGHT, telemetry);
                    }
                    telemetry.update();
                    break;
                case 2:
                    telemetry.addData("Step", "2");
                    robot.driveForwardStraight(AUTO_FAST_SPEED_BACKWARD);
                    robot.waiting(300);
                    robot.driveAll(0,0,0, telemetry);
                    step = 3;
                    telemetry.update();
                    break;
                case 3:
                    telemetry.addData("Step", "3");
                    if (robot.getHeadingAbsolute(telemetry) < 10) {
                        step = 4;
                    } else {
                        robot.driveAll(0,0,AUTO_ROTATE_LEFT, telemetry);
                    }
                    telemetry.update();
                    break;
                case 4:
                    telemetry.addData("Step", "4");
                    robot.setRotatorPosition(0);
                    if (robot.checkLeftDistance() < 800) {
                        robot.driveStrafeStraight(-AUTO_FAST_SPEED_RIGHT);
                    } else {
                        robot.driveAll(0,0,0, telemetry);
                        step = 5;
                    }
                    telemetry.update();
                    break;
                case 5:
                    telemetry.addData("Step", "5");
                    robot.driveAll(0,0,0, telemetry);
                    telemetry.update();
                    break;
            }
        }
        /* <Termination Stage> */

    }
}