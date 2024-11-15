package Command_Based_TeleOp_2024_08_17.Commands;


import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.DoubleSupplier;

import Command_Based_TeleOp_2024_08_17.Subsystems.MecanumDriveBaseSubsystem;

public class TeleOpJoystickRobotCentricCMD extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final MecanumDriveBaseSubsystem m_MecanumSub;
    private final Telemetry m_dashboardTelemetry;
    private DoubleSupplier m_forwardPower;
    private DoubleSupplier m_strafePower;
    private DoubleSupplier m_rotationPower;


    double frontLeftSpeed;
    double frontRightSpeed;
    double backLeftSpeed;
    double backRightSpeed;



    public TeleOpJoystickRobotCentricCMD(MecanumDriveBaseSubsystem mecanumDriveBaseSubsystem,
                                         Telemetry dashboardTelemetry, DoubleSupplier forwardPower,
                                         DoubleSupplier strafePower, DoubleSupplier rotationPower
    ) {
        m_dashboardTelemetry = dashboardTelemetry;
        m_MecanumSub = mecanumDriveBaseSubsystem;

        m_forwardPower = forwardPower;
        m_strafePower = strafePower;
        m_rotationPower = rotationPower;




        addRequirements(mecanumDriveBaseSubsystem);
    }
    @Override
    public  void execute(){
        double[] motorSpeeds = new double[4];

        m_dashboardTelemetry.addData("x_Pos", m_MecanumSub.getPosed2D().x);
        m_dashboardTelemetry.addData("y_Pos", m_MecanumSub.getPosed2D().y);
        m_dashboardTelemetry.addData("h_Pos", m_MecanumSub.getPosed2D().h);
        //smooth inputs
        double smoothForwardPower =
                m_MecanumSub.smoothJoystickInputs(m_forwardPower.getAsDouble());
        double smoothStrafePower =
                m_MecanumSub.smoothJoystickInputs(m_strafePower.getAsDouble());
        double smoothRotationPower =
                m_MecanumSub.smoothJoystickInputs(m_rotationPower.getAsDouble()) * 0.5;

        m_MecanumSub.setMotorSpeeds(smoothForwardPower,smoothStrafePower,smoothRotationPower);


    }
    @Override
    public boolean isFinished(){
        return false;
    }

}
