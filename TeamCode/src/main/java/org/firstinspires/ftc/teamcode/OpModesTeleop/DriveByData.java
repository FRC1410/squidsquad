package org.firstinspires.ftc.teamcode.OpModesTeleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Mechanisms.Robot;
import org.firstinspires.ftc.teamcode.Mechanisms.BasicMechanisms.DeadzonedAxis;

import static org.firstinspires.ftc.teamcode.Util.Constants.*;

@TeleOp()
public class DriveByData extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();

    private Robot robot = new Robot();
    // Code to run when the driver hits INIT
    @Override
    public void init() {
        robot.init(hardwareMap);
//        robot.setRotatorPosition(ROTATOR_HIGH_THRESHOLD);
    }

    // Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    @Override
    public void loop() {

    }
}
