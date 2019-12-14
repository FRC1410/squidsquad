package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import static org.firstinspires.ftc.teamcode.Util.Constants.*;


public class Claw {
    private Servo claw;

    void init(HardwareMap hwmap) {
        claw = hwmap.get(Servo.class, "claw");
    }

    void open(){
        claw.setPosition(CLAW_OPEN_POSITION);
    }
    void close() {
        claw.setPosition(CLAW_CLOSED_POSITION);
    }
}
