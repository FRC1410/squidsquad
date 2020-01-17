/*If you've made it this far good luck to you*/
package org.firstinspires.ftc.teamcode.OpModesTeleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Mechanisms.BasicMechanisms.DeadzonedAxis;
import org.firstinspires.ftc.teamcode.Mechanisms.Robot;
import static org.firstinspires.ftc.teamcode.Util.Constants.*;

@TeleOp
public class LinearDriveAll extends LinearOpMode {
    //Local Methods and Instances
    private Robot robot = new Robot();

    private double targetRotatorPosition = ROTATOR_HIGH_THRESHOLD;
    private boolean clawOpen = false;
    private boolean foundationClawOpen = true;

    private boolean aPressed = false;
    private boolean bPressed = false;
    private boolean xPressed = false;
    private boolean yPressed = false;

    private boolean leftPressed = false;
    private boolean rightPressed = false;

    boolean triggerThresholdReached = false;
    @Override
    public void runOpMode() throws InterruptedException {
        /* <Initialization Stage> */
        robot.init(hardwareMap);

        waitForStart();
        while (opModeIsActive()) {
            /* <Main Stage> */
            robot.getHeadingAbsolute(telemetry);

            robot.reportSensors(telemetry);
            telemetry.addData("Target Rotator", targetRotatorPosition);
            DeadzonedAxis LeftXAxis = DeadzonedAxis.Init(JOYSTICK_1_DEADZONE, X_AXIS, -gamepad1.left_stick_x, gamepad1.left_stick_y);   //Quantum quacks said the y axis is reversed, we'll trust them on it
            DeadzonedAxis LeftYAxis = DeadzonedAxis.Init(JOYSTICK_1_DEADZONE, Y_AXIS, -gamepad1.left_stick_x, gamepad1.left_stick_y);
            DeadzonedAxis RightXAxis = DeadzonedAxis.Init(JOYSTICK_2_DEADZONE, X_AXIS, -gamepad1.right_stick_x, gamepad1.right_stick_y);
            DeadzonedAxis RightYAxis = DeadzonedAxis.Init(JOYSTICK_2_DEADZONE, Y_AXIS, -gamepad1.right_stick_x, gamepad1.right_stick_y);

            double forwardInput = LeftYAxis.GetDeadzonedValue();
            double rotateInput = RightXAxis.GetDeadzonedValue();
            double strafeInput = LeftXAxis.GetDeadzonedValue();
            double armInput = RightYAxis.GetDeadzonedValue();
            telemetry.addData("Left X", strafeInput);
            telemetry.addData("Left Y", forwardInput);
            telemetry.addData("Right X", rotateInput);
            telemetry.addData("Right Y", armInput);

            robot.driveAll(forwardInput, strafeInput, rotateInput, telemetry);
            telemetry.addData("Forward", forwardInput);
            telemetry.addData("Strafe", strafeInput);
            telemetry.addData("Rotate", rotateInput);


            telemetry.addData("Rotate Arm", armInput);

            if (gamepad1.left_bumper && !leftPressed && (targetRotatorPosition + ROTATOR_INCREMENT_DOWN >= ROTATOR_LOW_THRESHOLD)) {//Set claw state to Y button state and check for infinite toggle loops
                targetRotatorPosition += ROTATOR_INCREMENT_DOWN;
                leftPressed = true;
            } else {
                leftPressed = false;
            }

            if (gamepad1.right_bumper && !rightPressed && (targetRotatorPosition + ROTATOR_INCREMENT_UP <= ROTATOR_HIGH_THRESHOLD)) {
                targetRotatorPosition += ROTATOR_INCREMENT_UP;
                rightPressed = true;
            } else {
                rightPressed = false;
            }

            robot.setRotatorPosition(targetRotatorPosition);
            telemetry.addData("Target Rotator Position", targetRotatorPosition);
            telemetry.addData("Current Rotator Position", robot.rotatorEncoder());

            if (gamepad1.right_trigger > RIGHT_TRIGGER_THRESHOLD && !triggerThresholdReached) {//Set claw state to Y button state and check for infinite toggle loops
                clawOpen = !clawOpen;
                triggerThresholdReached = true;
            }
            if (gamepad1.right_trigger < RIGHT_TRIGGER_THRESHOLD){
                triggerThresholdReached = false;
            }

            if (clawOpen == true) {
                robot.openClaw();
            } else {
                robot.closeClaw();
            }

            if (gamepad1.a && !aPressed) {//Set claw state to Y button state and check for infinite toggle loops
                foundationClawOpen = !foundationClawOpen;
                aPressed = true;
            }
            if (gamepad1.a == false){
                aPressed = false;
            }

            if (foundationClawOpen == true) {
                robot.openFoundationClaw();
            } else {
                robot.closeFoundationClaw();
            }
            telemetry.update();
            idle();
        }
        /* <Termination Stage> */
        robot.driveAll(0, 0, 0, telemetry);
        robot.setRotatorPosition(ROTATOR_HIGH_THRESHOLD);
    }
}