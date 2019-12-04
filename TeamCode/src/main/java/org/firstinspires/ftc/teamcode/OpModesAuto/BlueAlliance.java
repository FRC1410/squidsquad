package org.firstinspires.ftc.teamcode.OpModesAuto;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Mechanisms.Robot;

import static org.firstinspires.ftc.teamcode.Util.Constants.*;

@Autonomous()
public class BlueAlliance extends OpMode {
    private Robot robot = new Robot();
    private int step = 0;
    private double firstSkystoneDistance;
    @Override
    public void init() {
        robot.init(hardwareMap);

        robot.openClaw();
    }

    @Override
    public void loop(){
        robot.checkRightDistance();
        switch (step) {
            case 0: //What it Does:
                if (robot.checkSkystoneProximity() >  SKYSTONE_FAR_DISTANCE_THRESHOLD) {
                    robot.driveForward(AUTO_FAST_SPEED);
                } else {
                    robot.driveForward(0);
                    step = 1;
                }
                break;
            case 1: //What it Does:
                if (robot.checkSkystoneProximity() > SKYSTONE_CLOSE_DISTANCE_THRESHOLD) {
                    robot.driveForward(AUTO_SLOW_FORWARD_SPEED);
                    robot.driveForward(AUTO_STOP);
                    robot.closeClaw();
                    robot.driveForward(AUTO_BACKWARD_SPEED);
                    firstSkystoneDistance = robot.checkRightDistance();
                } else {
                    step = 2;
                }
                break;
            case 2: //What it Does:
                robot.driveForward(AUTO_BACKWARD_SPEED);
                robot.waiting(100);
                robot.driveForward(AUTO_STOP);
                step = 3;
                break;
            case 3:
                robot.driveAll(0,AUTO_FAST_SPEED_LEFT,0);
                if (robot.checkRightDistance() >= SKYSTONE_DROP_POINT){
                    robot.driveAll(0,0,0);
                    step = 4;
                }
                break;
            case 4:
                robot.openClaw();
                step = 5;
                break;
            case 5:
                robot.driveAll(0,AUTO_FAST_SPEED_RIGHT,0);
                if (robot.checkRightDistance() <= firstSkystoneDistance - SKYSTONE_OFFSET) {
                    robot.driveAll(0,0,0);
                    step = 6;
                }
                break;
            case 6:
                if (robot.checkSkystoneProximity() >  SKYSTONE_FAR_DISTANCE_THRESHOLD) {
                    robot.driveForward(AUTO_FAST_SPEED);
                } else {
                    robot.driveForward(0);
                    step = 7;
                }
                break;
            case 7:
                robot.driveAll(0,AUTO_SLOW_SPEED_RIGHT,0);
                if (robot.checkBlack() == true){
                    step = 1;
                }
                break;
            case 8:
                robot.driveAll(0,0,0);
        }
    }

}
