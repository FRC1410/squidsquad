package org.firstinspires.ftc.teamcode.OpModesAuto;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Mechanisms.Robot;

import static org.firstinspires.ftc.teamcode.Util.Constants.*;

@Autonomous()
public class ForwardBackwardTest extends OpMode {
    private Robot robot = new Robot();

    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {
        robot.reportSensors(telemetry);
        robot.driveForward(AUTO_FAST_SPEED_FORWARD, telemetry);
        robot.waiting(500);
        robot.driveForward(-AUTO_FAST_SPEED_FORWARD, telemetry);
        robot.waiting(500);
    }
}