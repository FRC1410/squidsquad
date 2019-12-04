package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static org.firstinspires.ftc.teamcode.Util.Constants.*;

class Rotator {
    private DcMotor rotator;

    //One of Two Possibilities. If HW Map in Drive Train for rotator does not work, use code bellow
    void init(HardwareMap hwMap) {
        rotator = hwMap.get(DcMotor.class, "rotator");
        rotator.setPower(0.6);
        rotator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }


    void reportEncoders(Telemetry telemetry) {
        telemetry.addData("Encoder", "%d", rotator.getCurrentPosition());
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
