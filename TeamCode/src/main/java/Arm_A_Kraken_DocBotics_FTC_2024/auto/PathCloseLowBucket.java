package Arm_A_Kraken_DocBotics_FTC_2024.auto;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Arm_A_Kraken_DocBotics_FTC_2024.AutoCommands.MoveRobotEncoderXYCMD;
import Arm_A_Kraken_DocBotics_FTC_2024.Commands.PowerVacuumCMD;
import Arm_A_Kraken_DocBotics_FTC_2024.Constants;

@Autonomous
public class PathCloseLowBucket extends AutoRobotContainer{
    @Override
    public void path(){
        schedule(new SequentialCommandGroup(



                        new MoveRobotEncoderXYCMD(24,24,3, 0.5,
                                mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),

                        new MoveRobotEncoderXYCMD(-30,30,3, 0.5,
                                mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),

                        new InstantCommand(() ->  {
                            shoulderSub.setSetpoint(Constants.ShoulderSetpoints.middleShoulderPos);
                            elbowSub.setSetpoint(Constants.ElbowSetpoints.middleElbowPos);
                        }),
                        new MoveRobotEncoderXYCMD(10,10,3, 0.5,
                                mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),
                        new PowerVacuumCMD(vacuumSubsystem,1, continousVacuumServo,
                                telemetryManagerSub.getTelemetryObject(), vacuumSensor, 3)

        )

        );






    }
}
