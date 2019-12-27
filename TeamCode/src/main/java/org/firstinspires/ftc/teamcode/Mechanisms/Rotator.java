package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static org.firstinspires.ftc.teamcode.Util.Constants.*;

class Rotator {
    private DcMotor rotator;

    void init(HardwareMap hwMap) {
        rotator = hwMap.get(DcMotor.class, "rotator");
        rotator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

//        rotator.setPower(1.0);
//        rotator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        rotator.setTargetPosition(0);
    }


    void reportEncoders(Telemetry telemetry) {
        telemetry.addData("Encoder", "%d", (Math.abs(rotator.getCurrentPosition())*(90/83)));
    }

    void setToPostion(double target) {
        double position = (Math.abs(rotator.getCurrentPosition())*(90/83));

        double error = target - position;
        double direction, power;
        if (Math.abs(error) < ROTATOR_INNER_THRESHOLD) {
            power = 0.0;
        } else if (Math.abs(error) > ROTATOR_OUTER_THRESHOLD) {
            power = ROTATOR_HIGH_SPEED;
        } else if (Math.abs(error) < ROTATOR_OUTER_THRESHOLD){
            power = ROTATOR_LOW_SPEED;
        } else {
            power = ROTATOR_LOW_SPEED;
        }

        if (error < 0){
            direction = ROTATOR_UP_MODIFIER;
        } else {
            direction = ROTATOR_DOWN_MODIFIER;
        }

        rotator.setPower(power * direction);
    }
//    void setSpeeds(double rspeed) {
//        double largest = 1.0;
//        largest = Math.max(largest, Math.abs(rspeed));
//
//        rotator.setPower(rspeed / largest);
//    }

    void setPosition(int position) {
        rotator.setTargetPosition(position);
    }
}