package Nautilus_DocBotics_FTC_2024.Commands;

import com.arcrobotics.ftclib.command.CommandBase;


import Nautilus_DocBotics_FTC_2024.Subsystems.TelemetryManagerSubsystem;


public class TelemetryManagerCMD extends CommandBase {
    private final TelemetryManagerSubsystem m_TelemetryManagerSubsystem;



    public TelemetryManagerCMD(TelemetryManagerSubsystem telemetryManagerSubsystem ) {
        m_TelemetryManagerSubsystem = telemetryManagerSubsystem;

        addRequirements(telemetryManagerSubsystem);
    }

    @Override
    public void initialize() {

        //TODO delete ftc dashboard during competition to prevent errors.
        //FTC Dashboard is PROHIBITED during games but allowed during pits
    }
        @Override
        public void execute() {

        m_TelemetryManagerSubsystem.getTelemetryObject().update();
        }

    }