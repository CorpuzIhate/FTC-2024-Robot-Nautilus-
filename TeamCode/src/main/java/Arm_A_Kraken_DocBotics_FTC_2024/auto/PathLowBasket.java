package Arm_A_Kraken_DocBotics_FTC_2024.auto;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import Arm_A_Kraken_DocBotics_FTC_2024.Commands.PowerVacuumCMD;
import Arm_A_Kraken_DocBotics_FTC_2024.Commands.waitCMD;
import Arm_A_Kraken_DocBotics_FTC_2024.Constants;
@Disabled
@Autonomous
public class PathLowBasket extends AutoRobotContainer{
    @Override
    public void path(){
        schedule(new SequentialCommandGroup(


                        new InstantCommand(() -> {
                            shoulderSub.setSetpoint(Constants.ShoulderSetpoints.middleShoulderPos);
                            elbowSub.setSetpoint(Constants.ElbowSetpoints.middleElbowPos);
                        } ),
                        new waitCMD(1),
                        new PowerVacuumCMD(
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
