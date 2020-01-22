package org.firstinspires.ftc.teamcode.Util;

//import static org.firstinspires.ftc.teamcode.Util.Constants.*;

public class Constants {
    //Converdion Constants
    public static final float mmPerInch = 25.4f;
    public static final float stoneZ = 2.00f * mmPerInch;

    //Controller Interface
    public static final double JOYSTICK_1_DEADZONE = 0.05;
    public static final double JOYSTICK_2_DEADZONE = 0.05;

    public static final int X_AXIS = 0;
    public static final int Y_AXIS = 1;

    //Rotator Constants
    public static final double ROTATOR_ENCODER_SCALAR_QUANTITY = 7;

    public static final double ROTATOR_HIGH_DEGREE_THRESHOLD = 50;
    public static final double ROTATOR_LOW_DEGREE_THRESHOLD = 0;

    public static final double ROTATOR_INCREMENT_SCALE = 2;

    public static final double ROTATOR_HIGH_THRESHOLD = (ROTATOR_HIGH_DEGREE_THRESHOLD - ROTATOR_LOW_DEGREE_THRESHOLD)*ROTATOR_ENCODER_SCALAR_QUANTITY;
    public static final double ROTATOR_LOW_THRESHOLD = (ROTATOR_LOW_DEGREE_THRESHOLD - ROTATOR_LOW_DEGREE_THRESHOLD)*ROTATOR_ENCODER_SCALAR_QUANTITY;

    public static final double ROTATOR_INCREMENT_UP = (ROTATOR_HIGH_THRESHOLD - ROTATOR_LOW_DEGREE_THRESHOLD)/ROTATOR_INCREMENT_SCALE;
    public static final double ROTATOR_INCREMENT_DOWN = -(ROTATOR_HIGH_THRESHOLD - ROTATOR_LOW_DEGREE_THRESHOLD)/ROTATOR_INCREMENT_SCALE;

    public static final int ROTATOR_OUTER_THRESHOLD = 50;
    public static final int ROTATOR_MEDIUM_THRESHOLD = 40;
    public static final int ROTATOR_INNER_THRESHOLD = 20;

    public static final double ROTATOR_HIGH_SPEED = 0.6;
    public static final double ROTATOR_MEDIUM_SPEED = 0.4;
    public static final double ROTATOR_LOW_SPEED = 0.3;

    public static final double ROTATOR_UP_MODIFIER = 0.75;
    public static final double ROTATOR_DOWN_MODIFIER = -0.2;


    //Claw constants
    public static final double CLAW_OPEN_POSITION = 0.0;
    public static final double CLAW_CLOSED_POSITION = 1.0;

    public static final double RIGHT_TRIGGER_THRESHOLD = 0.3;

    //Foundation Claw Constants (Open is up, down is closed)
    public static final double FOUNDATION_CLAW_OPEN_POSITION = 0;
    public static final double FOUNDATION_CLAW_CLOSED_POSITION = 250;

    public static final int FOUNDATION_CLAW_OUTER_THRESHOLD = 50;
    public static final int FOUNDATION_CLAW_MEDIUM_THRESHOLD = 40;
    public static final int FOUNDATION_CLAW_INNER_THRESHOLD = 20;

    public static final double FOUNDATION_CLAW_HIGH_SPEED = 0.9;
    public static final double FOUNDATION_CLAW_MEDIUM_SPEED = 0.7;
    public static final double FOUNDATION_CLAW_LOW_SPEED = 0.6;

    public static final double FOUNDATION_CLAW_UP_MODIFIER = 0.75;
    public static final double FOUNDATION_CLAW_DOWN_MODIFIER = -.75;

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
    public static final double AUTO_FAST_SPEED_FORWARD = -0.7;
    public static final double AUTO_FAST_SPEED_LEFT = -0.8;
    public static final double AUTO_FAST_SPEED_RIGHT = 0.8;
    public static final double AUTO_SLOW_SPEED_FORWARD = -0.4;
    public static final double AUTO_SLOW_SPEED_LEFT = -0.4;
    public static final double AUTO_SLOW_SPEED_RIGHT = 0.2;
    public static final double AUTO_STOP = 0.0;
    public static final double AUTO_FAST_SPEED_BACKWARD = 0.5;
    public static final double AUTO_SLOW_SPEED_BACKWARD = 0.15;
    public static final double AUTO_ROTATE_RIGHT = 0.3;
    public static final double AUTO_ROTATE_LEFT = -0.3;
    public static final double AUTO_STRAFE_RIGHT = 0.6;
    public static final double AUTO_STRAFE_LEFT = -0.6;
    public static final double AUTO_LOCATOR_LEFT = -0.175;
    public static final double AUTO_LOCATOR_RIGHT = 0.175;

    public static final double SKYSTONE_FAR_DISTANCE_THRESHOLD = 70;
    public static final double SKYSTONE_CLOSE_DISTANCE_THRESHOLD = 73.66;

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

    public static final float CAMERA_FORWARD_DISPLACEMENT = 0f * mmPerInch;   // eg: Camera is 4 Inches in front of robot center
    public static final float CAMERA_VERTICAL_DISPLACEMENT = 1f * mmPerInch;   // eg: Camera is 8 Inches above ground
    public static final float CAMERA_LEFT_DISPLACEMENT = 3.0f * mmPerInch;     // eg: Camera is ON the robot's center line
    public static double NO_TARGET = -2000;

    public static final double SKYSTONE_X_LOCATION = 0.0;
    public static final double SKYSTONE_Y_LOCATION_UPPER = 3.2;
    public static final double SKYSTONE_Y_LOCATION_LOWER = 0.6;
    public static final double SKYSTONE_Z_LOCATION = 0.0;
}
