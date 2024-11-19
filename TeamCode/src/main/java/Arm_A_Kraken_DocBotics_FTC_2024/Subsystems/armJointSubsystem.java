package Arm_A_Kraken_DocBotics_FTC_2024.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.motors.Motor;

import Arm_A_Kraken_DocBotics_FTC_2024.Constants;

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
    public double limitJointSpeed(double currentJointSpeed, String tag ){


        if( tag == "elbow" && Math.abs(currentJointSpeed) > 0.5) {
            return Math.signum(currentJointSpeed) *  0.5;

        }


        return currentJointSpeed;

    }
    public Motor getJointMotor(){
        return m_jointMotor;
    }

    public PIDFController getJointPIDFController(){
        return m_jointFeedForward;
    }
    public String getTag(){ return m_tag;}
    public double getEncoderPos_Degrees(){
        return
                m_jointMotor.getCurrentPosition() * Constants.armTicksToDegreesConversionFactor;
    }

    public void setSetpoint(double setpoint){ m_setpoint = setpoint;}

    public  double getSetpoint(){return m_setpoint;}



}
