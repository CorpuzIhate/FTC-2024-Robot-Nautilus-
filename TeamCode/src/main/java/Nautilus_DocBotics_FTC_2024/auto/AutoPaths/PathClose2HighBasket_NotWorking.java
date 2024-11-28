package Nautilus_DocBotics_FTC_2024.auto.AutoPaths;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Nautilus_DocBotics_FTC_2024.AutoCommands.MoveRobotEncoderXYCMD;
import Nautilus_DocBotics_FTC_2024.AutoCommands.PowerVacuumAutoCMD;
import Nautilus_DocBotics_FTC_2024.Commands.PowerVacuumCMD;
import Nautilus_DocBotics_FTC_2024.Constants;
import Nautilus_DocBotics_FTC_2024.auto.AutoRobotContainer;

@Autonomous
public class PathClose2HighBasket_NotWorking extends AutoRobotContainer {
    @Override
    public void path(){
        schedule(
                new SequentialCommandGroup(


                // starts facing the Submersible
                new MoveRobotEncoderXYCMD(21,21,0, 0.5,
                        mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),

                new MoveRobotEncoderXYCMD(-30,30,0, 0.5,
                        mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),

                new InstantCommand(() ->  {
                    shoulderSub.setSetpoint(Constants.ShoulderSetpoints.highBasketShoulderPos);
                    elbowSub.setSetpoint(Constants.ElbowSetpoints.highBasketElbowPos);
                }),
                new WaitCommand(300),
                new MoveRobotEncoderXYCMD(26,26,0, 0.5,
                        mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),
                new PowerVacuumAutoCMD(vacuumSubsystem,1, continousVacuumServo,
                        telemetryManagerSub.getTelemetryObject(), vacuumSensor, 1.5),
                //shoot sample into high basket


                new MoveRobotEncoderXYCMD(-12,-12,0, 0.5,
                        mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),

                new MoveRobotEncoderXYCMD(30,-30,0, 0.5, // turn the robot +90 relative to robot
                        mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),
                        new MoveRobotEncoderXYCMD(-5,-5,0, 0.5,
                                mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),

                        new InstantCommand(() -> {
                    shoulderSub.setSetpoint(Constants.ShoulderSetpoints.shoulderClearancePos);
                    elbowSub.setSetpoint(100);
                }),
               new WaitCommand(1000),
                        new InstantCommand(() -> {
                   shoulderSub.setSetpoint(Constants.ShoulderSetpoints.shoulderSubmersiblePickUpPos);
                   elbowSub.setSetpoint(Constants.ElbowSetpoints.elbowSubmersiblePickUpPos);

               }),
                        new WaitCommand(2000),
                        new PowerVacuumAutoCMD(vacuumSubsystem,-1, continousVacuumServo,
                                telemetryManagerSub.getTelemetryObject(), vacuumSensor, 0.5),
                        new ParallelCommandGroup(
                                new PowerVacuumAutoCMD(vacuumSubsystem,-1, continousVacuumServo,
                                        telemetryManagerSub.getTelemetryObject(), vacuumSensor, 2),
                                new MoveRobotEncoderXYCMD(15,15,1, 0.25,
                                mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject())



                                ),


                        new InstantCommand(() -> {
                            shoulderSub.setSetpoint(Constants.ShoulderSetpoints.highBasketShoulderPos);
                            elbowSub.setSetpoint(Constants.ElbowSetpoints.highBasketElbowPos);
                        }),

                        new MoveRobotEncoderXYCMD(-8,-8,0, 0.5,
                                mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()
                        ),
                        new MoveRobotEncoderXYCMD(-30,30,0, 0.5, // turn the robot +90 relative to robot
                                mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),

                        //extend arm
                        new MoveRobotEncoderXYCMD(15,15,0, 0.5, // turn the robot +90 relative to robot
                                mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()
                        ),
                        new PowerVacuumAutoCMD(vacuumSubsystem,1, continousVacuumServo,
                        telemetryManagerSub.getTelemetryObject(), vacuumSensor, 4)

        )

        );


    };
}
