package org.firstinspires.ftc.teamcode.Util;

//import static org.firstinspires.ftc.teamcode.Util.Constants.*;

public final class Constants {
    //Controller Interface
    public static final double JOYSTICK_1_DEADZONE = 0.05;
    public static final double JOYSTICK_2_DEADZONE = 0.05;

    public static final int X_AXIS = 0;
    public static final int Y_AXIS = 1;

    //Rotator Constants
    public static final double ROTATOR_HIGH_THRESHOLD = 90;
    public static final double ROTATOR_LOW_THRESHOLD = 0;

    public static final int ROTATOR_INCREMENT_UP = 45;
    public static final int ROTATOR_INCREMENT_DOWN = -45;

    public static final int ROTATOR_OUTER_THRESHOLD = 15;
    public static final int ROTATOR_INNER_THRESHOLD = 5;

    public static final double ROTATOR_HIGH_SPEED = 0.6;
    public static final double ROTATOR_LOW_SPEED = 0.4;

    public static final double ROTATOR_DOWN_MODIFIER = -0.5;
    public static final double ROTATOR_UP_MODIFIER = 1;


    //Claw constants
    public static final double CLAW_OPEN_POSITION = 0.0;
    public static final double CLAW_CLOSED_POSITION = 1.0;
    public static final double RIGHT_TRIGGER_THRESHOLD = 0.3;

    //IMU Constants
    public static final double HEADING_OFFSET = 0;
    public static final double ROTATE_HEADING_CHANGE_THRESHOLD = 10;

    //Color Sensor
    public static final double COLOR_SCALE_VALUE = 255;

    public static final double YELLOW_RED_THRESHOLD = 150;
    public static final double YELLOW_GREEN_THRESHOLD = 150;
    public static final double YELLOW_BLUE_THRESHOLD = 25;

    public static final double BLACK_RED_THRESHOLD = 25;
    public static final double BLACK_GREEN_THRESHOLD = 25;
    public static final double BLACK_BLUE_THRESHOLD = 25;

    public static final double BLACK_AND_YELLOW_THRESHOLD = 30000;

    //Auto Tuning
    public static final double AUTO_FAST_SPEED_FORWARD = -0.15;
    public static final double AUTO_FAST_SPEED_LEFT = -0.8;
    public static final double AUTO_FAST_SPEED_RIGHT = 0.8;
    public static final double AUTO_SLOW_SPEED_FORWARD = -0.11;
    public static final double AUTO_SLOW_SPEED_LEFT = -0.2;
    public static final double AUTO_SLOW_SPEED_RIGHT = 0.2;
    public static final double AUTO_STOP = 0.0;
    public static final double AUTO_FAST_SPEED_BACKWARD = 0.4;
    public static final double AUTO_ROTATE_RIGHT = 0.3;
    public static final double AUTO_ROTATE_LEFT = -0.3;
    public static final double AUTO_STRAFE_RIGHT = 0.6;
    public static final double AUTO_STRAFE_LEFT = -0.6;

    public static final double SKYSTONE_FAR_DISTANCE_THRESHOLD = 4;
    public static final double SKYSTONE_CLOSE_DISTANCE_THRESHOLD = 0.8;

    public static final double FOUNDATION_DISTANCE_THRESHOLD = 50;
    public static final double FOUNDATION_CORNER_THRESHOLD = 20;

    public static final int AUTO_WAIT_PERIOD = 1100;

    //Distance Sensor Constants
    public static final double FAR_DISTANCE_THRESHOLD = 126;
    public static final double CLOSE_DISTANCE_THRESHOLD = 70;
    public static final double SKYSTONE_OFFSET = 50; //"Do this and git gud" - Santi

    //Camera Constants
    public static final float camXRotate = 0;
    public final static float camYRotate = 0;
    public static final float camZRotate = 0;
}
