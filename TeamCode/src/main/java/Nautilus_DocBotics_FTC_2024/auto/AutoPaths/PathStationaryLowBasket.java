package Nautilus_DocBotics_FTC_2024.auto.AutoPaths;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Nautilus_DocBotics_FTC_2024.AutoCommands.MoveRobotEncoderXYCMD;
import Nautilus_DocBotics_FTC_2024.AutoCommands.PowerVacuumAutoCMD;
import Nautilus_DocBotics_FTC_2024.Constants;
import Nautilus_DocBotics_FTC_2024.auto.AutoRobotContainer;

@Autonomous
public class PathStationaryLowBasket extends AutoRobotContainer {
    @Override
    public void path(){
        schedule(new SequentialCommandGroup(


                        new InstantCommand(() -> {
                            shoulderSub.setSetpoint(Constants.ShoulderSetpoints.middleShoulderPos);
                            elbowSub.setSetpoint(Constants.ElbowSetpoints.middleElbowPos);
                        } ),
                        new WaitCommand(3000),
                        new PowerVacuumAutoCMD(
                                vacuumSubsystem,
                                1,
                                continousVacuumServo,
                                telemetryManagerSub.getTelemetryObject(),
                                vacuumSensor,
                                3
                        ),
                        new WaitCommand(3000),
                        new InstantCommand(() -> {
                            shoulderSub.setSetpoint(300);
                            elbowSub.setSetpoint(100);
                         } ),
                        new MoveRobotEncoderXYCMD(24,24,3, 0.5,
                        mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject())


                )


        );
    }
}
