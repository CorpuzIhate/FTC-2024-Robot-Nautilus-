package Command_Based_TeleOp_2024_08_17.AutoCommands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Vector2d;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Vector;

import Command_Based_TeleOp_2024_08_17.Constants;
import Command_Based_TeleOp_2024_08_17.Subsystems.MecanumDriveBaseSubsystem;
import Command_Based_TeleOp_2024_08_17.auto.CircularPIDController;

public class MoveRobotToFieldPosCMD extends CommandBase {
    private final double m_xPosSetpoint;
    private final double m_yPosSetpoint;
    private final double m_hPosSetpoint;


    private final Telemetry m_dashboardTelemetry;
    private final MecanumDriveBaseSubsystem m_MecanumDriveBaseSubsystem;

    private SparkFunOTOS.Pose2D soosPos;
    private SparkFunOTOS.Pose2D fieldPos;
    private Vector2d fieldOutputs;
    private Vector2d relativeOutput;

    private double fieldXOutput;
    private double fieldYOutput;


    private  double hOutput;
    private  double xOutput;
    private  double yOutput;

    private  CircularPIDController hPosController;
    private PIDFController xPosController;
    private  PIDFController yPosController;

    public MoveRobotToFieldPosCMD(double xPosSetpoint,
                                  double yPosSetpoint,
                                  double hPosSetpoint,
                                  MecanumDriveBaseSubsystem mecanumDriveBaseSubsystem,
                                  Telemetry dashboardTelemetry){
        hPosController = new CircularPIDController(
                Constants.AutoConstants.turnkP);


        m_xPosSetpoint = xPosSetpoint;
        m_yPosSetpoint = yPosSetpoint;
        m_hPosSetpoint = hPosSetpoint;

        m_MecanumDriveBaseSubsystem =  mecanumDriveBaseSubsystem;
        m_dashboardTelemetry = dashboardTelemetry;
    }

    @Override
    public  void  initialize(){
        soosPos = m_MecanumDriveBaseSubsystem.getPosed2D();


    }

    @Override
    public  void  execute(){

        soosPos = m_MecanumDriveBaseSubsystem.getPosed2D();

        fieldPos = m_MecanumDriveBaseSubsystem.convertSoosCentricPosToRobotCentricPos(soosPos);


        fieldXOutput = xPosController.calculate(fieldPos.x , m_xPosSetpoint);
        fieldYOutput = yPosController.calculate(fieldPos.y, m_yPosSetpoint);

        fieldOutputs = new Vector2d(fieldXOutput,fieldYOutput);
        relativeOutput = m_MecanumDriveBaseSubsystem.fieldVelocityToRobotVelocity(
                fieldOutputs,
                fieldPos.h
        );





        hOutput = hPosController.calculate(fieldPos.h, m_hPosSetpoint);
        xOutput = relativeOutput.getX();
        yOutput = relativeOutput.getY();



        m_MecanumDriveBaseSubsystem.setMotorSpeeds(xOutput,yOutput,hOutput);
    }
    @Override
    public boolean isFinished(){
       if(hPosController.atSetPoint() &&
               xPosController.atSetPoint() &&
               yPosController.atSetPoint()){
           return  true;
       }
       return false;
    }
    public void UpdateAutoTelemetry(){

//TODO Add telemetry

        m_dashboardTelemetry.update();

    }
}
