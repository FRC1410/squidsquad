package org.firstinspires.ftc.teamcode.OpModesAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Mechanisms.Robot;
import org.firstinspires.ftc.teamcode.Mechanisms.Lifecam;

import static org.firstinspires.ftc.teamcode.Util.Constants.*;

@Autonomous
public class LinearSkystoneSearch extends LinearOpMode {
    private Robot robot = new Robot();
    private Lifecam lifecam = new Lifecam();

    @Override
    public void runOpMode() throws InterruptedException {
        /* <Initialization Stage> */
        robot.init(hardwareMap);
        lifecam.init(hardwareMap);

        while (opModeIsActive()) {
            double distance = lifecam.whereIsSkystone(telemetry);
            double speed = (distance * 0.008);
            telemetry.addData("Distance", distance);
            if (distance != -2000) {
                robot.driveStrafeStraight(speed);
                telemetry.addData("Traveling", speed);
            } else {
                robot.driveStrafeStraight(0);
                telemetry.addData("Traveling", "Not Found");
            }
//        }
        }

    }
}

