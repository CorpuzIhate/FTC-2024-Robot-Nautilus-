package Nautilus_DocBotics_FTC_2024.Commands;


import static Nautilus_DocBotics_FTC_2024.TeleOpRobotContainer.isSlowmode;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.DoubleSupplier;

import Nautilus_DocBotics_FTC_2024.Subsystems.MecanumDriveBaseSubsystem;

public class TeleOpJoystickRobotCentricCMD extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final MecanumDriveBaseSubsystem m_MecanumSub;
    private final Telemetry m_dashboardTelemetry;
    private DoubleSupplier m_forwardPower;
    private DoubleSupplier m_strafePower;
    private DoubleSupplier m_rotationPower;
    private double m_slowTrigger;

    double frontLeftSpeed;
    double frontRightSpeed;
    double backLeftSpeed;
    double backRightSpeed;



    public TeleOpJoystickRobotCentricCMD(MecanumDriveBaseSubsystem mecanumDriveBaseSubsystem,
                                         Telemetry dashboardTelemetry, DoubleSupplier forwardPower,
                                         DoubleSupplier strafePower, DoubleSupplier rotationPower,
                                         double slowTrigger

    ) {
        m_dashboardTelemetry = dashboardTelemetry;
        m_MecanumSub = mecanumDriveBaseSubsystem;

        m_forwardPower = forwardPower;
        m_strafePower = strafePower;
        m_rotationPower = rotationPower;

        m_slowTrigger = slowTrigger;
//




        addRequirements(mecanumDriveBaseSubsystem);
    }
    @Override
    public  void execute(){
        double[] motorSpeeds = new double[4];

        m_dashboardTelemetry.addData("x_Pos", m_MecanumSub.getPosed2D().x);
        m_dashboardTelemetry.addData("y_Pos", m_MecanumSub.getPosed2D().y);
        m_dashboardTelemetry.addData("h_Pos", m_MecanumSub.getPosed2D().h);
        //smooth inputs
//        double smoothForwardPower =
//                m_MecanumSub.smoothJoystickInputs(m_forwardPower.getAsDouble());
//        double smoothStrafePower =
//                m_MecanumSub.smoothJoystickInputs(m_strafePower.getAsDouble());
//        double smoothRotationPower =
//                m_MecanumSub.smoothJoystickInputs(m_rotationPower.getAsDouble()) * 0.5;
        m_dashboardTelemetry.addData("trigger", m_slowTrigger);
        if(isSlowmode) {
            m_MecanumSub.setMotorSpeeds(
                    m_forwardPower.getAsDouble() * 0.25,
                    m_strafePower.getAsDouble() * 0.25,
                    m_rotationPower.getAsDouble() * 0.25);

        }
        else{

        m_MecanumSub.setMotorSpeeds(
                m_forwardPower.getAsDouble(),
                m_strafePower.getAsDouble(),
                m_rotationPower.getAsDouble()
        );

        }


    }
    @Override
    public boolean isFinished(){
        return false;
    }

}
