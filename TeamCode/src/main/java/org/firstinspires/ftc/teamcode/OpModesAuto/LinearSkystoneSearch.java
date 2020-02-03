package org.firstinspires.ftc.teamcode.OpModesAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.Mechanisms.Robot;
import static org.firstinspires.ftc.teamcode.Util.Constants.*;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;


//@Autonomous
public class LinearSkystoneSearch extends LinearOpMode {

    // IMPORTANT: If you are using a USB WebCam, you must select CAMERA_CHOICE = BACK; and PHONE_IS_PORTRAIT = false;
    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    private static final boolean PHONE_IS_PORTRAIT = false;

    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
    private static final String VUFORIA_KEY =
            "Acbdwqb/////AAABmZYct0JfVkJUpxqC2cEf69Yb6bmcZvsuNyFPMy5e0TpYgmivsx0lng3Nqh1Z3MyNr6XJbfjkFxDMWjOh6K85lUEXCMyB7fBO3FnagQBmA6oPJuuKUNknLxhTeZYfEZfCmOU5YwR5U1pBtQ6q0PdUbv/C4IxbUKlvgZakeqPAECg4sn23k2gLx6XC2xN8JToJeO3YNQ/2Tp1j+9aYwCLWVzcrrZ6d9OU+MakNZg5Jhj85wMVZPxX4w4zCH72XANX9P2qbq7Rqtd1YQLG8pFDrHyBvun/UdHo3XC731TDRApE0CWLXC1DBYcOYD3WBH3oXBAg2cs9mUsgmf8+fxWiMVPmT8KQ8rWqeZ4D9G4y8WxHI";

    // Since ImageTarget trackables use mm to specifiy their dimensions, we must use mm for all the physical dimension.
    // We will define some constants and conversions here
    private static final float mmPerInch = 25.4f;
    private static final float mmTargetHeight = (6) * mmPerInch;          // the height of the center of the target image above the floor

    // Constant for Stone Target
    private static final float stoneZ = 2.00f * mmPerInch;

    // Constants for the center support targets
    private static final float bridgeZ = 6.42f * mmPerInch;
    private static final float bridgeY = 23 * mmPerInch;
    private static final float bridgeX = 5.18f * mmPerInch;
    private static final float bridgeRotY = 59;                                 // Units are degrees
    private static final float bridgeRotZ = 180;

    // Constants for perimeter targets
    private static final float halfField = 72 * mmPerInch;
    private static final float quadField = 36 * mmPerInch;

    // Class Members
    private OpenGLMatrix lastLocation = null;
    private VuforiaLocalizer vuforia = null;

    /**
     * This is the webcam we are to use. As with other hardware devices such as motors and
     * servos, this device is identified using the robot configuration tool in the FTC application.
     */
    WebcamName lifecam = null;
    private Robot robot = new Robot();

    private int step = 0;
    private double firstSkystoneDistance;
    private int skystonesGrabbed = 0;

    private boolean targetVisible = false;
    private float phoneXRotate = 0;
    private float phoneYRotate = 0;
    private float phoneZRotate = 0;

    private double X, Y, Z;
    private boolean vision;

