package Command_Based_TeleOp_2024_08_17.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.motors.Motor;

import Command_Based_TeleOp_2024_08_17.Constants;

public class armSubsystem extends SubsystemBase {

    private final armJointModule m_elbowJoint;
    private final armJointModule m_shoulderJointModule;

    public armSubsystem(Motor shoulder_Motor, Motor elbow_Motor){
        PIDFController m_ShoulderfeedForward = new PIDFController(
                Constants.ShoulderPIDConstants.kP,
                Constants.ShoulderPIDConstants.kI,
                Constants.ShoulderPIDConstants.kD,
                Constants.ShoulderPIDConstants.kF);
        m_shoulderJointModule = new armJointModule(shoulder_Motor,m_ShoulderfeedForward);

        PIDFController m_ElbowForward = new PIDFController(
                Constants.ElbowPIDConstants.kP,
                Constants.ElbowPIDConstants.kI,
                Constants.ElbowPIDConstants.kD,
                Constants.ElbowPIDConstants.kF);
        m_elbowJoint = new armJointModule(elbow_Motor,m_ElbowForward);
    }
    public armJointModule getElbowJoint(){
        return m_elbowJoint;
    }
    public armJointModule getShoulderJoint(){
      return m_shoulderJointModule;
    }


}
