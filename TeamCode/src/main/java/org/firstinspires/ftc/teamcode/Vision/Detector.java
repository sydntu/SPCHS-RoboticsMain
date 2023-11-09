package org.firstinspires.ftc.teamcode.Vision;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class Detector extends OpenCvPipeline {
    Mat mat = new Mat();
    Mat leftMat = new Mat();
    Mat middleMat = new Mat();
    Mat rightMat = new Mat();
    private TeamPropScanner result = null;
    private Telemetry telemetry;
    public Detector(Telemetry t) {
        telemetry = t;
    }



    Rect leftROI = new Rect(new Point(0,0), new Point(640/3,480));
    Rect middleROI = new Rect(new Point(640/3,0), new Point(2 * (640/3),480));
    Rect rightROI = new Rect(new Point(2 * (640/3),0), new Point(640,480));

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, input, Imgproc.COLOR_RGBA2RGB);
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);

        Scalar lowerBound = new Scalar(0,100,100);
        Scalar upperBound = new Scalar(30,255,255);
        Core.inRange(mat, lowerBound, upperBound, mat);

        leftMat = mat.submat(leftROI);
        middleMat = mat.submat(middleROI);
        rightMat = mat.submat(rightROI);

        double leftValue = Core.sumElems(leftMat).val[0];
        double middleValue = Core.sumElems(middleMat).val[0];
        double rightValue = Core.sumElems(rightMat).val[0];
        telemetry.addData("Left", leftValue);
        telemetry.addData("Middle", middleValue);
        telemetry.addData("Right", rightValue);

        double maxValue = Math.max(leftValue, Math.max(middleValue, rightValue));
        Scalar matchColor = new Scalar(255,0,0);
        Scalar mismatchColor = new Scalar(0,255,0);
        if (maxValue == leftValue) {
            result = TeamPropScanner.LEFT;
            Imgproc.rectangle(input,leftROI, matchColor);
            Imgproc.rectangle(input,middleROI, mismatchColor);
            Imgproc.rectangle(input,rightROI, mismatchColor);
            } else if (maxValue == middleValue) {
            result = TeamPropScanner.MIDDLE;
            Imgproc.rectangle(input,leftROI, mismatchColor);
            Imgproc.rectangle(input,middleROI, matchColor);
            Imgproc.rectangle(input,rightROI, mismatchColor);

            } else {
            result = TeamPropScanner.RIGHT;
            Imgproc.rectangle(input,leftROI, mismatchColor);
            Imgproc.rectangle(input,middleROI, mismatchColor);
            Imgproc.rectangle(input,rightROI, matchColor);

        }
        telemetry.addData("TeamProp", result.toString().toLowerCase());
        telemetry.update();

        return input;

    }

    public TeamPropScanner getResult() {
        while (result == null);
        return result;
    }
}
