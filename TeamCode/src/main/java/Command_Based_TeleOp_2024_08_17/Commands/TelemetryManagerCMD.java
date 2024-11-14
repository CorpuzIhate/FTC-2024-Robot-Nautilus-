package Command_Based_TeleOp_2024_08_17.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;


import Command_Based_TeleOp_2024_08_17.Subsystems.TelemetryManagerSubsystem;


public class TelemetryManagerCMD extends CommandBase {
    private final TelemetryManagerSubsystem m_TelemetryManagerSubsystem;
    private SparkFunOTOS.Pose2D pos;
    private final SparkFunOTOS m_otos;

    public TelemetryManagerCMD(TelemetryManagerSubsystem telemetryManagerSubsystem,SparkFunOTOS otos ) {
        m_TelemetryManagerSubsystem = telemetryManagerSubsystem;
        m_otos = otos;
        addRequirements(telemetryManagerSubsystem);
    }

    @Override
    public void initialize() {

        //TODO delete ftc dashboard during competition to prevent errors.
        //FTC Dashboard is PROHIBITED during games but allowed during pits
    }
        @Override
        public void execute() {
        pos = m_otos.getPosition();


        m_TelemetryManagerSubsystem.getTelemetryObject().addData("X pos",pos.x );
        m_TelemetryManagerSubsystem.getTelemetryObject().addData("Y pos",pos.y);
        m_TelemetryManagerSubsystem.getTelemetryObject().addData("H pos",pos.h );
        m_TelemetryManagerSubsystem.getTelemetryObject().update();
        }

    }