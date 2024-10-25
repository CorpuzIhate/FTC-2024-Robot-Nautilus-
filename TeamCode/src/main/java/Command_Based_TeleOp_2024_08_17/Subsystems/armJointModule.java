package Command_Based_TeleOp_2024_08_17.Subsystems;

import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.motors.Motor;

import Command_Based_TeleOp_2024_08_17.Constants;

public class armJointModule {

    private final Motor m_jointMotor;
    private final PIDFController m_jointFeedForward;
    armJointModule(Motor jointMotor, PIDFController jointFeedForward){
        m_jointMotor = jointMotor;
        m_jointFeedForward = jointFeedForward;
    }
    public Motor getJointMotor(){
        return m_jointMotor;
    }
    public PIDFController getJointPIDFController(){
        return m_jointFeedForward;
    }


}
