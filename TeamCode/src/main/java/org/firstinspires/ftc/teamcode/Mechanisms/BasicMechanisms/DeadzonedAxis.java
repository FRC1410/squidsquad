package org.firstinspires.ftc.teamcode.Mechanisms.BasicMechanisms;

public class DeadzonedAxis {
    private double deadzone;
    private double axis;
    private double axis1;
    private double axis2;

    DeadzonedAxis(double deadzone, double axis, double axis1, double axis2) {
        this.deadzone = deadzone;
        this.axis = axis;
        this.axis1 = axis1;
        this.axis2 = axis2;
    }

    public static DeadzonedAxis Init(double deadzone, double axis, double axis1, double axis2){
        return new DeadzonedAxis(deadzone, axis, axis1, axis2);
    }

    public double GetHypotenuse() {
        return (Math.sqrt(Math.pow(axis1, 2) + Math.pow(axis2, 2)));
    }

    public double GetDeadzonedValue() {
        if (GetHypotenuse() <= deadzone) {
            return 0;
        } else {
            if (axis == 0) {
                return ((GetHypotenuse() - deadzone) / (1 - deadzone)) * (axis1 / GetHypotenuse());
            } else if (axis == 1) {
                return ((GetHypotenuse() - deadzone) / (1 - deadzone)) * (axis2 / GetHypotenuse());
            }
        }

        return 0;
    }

}
