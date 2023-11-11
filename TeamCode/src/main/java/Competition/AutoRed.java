package Competition;

import static Competition.magic.armmotor;
import static Competition.magic.hand;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name = "AutoRed", group = "Competition")
public class AutoRed extends LinearOpMode {
    private Servo hand;
    private Servo arm;
    private DcMotor armmotor;

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
        TrajectorySequence genesis = drive.trajectorySequenceBuilder(new Pose2d(0,0,Math.toRadians(0))) //(0,0) is the starting position and 270 degrees is the direction it is facing if you put it on a coordinate system(straight down)
                .addTemporalMarker(() -> hand.setPosition(.8))
                .addTemporalMarker(() -> arm.setPosition(.8))
                .splineToSplineHeading(new Pose2d(42,0,Math.toRadians(270)), Math.PI +Math.PI)
                .splineToLinearHeading(new Pose2d(25,-90,Math.toRadians(270)), Math.PI)
                .addTemporalMarker(() -> {
                    armmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    armmotor.setTargetPosition(3200);
                    armmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    armmotor.setPower(1);
                })
                .addTemporalMarker(() -> arm.setPosition(.5))
                .waitSeconds(2)
                .addTemporalMarker(() -> hand.setPosition(.2))

                .build();

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        waitForStart();

        if(isStopRequested()) return;

        drive.followTrajectorySequence(genesis);

    }
}