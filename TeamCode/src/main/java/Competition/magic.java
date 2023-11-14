package Competition;

import static org.opencv.core.Core.sqrt;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;


import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.lang.Math;

@TeleOp(name = "main", group = "Competition")
public class magic extends LinearOpMode {

    public static int rdirection = 1;
    public static int ldirection = -1;
    public static DcMotor rightFront;
    public static DcMotor leftFront;
    public static DcMotor leftRear;
    public static DcMotor rightRear;
    public static DcMotor armmotor;
    public static Servo drone;
    public static Servo arm;
    public static Servo riggingsupport;
    public static DcMotor intakemotor;
    public static double h = .7;
    public static double g = 1;
    public static boolean z;
    public static Servo hand;
    public static double k = -1;
    //  public static double x = 0.4;
    public static int c = 0;
    public static double b = 0.5;
    public static double r = 0.56;
    public static double s;



    double frontLeftPower;
    double backLeftPower;
    double frontRightPower;
    double backRightPower;
    /**
     * This function is executed when this OpMode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() throws InterruptedException {
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftRear = hardwareMap.get(DcMotor.class, "leftRear");
        rightRear = hardwareMap.get(DcMotor.class, "rightRear");
        armmotor = hardwareMap.get(DcMotor.class, "armmotor");
        intakemotor = hardwareMap.get(DcMotor.class, "intakemotor");
        drone = hardwareMap.get(Servo.class, "drone");
        arm = hardwareMap.get(Servo.class, "arm"); //wrist
        hand = hardwareMap.get(Servo.class, "hand");
        riggingsupport = hardwareMap.get(Servo.class, "riggingsupport");
        IMU imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));
        imu.initialize(parameters);



        //////////////////////////////////////////////////////////////////


        waitForStart();
        if (opModeIsActive()) {
            drone.setPosition(0);
            riggingsupport.setPosition(0.56);
            leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
            leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
            // Put run blocks here.
            while (opModeIsActive()) {
                // Put loop blocks here.
                // The Y axis of a joystick ranges from -1 in its topmost position
                // to +1 in its bottommost position. We negate this value so that
                // the topmost position corresponds to maximum forward power.

                if (gamepad2.right_trigger > 0){
                    s = 1;

                } else {
                    if (gamepad2.left_trigger > 0){
                        s = 0.4;

                    } else {
                        s = 0.65;

                    }
                }

                if (gamepad1.right_trigger>0)
                    intakemotor.setPower(1);
                else if (gamepad1.left_trigger>0) {
                    intakemotor.setPower(-1);
                }
                else intakemotor.setPower(0);
                //intakemotor.setPower(gamepad1.right_trigger);


                armmotor.setPower(gamepad1.right_stick_y);
                armmotor.setDirection(DcMotorSimple.Direction.REVERSE);
                if (0.4 <= (h - (-gamepad1.left_stick_y/1000)) && (h - (-gamepad1.left_stick_y/1000)) <= 1){
                    h = h - (-gamepad1.left_stick_y/1000);
                }


                ///////////////////////////////////////////////////////


        /*
        leftfront.setPower((gamepad1.right_stick_y)*s);
        leftback.setPower((gamepad1.right_stick_y)*s);
        rightfront.setPower((gamepad1.right_stick_y)*s);
        rightback.setPower(((gamepad1.right_stick_y)*s);

        */


                /////////////////////////////////////////////////////////

                if (gamepad2.dpad_right){
                    leftFront.setPower(s);
                    leftRear.setPower(s);
                    rightFront.setPower(-s);
                    rightRear.setPower(-s);
                }
                if (gamepad2.dpad_left){
                    leftFront.setPower(-s);
                    leftRear.setPower(-s);
                    rightFront.setPower(s);
                    rightRear.setPower(s);
                }
                if (gamepad2.dpad_up){
                    leftFront.setPower(s);
                    leftRear.setPower(s);
                    rightFront.setPower(s);
                    rightRear.setPower(s);
                }
                if (gamepad2.dpad_down){
                    leftFront.setPower(-s);
                    leftRear.setPower(-s);
                    rightFront.setPower(-s);
                    rightRear.setPower(-s);
                }

                //////////////////////////////////////////////////////////////

                double y = -gamepad2.left_stick_y; // Remember, Y stick value is reversed
                double x = gamepad2.left_stick_x * 1.1; // Counteract imperfect strafing
                double rx = gamepad2.right_stick_x;

                // Denominator is the largest motor power (absolute value) or 1
                // This ensures all the powers maintain the same ratio,
                // but only if at least one is out of the range [-1, 1]


                /////////////////////////////////////////////////////////////////////////

                // This is field centric driving

/*
                double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

                double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
                double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

                double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
                double frontLeftPower = ((rotY + x + rotX)*s) / denominator;
                double backLeftPower = ((rotY - x + rotX)*s) / denominator;
                double frontRightPower = ((rotY - rotX - rx)*s) / denominator;
                double backRightPower = ((rotY + rotX - rx)*s) / denominator;
*/

                //////////////////////////////////////////////////////////

                double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
                double frontLeftPower = ((y + x + rx)*s) / denominator;
                double backLeftPower = ((y - x + rx)*s) / denominator;
                double frontRightPower = ((y - x - rx)*s) / denominator;
                double backRightPower = ((y + x - rx)*s) / denominator;

                leftFront.setPower(frontLeftPower);
                leftRear.setPower(backLeftPower);
                rightFront.setPower(frontRightPower);
                rightRear.setPower(backRightPower);

                /////////////////////////////////////////////////////////////
/*
               //Left and Right Claw Code

                if (gamepad1.left_bumper) {
                    leftClaw.setPosition(0.2);
                } else if (leftClaw.getPosition() == 0.2){
                    leftClaw.setPosition(0.8);
                }
                if (gamepad1.right_bumper) {
                    rightClaw.setPosition(0.2);
                } else if (rightClaw.getPosition() == 0.2){
                    rightClaw.setPosition(0.8);
                }
*/
                if (gamepad1.y) {
                    armmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    armmotor.setTargetPosition(3200);
                    armmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    armmotor.setPower(1);
                }

                if (gamepad1.a){
                    k = 1;
                }
                if (gamepad1.b){
                    k = -1;
                }
                if (gamepad1.x){
                    c = c+1;
                    if (c == 1000){
                        drone.setPosition(0.9);
                    }
                } else {
                    drone.setPosition(0.65);
                    c = 0;
                }
                if (gamepad2.y){
                    r = 0.56;
                }
                if (gamepad2.x){
                    r = 0.8;
                }
                if (0.30 <= (b - (k/1000)) && (b - (k/1000)) <= 0.80){
                    b = b - (k/1000);
                }
                arm.setPosition(h);
                hand.setPosition(b);
                riggingsupport.setPosition(r);


            }
        }
    }
}


