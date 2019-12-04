package org.firstinspires.ftc.teamcode.Mechanisms.BasicMechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

class Motor extends Device {
    private double speed;

    Motor(String description, double speed, DcMotor motor) {
        super(description);
        this.speed = speed;
        this.motor = motor;
    }

    private DcMotor motor;

    @Override
    public void run(boolean on, Telemetry telemetry) {
        if (on){
            motor.setPower(speed);
        } else {
            motor.setPower(0.0);
        }
        telemetry.addData("Encoders:", motor.getCurrentPosition());
    }
}
