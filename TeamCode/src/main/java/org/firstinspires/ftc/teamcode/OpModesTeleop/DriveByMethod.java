package org.firstinspires.ftc.teamcode.OpModesTeleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Mechanisms.Robot;
import org.firstinspires.ftc.teamcode.Mechanisms.BasicMechanisms.DeadzonedAxis;

import static org.firstinspires.ftc.teamcode.Util.Constants.*;

@TeleOp()
public class DriveByMethod extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();

    private Robot robot = new Robot();

    private int targetRotatorPosition = 0;
    private boolean clawOpen = false;

    // Code to run when the driver hits INIT
    @Override
    public void init() {
        robot.init(hardwareMap);
//        robot.setRotatorPosition(ROTATOR_HIGH_THRESHOLD);
    }

    // Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    @Override
    public void loop() {
        robot.reportSensors(telemetry);
        telemetry.addData("Target Rotator", targetRotatorPosition);
        DeadzonedAxis LeftXAxis = DeadzonedAxis.Init(JOYSTICK_1_DEADZONE, X_AXIS, -gamepad1.left_stick_x, gamepad1.left_stick_y);
        DeadzonedAxis LeftYAxis = DeadzonedAxis.Init(JOYSTICK_1_DEADZONE, Y_AXIS, -gamepad1.left_stick_x, gamepad1.left_stick_y);
        DeadzonedAxis RightXAxis = DeadzonedAxis.Init(JOYSTICK_2_DEADZONE, X_AXIS, -gamepad1.right_stick_x, gamepad1.right_stick_y);
        DeadzonedAxis RightYAxis = DeadzonedAxis.Init(JOYSTICK_2_DEADZONE, Y_AXIS, -gamepad1.right_stick_x, gamepad1.right_stick_y);
    }
}
