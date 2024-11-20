package Arm_A_Kraken_DocBotics_FTC_2024.auto;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Arm_A_Kraken_DocBotics_FTC_2024.AutoCommands.MoveRobotEncoderXYCMD;
import Arm_A_Kraken_DocBotics_FTC_2024.AutoCommands.MoveRobotHCMD;
import Arm_A_Kraken_DocBotics_FTC_2024.AutoCommands.MoveRobotYCMD;
import Arm_A_Kraken_DocBotics_FTC_2024.Commands.PowerVacuumCMD;
import Arm_A_Kraken_DocBotics_FTC_2024.Commands.waitCMD;
import Arm_A_Kraken_DocBotics_FTC_2024.Constants;
@Autonomous
public class AutoPath1 extends AutoRobotContainer{
    @Override
    public void path(){
        schedule(new SequentialCommandGroup(



                        new MoveRobotEncoderXYCMD(6,
                                6,
                                5,
                                0.5,
                                mecanumDriveBaseSub,
                                telemetryManagerSub.getTelemetryObject()),
                        new MoveRobotHCMD(90,
                                mecanumDriveBaseSub,
                                telemetryManagerSub.getTelemetryObject()))





        );
    }
}