    @Override
    public void runOpMode() {
        /*
         * Retrieve the camera we are to use.
         */
        lifecam = hardwareMap.get(WebcamName.class, "Webcam 1");

        robot.init(hardwareMap);

        robot.openClaw();
        robot.setRotatorPosition(0);

        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         * We can pass Vuforia the handle to a camera preview resource (on the RC phone);
         * If no camera monitor is desired, use the parameter-less constructor instead (commented out below).
         */
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;

        /**
         * We also indicate which camera on the RC we wish to use.
         */
        parameters.cameraName = lifecam;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Load the data sets for the trackable objects. These particular data
        // sets are stored in the 'assets' part of our application.
        VuforiaTrackables targetsSkyStone = this.vuforia.loadTrackablesFromAsset("Skystone");

        VuforiaTrackable stoneTarget = targetsSkyStone.get(0);
        stoneTarget.setName("Stone Target");
        VuforiaTrackable blueRearBridge = targetsSkyStone.get(1);
        blueRearBridge.setName("Blue Rear Bridge");
        VuforiaTrackable redRearBridge = targetsSkyStone.get(2);
        redRearBridge.setName("Red Rear Bridge");
        VuforiaTrackable redFrontBridge = targetsSkyStone.get(3);
        redFrontBridge.setName("Red Front Bridge");
        VuforiaTrackable blueFrontBridge = targetsSkyStone.get(4);
        blueFrontBridge.setName("Blue Front Bridge");
        VuforiaTrackable red1 = targetsSkyStone.get(5);
        red1.setName("Red Perimeter 1");
        VuforiaTrackable red2 = targetsSkyStone.get(6);
        red2.setName("Red Perimeter 2");
        VuforiaTrackable front1 = targetsSkyStone.get(7);
        front1.setName("Front Perimeter 1");
        VuforiaTrackable front2 = targetsSkyStone.get(8);
        front2.setName("Front Perimeter 2");
        VuforiaTrackable blue1 = targetsSkyStone.get(9);
        blue1.setName("Blue Perimeter 1");
        VuforiaTrackable blue2 = targetsSkyStone.get(10);
        blue2.setName("Blue Perimeter 2");
        VuforiaTrackable rear1 = targetsSkyStone.get(11);
        rear1.setName("Rear Perimeter 1");
        VuforiaTrackable rear2 = targetsSkyStone.get(12);
        rear2.setName("Rear Perimeter 2");

        // For convenience, gather together all the trackable objects in one easily-iterable collection */
        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(targetsSkyStone);

        /**
         * In order for localization to work, we need to tell the system where each target is on the field, and
         * where the phone resides on the robot.  These specifications are in the form of <em>transformation matrices.</em>
         * Transformation matrices are a central, important concept in the math here involved in localization.
         * See <a href="https://en.wikipedia.org/wiki/Transformation_matrix">Transformation Matrix</a>
         * for detailed information. Commonly, you'll encounter transformation matrices as instances
         * of the {@link OpenGLMatrix} class.
         *
         * If you are standing in the Red Alliance Station looking towards the center of the field,
         *     - The X axis runs from your left to the right. (positive from the center to the right)
         *     - The Y axis runs from the Red Alliance Station towards the other side of the field
         *       where the Blue Alliance Station is. (Positive is from the center, towards the BlueAlliance station)
         *     - The Z axis runs from the floor, upwards towards the ceiling.  (Positive is above the floor)
         *
         * Before being transformed, each target image is conceptually located at the origin of the field's
         *  coordinate system (the center of the field), facing up.
         */

        // Set the position of the Stone Target.  Since it's not fixed in position, assume it's at the field origin.
        // Rotated it to to face forward, and raised it to sit on the ground correctly.
        // This can be used for generic target-centric approach algorithms
        stoneTarget.setLocation(OpenGLMatrix
                .translation(0, 0, stoneZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

        //Set the position of the bridge support targets with relation to origin (center of field)
        blueFrontBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, bridgeRotY, bridgeRotZ)));

        blueRearBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, -bridgeRotY, bridgeRotZ)));

        redFrontBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, -bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, -bridgeRotY, 0)));

        redRearBridge.setLocation(OpenGLMatrix
                .translation(bridgeX, -bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, bridgeRotY, 0)));

        //Set the position of the perimeter targets with relation to origin (center of field)
        red1.setLocation(OpenGLMatrix
                .translation(quadField, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));

        red2.setLocation(OpenGLMatrix
                .translation(-quadField, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));

        front1.setLocation(OpenGLMatrix
                .translation(-halfField, -quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 90)));

        front2.setLocation(OpenGLMatrix
                .translation(-halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 90)));

        blue1.setLocation(OpenGLMatrix
                .translation(-quadField, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));

        blue2.setLocation(OpenGLMatrix
                .translation(quadField, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));

        rear1.setLocation(OpenGLMatrix
                .translation(halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

        rear2.setLocation(OpenGLMatrix
                .translation(halfField, -quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

        //
        // Create a transformation matrix describing where the phone is on the robot.
        //
        // NOTE !!!!  It's very important that you turn OFF your phone's Auto-Screen-Rotation option.
        // Lock it into Portrait for these numbers to work.
        //
        // Info:  The coordinate frame for the robot looks the same as the field.
        // The robot's "forward" direction is facing out along X axis, with the LEFT side facing out along the Y axis.
        // Z is UP on the robot.  This equates to a bearing angle of Zero degrees.
        //
        // The phone starts out lying flat, with the screen facing Up and with the physical top of the phone
        // pointing to the LEFT side of the Robot.
        // The two examples below assume that the camera is facing forward out the front of the robot.

        // We need to rotate the camera around it's long axis to bring the correct camera forward.
        if (CAMERA_CHOICE == BACK) {
            phoneYRotate = -90;
        } else {
            phoneYRotate = 90;
        }

        // Rotate the phone vertical about the X axis if it's in portrait mode
        if (PHONE_IS_PORTRAIT) {
            phoneXRotate = 90;
        }

        // Next, translate the camera lens to where it is on the robot.
        // In this example, it is centered (left to right), but forward of the middle of the robot, and above ground level.
        final float CAMERA_FORWARD_DISPLACEMENT = 4.0f * mmPerInch;   // eg: Camera is 4 Inches in front of robot-center
        final float CAMERA_VERTICAL_DISPLACEMENT = 8.0f * mmPerInch;   // eg: Camera is 8 Inches above ground
        final float CAMERA_LEFT_DISPLACEMENT = 0;     // eg: Camera is ON the robot's center line

        OpenGLMatrix robotFromCamera = OpenGLMatrix
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES, phoneYRotate, phoneZRotate, phoneXRotate));

        /**  Let all the trackable listeners know where the phone is.  */
        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(robotFromCamera, parameters.cameraDirection);
        }

        // WARNING:
        // In this sample, we do not wait for PLAY to be pressed.  Target Tracking is started immediately when INIT is pressed.
        // This sequence is used to enable the new remote DS Camera Preview feature to be used with this sample.
        // CONSEQUENTLY do not put any driving commands in this loop.
        // To restore the normal opmode structure, just un-comment the following line:

        // waitForStart();

        // Note: To use the remote camera preview:
        // AFTER you hit Init on the Driver Station, use the "options menu" to select "Camera Stream"
        // Tap the preview window to receive a fresh image.

        targetsSkyStone.activate();

            while (!isStopRequested()) {

                // check all the trackable targets to see which one (if any) is visible.
                targetVisible = false;
                for (VuforiaTrackable trackable : allTrackables) {
                    if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible()) {
                        telemetry.addData("Visible Target", trackable.getName());
                        targetVisible = true;

                        // getUpdatedRobotLocation() will return null if no new information is available since
                        // the last time that call was made, or if the trackable is not currently visible.
                        OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) trackable.getListener()).getUpdatedRobotLocation();
                        if (robotLocationTransform != null) {
                            lastLocation = robotLocationTransform;
                        }
                        break;
                    }
                }

                // Provide feedback as to where the robot is located (if we know).
                if (targetVisible) {
                    // express position (translation) of robot in inches.
                    VectorF translation = lastLocation.getTranslation();
                    telemetry.addData("Pos (in)", "{X, Y, Z} = %.1f, %.1f, %.1f",
                            translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch);

                    // express the rotation of the robot in degrees.
                    Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
                    X = translation.get(0) / mmPerInch;
                    Y = translation.get(1) / mmPerInch;
                    Z = translation.get(2) / mmPerInch;
                    vision = true;
                    telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
                } else {
                    telemetry.addData("Visible Target", "none");
                    vision = false;
                }
