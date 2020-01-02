package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;

//class BlackFilter extends OpenCvPipeline {
//
//}

public class Lifecam {


    void init(HardwareMap hwmap) {
        int cameraMonitorViewId = hwmap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hwmap.appContext.getPackageName());
        OpenCvCamera lifecam = OpenCvCameraFactory.getInstance().createWebcam(hwmap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        lifecam.openCameraDevice();
        lifecam.startStreaming(320, 240, OpenCvCameraRotation.SIDEWAYS_RIGHT);
        //lifecam.setPipeline(new BlackFilter());


    }
}
