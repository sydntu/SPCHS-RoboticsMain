package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import com.acmerobotics.roadrunner.trajectory.TemporalMarker;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(219.476), Math.toRadians(219.476), 13.12)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-0, -0, Math.toRadians(90)))
                                .splineToSplineHeading(new Pose2d(0,65,Math.toRadians(0)), Math.PI + Math.PI)
                                .splineToLinearHeading(new Pose2d(65, 34.5, Math.toRadians(0)), Math.toRadians(300))
                                .waitSeconds(5)
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(.90f)
                .addEntity(myBot)
                .start();
    }
}