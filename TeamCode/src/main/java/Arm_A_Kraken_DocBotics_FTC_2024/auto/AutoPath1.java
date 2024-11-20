package Arm_A_Kraken_DocBotics_FTC_2024.auto;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Arm_A_Kraken_DocBotics_FTC_2024.AutoCommands.MoveRobotYCMD;
import Arm_A_Kraken_DocBotics_FTC_2024.Commands.PowerVacuumCMD;
import Arm_A_Kraken_DocBotics_FTC_2024.Commands.waitCMD;
import Arm_A_Kraken_DocBotics_FTC_2024.Constants;
@Autonomous
public class AutoPath1 extends AutoRobotContainer{
    @Override
    public void path(){
        schedule(new SequentialCommandGroup(
                        new InstantCommand(() -> {
                            shoulderSub.setSetpoint(Constants.ShoulderSetpoints.highBasketShoulderPos);
                            elbowSub.setSetpoint(Constants.ElbowSetpoints.highBasketElbowPos);
                        }),
                        new waitCMD((3)),

                        new MoveRobotYCMD(-56
                                ,mecanumDriveBaseSub,
                                telemetryManagerSub.getTelemetryObject()),

                        new waitCMD(3)
                ),
                new PowerVacuumCMD(vacuumSubsystem,
                        1,
                        continousVacuumServo,
                        telemetryManagerSub.getTelemetryObject(),
                        vacuumSensor,
                        3)




        );
    }
}
