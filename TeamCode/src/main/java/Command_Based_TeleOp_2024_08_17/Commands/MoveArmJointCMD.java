package Command_Based_TeleOp_2024_08_17.Commands;



import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.motors.Motor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import Command_Based_TeleOp_2024_08_17.Subsystems.armJointSubsystem;

public class MoveArmJointCMD extends CommandBase {
    private final Telemetry m_dashboardTelemetry;
    private final armJointSubsystem m_jointSub;

    private final boolean m_useIsFinished;

    private  Motor jointMotor;
    private  PIDFController feedforward;
    private  double output;
    private boolean shoulderisAtpoint;
;


    public MoveArmJointCMD(Telemetry dashboardTelemetry,
                           armJointSubsystem jointSub, boolean useIsFinished) {

        m_dashboardTelemetry = dashboardTelemetry;
        m_jointSub = jointSub;

        m_useIsFinished = useIsFinished;
        addRequirements(jointSub);
    }

    @Override
    public void initialize(){
        jointMotor = m_jointSub.getJointMotor();
        feedforward = m_jointSub.getJointPIDFController();



        jointMotor.setRunMode(Motor.RunMode.RawPower);

        m_dashboardTelemetry.addData( m_jointSub.getTag() + " kP", feedforward.getP());
        m_dashboardTelemetry.addData(m_jointSub.getTag() + " kI",feedforward.getI());
        m_dashboardTelemetry.addData(m_jointSub.getTag() + " kD",feedforward.getD());
        m_dashboardTelemetry.addData(m_jointSub.getTag() + " kF",feedforward.getF());

        m_dashboardTelemetry.addData(m_jointSub.getTag() + " position", jointMotor.getCurrentPosition());
        m_dashboardTelemetry.update();
        m_dashboardTelemetry.addData(m_jointSub.getTag() + " setpoint", m_jointSub.getSetpoint());

//hi
    }
    @Override
    public void execute(){


        m_dashboardTelemetry.addData(m_jointSub.getTag() + " position", jointMotor.getCurrentPosition());
        m_dashboardTelemetry.addData(m_jointSub.getTag() + " setpoint", m_jointSub.getSetpoint());

        output = feedforward.calculate(jointMotor.getCurrentPosition(), m_jointSub.getSetpoint());

        m_dashboardTelemetry.update();
        if(feedforward.atSetPoint()){

            m_dashboardTelemetry.addData(m_jointSub.getTag() + " atPoint","yes");


        }

           jointMotor.set(output); // error might happen here cuz
            // we just pass the shoulderMotor object through the subsystem


    }

    @Override
    public boolean isFinished() {
        if(m_useIsFinished && feedforward.getSetPoint() ==  m_jointSub.getSetpoint() ){
            return true;
        }
        return false;

    }
}
