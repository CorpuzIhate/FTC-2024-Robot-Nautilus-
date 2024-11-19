package Arm_A_Kraken_DocBotics_FTC_2024.AutoCommands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.geometry.Vector2d;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import Arm_A_Kraken_DocBotics_FTC_2024.Constants;
import Arm_A_Kraken_DocBotics_FTC_2024.Subsystems.MecanumDriveBaseSubsystem;
import Arm_A_Kraken_DocBotics_FTC_2024.auto.CircularPIDController;

public class MoveRobotToFieldPosCMD extends CommandBase {
    private final double m_xPosSetpoint;
    private final double m_yPosSetpoint;
    private final double m_hPosSetpoint;


    private final Telemetry m_dashboardTelemetry;
    private final MecanumDriveBaseSubsystem m_MecanumDriveBaseSubsystem;

    private SparkFunOTOS.Pose2D soosPos_Inches_Inches_Degrees;
    private SparkFunOTOS.Pose2D fieldPos_Inches_Inches_Degrees;
    private Vector2d desiredFieldSpeeds;
    private Vector2d robotRelativeOutput;

    private double desiredFieldXSpeed;
    private double desiredFieldYSPeed;

    double soosHeading_radians;

    private  double hOutput;
    private  double xOutput;
    private  double yOutput;

    private  CircularPIDController hPosController;
    private PIDFController xPosController =
            new PIDFController(
                    Constants.AutoConstants.movekP,
                    Constants.AutoConstants.movekI,
                    Constants.AutoConstants.movekD,
                    Constants.AutoConstants.movekF
            );
    private  PIDFController yPosController =
            new PIDFController(
                    Constants.AutoConstants.movekP,
                    Constants.AutoConstants.movekI,
                    Constants.AutoConstants.movekD,
                    Constants.AutoConstants.movekF
            );

    public MoveRobotToFieldPosCMD(double xPosSetpoint_Inches,
                                  double yPosSetpoint_Inches,
                                  double hPosSetpoint_Degrees,
                                  MecanumDriveBaseSubsystem mecanumDriveBaseSubsystem,
                                  Telemetry dashboardTelemetry){
        hPosController = new CircularPIDController(
                Constants.AutoConstants.turnkP);

        xPosController.setTolerance(0.05);
        yPosController.setTolerance(0.05);
        m_xPosSetpoint = xPosSetpoint_Inches;
        m_yPosSetpoint = yPosSetpoint_Inches;
        m_hPosSetpoint = hPosSetpoint_Degrees;



        m_MecanumDriveBaseSubsystem =  mecanumDriveBaseSubsystem;
        m_dashboardTelemetry = dashboardTelemetry;


    }

    @Override
    public  void  initialize(){
        soosPos_Inches_Inches_Degrees = m_MecanumDriveBaseSubsystem.getPosed2D();


    }

    @Override
    public  void  execute(){

        soosPos_Inches_Inches_Degrees = m_MecanumDriveBaseSubsystem.getPosed2D();

        fieldPos_Inches_Inches_Degrees = m_MecanumDriveBaseSubsystem.convertSoosCentricPosToRobotCentricPos(soosPos_Inches_Inches_Degrees);


        desiredFieldXSpeed = xPosController.calculate(fieldPos_Inches_Inches_Degrees.x , m_xPosSetpoint);
        desiredFieldYSPeed = yPosController.calculate(fieldPos_Inches_Inches_Degrees.y, m_yPosSetpoint);

        soosHeading_radians = Math.toRadians(fieldPos_Inches_Inches_Degrees.h);

        desiredFieldSpeeds = new Vector2d(desiredFieldXSpeed, desiredFieldYSPeed);
        robotRelativeOutput = m_MecanumDriveBaseSubsystem.fieldVelocityToRobotVelocity(
                desiredFieldSpeeds,
                soosHeading_radians

        );





        hOutput = hPosController.calculate(fieldPos_Inches_Inches_Degrees.h, m_hPosSetpoint);
        xOutput = robotRelativeOutput.getX();
        yOutput = robotRelativeOutput.getY();



        m_MecanumDriveBaseSubsystem.setMotorSpeeds(yOutput,xOutput,hOutput);
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