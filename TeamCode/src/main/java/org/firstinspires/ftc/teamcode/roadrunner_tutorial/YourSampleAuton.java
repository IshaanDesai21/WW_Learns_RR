package org.firstinspires.ftc.teamcode.roadrunner_tutorial;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner_tutorial.base_subsystem_templates.*;

@Config
@Autonomous(name = "Your Own Autonomous!", group = "Autonomous")
public class YourSampleAuton extends LinearOpMode {


    @Override
    public void runOpMode() {
        //Pose that the robot starts at
        Pose2d initialPose = new Pose2d(-63, 39, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        Motor_Template motor = new Motor_Template(hardwareMap);
        Servo_Template servo = new Servo_Template(hardwareMap);


        //-----------------------Paths-----------------------\\
        Action path1 = drive.actionBuilder(initialPose)
                .strafeToLinearHeading(new Vector2d(-58,57),Math.toRadians(315))
                .build();

        Action path2 = drive.actionBuilder(new Pose2d(-58, 57, Math.toRadians(315)))
                .strafeToLinearHeading(new Vector2d(-35,49),Math.toRadians(0))
                .build();



        // Initialize
        Actions.runBlocking(
                new SequentialAction(
                    servo.toPos1(),
                    motor.toPos2()
                )
        );


        waitForStart();

        if (isStopRequested()) return;


        Actions.runBlocking(
                new SequentialAction(

                        //----------Do the first Path!----------\\

                        //Runs path 1 *WHILE* motor moves to position 3
                        new ParallelAction(
                                path1,
                                motor.toPos3()
                        ),


                        //----------Do the first Path!----------\\

                        //Runs path 2 *AFTER*
                        //(motor moves to position 2 *WHILE* servo moves to position 2)
                        new SequentialAction(
                                path2,
                                new ParallelAction(
                                        motor.toPos2(),
                                        servo.toPos2()
                                )

                        )

                )
        );


    }
}