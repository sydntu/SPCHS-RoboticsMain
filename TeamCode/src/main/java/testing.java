import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name = "Testing", group = "Test")
public class testing extends LinearOpMode{
    @Override
    public void runOpMode(){
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);


        TrajectorySequence Test = drive.trajectorySequenceBuilder(new Pose2d(0,0, Math.toRadians(0)))
                .forward(30)
                .strafeRight(30)
                .build();

    }
}

