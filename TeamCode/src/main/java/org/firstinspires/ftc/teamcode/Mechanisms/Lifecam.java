package org.firstinspires.ftc.teamcode.Mechanisms;

import com.vuforia.Frame;
import android.content.Context;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.function.Consumer;
import org.firstinspires.ftc.robotcore.external.function.Continuation;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.matrices.MatrixF;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.firstinspires.ftc.teamcode.Util.Constants.*;

public class Lifecam {

    private OpenGLMatrix lastLocation = null;
    private VuforiaLocalizer vuforia = null;
    private WebcamName lifecam;
    private Context appContext;
    private int captureCounter = 0;
    private File captureDirectory = AppUtil.ROBOT_DATA_DIR;
    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = VuforiaLocalizer.CameraDirection.FRONT;


    void init(HardwareMap hwMap) {
        lifecam = hwMap.get(WebcamName.class, "Webcam 1");
        appContext = hwMap.appContext;
        int cameraMonitorViewId = hwMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hwMap.appContext.getPackageName());

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = "Acbdwqb/////AAABmZYct0JfVkJUpxqC2cEf69Yb6bmcZvsuNyFPMy5e0TpYgmivsx0lng3Nqh1Z3MyNr6XJbfjkFxDMWjOh6K85lUEXCMyB7fBO3FnagQBmA6oPJuuKUNknLxhTeZYfEZfCmOU5YwR5U1pBtQ6q0PdUbv/C4IxbUKlvgZakeqPAECg4sn23k2gLx6XC2xN8JToJeO3YNQ/2Tp1j+9aYwCLWVzcrrZ6d9OU+MakNZg5Jhj85wMVZPxX4w4zCH72XANX9P2qbq7Rqtd1YQLG8pFDrHyBvun/UdHo3XC731TDRApE0CWLXC1DBYcOYD3WBH3oXBAg2cs9mUsgmf8+fxWiMVPmT8KQ8rWqeZ4D9G4y8WxHI\n";
        parameters.cameraDirection = CAMERA_CHOICE;
        parameters.cameraName = lifecam;

        //This formats the files sent from the camera into something that we can use
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
        vuforia.enableConvertFrameToBitmap();
        AppUtil.getInstance().ensureDirectoryExists(captureDirectory);

        VuforiaTrackables targetsSkyStone = this.vuforia.loadTrackablesFromAsset("Skystone");

        VuforiaTrackable stoneTarget = targetsSkyStone.get(0);
        stoneTarget.setName("SkyStone");

        // For convenience, gather together all the trackable objects in one easily-iterable collection
        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(targetsSkyStone);
    }
}