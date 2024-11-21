package Arm_A_Kraken_DocBotics_FTC_2024.auto;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Arm_A_Kraken_DocBotics_FTC_2024.AutoCommands.MoveRobotEncoderXYCMD;
import Arm_A_Kraken_DocBotics_FTC_2024.Commands.PowerVacuumCMD;
import Arm_A_Kraken_DocBotics_FTC_2024.Constants;


@Autonomous
public class PathCloseFromHighBucket extends AutoRobotContainer {
    @Override
    public void path(){
        schedule(new SequentialCommandGroup(


                // starts facing the Submersible
                        new MoveRobotEncoderXYCMD(24,24,3, 0.5,
                                mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),

                        new MoveRobotEncoderXYCMD(-30,30,3, 0.5,
                                mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),

                        new InstantCommand(() ->  {
                            shoulderSub.setSetpoint(Constants.ShoulderSetpoints.highBasketShoulderPos);
                            elbowSub.setSetpoint(Constants.ElbowSetpoints.highBasketElbowPos);
                        }),
                        new WaitCommand(3000),
                        new MoveRobotEncoderXYCMD(15,15,3, 0.5,
                                mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),
                        new PowerVacuumCMD(vacuumSubsystem,1, continousVacuumServo,
                                telemetryManagerSub.getTelemetryObject(), vacuumSensor, 3)

                )

        );






    }
}
