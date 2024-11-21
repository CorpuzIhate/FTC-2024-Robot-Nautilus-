package Arm_A_Kraken_DocBotics_FTC_2024.AutoCommands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDFController;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import Arm_A_Kraken_DocBotics_FTC_2024.Constants;
import Arm_A_Kraken_DocBotics_FTC_2024.Subsystems.MecanumDriveBaseSubsystem;
import Arm_A_Kraken_DocBotics_FTC_2024.auto.CircularPIDController;

public class MoveRobotYCMD extends CommandBase {
    private final double m_yPosSetpoint;

    private final Telemetry m_dashboardTelemetry;
    private final MecanumDriveBaseSubsystem m_MecanumDriveBaseSubsystem;
    public double yOutput;
    public double hOuput;

    double hpos;
    double yPos;
    private PIDFController yPosController;
    private  CircularPIDController hPosController;

    public MoveRobotYCMD( double yPosSetpoint,
                         MecanumDriveBaseSubsystem mecanumDriveBaseSubsystem,
                         Telemetry dashboardTelemetry){
        m_yPosSetpoint = yPosSetpoint;
        m_MecanumDriveBaseSubsystem =  mecanumDriveBaseSubsystem;
        m_dashboardTelemetry = dashboardTelemetry;


        hPosController = new CircularPIDController(
                Constants.AutoConstants.turnkP
        );
        yPosController = new PIDFController(
                Constants.AutoConstants.movekP,
                Constants.AutoConstants.movekI,
                Constants.AutoConstants.movekD,
                Constants.AutoConstants.movekF

        );

        yPosController.setTolerance(0.5);
    }

    @Override
    public  void  initialize(){

        yPos = m_MecanumDriveBaseSubsystem.getPosed2D().y;

    }

    @Override
    public  void  execute(){

            yPos = m_MecanumDriveBaseSubsystem.getPosed2D().y;
            UpdateAutoTelemetry( m_yPosSetpoint, yPosController );
            yOutput = yPosController.calculate(yPos,m_yPosSetpoint);
            if(Math.abs(yOutput) > 0.7)
            {
                yOutput = 0.7 * Math.signum(0.7);
            }
            m_MecanumDriveBaseSubsystem.setMotorSpeeds(-yOutput,0, 0);

    }
    @Override
    public boolean isFinished(){
        if(yPosController.atSetPoint() ){
            // m_dashboardTelemetry.addData("finished?",true);
            m_MecanumDriveBaseSubsystem.setMotorSpeeds(0,0, 0);
            return true;
        }
        return  false;
    }
    public void UpdateAutoTelemetry( double yPosSetpoint ,PIDFController ypidfController){
        m_dashboardTelemetry.addData("yPosSetpoint", yPosSetpoint);


        m_dashboardTelemetry.addData("pos x", m_MecanumDriveBaseSubsystem.getPosed2D().x);
        m_dashboardTelemetry.addData("pos y", m_MecanumDriveBaseSubsystem.getPosed2D().y);
        m_dashboardTelemetry.addData("pos h", m_MecanumDriveBaseSubsystem.getPosed2D().h);


        m_dashboardTelemetry.update();

    }
}
