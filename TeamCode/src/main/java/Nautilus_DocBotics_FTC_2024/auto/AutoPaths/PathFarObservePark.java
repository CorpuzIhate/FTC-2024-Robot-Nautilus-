package Nautilus_DocBotics_FTC_2024.auto.AutoPaths;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Nautilus_DocBotics_FTC_2024.AutoCommands.MoveRobotEncoderXYCMD;
import Nautilus_DocBotics_FTC_2024.Constants;
import Nautilus_DocBotics_FTC_2024.auto.AutoRobotContainer;

@Autonomous
public class PathFarObservePark extends AutoRobotContainer {

    @Override
    public void path() {
        schedule(new SequentialCommandGroup(
                new MoveRobotEncoderXYCMD(5, 5, 1, 0.5,
                        mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),
                        new MoveRobotEncoderXYCMD(-20, 20, 1, 0.5,
                                mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),
                        new MoveRobotEncoderXYCMD(-40, -40, 1, 0.5,
                                mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject())

                )


        );
    }
}