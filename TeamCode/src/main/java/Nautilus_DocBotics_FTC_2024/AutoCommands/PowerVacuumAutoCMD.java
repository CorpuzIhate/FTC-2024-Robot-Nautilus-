package Nautilus_DocBotics_FTC_2024.AutoCommands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import Nautilus_DocBotics_FTC_2024.Subsystems.VacuumSubsystem;

public class PowerVacuumAutoCMD extends CommandBase {
    private final VacuumSubsystem m_vacuumSub;
    private final CRServo m_ContinousVacuumServo;
    private final double m_power;
    private final Telemetry m_dashboardTelemetry;
    private final ColorRangeSensor m_vacuumSensor;
    private final double m_VacuumRunTime_Seconds;

    private final ElapsedTime runtime = new ElapsedTime();
    public PowerVacuumAutoCMD(VacuumSubsystem vacuumSubsystem,
                          double power,
                          CRServo ContinousVacuumServo,
                          Telemetry dashboardTelemetry,
                          ColorRangeSensor vacuumSensor,
                          double vacuumRunTime_Seconds){
        m_VacuumRunTime_Seconds = vacuumRunTime_Seconds;
        m_power = power;
        m_vacuumSub = vacuumSubsystem;
        m_ContinousVacuumServo = ContinousVacuumServo;
        m_dashboardTelemetry = dashboardTelemetry;
        m_vacuumSensor = vacuumSensor;

        addRequirements(vacuumSubsystem);

    }
    @Override
    public void initialize(){
        runtime.reset();
    }
    @Override
    public void execute(){
        m_ContinousVacuumServo.set(m_power);
        m_dashboardTelemetry.addData("distace_Between_sample(CM)", m_vacuumSensor.getDistance(DistanceUnit.CM));
        m_dashboardTelemetry.addData("m_VacuumRunTime_Seconds", m_VacuumRunTime_Seconds);
        m_dashboardTelemetry.addData("timer", runtime.seconds());
    }
    @Override
    public boolean isFinished() {
        if(m_VacuumRunTime_Seconds == 0){

            return  false;
        }
        if(  m_VacuumRunTime_Seconds <= runtime.seconds())
        {
            m_ContinousVacuumServo.set(0);
            return  true;
        }
        return false;
    }
}

