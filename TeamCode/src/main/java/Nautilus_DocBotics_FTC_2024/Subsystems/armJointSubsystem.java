package Nautilus_DocBotics_FTC_2024.Subsystems;

import static Nautilus_DocBotics_FTC_2024.TeleOpRobotContainer.armState;
import static Nautilus_DocBotics_FTC_2024.TeleOpRobotContainer.isClimbing;
import static Nautilus_DocBotics_FTC_2024.TeleOpRobotContainer.previousArmState;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.motors.Motor;

import Nautilus_DocBotics_FTC_2024.Constants;

public class armJointSubsystem extends SubsystemBase {

    private final Motor m_jointMotor;
    private final PIDFController m_jointFeedForward;
    private final String m_tag;




    private double m_setpoint = 0;

    public armJointSubsystem(Motor jointMotor, PIDFController jointFeedForward,
                             String tag,
                             double startingPos){
        m_jointMotor = jointMotor;
        m_jointFeedForward = jointFeedForward;
        m_tag = tag;
        m_setpoint = startingPos;





    }
    public double limitJointSpeed(double currentJointSpeed, String tag){
        if(Constants.AutoConstants.isArmJointLimiterOff){
            return currentJointSpeed;
        }


        // + for the elbow is down
        // - for the elbow is up

            if (tag.equals("elbow")) {


                if (currentJointSpeed > 0.3) {// limits down speed
                    if(previousArmState.equals("foldUp")  && armState.equals("highBasket")) {
                        //from fold up to high basket, turn off the speed limiter
                        return currentJointSpeed;
                    }
                    if(previousArmState.equals("armHighBasketClearance")) {
                        return currentJointSpeed;
                    }


                    return 0.3;

                } else if (currentJointSpeed < -0.7) { // limits up speed
                    return -0.7;
                }

            }
        // + for the shoulder is up
        // - for the shoulder is down
//hi
        if(isClimbing)
        {
            return currentJointSpeed;
        }
            if (tag.equals("shoulder")) {
                if (currentJointSpeed > 0.7) {  // limits up speed
                    return 0.7;
                } else if (currentJointSpeed < -0.2) { // limits down speed
                    return -0.2;
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
