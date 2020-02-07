package org.firstinspires.ftc.teamcode.OpModesAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Mechanisms.Robot;

import static org.firstinspires.ftc.teamcode.Util.Constants.*;

@Autonomous
//Goes to the right

public class LinearForwardScoot extends LinearOpMode {
    private Robot robot = new Robot();

    private int step = 1;

    @Override
    public void runOpMode() throws InterruptedException {
        /* <Initialization Stage> */
        robot.init(hardwareMap);
        robot.setRotatorPosition(0);
        robot.closeClaw();
        waitForStart();

        while (opModeIsActive()) {
            /* <Main Stage> */
            switch (step) {
                case 1:
//                    robot.driveAll(AUTO_FAST_SPEED_FORWARD, 0, 0, telemetry);
//                    robot.waiting(400);
//                    robot.driveAll(0, 0, 0, telemetry);
                    robot.runTapeMeasure(0.7, telemetry);
                    robot.waiting(5700);
                    robot.runTapeMeasure(0, telemetry);
                    step = 2;
                    break;
                case 2:
                    robot.driveAll(0,0,0, telemetry);
            }
//            switch (step) {
//                case 0:
//                    telemetry.addData("Step", "0");
//                    robot.driveAll(AUTO_FAST_SPEED_FORWARD,0,0, telemetry);
//                    robot.waiting(400);
//                    robot.driveAll(0,0,0, telemetry);
//                    step = 1;
//                    telemetry.update();
//                    break;
//                case 1:
//                    telemetry.addData("Step", "1");
//                    if (robot.checkLeftDistance() < 800) {
//                        robot.driveAll(0, AUTO_FAST_SPEED_LEFT, 0, telemetry);
//                    } else {
//                        robot.driveAll(0, 0, 0, telemetry);
//                        step = 2;
//                    }
//                    telemetry.update();
//                    break;
//                case 2:
//                    telemetry.addData("Step", "2");
//                    robot.driveAll(0,0,0,telemetry);
//                    telemetry.update();
//                    break;
//            }
        }
        /* <Termination Stage> */
    }
}

