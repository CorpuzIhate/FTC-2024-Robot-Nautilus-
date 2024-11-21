package Arm_A_Kraken_DocBotics_FTC_2024.AutoCommands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDFController;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import Arm_A_Kraken_DocBotics_FTC_2024.Constants;
import Arm_A_Kraken_DocBotics_FTC_2024.Subsystems.MecanumDriveBaseSubsystem;

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

        xPosController.setTolerance(0.5);
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
        xPos = m_MecanumDriveBaseSubsystem.getPosed2D().x;
        yPos = m_MecanumDriveBaseSubsystem.getPosed2D().y;

    }

    @Override
    public  void  execute(){


            xPos = m_MecanumDriveBaseSubsystem.getPosed2D().x;
            yPos = m_MecanumDriveBaseSubsystem.getPosed2D().y;
            UpdateAutoTelemetry(m_xPosSetpoint, m_yPosSetpoint,xPosController, yPosController );

            xOutput = xPosController.calculate(xPos,m_xPosSetpoint);
            yOutput = yPosController.calculate(yPos,m_yPosSetpoint);
            m_MecanumDriveBaseSubsystem.setMotorSpeeds(-yOutput,xOutput, 0);

    }
    @Override
    public boolean isFinished(){
        if(xPosController.atSetPoint()  && yPosController.atSetPoint() ){
           //
            // m_dashboardTelemetry.addData("finished?",true);
            m_MecanumDriveBaseSubsystem.setMotorSpeeds(0,0, 0);
            return true;
        }
        return  false;
    }
    public void UpdateAutoTelemetry(double xPosSetpoint, double yPosSetpoint , PIDFController xpidfController,PIDFController ypidfController){
        m_dashboardTelemetry.addData("xPosSetpoint", xPosSetpoint);
        m_dashboardTelemetry.addData("yPosSetpoint", yPosSetpoint);


        m_dashboardTelemetry.addData("pos x", m_MecanumDriveBaseSubsystem.getPosed2D().x);
        m_dashboardTelemetry.addData("pos y", m_MecanumDriveBaseSubsystem.getPosed2D().y);
        m_dashboardTelemetry.addData("pos h", m_MecanumDriveBaseSubsystem.getPosed2D().h);
//        m_dashboardTelemetry.addData("Xerror",xpidfController.getPositionError());
      //  m_dashboardTelemetry.addData("XatSetpoint",xpidfController.atSetPoint());

 //       m_dashboardTelemetry.addData("Yerror",ypidfController.getPositionError());
     //   m_dashboardTelemetry.addData("YatSetpoint",ypidfController.atSetPoint());

        m_dashboardTelemetry.update();

    }
}
