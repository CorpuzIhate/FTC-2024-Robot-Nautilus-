package Arm_A_Kraken_DocBotics_FTC_2024.auto;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import Arm_A_Kraken_DocBotics_FTC_2024.AutoCommands.MoveRobotEncoderXYCMD;
import Arm_A_Kraken_DocBotics_FTC_2024.Commands.PowerVacuumCMD;
import Arm_A_Kraken_DocBotics_FTC_2024.Constants;


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

                        new InstantCommand(() ->  {
                            shoulderSub.setSetpoint(Constants.ShoulderSetpoints.highBasketShoulderPos);
                            elbowSub.setSetpoint(Constants.ElbowSetpoints.highBasketElbowPos);
                        }),
                        new WaitCommand(1000),
                        new MoveRobotEncoderXYCMD(26,26,3, 0.5,
                                mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),
                        new PowerVacuumCMD(vacuumSubsystem,1, continousVacuumServo,
                                telemetryManagerSub.getTelemetryObject(), vacuumSensor, 3),


                         new MoveRobotEncoderXYCMD(-25,-25,3, 0.5,
                        mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),
                        new MoveRobotEncoderXYCMD(30,-30,3, 0.5,
                                mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),
                        new InstantCommand(() ->  {
                            shoulderSub.setSetpoint(300);
                            elbowSub.setSetpoint(100);
                        }),
                       new WaitCommand(1000),

                        new MoveRobotEncoderXYCMD(29,29,3, 0.5,
                                mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject())
//                new MoveRobotEncoderXYCMD(24,-24,3, 0.5,
//                        mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject())


                )

        );






    }
}
