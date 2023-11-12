package Competition;

import static org.firstinspires.ftc.teamcode.Vision.Side.CENTER;
import static org.firstinspires.ftc.teamcode.Vision.Side.RIGHT;

import android.provider.Settings;
import android.util.Size;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Vision.PropPipeline;
import org.firstinspires.ftc.teamcode.Vision.Side;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.vision.VisionPortal;

@Autonomous(name = "AutoRed", group = "Competition")
public class AutoRed extends LinearOpMode {
    private Servo hand;
    private Servo arm;
    private DcMotor armmotor;
    private PropPipeline propPipeline;
    private VisionPortal portal;



    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        hand = hardwareMap.get(Servo.class, "hand");
        arm = hardwareMap.get(Servo.class, "arm"); //wrist
        armmotor = hardwareMap.get(DcMotor.class, "armmotor");
        propPipeline = new PropPipeline();
        portal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam"))
                .setCameraResolution(new Size(1920, 1080))
                .setCamera(BuiltinCameraDirection.BACK)
                .addProcessor(propPipeline)
//                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .enableLiveView(true)
                .setAutoStopLiveView(true)
                .build();
        Side side = propPipeline.getLocation();
        portal.close();

        telemetry = new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry());

        /* Trajectories either consist of vectors or poses. Vectors are for moving only x and y coordinates while poses have a heading(angle)
            For example
            a pose at coordinates (10,-10) facing 120 degrees would look like
            Pose2d myPose = new Pose2d(10,-10, Math.toRadians(120));
            Assuming you start at (0,0) at the start of the program, the robot with move to the coordinates labeled at an 120 degree heading
         */


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        waitForStart();

        if(isStopRequested()) return;

        switch (side) {
            case LEFT:

                break;
            case CENTER:

                break;
            case RIGHT:

                break;
            default:
                TrajectorySequence genesis = drive.trajectorySequenceBuilder(new Pose2d(0, 0, Math.toRadians(0))) //(0,0) is the starting position and 270 degrees is the direction it is facing if you put it on a coordinate system(straight down)
                        .addTemporalMarker(() -> hand.setPosition(.8)) //tightens grip on pixel
                        .addTemporalMarker(() -> arm.setPosition(.8)) //forces the wrist portion to snap inwards
                        .splineToSplineHeading(new Pose2d(42, 0, Math.toRadians(270)), Math.PI + Math.PI)
                        .splineToLinearHeading(new Pose2d(25, -90, Math.toRadians(270)), Math.PI)
                        .addTemporalMarker(() -> {
                            armmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); //resets the value of the encoder to 0
                            armmotor.setTargetPosition(3200); //tells the motor the desired encoder value
                            armmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION); //tells the motor to go to that desire value
                            armmotor.setPower(1); // gives the motor value
                        })
                        .addTemporalMarker(() -> arm.setPosition(.5)) //snaps the wrist to the front
                        .waitSeconds(3)
                        .addTemporalMarker(() -> hand.setPosition(.2)) // opens the claw

                        .build();

                drive.followTrajectorySequence(genesis);
                break;

        }

    }
}