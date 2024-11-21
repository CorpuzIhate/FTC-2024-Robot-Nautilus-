package Nautilus_DocBotics_FTC_2024.auto.AutoPaths;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Nautilus_DocBotics_FTC_2024.AutoCommands.PowerVacuumAutoCMD;
import Nautilus_DocBotics_FTC_2024.Commands.PowerVacuumCMD;
import Nautilus_DocBotics_FTC_2024.AutoCommands.waitCMD;
import Nautilus_DocBotics_FTC_2024.Constants;
import Nautilus_DocBotics_FTC_2024.auto.AutoRobotContainer;

@Autonomous
public class PathLowBasket extends AutoRobotContainer {
    @Override
    public void path(){
        schedule(new SequentialCommandGroup(


                        new InstantCommand(() -> {
                            shoulderSub.setSetpoint(Constants.ShoulderSetpoints.middleShoulderPos);
                            elbowSub.setSetpoint(Constants.ElbowSetpoints.middleElbowPos);
                        } ),
                        new waitCMD(1),
                        new PowerVacuumAutoCMD(
                                vacuumSubsystem,
                                1,
                                continousVacuumServo,
                                telemetryManagerSub.getTelemetryObject(),
                                vacuumSensor,
                                3
                        ),
                        new waitCMD(1),
                        new InstantCommand(() -> {
                            shoulderSub.setSetpoint(300);
                            elbowSub.setSetpoint(100);
                         } )

                )


        );
    }
}
