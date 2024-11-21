package Nautilus_DocBotics_FTC_2024.Subsystems;

import static Nautilus_DocBotics_FTC_2024.TeleOpRobotContainer.isClimbing;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.motors.Motor;

import Nautilus_DocBotics_FTC_2024.Constants;

public class armJointSubsystem extends SubsystemBase {

    private final Motor m_jointMotor;
    private final PIDFController m_jointFeedForward;
    private final String m_tag;


    private double m_setpoint = 0;

    public armJointSubsystem(Motor jointMotor, PIDFController jointFeedForward, String tag,
                             double startingPos){
        m_jointMotor = jointMotor;
        m_jointFeedForward = jointFeedForward;
        m_tag = tag;
        m_setpoint = startingPos;


    }
    public double limitJointSpeed(double currentJointSpeed, String tag){
        if(Constants.AutoConstants.isAuto){
            return currentJointSpeed;
        }


        // + for the elbow is down
        // - for the elbow is up
        if( tag.equals("elbow") ) {
            if( currentJointSpeed > Constants.teleOpConstants.maxDownElbowSpeed) {// limits down speed
                return Constants.teleOpConstants.maxDownElbowSpeed ;
            }
            else if( currentJointSpeed < Constants.teleOpConstants.maxUpElbowSpeed){ // limits up speed
                return Constants.teleOpConstants.maxUpElbowSpeed;
            }

        }
        // + for the shoulder is up
        // - for the shoulder is down

        if(isClimbing)
        {
            return currentJointSpeed;
        }
            if (tag.equals("shoulder")) {
                if (currentJointSpeed > Constants.teleOpConstants.maxUpShoulderSpeed) {  // limits up speed
                    return Constants.teleOpConstants.maxUpShoulderSpeed;
                } else if (currentJointSpeed < Constants.teleOpConstants.maxDownShoulderSpeed) { // limits down speed
                    return Constants.teleOpConstants.maxDownShoulderSpeed;
                }

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

    public void setSetpoint(double setpoint){ m_setpoint = setpoint;}

    public  double getSetpoint(){return m_setpoint;}



}
