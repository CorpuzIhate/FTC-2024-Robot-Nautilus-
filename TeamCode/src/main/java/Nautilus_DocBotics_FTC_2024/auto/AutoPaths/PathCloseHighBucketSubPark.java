package Nautilus_DocBotics_FTC_2024.auto.AutoPaths;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Nautilus_DocBotics_FTC_2024.AutoCommands.MoveRobotEncoderXYCMD;
import Nautilus_DocBotics_FTC_2024.AutoCommands.PowerVacuumAutoCMD;
import Nautilus_DocBotics_FTC_2024.Commands.PowerVacuumCMD;
import Nautilus_DocBotics_FTC_2024.Constants;
import Nautilus_DocBotics_FTC_2024.auto.AutoRobotContainer;


@Autonomous
public class PathCloseHighBucketSubPark extends AutoRobotContainer {
    @Override
    public void path(){
        schedule(new SequentialCommandGroup(


                // starts facing the Submersible
                        new MoveRobotEncoderXYCMD(21,21,3, 0.5,
                                mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),

                        new MoveRobotEncoderXYCMD(-30,30,3, 0.5,
                                mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),
                        //robot moves forward, turns to basket
                        new InstantCommand(() ->  {
                            shoulderSub.setSetpoint(Constants.ShoulderSetpoints.highBasketShoulderPos);
                            elbowSub.setSetpoint(Constants.ElbowSetpoints.highBasketElbowPos);
                        }),
                        new WaitCommand(1000),
                        new MoveRobotEncoderXYCMD(26,26,3, 0.5,
                                mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),
                        //robot extends arm and moves forward
                        new PowerVacuumAutoCMD(vacuumSubsystem,1, continousVacuumServo,
                                telemetryManagerSub.getTelemetryObject(), vacuumSensor, 3),
                        //robot inputs sample into high-basket

                         new MoveRobotEncoderXYCMD(-25,-25,3, 0.5,
                        mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),
                        new MoveRobotEncoderXYCMD(30,-30,3, 0.5,
                                mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),
                        //robot turns 90+, lowers arm
                        new InstantCommand(() ->  {
                            shoulderSub.setSetpoint(300);
                            elbowSub.setSetpoint(100);
                        }),
                       new WaitCommand(1000),

                        new MoveRobotEncoderXYCMD(29,29,3, 0.5,
                                mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject())
                //robot moves forward to park


                )

        );






    }
}