//        }
                /* <Termination Stage> */
                telemetry.update();
                telemetry.addData("X", X);
                telemetry.addData("Y", Y);
                telemetry.addData("Z", Z);
                telemetry.addData("Back Distance", robot.checkBackDistance());
                telemetry.addData("First Skystone Distance", firstSkystoneDistance);
                telemetry.addData("Grabbed Skystones", skystonesGrabbed);
                robot.getHeadingAbsolute(telemetry);


                switch (step) {
                    //# = Tested
                    //$ = Makes Sense
                    //@ = Needs Review
                    case 0: //#$
                        telemetry.addData("Auto Step", "0");
                        robot.driveAll(AUTO_FAST_SPEED_FORWARD, 0, 0, telemetry);
                        robot.waiting(400);
                        robot.driveAll(0, 0, 0, telemetry);
                        step = 1;
                        telemetry.update();
                        break;
                    case 1: //$#
                        telemetry.addData("Auto Step", "1");
                        if (vision) {
                            step = 2;
                            telemetry.update();
                        } else {
                            robot.driveAll(0, -AUTO_SLOW_SPEED_LEFT, 0, telemetry);
                            robot.waiting(200);
                            robot.driveAll(0, 0, 0, telemetry);
                            robot.waiting(500);
                            telemetry.update();
                        }
                        telemetry.update();
                        break;
                    case 2: //$#
                        telemetry.addData("Auto Step", "2");
                        if (Y < SKYSTONE_Y_LOCATION_LOWER_BLUE) {
                            //robot.driveAll(0, -AUTO_LOCATOR_LEFT, 0, telemetry);
                            robot.driveStrafeStraight(-AUTO_LOCATOR_LEFT);
                        } else if (Y > SKYSTONE_Y_LOCATION_UPPER_BLUE) {
                            //robot.driveAll(0, -AUTO_LOCATOR_RIGHT, 0, telemetry);
                            robot.driveStrafeStraight(-AUTO_LOCATOR_RIGHT);
                        } else {
                            robot.driveAll(0,0,0, telemetry);
                            step = 3;
                        }
                        telemetry.update();
                        break;
                    case 3: //$#
                        telemetry.addData("Auto Step", "3");
                        if (robot.checkBackDistance() > SKYSTONE_FAR_DISTANCE_THRESHOLD) {
                            robot.driveAll(AUTO_FAST_SPEED_FORWARD, 0, 0, telemetry);
                        } else {
                            robot.driveAll(0, 0, 0, telemetry);
                            step = 4;
                        }
                        telemetry.update();
                        break;
                    case 4: //$#
                        telemetry.addData("Auto Step", "4");
                        if (robot.checkBackDistance() > SKYSTONE_CLOSE_DISTANCE_THRESHOLD) {
                            robot.driveAll(AUTO_SLOW_SPEED_FORWARD, 0, 0, telemetry);
                        } else {
                            robot.driveAll(0, 0, 0, telemetry);
                            step = 5;
                        }
                        telemetry.update();
                        break;
                    case 5: //$#
                        telemetry.addData("Auto Step", "5");
                        robot.closeClaw();
                        robot.waiting(200);
                        robot.setRotatorPosition(50);
                        robot.waiting(200);
                        firstSkystoneDistance = robot.checkLeftDistance();
                        step = 6;
                        telemetry.update();
                        break;
                    case 6: //$#
                        telemetry.addData("Auto S2tep", "6");
                        robot.driveAll(AUTO_FAST_SPEED_BACKWARD,0,0, telemetry);
                        if (skystonesGrabbed == 0) {
                            robot.waiting(800);
                        } else {
                            robot.waiting(900);
                        }
                        robot.driveAll(0, 0, 0, telemetry);
                        step = 7;
                        telemetry.update();
                        break;
                    case 7: //$@#
                        telemetry.addData("Auto Step", "7");
                        if (robot.checkLeftDistance() > 800) {
                            robot.driveAll(0,-AUTO_FAST_SPEED_RIGHT,0, telemetry);
                            robot.waiting(800);
                            robot.driveAll(0, 0, 0, telemetry);
                            robot.setRotatorPosition(0);
                            robot.openClaw();
                            skystonesGrabbed++;
                            if (skystonesGrabbed == 1) {
                                step = 8; //This is case 1
                            } else {
                                step = 9; //This is case 9
                            }
//                            step = 8;
                        } else {
                            robot.driveAll(0,-AUTO_FAST_SPEED_RIGHT,0, telemetry);
                        }
                        telemetry.update();
                        break;
                    case 8: //$
                        telemetry.addData("Auto Step", "8");
//                        if (robot.checkLeftDistance() > firstSkystoneDistance + SKYSTONE_DISTANCE_OFFSET) {
                        if (robot.checkLeftDistance() > 70) {
                            robot.driveAll(0, -AUTO_FAST_SPEED_LEFT, 0, telemetry);
                        } else {
                            robot.driveAll(AUTO_FAST_SPEED_FORWARD,0,0, telemetry);
                            robot.waiting(200);
                            robot.driveAll(0, 0, 0, telemetry);
//                            skystonesGrabbed++;
//                            if (skystonesGrabbed <= 2) {
//                                step = 1; //This is case 1
//                            } else {
//                                step = 9; //This is case 9
//                            }
                            step = 1;
                        }
                        telemetry.update();
                        break;
                    case 9: //$#
                        telemetry.addData("Auto Step", "9");
                        if (robot.checkRightDistance() < 800) {
                            robot.driveAll(0, -AUTO_FAST_SPEED_LEFT, 0, telemetry);
                        } else {
                            robot.driveAll(0, 0, 0, telemetry);
                            telemetry.addData("Auto Step", "Finished");
                            telemetry.update();
                            step = 1410;
                        }
                        telemetry.update();
                        break;
                    case 1410:
                        telemetry.addData("Auto Step", "1410");
                        robot.driveAll(0,0,0, telemetry);
                        telemetry.update();
                        break;
                }
            }

            // Disable Tracking when we are done;
            targetsSkyStone.deactivate();
    }
}
