package Command_Based_TeleOp_2024_08_17.AutoCommands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDFController;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import Command_Based_TeleOp_2024_08_17.Constants;
import Command_Based_TeleOp_2024_08_17.Subsystems.MecanumDriveBaseSubsystem;

public class MoveRobotXYCMD extends CommandBase {
    private final double m_xPosSetpoint;
    private final double m_yPosSetpoint;

    private final Telemetry m_dashboardTelemetry;
    private final MecanumDriveBaseSubsystem m_MecanumDriveBaseSubsystem;
    public double xOutput;
    public double yOutput;
    double xPos;
    double yPos;
    private PIDFController xPosController;
    private  PIDFController yPosController;
    private  PIDFController hPosController;

    public MoveRobotXYCMD(double xPosSetpoint, double yPosSetpoint,
                          MecanumDriveBaseSubsystem mecanumDriveBaseSubsystem,
                          Telemetry dashboardTelemetry){
        m_xPosSetpoint = xPosSetpoint;
        m_yPosSetpoint = yPosSetpoint;
        m_MecanumDriveBaseSubsystem =  mecanumDriveBaseSubsystem;
        m_dashboardTelemetry = dashboardTelemetry;

        xPosController = new PIDFController(
                Constants.AutoConstants.movekP,
                Constants.AutoConstants.movekI,
                Constants.AutoConstants.movekD,
                Constants.AutoConstants.movekF

        );

        yPosController = new PIDFController(
                Constants.AutoConstants.movekP,
                Constants.AutoConstants.movekI,
                Constants.AutoConstants.movekD,
                Constants.AutoConstants.movekF

        );

    }

    @Override
    public  void  initialize(){
        xPos = m_MecanumDriveBaseSubsystem.getPosed2D().x;
        yPos = m_MecanumDriveBaseSubsystem.getPosed2D().y;
    }

    @Override
    public  void  execute(){


            xPos = m_MecanumDriveBaseSubsystem.getPosed2D().x;
            yPos = m_MecanumDriveBaseSubsystem.getPosed2D().y;
            UpdateAutoTelemetry(m_xPosSetpoint, m_yPosSetpoint);

            xOutput = xPosController.calculate(xPos,m_xPosSetpoint);
            yOutput = yPosController.calculate(yPos,m_yPosSetpoint);
            m_MecanumDriveBaseSubsystem.setMotorSpeeds(xOutput,yOutput, 0);

    }
    @Override
    public boolean isFinished(){
        if(xPosController.atSetPoint()  && yPosController.atSetPoint() ){
            return true;
        }
        return  false;
    }
    public void UpdateAutoTelemetry(double xPosSetpoint, double yPosSetpoint ){
        m_dashboardTelemetry.addData("xPosSetpoint", xPosSetpoint);
        m_dashboardTelemetry.addData("yPosSetpoint", yPosSetpoint);


        m_dashboardTelemetry.addData("pos x", m_MecanumDriveBaseSubsystem.getPosed2D().x);
        m_dashboardTelemetry.addData("pos y", m_MecanumDriveBaseSubsystem.getPosed2D().y);
        m_dashboardTelemetry.addData("pos h", m_MecanumDriveBaseSubsystem.getPosed2D().h);



        m_dashboardTelemetry.update();

    }
}
