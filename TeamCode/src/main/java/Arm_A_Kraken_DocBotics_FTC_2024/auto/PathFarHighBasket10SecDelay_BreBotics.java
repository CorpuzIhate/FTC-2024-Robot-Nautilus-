package Arm_A_Kraken_DocBotics_FTC_2024.auto;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Arm_A_Kraken_DocBotics_FTC_2024.AutoCommands.MoveRobotEncoderXYCMD;
import Arm_A_Kraken_DocBotics_FTC_2024.Commands.PowerVacuumCMD;
import Arm_A_Kraken_DocBotics_FTC_2024.Constants;
@Autonomous
public class PathFarHighBasket10SecDelay_BreBotics extends AutoRobotContainer{
    @Override
    public void path(){
        schedule( new SequentialCommandGroup(
                new MoveRobotEncoderXYCMD(3, 3, 3, 0.5,
                        mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),



                new MoveRobotEncoderXYCMD(-20, 20, 3, 0.5,
                        mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),
                new InstantCommand( () -> {
                    shoulderSub.setSetpoint(Constants.ShoulderSetpoints.highBasketShoulderPos);
                    elbowSub.setSetpoint(Constants.ElbowSetpoints.highBasketElbowPos);
                }),

                new MoveRobotEncoderXYCMD(63, 63, 3, 0.15,
                        mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),


                new MoveRobotEncoderXYCMD(-8, 9, 3, 0.5,
                        mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),
                new MoveRobotEncoderXYCMD(8, 8, 3, 0.5,
                        mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),
                new PowerVacuumCMD(vacuumSubsystem,1, continousVacuumServo,
                        telemetryManagerSub.getTelemetryObject(), vacuumSensor, 3),

                new MoveRobotEncoderXYCMD(-12, -12, 3, 0.5,
                        mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),

                new InstantCommand( () -> {
                    shoulderSub.setSetpoint(Constants.ShoulderSetpoints.shoulderClearancePos);
                    elbowSub.setSetpoint(300);
                })
        ));


    }

}
