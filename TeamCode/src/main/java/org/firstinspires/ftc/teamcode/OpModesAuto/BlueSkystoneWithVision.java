package org.firstinspires.ftc.teamcode.OpModesAuto;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Mechanisms.Robot;

import static org.firstinspires.ftc.teamcode.Util.Constants.*;

@Autonomous()
public class BlueSkystoneWithVision extends OpMode {
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
        robot.getHeadingAbsolute(telemetry);

        switch (step) {
            case 0:
                telemetry.addData("Auto Step", "0");
                if (false) {                         //TODO: Robot is not aligned properly with left skystone
                    robot.driveAll(0, AUTO_SLOW_SPEED_LEFT, 0, telemetry);
                } else {
                    robot.driveAll(0, 0, 0, telemetry);
                    step = 1;
                }
            break;
            case 1:
                telemetry.addData("Auto Step", "1");
                if (robot.checkSkystoneProximity() < SKYSTONE_CLOSE_DISTANCE_THRESHOLD) {
                    robot.driveForward(AUTO_SLOW_SPEED_FORWARD, telemetry);
                } else {
                    robot.driveForward(0, telemetry);
                    step = 2;
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
                if (robot.checkRightDistance() == 819.0){                              //robot.checkRightDistance() < SKYSTONE_DROP_POINT
                    step = 4;                                                                       //robot.checkRightDistance() >= DISTANCE_THRESHOLD
                } else {
                    robot.driveAll(0, AUTO_FAST_SPEED_LEFT,0, telemetry);
                }
            break;
            case 4:


        }
    }

}