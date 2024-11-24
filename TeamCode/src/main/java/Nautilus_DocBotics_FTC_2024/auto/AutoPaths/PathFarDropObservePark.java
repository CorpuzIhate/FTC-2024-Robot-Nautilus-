package Nautilus_DocBotics_FTC_2024.auto.AutoPaths;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import Nautilus_DocBotics_FTC_2024.AutoCommands.MoveRobotEncoderXYCMD;
import Nautilus_DocBotics_FTC_2024.Constants;
import Nautilus_DocBotics_FTC_2024.auto.AutoRobotContainer;

@Disabled
@Autonomous
public class PathFarDropObservePark extends AutoRobotContainer {
    @Override
    public void path(){
        schedule(new SequentialCommandGroup(
                        new MoveRobotEncoderXYCMD(24,-24,3, 0.5,
                        mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),
                    new MoveRobotEncoderXYCMD(24,24,3, 0.5,
                        mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),

                        new InstantCommand(() -> {
                            shoulderSub.setSetpoint(Constants.ShoulderSetpoints.shoulderClearancePos);
                            elbowSub.setSetpoint(Constants.ElbowSetpoints.elbowClearancePos);
                        } ),
                        new WaitCommand(3000),
                        new InstantCommand(() -> {
                            shoulderSub.setSetpoint(Constants.ShoulderSetpoints.shoulderSubmersiblePickUpPos);
                            elbowSub.setSetpoint(Constants.ElbowSetpoints.elbowSubmersiblePickUpPos);
                        }),
                        new WaitCommand(3000),
                        new InstantCommand(() -> {
                            shoulderSub.setSetpoint(300);
                            elbowSub.setSetpoint(100);
                        })





                )


        );
    }
}

