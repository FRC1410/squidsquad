package org.firstinspires.ftc.teamcode.OpModesTeleop;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

import com.qualcomm.robotcore.hardware.HardwareMap;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static org.firstinspires.ftc.teamcode.Util.Constants.*;

@TeleOp
public class LinearMechConstruct extends LinearOpMode {

    /* <Device Constructors> */
    //Motors
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;
    private DcMotor rotator;

    //Servos
    private Servo claw;
    private Servo foundationClaw;

    //Sensors
    private DistanceSensor leftDistanceSensor;
    private DistanceSensor rightDistanceSensor;
    private ColorSensor colorSensor;
    private DistanceSensor proximitySensor;

    private BNO055IMU imu;
    private BNO055IMU.Parameters parameters;

    /* <Method Constructors> */
    //Drivetrain Motors
    void setSpeeds(double flSpeed, double frSpeed, double blSpeed, double brSpeed) {
        double largest = 1.0;
        largest = Math.max(largest, Math.abs(flSpeed));
        largest = Math.max(largest, Math.abs(frSpeed));
        largest = Math.max(largest, Math.abs(blSpeed));
        largest = Math.max(largest, Math.abs(brSpeed));

        frontLeftMotor.setPower(flSpeed / largest);
        frontRightMotor.setPower(frSpeed / largest);
        backLeftMotor.setPower(blSpeed / largest);
        backRightMotor.setPower(brSpeed / largest);
    }

    public void driveAll(double fSpeed, double sSpeed, double rSpeed, Telemetry telemetry) {
        setSpeeds((fSpeed + sSpeed + rSpeed), (fSpeed + sSpeed - rSpeed), (fSpeed - sSpeed + rSpeed), (fSpeed - sSpeed - rSpeed));
        //telemetry.addData("Speeds", "%d %d %d", fSpeed, sSpeed, rSpeed);
    }

    //Rotator
    void setRotatorPosition(double target) {
        double position = (Math.abs(rotator.getCurrentPosition())*(90/83));

        double error = target - position;
        double direction, power;

        if (Math.abs(error) < ROTATOR_INNER_THRESHOLD) {
            power = 0.0;
        } else if (Math.abs(error) > ROTATOR_OUTER_THRESHOLD) {
            power = ROTATOR_HIGH_SPEED;
        } else if (Math.abs(error) < ROTATOR_OUTER_THRESHOLD) {
            power = ROTATOR_MEDIUM_SPEED;
            if (Math.abs(error) < ROTATOR_MEDIUM_THRESHOLD){
                power = ROTATOR_LOW_SPEED;
            }
        } else {
            power = ROTATOR_LOW_SPEED;
        }

        if (error < 0) {
            direction = ROTATOR_DOWN_MODIFIER;
        } else {
            direction = ROTATOR_UP_MODIFIER;
        }

        rotator.setPower(power * direction);
    }

    /* <Local Button/Threshold Variables> */
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
        //Drivetrain Motors
        frontLeftMotor = hardwareMap.get(DcMotor.class, "front_left");
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor = hardwareMap.get(DcMotor.class, "front_right");
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor = hardwareMap.get(DcMotor.class, "back_left");
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor = hardwareMap.get(DcMotor.class, "back_right");
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        //Rotator
        rotator = hardwareMap.get(DcMotor.class, "rotator");
        rotator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Servos
        claw = hardwareMap.get(Servo.class, "claw");

        foundationClaw = hardwareMap.get(Servo.class, "foundation_claw");

        //Sensors
        leftDistanceSensor = hardwareMap.get(DistanceSensor.class, "left_distance_sensor");
        rightDistanceSensor = hardwareMap.get(DistanceSensor.class, "right_distance_sensor");
        colorSensor = hardwareMap.get(ColorSensor.class, "color_sensor");
        proximitySensor = hardwareMap.get(DistanceSensor.class, "color_sensor");

        parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;
        parameters.mode = BNO055IMU.SensorMode.IMU;

        imu = hardwareMap.get(BNO055IMU.class, "imu 1");

        imu.initialize(parameters);

        while  (opModeIsActive()) {
            /* <Main Stage> */
            telemetry.addData("Target Rotator", targetRotatorPosition);
            double LeftXAxis = -gamepad1.left_stick_x;
            double LeftYAxis = gamepad1.left_stick_y;
            double RightXAxis = -gamepad1.right_stick_x;
            double RightYAxis = gamepad1.right_stick_y;

            telemetry.addData("Left X", LeftXAxis);
            telemetry.addData("Left Y", LeftYAxis);
            telemetry.addData("Right X", RightXAxis);
            telemetry.addData("Right Y", RightYAxis);

            driveAll(LeftYAxis, LeftXAxis, RightXAxis, telemetry);
            telemetry.addData("Forward", LeftYAxis);
            telemetry.addData("Strafe", LeftXAxis);
            telemetry.addData("Rotate", RightXAxis);

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

            setRotatorPosition(targetRotatorPosition);

            if (gamepad1.right_trigger > RIGHT_TRIGGER_THRESHOLD && !triggerThresholdReached) {//Set claw state to Y button state and check for infinite toggle loops
                clawOpen = !clawOpen;
                triggerThresholdReached = true;
            }
            if (gamepad1.right_trigger < RIGHT_TRIGGER_THRESHOLD){
                triggerThresholdReached = false;
            }

            if (clawOpen == true) {
                claw.setPosition(CLAW_OPEN_POSITION);
            } else {
                claw.setPosition(CLAW_CLOSED_POSITION);
            }

            if (gamepad1.a && !aPressed) {//Set claw state to Y button state and check for infinite toggle loops
                foundationClawOpen = !foundationClawOpen;
                aPressed = true;
            }
            if (gamepad1.a == false){
                aPressed = false;
            }

            if (foundationClawOpen == true) {
                foundationClaw.setPosition(CLAW_OPEN_POSITION);
            } else {
                foundationClaw.setPosition(CLAW_CLOSED_POSITION);
            }

            idle();
        }
        /* <Termination Stage> */

        driveAll(0, 0, 0, telemetry);
        rotator.setPower(0);
    }
}
