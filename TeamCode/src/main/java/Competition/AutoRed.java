package Competition;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.vision.VisionPortal;

@Autonomous(name = "AutoRed", group = "Competition")
public class AutoRed extends LinearOpMode {
    private Servo hand;
    private Servo arm;
    private DcMotor armmotor;
    private VisionPortal portal;

    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        hand = hardwareMap.get(Servo.class, "hand");
        arm = hardwareMap.get(Servo.class, "arm"); //wrist
        armmotor = hardwareMap.get(DcMotor.class, "armmotor");

        /* Trajectories either consist of vectors or poses. Vectors are for moving only x and y coordinates while poses have a heading(angle)
            For example
            a pose at coordinates (10,-10) facing 120 degrees would look like
            Pose2d myPose = new Pose2d(10,-10, Math.toRadians(120));
            Assuming you start at (0,0) at the start of the program, the robot with move to the coordinates labeled at an 120 degree heading
         */
        TrajectorySequence genesis = drive.trajectorySequenceBuilder(new Pose2d(0,0,Math.toRadians(270))) //(0,0) is the starting position and 270 degrees is the direction it is facing if you put it on a coordinate system(straight down)
                .forward(28)
                .addTemporalMarker(() -> armmotor.setPower(1))
                .waitSeconds(3)
                .splineToLinearHeading(new Pose2d(-10,-65,Math.toRadians(0)), Math.toRadians(270))
                .forward(90)
                .strafeLeft(25)
                .addTemporalMarker(() -> hand.setPosition(0))
                .waitSeconds(5)
                .strafeLeft(25)
                .forward(15)
                .build();


        waitForStart();

        if(isStopRequested()) return;

        drive.followTrajectorySequence(genesis);

    }
}