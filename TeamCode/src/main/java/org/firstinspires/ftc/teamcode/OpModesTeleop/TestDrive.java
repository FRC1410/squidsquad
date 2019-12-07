package org.firstinspires.ftc.teamcode.OpModesTeleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Mechanisms.Robot;
import org.firstinspires.ftc.teamcode.Mechanisms.BasicMechanisms.DeadzonedAxis;

import static org.firstinspires.ftc.teamcode.Util.Constants.*;

//TODO Rotator
@TeleOp()
public class TestDrive extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();

    private Robot robot = new Robot();
    private int currentRotatorPosition = 0;
    // Code to run when the driver hits INIT
    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    // Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    @Override
    public void loop() {

        robot.reportSensors(telemetry);
        telemetry.addData("Target Rotator", currentRotatorPosition);
        DeadzonedAxis LeftXAxis = DeadzonedAxis.Init(JOYSTICK_1_DEADZONE, X_AXIS, -gamepad1.left_stick_x, gamepad1.left_stick_y);   //Quantum quacks said the y axis is reversed, we'll trust them on it
        DeadzonedAxis LeftYAxis = DeadzonedAxis.Init(JOYSTICK_1_DEADZONE, Y_AXIS, -gamepad1.left_stick_x, gamepad1.left_stick_y);
        DeadzonedAxis RightXAxis = DeadzonedAxis.Init(JOYSTICK_2_DEADZONE, X_AXIS, -gamepad1.right_stick_x, gamepad1.right_stick_y);
        DeadzonedAxis RightYAxis = DeadzonedAxis.Init(JOYSTICK_2_DEADZONE, Y_AXIS, -gamepad1.right_stick_x, gamepad1.right_stick_y);

        double forwardInput = LeftYAxis.GetDeadzonedValue();
        double rotateInput = RightXAxis.GetDeadzonedValue();
        double strafeInput = LeftXAxis.GetDeadzonedValue();
        double armInput = RightYAxis.GetDeadzonedValue();

        boolean aPressed = false;
        boolean bPressed = false;
        boolean xPressed = false;
        boolean yPressed = false;

        boolean leftPressed = false;
        boolean rightPressed = false;

        boolean clawOpen = false;

//        if (Math.abs(forwardInput) > Math.abs(strafeInput)) {
//            robot.driveForwardAndRotate(forwardInput, rotateInput);
//            telemetry.addData("Driving", "Forward");
//        } else if (Math.abs(forwardInput) < Math.abs(strafeInput)) {
//            robot.driveStrafe(strafeInput);
//            telemetry.addData("Driving", "Sideways");
//
//        } else {
//            robot.driveRotate(rotateInput);
//            telemetry.addData("Driving", "Rotating"); //If both magnitudes equal each other, rotate in place.
//        }

        robot.driveAll(forwardInput, strafeInput, rotateInput);
        telemetry.addData("Forward", forwardInput);
        telemetry.addData("Strafe", strafeInput);
        telemetry.addData("Rotate", rotateInput);


        telemetry.addData("Rotate Arm", armInput);

        if (gamepad1.left_bumper && !leftPressed && (currentRotatorPosition + ROTATOR_INCREMENT_DOWN >= ROTATOR_LOW_THRESHOLD)) {//Set claw state to Y button state and check for infinite toggle loops
            currentRotatorPosition += ROTATOR_INCREMENT_DOWN;
            leftPressed = true;
        } else {
            leftPressed = false;
        }

        if (gamepad1.right_bumper && !rightPressed && (currentRotatorPosition + ROTATOR_INCREMENT_UP <= ROTATOR_HIGH_THRESHOLD)) {
            currentRotatorPosition += ROTATOR_INCREMENT_UP;
            rightPressed = true;
        } else {
            rightPressed = false;
        }

        robot.setRotator(currentRotatorPosition);

        if (gamepad1.y && !yPressed) {//Set claw state to Y button state and check for infinite toggle loops
            clawOpen = !clawOpen;
            yPressed = true;
        } else {
            yPressed = false;
        }
        if (clawOpen == true) {
            robot.openClaw();
        } else {
            robot.closeClaw();
        }

//        if (gamepad1.y && !yPressed) {//Set claw state to Y button state and check for infinite toggle loops
//            clawOpen = !clawOpen;
//            telemetry.addData("Claw Open", clawOpen);
//        }
//        yPressed = gamepad1.y;
//        if (clawOpen) {
//            robot.openClaw();
//            telemetry.addData("Claw Open","True");
//        } else {
//            robot.closeClaw();
//            telemetry.addData("Claw Open", "False");
//        }

//    }
        //Elapsed Time Stuff
//    @Override
////    public void start() {
////        runtime.reset();
////    }
////    @Override
//    public void loop() {33
//        telemetry.addData("Status", "Run Time: " + runtime.toString());
//    }
    }
}
