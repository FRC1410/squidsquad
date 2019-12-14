package org.firstinspires.ftc.teamcode.Mechanisms.BasicMechanisms;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

class ServoMotor extends Device {
    private double offLocation;
    private double onLocation;
    private Servo servo;

    ServoMotor(String description, double offLocation, double onLocation, Servo servo) {
        super(description);
        this.offLocation = offLocation;
        this.onLocation = onLocation;
        this.servo = servo;
    }

    @Override
    void run(boolean on, Telemetry telemetry) {
        if (on){
            servo.setPosition(onLocation);
        } else {
            servo.setPosition(offLocation);
        }
        telemetry.addData("Location", servo.getPosition());
    }
}
