package Arm_A_Kraken_DocBotics_FTC_2024.Commands;



import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.controller.wpilibcontroller.SimpleMotorFeedforward;
import com.arcrobotics.ftclib.hardware.motors.Motor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import Arm_A_Kraken_DocBotics_FTC_2024.Subsystems.armJointSubsystem;

public class MoveArmJointCMD extends CommandBase {
    private final Telemetry m_dashboardTelemetry;
    private final armJointSubsystem m_jointSub;

    private  Motor jointMotor;
    private  PIDFController jointPID;
    private SimpleMotorFeedforward jointFeedForward;
    private  double jointSpeed;
    private boolean shoulderisAtpoint;
;


    public MoveArmJointCMD(Telemetry dashboardTelemetry,
                           armJointSubsystem jointSub) {

        m_dashboardTelemetry = dashboardTelemetry;
        m_jointSub = jointSub;


        addRequirements(jointSub);
    }

    @Override
    public void initialize(){
        jointMotor = m_jointSub.getJointMotor();
        jointPID = m_jointSub.getJointPIDFController();
        jointFeedForward = new SimpleMotorFeedforward(0,0,0);


        jointMotor.setRunMode(Motor.RunMode.RawPower);

        m_dashboardTelemetry.addData( m_jointSub.getTag() + " kP", jointPID.getP());
        m_dashboardTelemetry.addData(m_jointSub.getTag() + " kI", jointPID.getI());
        m_dashboardTelemetry.addData(m_jointSub.getTag() + " kD", jointPID.getD());
        m_dashboardTelemetry.addData(m_jointSub.getTag() + " kF", jointPID.getF());

        m_dashboardTelemetry.addData(m_jointSub.getTag() + " position_degrees", m_jointSub.getEncoderPos_Degrees());
        m_dashboardTelemetry.update();
        m_dashboardTelemetry.addData(m_jointSub.getTag() + " setpoint_degrees", m_jointSub.getSetpoint());

//hi
    }
    @Override
    public void execute(){


        m_dashboardTelemetry.addData(m_jointSub.getTag() + " position_degrees", m_jointSub.getEncoderPos_Degrees());
        m_dashboardTelemetry.addData(m_jointSub.getTag() + " setpoint_degrees", m_jointSub.getSetpoint());

        jointSpeed = jointPID.calculate(m_jointSub.getEncoderPos_Degrees(), m_jointSub.getSetpoint());


        m_dashboardTelemetry.update();
        if(jointPID.atSetPoint()){

            m_dashboardTelemetry.addData(m_jointSub.getTag() + " atPoint","yes");


        }
            //jointSpeed = m_jointSub.limitJointSpeed(jointSpeed, m_jointSub.getTag());
        jointSpeed = m_jointSub.limitJointSpeed(jointSpeed, m_jointSub.getTag());

        m_dashboardTelemetry.addData(m_jointSub.getTag() + " output", jointSpeed);
           jointMotor.set(jointSpeed);


    }

    @Override
    public boolean isFinished() {

        return false;

    }
}
