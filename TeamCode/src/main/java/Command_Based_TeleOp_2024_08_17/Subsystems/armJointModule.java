package Command_Based_TeleOp_2024_08_17.Subsystems;

import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.motors.Motor;

import Command_Based_TeleOp_2024_08_17.Constants;

public class armJointModule {

    private final Motor m_jointMotor;
    private final PIDFController m_jointFeedForward;
    private final String m_tag;
    private double m_setpoint = 0;

    armJointModule(Motor jointMotor, PIDFController jointFeedForward,String tag){
        m_jointMotor = jointMotor;
        m_jointFeedForward = jointFeedForward;
        m_tag = tag;
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
