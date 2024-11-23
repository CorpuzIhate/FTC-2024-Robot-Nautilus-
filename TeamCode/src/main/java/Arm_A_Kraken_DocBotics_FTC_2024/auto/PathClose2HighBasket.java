package Arm_A_Kraken_DocBotics_FTC_2024.auto;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Arm_A_Kraken_DocBotics_FTC_2024.AutoCommands.MoveRobotEncoderXYCMD;
import Arm_A_Kraken_DocBotics_FTC_2024.Commands.PowerVacuumCMD;
import Arm_A_Kraken_DocBotics_FTC_2024.Constants;
@Autonomous
public class PathClose2HighBasket extends AutoRobotContainer{
    @Override
    public void path(){
        schedule(new SequentialCommandGroup(


                // starts facing the Submersible
                new MoveRobotEncoderXYCMD(21,21,3, 0.5,
                        mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),

                new MoveRobotEncoderXYCMD(-30,30,3, 0.5,
                        mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),

                new InstantCommand(() ->  {
                    shoulderSub.setSetpoint(Constants.ShoulderSetpoints.highBasketShoulderPos);
                    elbowSub.setSetpoint(Constants.ElbowSetpoints.highBasketElbowPos);
                }),
                new WaitCommand(1000),
                new MoveRobotEncoderXYCMD(25,25,3, 0.5,
                        mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),
                new PowerVacuumCMD(vacuumSubsystem,1, continousVacuumServo,
                        telemetryManagerSub.getTelemetryObject(), vacuumSensor, 3),
                //drive to high basket and drop off a sample




                new MoveRobotEncoderXYCMD(-9,-9,3, 0.5,
                        mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),
                new MoveRobotEncoderXYCMD(30,-30,3, 0.5,
                        mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),
                new InstantCommand(() ->{
                    Constants.AutoConstants.isArmJointLimiterOff = false;
                    shoulderSub.setSetpoint(Constants.ShoulderSetpoints.shoulderClearancePos);
                    elbowSub.setSetpoint(300);

                 }),
                new WaitCommand(2000),
                new InstantCommand(() ->{

                    shoulderSub.setSetpoint(Constants.ShoulderSetpoints.shoulderSubmersiblePickUpPos);
                    elbowSub.setSetpoint(Constants.ElbowSetpoints.elbowSubmersiblePickUpPos);

                }),
                new MoveRobotEncoderXYCMD(7,7,3, 0.5,
                        mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),
                new WaitCommand(2000),
                new PowerVacuumCMD(vacuumSubsystem,-1, continousVacuumServo,
                        telemetryManagerSub.getTelemetryObject(), vacuumSensor, 3)

                ),
                new MoveRobotEncoderXYCMD(7,7,3, 0.5,
                        mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),
                new MoveRobotEncoderXYCMD(-30,30,3, 0.5,
                        mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),
                new InstantCommand(() ->  {
                    shoulderSub.setSetpoint(Constants.ShoulderSetpoints.highBasketShoulderPos);
                    elbowSub.setSetpoint(Constants.ElbowSetpoints.highBasketElbowPos);
                }),
                new WaitCommand(2000),
                new MoveRobotEncoderXYCMD(9,9,3, 0.5,
                        mecanumDriveBaseSub, telemetryManagerSub.getTelemetryObject()),
                new PowerVacuumCMD(vacuumSubsystem,1, continousVacuumServo,
                        telemetryManagerSub.getTelemetryObject(), vacuumSensor, 3)




        );
    };
}
