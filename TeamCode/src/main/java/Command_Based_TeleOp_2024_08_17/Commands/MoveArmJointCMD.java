package Command_Based_TeleOp_2024_08_17.Commands;



import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.controller.wpilibcontroller.SimpleMotorFeedforward;
import com.arcrobotics.ftclib.hardware.motors.Motor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import Command_Based_TeleOp_2024_08_17.Subsystems.armJointSubsystem;

public class MoveArmJointCMD extends CommandBase {
    private final Telemetry m_dashboardTelemetry;
    private final armJointSubsystem m_jointSub;

    private  Motor jointMotor;
    private  PIDFController jointPID;
    private SimpleMotorFeedforward jointFeedForward;
    private  double output;
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

        m_dashboardTelemetry.addData(m_jointSub.getTag() + " position", jointMotor.getCurrentPosition());
        m_dashboardTelemetry.update();
        m_dashboardTelemetry.addData(m_jointSub.getTag() + " setpoint", m_jointSub.getSetpoint());

//hi
    }
    @Override
    public void execute(){


        m_dashboardTelemetry.addData(m_jointSub.getTag() + " position", jointMotor.getCurrentPosition());
        m_dashboardTelemetry.addData(m_jointSub.getTag() + " setpoint", m_jointSub.getSetpoint());

        output = jointPID.calculate(jointMotor.getCurrentPosition(), m_jointSub.getSetpoint());


        m_dashboardTelemetry.update();
        if(jointPID.atSetPoint()){

            m_dashboardTelemetry.addData(m_jointSub.getTag() + " atPoint","yes");


        }
            //output =
        m_dashboardTelemetry.addData(m_jointSub.getTag() + " output", m_jointSub.limitJointSpeed(output));
           jointMotor.set(output); // error might happen here cuz
            // we just pass the shoulderMotor object through the subsystem hi


    }

    @Override
    public boolean isFinished() {

        return false;

    }
}
