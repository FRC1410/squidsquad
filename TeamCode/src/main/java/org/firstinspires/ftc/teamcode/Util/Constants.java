package org.firstinspires.ftc.teamcode.Util;

//import static org.firstinspires.ftc.teamcode.Util.Constants.*;

public final class Constants {
    //Controller Interface
    public static final double JOYSTICK_1_DEADZONE = 0.1;
    public static final double JOYSTICK_2_DEADZONE = 0.1;

    public static final int X_AXIS = 0;
    public static final int Y_AXIS = 1;

    //Rotator Constants
    public static final int ROTATOR_INCREMENT_UP = 45;
    public static final int ROTATOR_INCREMENT_DOWN = -45;

    public static final int ROTATOR_LOW_THRESHOLD = 0;
    public static final int ROTATOR_HIGH_THRESHOLD = 90;

    //Claw constants
    public static final double CLAW_OPEN_POSITION = 0.0;
    public static final double CLAW_CLOSED_POSITION = 1.0;

    //IMU Constants
    public static final double HEADING_OFFSET = 0;

    //Color Sensor
    public static final double COLOR_SCALE_VALUE = 255;

    public static final double YELLOW_RED_THRESHOLD = 150;
    public static final double YELLOW_GREEN_THRESHOLD = 150;
    public static final double YELLOW_BLUE_THRESHOLD = 25;

    public static final double BLACK_RED_THRESHOLD = 25;
    public static final double BLACK_GREEN_THRESHOLD = 25;
    public static final double BLACK_BLUE_THRESHOLD = 25;

    //Auto Tuning
    public static final double AUTO_FAST_SPEED = 0.8;
    public static final double AUTO_FAST_SPEED_LEFT = -0.8;
    public static final double AUTO_FAST_SPEED_RIGHT = 0.8;
    public static final double AUTO_SLOW_FORWARD_SPEED = 0.2;
    public static final double AUTO_SLOW_SPEED_LEFT = -0.2;
    public static final double AUTO_SLOW_SPEED_RIGHT = 0.2;
    public static final double AUTO_STOP = 0.0;
    public static final double AUTO_BACKWARD_SPEED = -0.5;
    public static final double SKYSTONE_FAR_DISTANCE_THRESHOLD = 10;
    public static final double SKYSTONE_CLOSE_DISTANCE_THRESHOLD = 1;

    //Distance Sensor Constants
    public static final double SKYSTONE_DROP_POINT = 185;
    public static final double SKYSTONE_OFFSET = 3; //Do

}
