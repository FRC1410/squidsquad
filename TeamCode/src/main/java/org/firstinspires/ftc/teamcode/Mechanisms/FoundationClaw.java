package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static org.firstinspires.ftc.teamcode.Util.Constants.*;


public class FoundationClaw {
    private DcMotor foundationClaw;

    void init(HardwareMap hwMap) {
        foundationClaw = hwMap.get(DcMotor.class, "foundation_claw");
        foundationClaw.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    void reportEncoders(Telemetry telemetry) {
        telemetry.addData("Encoder Foundation", "%d", (Math.abs(foundationClaw.getCurrentPosition())*(90/83)));
    }

    void open(){
        double position = (Math.abs(foundationClaw.getCurrentPosition())*(90/83));

        double error = FOUNDATION_CLAW_OPEN_POSITION - position;
        double direction, power;

        if (Math.abs(error) < FOUNDATION_CLAW_INNER_THRESHOLD) {
            power = 0.0;
        } else if (Math.abs(error) > FOUNDATION_CLAW_OUTER_THRESHOLD) {
            power = FOUNDATION_CLAW_HIGH_SPEED;
        } else if (Math.abs(error) < FOUNDATION_CLAW_OUTER_THRESHOLD) {
            power = FOUNDATION_CLAW_MEDIUM_SPEED;

            if (Math.abs(error) < FOUNDATION_CLAW_MEDIUM_THRESHOLD){
                power = FOUNDATION_CLAW_LOW_SPEED;
            }
        } else {
            power = FOUNDATION_CLAW_LOW_SPEED;
        }

        if (error < 0) {
            direction = FOUNDATION_CLAW_DOWN_MODIFIER;
        } else {
            direction = FOUNDATION_CLAW_UP_MODIFIER;
        }

        foundationClaw.setPower(power * direction);
    }
    void close() {
        double position = (Math.abs(foundationClaw.getCurrentPosition())*(90/83));

        double error = FOUNDATION_CLAW_CLOSED_POSITION - position;
        double direction, power;

        if (Math.abs(error) < FOUNDATION_CLAW_INNER_THRESHOLD) {
            power = 0.0;
        } else if (Math.abs(error) > FOUNDATION_CLAW_OUTER_THRESHOLD) {
            power = FOUNDATION_CLAW_HIGH_SPEED;
        } else if (Math.abs(error) < FOUNDATION_CLAW_OUTER_THRESHOLD) {
            power = FOUNDATION_CLAW_MEDIUM_SPEED;
            if (Math.abs(error) < FOUNDATION_CLAW_MEDIUM_THRESHOLD){
                power = FOUNDATION_CLAW_LOW_SPEED;
            }
        } else {
            power = FOUNDATION_CLAW_LOW_SPEED;
        }

        if (error < 0) {
            direction = FOUNDATION_CLAW_DOWN_MODIFIER;
        } else {
            direction = FOUNDATION_CLAW_UP_MODIFIER;
        }

        foundationClaw.setPower(power * direction);
    }
}
