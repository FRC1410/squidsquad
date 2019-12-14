package org.firstinspires.ftc.teamcode.OpModesAuto;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Mechanisms.Robot;

import static org.firstinspires.ftc.teamcode.Util.Constants.*;

@Autonomous()
public class TestingOnly extends OpMode {
    private Robot robot = new Robot();

    @Override
    public void init() {
        robot.init(hardwareMap);
        robot.openClaw();
    }

    @Override
    public void loop() {
        robot.reportSensors(telemetry);
        robot.getHeadingAbsolute(telemetry);

        if (robot.checkRightDistance() < FAR_DISTANCE_THRESHOLD){                              //robot.checkRightDistance() < SKYSTONE_DROP_POINT
            //robot.driveAll(0,AUTO_FAST_SPEED_LEFT,0, telemetry);
            telemetry.addData("Test", "Success");
        } else {
            telemetry.addData("Test", "Is in else statement");
        }
    }

}