package org.firstinspires.ftc.teamcode.Mechanisms.BasicMechanisms;

import org.firstinspires.ftc.robotcore.external.Telemetry;

abstract class Device {
    private String description;

    Device(String description) {
        this.description = description;
    }

    String getDescription() {
        return description;
    }

    abstract void run(boolean on, Telemetry telemetry);

}
