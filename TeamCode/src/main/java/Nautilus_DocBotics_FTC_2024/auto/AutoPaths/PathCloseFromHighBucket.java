package Nautilus_DocBotics_FTC_2024.auto.AutoPaths;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Nautilus_DocBotics_FTC_2024.AutoCommands.MoveRobotEncoderXYCMD;
import Nautilus_DocBotics_FTC_2024.AutoCommands.PowerVacuumAutoCMD;
import Nautilus_DocBotics_FTC_2024.Constants;
import Nautilus_DocBotics_FTC_2024.auto.AutoRobotContainer;

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
                        new MoveRobotEncoderXYCMD(10,10,3, 0.5,
                                mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),
                        new PowerVacuumAutoCMD(vacuumSubsystem,1, continousVacuumServo,
                                telemetryManagerSub.getTelemetryObject(), vacuumSensor, 3)

                )

        );






    }
}
