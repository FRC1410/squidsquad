package org.firstinspires.ftc.teamcode.OpModesAuto;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Mechanisms.Robot;

import static org.firstinspires.ftc.teamcode.Util.Constants.*;

@Autonomous()
public class BlueFoundation extends OpMode {
    private Robot robot = new Robot();
    private ElapsedTime runtime = new ElapsedTime();

    private int step = 1;
    private double firstSkystoneDistance;
    private int skystonesGrabbed = 0;

    @Override
    public void init() {
        robot.init(hardwareMap);
        runtime.reset();

        robot.openClaw();
    }

    @Override
    public void loop() {
        robot.reportSensors(telemetry);
        robot.getHeadingAbsolute(telemetry);
        telemetry.addData("Auto", "Run Time: " + runtime.toString());
        switch (step) {
            case 1:
                telemetry.addData("Auto Step", "1");
                robot.openFoundationClaw();
                if (robot.checkLeftDistance() > FOUNDATION_DISTANCE_THRESHOLD) {
                    robot.driveAll(0, AUTO_FAST_SPEED_LEFT, 0, telemetry);
                } else {
                    robot.driveAll(0, 0, 0, telemetry);
                    step = 2;
                }
                break;
            case 2:
                telemetry.addData("Auto Step", "2");
                if (robot.getHeadingAbsolute(telemetry) < 180) {
                    robot.driveAll(0, 0, AUTO_ROTATE_RIGHT, telemetry);
                } else if (robot.getHeadingAbsolute(telemetry) > 180) {
                    robot.driveAll(0, 0, AUTO_ROTATE_LEFT, telemetry);
                } else {
                    step = 3;
                }
                break;
            case 3:
                telemetry.addData("Auto Step", "3");
                robot.driveAll(AUTO_FAST_SPEED_BACKWARD, 0, 0, telemetry);
                robot.waiting(250);
                robot.driveAll(0, 0, 0, telemetry);
                step = 4;
                break;
            case 4:
                telemetry.addData("Auto Step", "4");
                robot.closeFoundationClaw();
                robot.driveAll(AUTO_FAST_SPEED_FORWARD, 0, 0, telemetry);
                robot.waiting(320);
                robot.driveAll(0, 0, 0, telemetry);
                step = 5;
                break;
            case 5:
                telemetry.addData("Auto Step", "5");
                if (robot.checkRightDistance() > FOUNDATION_CORNER_THRESHOLD) {
                    robot.driveAll(0, AUTO_STRAFE_RIGHT, 0, telemetry);
                } else {
                    robot.driveAll(0, 0, 0, telemetry);
                    robot.openFoundationClaw();
                    step = 6;
                }
                break;
            case 6:
                telemetry.addData("Auto Step", "6");
                if (robot.getHeadingAbsolute(telemetry) < 180) {
                    robot.driveAll(0, 0, AUTO_ROTATE_RIGHT, telemetry);
                } else if (robot.getHeadingAbsolute(telemetry) > 180) {
                    robot.driveAll(0, 0, AUTO_ROTATE_LEFT, telemetry);
                } else {
                    step = 7;
                }
            case 7:
                telemetry.addData("Auto Step", "7");
                if (robot.checkLeftDistance() < 819) {
                    robot.driveAll(0, AUTO_FAST_SPEED_RIGHT, 0, telemetry);
                } else if (robot.checkRightDistance() < 819) {
                    robot.driveAll(0, AUTO_FAST_SPEED_LEFT, 0, telemetry);
                } else {
                    robot.driveAll(0, 0, 0, telemetry);
                    telemetry.addData("Auto", "Finsihed");
                }
        }
    }
}
