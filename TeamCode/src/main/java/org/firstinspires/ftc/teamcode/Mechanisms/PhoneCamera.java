package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.vuforia.CameraDevice;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.R;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

public class PhoneCamera {
    public enum Location {
        NOT_FOUND,
        LEFT,
        CENTER,
        RIGHT
    }

    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    private static final boolean PHONE_IS_PORTRAIT = true;
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
    private boolean targetVisible = false;
    private float phoneXRotate = 180;
    private float phoneYRotate = 0;
    private float phoneZRotate = 0;
    private VuforiaTrackable stoneTarget;
    private VuforiaTrackables targetsSkyStone;

    //Custom
    private static double NO_TARGET = -2000;


    public void init(HardwareMap hardwareMap) {
        System.out.println("QQ_Test -- Started");
        System.out.flush();


        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "Acbdwqb/////AAABmZYct0JfVkJUpxqC2cEf69Yb6bmcZvsuNyFPMy5e0TpYgmivsx0lng3Nqh1Z3MyNr6XJbfjkFxDMWjOh6K85lUEXCMyB7fBO3FnagQBmA6oPJuuKUNknLxhTeZYfEZfCmOU5YwR5U1pBtQ6q0PdUbv/C4IxbUKlvgZakeqPAECg4sn23k2gLx6XC2xN8JToJeO3YNQ/2Tp1j+9aYwCLWVzcrrZ6d9OU+MakNZg5Jhj85wMVZPxX4w4zCH72XANX9P2qbq7Rqtd1YQLG8pFDrHyBvun/UdHo3XC731TDRApE0CWLXC1DBYcOYD3WBH3oXBAg2cs9mUsgmf8+fxWiMVPmT8KQ8rWqeZ4D9G4y8WxHI\n";
        parameters.cameraDirection = CAMERA_CHOICE;
        System.out.println("QQ_Test -- parameters set");
        System.out.flush();
        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
        System.out.println("QQ_Test -- Instantiate");
        System.out.flush();

        parameters.useExtendedTracking = false;
        // Load the data sets for the trackable objects. These particular data
        // sets are stored in the 'assets' part of our application.
        targetsSkyStone = this.vuforia.loadTrackablesFromAsset("Skystone");
        System.out.println("QQ_Test -- load data sets");
        System.out.flush();

        stoneTarget = targetsSkyStone.get(0);
        stoneTarget.setName("Stone Target");
        stoneTarget.setLocation(OpenGLMatrix
                .translation(0, 0, stoneZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));
        System.out.println("QQ_Test -- setPosition");
        System.out.flush();
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
        final float CAMERA_FORWARD_DISPLACEMENT = -90;   // eg: Camera is 90 mm back from the front
        final float CAMERA_VERTICAL_DISPLACEMENT = 120;   // eg: Camera is 120 mm above ground
        final float CAMERA_LEFT_DISPLACEMENT = -140;     // eg: Camera is 140 to the right of the center line
        OpenGLMatrix robotFromCamera = OpenGLMatrix
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES, phoneYRotate, phoneZRotate, phoneXRotate));
        ((VuforiaTrackableDefaultListener) stoneTarget.getListener()).setPhoneInformation(robotFromCamera, parameters.cameraDirection);
        System.out.println("QQ_Test -- translate camera");
        System.out.flush();
        System.out.println("QQ_Test -- Done");
        System.out.flush();
    }

    public void startSkystone() {
        targetsSkyStone.activate();
    }

    public double whereIsSkystone(Telemetry telemetry) {
        CameraDevice.getInstance().setFlashTorchMode(true); //turns flashlight on

        if (((VuforiaTrackableDefaultListener) stoneTarget.getListener()).isVisible()) {
            // getUpdatedRobotLocation() will return null if no new information is available since
            // the last time that call was made, or if the trackable is not currently visible.
            OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) stoneTarget.getListener()).getUpdatedRobotLocation();
            if (robotLocationTransform != null) {
                lastLocation = robotLocationTransform;
            }
            VectorF translation = lastLocation.getTranslation();
            telemetry.addData("Pos (in)", "{X, Y, Z} = %.1f, %.1f, %.1f",
                    translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch);
            double yPosition = translation.get(1);
            return yPosition;
        } else {
            telemetry.addData("Direction", "Not Found");
            return NO_TARGET;
        }
    }

    public void end() {
// Disable Tracking when we are done;
        targetsSkyStone.deactivate();
    }

}