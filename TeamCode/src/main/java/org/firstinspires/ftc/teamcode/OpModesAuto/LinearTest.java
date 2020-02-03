package org.firstinspires.ftc.teamcode.OpModesAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Mechanisms.Robot;

@Autonomous
public class LinearTest extends LinearOpMode {
    private Robot robot = new Robot();
    private int step = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        robot.setRotatorPosition(0);
        robot.closeClaw();

        waitForStart();

        while (opModeIsActive()) {
            robot.getHeadingAbsolute(telemetry);
            telemetry.addData("Test", "Works");
            telemetry.update();
            switch (step) {
                case 0:
                    telemetry.addData("Auto Step", "0");
                    robot.driveStrafeStraight(0.3);
                    robot.waiting(10000);
                    step = 1;
                    telemetry.update();
                    break;
                case 1:
                    telemetry.addData("Auto Step", "Finsihed");
                    robot.driveAll(0,0,0, telemetry);
                    telemetry.update();
                    break;
            }
            telemetry.update();
            idle();
        }
    }
}
