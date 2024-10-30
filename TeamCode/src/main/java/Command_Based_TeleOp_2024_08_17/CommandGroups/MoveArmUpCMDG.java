package Command_Based_TeleOp_2024_08_17.CommandGroups;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import Command_Based_TeleOp_2024_08_17.Commands.MoveArmJointCMD;
import Command_Based_TeleOp_2024_08_17.Subsystems.armJointModule;
import Command_Based_TeleOp_2024_08_17.Subsystems.armSubsystem;

public class MoveArmUpCMDG extends SequentialCommandGroup {


    public MoveArmUpCMDG(double shoulderPositions, double elbowPositions,
                         armSubsystem armSub, Telemetry dashboardTelemetry){

        new SequentialCommandGroup(
                new MoveArmJointCMD(armSub,dashboardTelemetry,armSub.getElbowJoint() , true),
                new MoveArmJointCMD(armSub,dashboardTelemetry,armSub.getShoulderJoint() , true)
        );
    }


}
