package Command_Based_TeleOp_2024_08_17.Commands;



import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.motors.Motor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import Command_Based_TeleOp_2024_08_17.Subsystems.armJointModule;
import Command_Based_TeleOp_2024_08_17.Subsystems.armSubsystem;

public class MoveArmJointCMD extends CommandBase {
    private final armSubsystem m_armSub;
    private final Telemetry m_dashboardTelemetry;
    private final armJointModule m_joint;
    private final double m_jointSetPoint;
    private final boolean m_useIsFinished;

    private  Motor jointMotor;
    private  PIDFController feedforward;
    private  double output;
    private boolean shoulderisAtpoint;
    private double currentSetpoint = 0;


    public MoveArmJointCMD(armSubsystem armSub, Telemetry dashboardTelemetry,
                           armJointModule joint, double jointSetPoint, boolean useIsFinished) {
        m_armSub = armSub;
        m_dashboardTelemetry = dashboardTelemetry;
        m_joint = joint;
        m_jointSetPoint = jointSetPoint;
        m_useIsFinished = useIsFinished;
        addRequirements(m_armSub);
    }

    @Override
    public void initialize(){
        jointMotor = m_joint.getJointMotor();
        feedforward = m_joint.getJointPIDFController();


        jointMotor.setRunMode(Motor.RunMode.RawPower);

        m_dashboardTelemetry.addData( m_joint.getTag() + " kP", feedforward.getP());
        m_dashboardTelemetry.addData(m_joint.getTag() + " kI",feedforward.getI());
        m_dashboardTelemetry.addData(m_joint.getTag() + " kD",feedforward.getD());
        m_dashboardTelemetry.addData(m_joint.getTag() + " kF",feedforward.getF());

        m_dashboardTelemetry.addData(m_joint.getTag() + " position", jointMotor.getCurrentPosition());
        m_dashboardTelemetry.update();
//hi
    }
    @Override
    public void execute(){


        m_dashboardTelemetry.addData(m_joint.getTag() + " position", jointMotor.getCurrentPosition());
        m_dashboardTelemetry.addData(m_joint.getTag() + " setpoint",currentSetpoint);
        output = feedforward.calculate(jointMotor.getCurrentPosition(), m_jointSetPoint);

        m_dashboardTelemetry.update();
        if(feedforward.atSetPoint()){

            m_dashboardTelemetry.addData(m_joint.getTag() + " atPoint","yes");


        }

            jointMotor.set(output); // error might happen here cuz
            // we just pass the shoulderMotor object through the subsystem


    }

    @Override
    public boolean isFinished() {
        if(m_useIsFinished && feedforward.atSetPoint()){
            return true;
        }
        return false;

    }
}
