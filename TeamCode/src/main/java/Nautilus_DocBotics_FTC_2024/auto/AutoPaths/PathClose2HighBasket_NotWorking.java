package Nautilus_DocBotics_FTC_2024.auto.AutoPaths;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Nautilus_DocBotics_FTC_2024.AutoCommands.MoveRobotDiagonalEncoderCMD;
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
                        //starts facing the Submersible
                new MoveRobotDiagonalEncoderCMD(24,0,0.5,
                        false,
                        mecanumDriveBaseSub,
                        telemetryManagerSub.getTelemetryObject()),


                        new InstantCommand(() ->  {
                            shoulderSub.setSetpoint(Constants.ShoulderSetpoints.highBasketShoulderPos);
                            elbowSub.setSetpoint(Constants.ElbowSetpoints.highBasketElbowPos);
                        }),
                //robot moves diagonal, extends arm

                new MoveRobotEncoderXYCMD(-30,30,0, 0.5,
                        mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),


                new WaitCommand(300),
                new MoveRobotEncoderXYCMD(15,15,0, 0.5,
                        mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),
                new PowerVacuumAutoCMD(vacuumSubsystem,1, continousVacuumServo,
                        telemetryManagerSub.getTelemetryObject(), vacuumSensor, 1.5),
                //moves forward and inputs sample into high basket


                new MoveRobotEncoderXYCMD(-16,-16,0, 0.5,
                        mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),
                        // moves back and turns the robot +90 relative to robot
                new MoveRobotEncoderXYCMD(30,-30,0, 0.5,
                        mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),

                        //robot moves back while arm to clearance position
                        new InstantCommand(() -> {
                            shoulderSub.setSetpoint(Constants.ShoulderSetpoints.shoulderClearancePos);
                            elbowSub.setSetpoint(100);
                        }),

                        new MoveRobotEncoderXYCMD(-10,-10,0, 0.5,
                                mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),


                        new InstantCommand(() -> {
                   shoulderSub.setSetpoint(Constants.ShoulderSetpoints.shoulderSubmersiblePickUpPos);
                   elbowSub.setSetpoint(Constants.ElbowSetpoints.elbowSubmersiblePickUpPos);

               }),
                        new WaitCommand(2000),

                        new ParallelCommandGroup(
                                new PowerVacuumAutoCMD(vacuumSubsystem,-1, continousVacuumServo,
                                        telemetryManagerSub.getTelemetryObject(), vacuumSensor, 2),
                                new MoveRobotEncoderXYCMD(15,15,0, 0.5,
                                mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject())



                                ),
                // robot goes to pick up position and moves forward to pick up more sample
                        new InstantCommand(() -> {
                            elbowSub.setSetpoint(Constants.ElbowSetpoints.elbowClearancePos);
                        }),
                        new WaitCommand(1000),
                        new InstantCommand(() -> {
                            shoulderSub.setSetpoint(Constants.ShoulderSetpoints.highBasketShoulderPos);
                            elbowSub.setSetpoint(Constants.ElbowSetpoints.highBasketElbowPos);
                        }),
                        new MoveRobotEncoderXYCMD(-8,-8,0, 0.5,
                                mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()
                        ),
                        //robot extends arm back up and moves back again

                        new MoveRobotEncoderXYCMD(-30,30,0, 0.5, // turn the robot +90 relative to robot
                                mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),

                        //robot moves forward and input sample into high basket
                        new MoveRobotEncoderXYCMD(15,15,0, 0.5,
                                mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()
                        ),

                        new PowerVacuumAutoCMD(vacuumSubsystem,1, continousVacuumServo,
                        telemetryManagerSub.getTelemetryObject(), vacuumSensor, 2),
                    //robot moves back from basket so that when we enter tele-op, the arm doesn't get caught on the basket

                    new MoveRobotEncoderXYCMD(-20,-20,0, 1,
                            mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()
                    ),
                        new InstantCommand(() -> {
                            shoulderSub.setSetpoint(300);
                            elbowSub.setSetpoint(100);
                        })

                )
        );


    };
}
