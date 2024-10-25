package Command_Based_TeleOp_2024_08_17.Commands;

import static Command_Based_TeleOp_2024_08_17.RobotContainer.armSetpoint;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.motors.Motor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import Command_Based_TeleOp_2024_08_17.Subsystems.armJointModule;
import Command_Based_TeleOp_2024_08_17.Subsystems.armSubsystem;

public class MoveArmJointCMD extends CommandBase {
    private final armSubsystem m_armSub;
    private final Telemetry m_dashboardTelemetry;


    private  Motor jointMotor;
    private  PIDFController feedforward;
    private  double output;
    private boolean shoulderisAtpoint;
    private double currentSetpoint = 0;
    private armJointModule m_joint;

    public MoveArmJointCMD(armSubsystem armSub, Telemetry dashboardTelemetry,
                           armJointModule joint) {
        m_armSub = armSub;
        m_dashboardTelemetry = dashboardTelemetry;
        m_joint = joint;

        addRequirements(m_armSub);
    }

    @Override
    public void initialize(){
        jointMotor = m_joint.getJointMotor();
        feedforward = m_joint.getJointPIDFController();


        jointMotor.setRunMode(Motor.RunMode.RawPower);

        m_dashboardTelemetry.addData("kP", feedforward.getP());
        m_dashboardTelemetry.addData("kI",feedforward.getI());
        m_dashboardTelemetry.addData("kD",feedforward.getD());
        m_dashboardTelemetry.addData("kF",feedforward.getF());

        m_dashboardTelemetry.addData("position", jointMotor.getCurrentPosition());
        m_dashboardTelemetry.update();
//hi
    }
    @Override
    public void execute(){


        m_dashboardTelemetry.addData("position", jointMotor.getCurrentPosition());
        m_dashboardTelemetry.addData("setpoint",currentSetpoint);
        output = feedforward.calculate(jointMotor.getCurrentPosition(), armSetpoint);

        m_dashboardTelemetry.update();
        if(feedforward.atSetPoint()){

            m_dashboardTelemetry.addData("atPoint","yes");


        }

            jointMotor.set(output); // error might happen here cuz
            // we just pass the shoulderMotor object through the subsystem


    }

    @Override
    public boolean isFinished() {
        return false;

    }
}
