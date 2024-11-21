package Nautilus_DocBotics_FTC_2024.AutoCommands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import Nautilus_DocBotics_FTC_2024.Constants;
import Nautilus_DocBotics_FTC_2024.Subsystems.MecanumDriveBaseSubsystem;
import Nautilus_DocBotics_FTC_2024.auto.CircularPIDController;

public class MoveRobotHCMD extends CommandBase {
    private final double m_hPosSetpoint;


    private final Telemetry m_dashboardTelemetry;
    private final MecanumDriveBaseSubsystem m_MecanumDriveBaseSubsystem;

    public double hOutput;

    double hPos;

    private  CircularPIDController hPosController;

    public MoveRobotHCMD(double hPosSetpoint,
                         MecanumDriveBaseSubsystem mecanumDriveBaseSubsystem,
                         Telemetry dashboardTelemetry){
        hPosController = new CircularPIDController(
                Constants.AutoConstants.turnkP);



        m_hPosSetpoint = hPosSetpoint;
        m_MecanumDriveBaseSubsystem =  mecanumDriveBaseSubsystem;
        m_dashboardTelemetry = dashboardTelemetry;
    }

    @Override
    public  void  initialize(){
        hPos = m_MecanumDriveBaseSubsystem.getPosed2D().h;
        hOutput = hPosController.calculate(hPos,m_hPosSetpoint);
        m_dashboardTelemetry.addData("went throguht intial cmd", true);
    }

    @Override
    public  void  execute(){


            hPos = m_MecanumDriveBaseSubsystem.getPosed2D().h;


            hOutput = hPosController.calculate(hPos,m_hPosSetpoint);
            UpdateAutoTelemetry(hPosController);

            m_MecanumDriveBaseSubsystem.setMotorSpeeds(0,0, -hOutput);

    }
    @Override
    public boolean isFinished(){
        if(hPosController.atSetPoint()){

            m_MecanumDriveBaseSubsystem.setMotorSpeeds(0,0,0);
            return true;
        }
        else {
            return false;
        }
    }
    public void UpdateAutoTelemetry( CircularPIDController hController){



        m_dashboardTelemetry.addData("pos h", m_MecanumDriveBaseSubsystem.getPosed2D().h);

        m_dashboardTelemetry.addData("error", hPosController.getError());
        m_dashboardTelemetry.addData("counterClockwiseAngleShorter", hPosController.isCounterClockwiseAngleShorter());
        m_dashboardTelemetry.addData("atSetpoint?", hPosController.atSetPoint());



        m_dashboardTelemetry.update();

    }
}
