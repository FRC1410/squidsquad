package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Util.Constants.*;

public class TapeMeasure {
    private DcMotor motor;

    void init(HardwareMap hwMap) {
        motor = hwMap.get(DcMotor.class, "tape_measure");
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    void setSpeed(double speed) {
        double largest = 1.0;

        largest = Math.max(largest, Math.abs(speed));
        motor.setPower(speed / largest);
    }
}
