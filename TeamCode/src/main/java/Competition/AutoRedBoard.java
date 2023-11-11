package Competition;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name = "AutoRedBoard", group = "Competition")
public class AutoRedBoard extends LinearOpMode {
    private Servo hand;
    private Servo arm;
    private DcMotor armmotor;
    private int lowpos = -2900; //this value is the lowest position the arm can go in order to pick up the pixels
    private int bbpos = 500; //this value is the arm position needed for placing pixels on backboard

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
        TrajectorySequence genesis = drive.trajectorySequenceBuilder(new Pose2d(0,0,Math.toRadians(0)))//(0,0) is the starting position and 270 degrees is the direction it is facing if you put it on a coordinate system(straight down)
                .addTemporalMarker(() -> hand.setPosition(.8))
                .addTemporalMarker(() -> arm.setPosition(.8))
                .strafeRight(60)
                .waitSeconds(.5)
                .addTemporalMarker(() -> hand.setPosition(0.2)) //opens claw
                .addTemporalMarker(() -> {
                    armmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    armmotor.setTargetPosition(3000);
                    armmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    armmotor.setPower(1);
                 }) //moves the arm to the drop the pixel on the backboard
                .waitSeconds(5)
                //.back(5)
                //.strafeRight(15)
                //.forward(5)

                .build();


        waitForStart();

        if(isStopRequested()) return;

        drive.followTrajectorySequence(genesis);

    }
}