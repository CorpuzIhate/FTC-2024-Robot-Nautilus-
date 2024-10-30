package Command_Based_TeleOp_2024_08_17.CommandGroups;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import Command_Based_TeleOp_2024_08_17.Subsystems.armJointSubsystem;

public class MoveArmUpCMDG extends SequentialCommandGroup {


    public MoveArmUpCMDG(double shoulderPositions, double elbowPositions,
                         armJointSubsystem armSub, Telemetry dashboardTelemetry){

        new SequentialCommandGroup(
//                new MoveArmJointCMD(armSub,dashboardTelemetry, , true),
//                new MoveArmJointCMD(armSub,dashboardTelemetry, , true)
        );
    }


}
