package Command_Based_TeleOp_2024_08_17.AutoCommands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDFController;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import Command_Based_TeleOp_2024_08_17.Constants;
import Command_Based_TeleOp_2024_08_17.Subsystems.MecanumDriveBaseSubsystem;

public class MoveRobotHCMD extends CommandBase {
    private final double m_hPosSetpoint;


    private final Telemetry m_dashboardTelemetry;
    private final MecanumDriveBaseSubsystem m_MecanumDriveBaseSubsystem;

    public double hOutput;

    double hPos;

    private  PIDFController hPosController;

    public MoveRobotHCMD(double hPosSetpoint,
                         MecanumDriveBaseSubsystem mecanumDriveBaseSubsystem,
                         Telemetry dashboardTelemetry){
        hPosController = new PIDFController(
                Constants.AutoConstants.turnkP,
                Constants.AutoConstants.turnkI,
                Constants.AutoConstants.turnkD,
                Constants.AutoConstants.turnkF

        );

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
            UpdateAutoTelemetry(hPosController);

            hOutput = hPosController.calculate(hPos,m_hPosSetpoint);

            m_MecanumDriveBaseSubsystem.setMotorSpeeds(0,0, hOutput);

    }
    @Override
    public boolean isFinished(){
        if(hPosController.atSetPoint()){
            return true;
        }
        else {
            return false;
        }
    }
    public void UpdateAutoTelemetry( PIDFController hController){

        m_dashboardTelemetry.addData("hPosSetpoint", hController.getSetPoint());


        m_dashboardTelemetry.addData("pos h", m_MecanumDriveBaseSubsystem.getPosed2D().h);

        m_dashboardTelemetry.addData("Turning P", hController.getP());
        m_dashboardTelemetry.addData("Turning I", hController.getI());
        m_dashboardTelemetry.addData("Turning D", hController.getD());
        m_dashboardTelemetry.addData("Turning F", hController.getF());


        m_dashboardTelemetry.update();

    }
}
