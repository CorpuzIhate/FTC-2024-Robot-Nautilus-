package Command_Based_TeleOp_2024_08_17.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import Command_Based_TeleOp_2024_08_17.Subsystems.VacuumSubsystem;

public class PowerVacuumCMD extends CommandBase {
    private final VacuumSubsystem m_vacuumSub;
    private final CRServo m_ContinousVacuumServo;
    private final double m_power;
    private final Telemetry m_dashboardTelemetry;
    private final ColorRangeSensor m_vacuumSensor;
    private final double m_VacuumRunTime_Seconds;
    private final ElapsedTime runtime = new ElapsedTime();
    public PowerVacuumCMD(VacuumSubsystem vacuumSubsystem,
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
    }
    @Override
    public boolean isFinished() {
        if(m_VacuumRunTime_Seconds == 0){
            return  false;
        }
        if(  m_VacuumRunTime_Seconds <= runtime.seconds())
        {
            return  true;
        }
        return false;
    }
};