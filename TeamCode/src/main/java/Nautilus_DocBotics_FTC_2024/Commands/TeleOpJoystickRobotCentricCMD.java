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

        double[] joystickInputs = {
                m_forwardPower.getAsDouble(),
                m_strafePower.getAsDouble(),
                m_rotationPower.getAsDouble()

        };

       double[] slewedSignal = m_MecanumSub.slewRateLimiter(
               joystickInputs
       );

        m_dashboardTelemetry.addData("forwardSignalSlewed",  slewedSignal[0]);
        m_dashboardTelemetry.addData("forwardSignal", m_forwardPower.getAsDouble() );

        if(isSlowmode) {
            m_MecanumSub.setMotorSpeeds(
                    joystickInputs[0] * 0.25,
                    joystickInputs[1]* 0.25,
                    joystickInputs[2] * 0.25);

        }
        else{

        m_MecanumSub.setMotorSpeeds(
                slewedSignal[0],
                slewedSignal[1],
                slewedSignal[2]
        );

        }


    }
    @Override
    public boolean isFinished(){
        return false;
    }

}
