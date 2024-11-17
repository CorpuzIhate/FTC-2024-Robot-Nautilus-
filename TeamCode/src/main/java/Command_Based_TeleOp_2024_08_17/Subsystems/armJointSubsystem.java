package Command_Based_TeleOp_2024_08_17.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.motors.Motor;

public class armJointSubsystem extends SubsystemBase {

    private final Motor m_jointMotor;
    private final PIDFController m_jointFeedForward;
    private final String m_tag;

    private final double m_maxExtensionJointSpeed;
    private final double m_maxRetractionJointSpeed;


    private double m_setpoint = 0;

    public armJointSubsystem(Motor jointMotor, PIDFController jointFeedForward, String tag,
                             double maxExtensionJointSpeed ,
                             double maxRetractionJointSpeed_PositiveInput,
                             double startingPos){
        m_jointMotor = jointMotor;
        m_jointFeedForward = jointFeedForward;
        m_tag = tag;
        m_setpoint = startingPos;
        m_maxExtensionJointSpeed = maxExtensionJointSpeed;
        m_maxRetractionJointSpeed = maxRetractionJointSpeed_PositiveInput;




    }
    public double limitJointSpeed(double currentJointOutput){
        double cappedOutput = currentJointOutput;
        double maxSpeedLimit = 0.7;
        if(Math.abs(currentJointOutput) > maxSpeedLimit )
        {
            currentJointOutput = maxSpeedLimit;
        }

        return cappedOutput;

    }
    public Motor getJointMotor(){
        return m_jointMotor;
    }

    public PIDFController getJointPIDFController(){
        return m_jointFeedForward;
    }
    public String getTag(){ return m_tag;}

    public void setSetpoint(double setpoint){ m_setpoint = setpoint;}

    public  double getSetpoint(){return m_setpoint;}



}
