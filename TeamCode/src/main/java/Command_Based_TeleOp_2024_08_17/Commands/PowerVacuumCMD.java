package Command_Based_TeleOp_2024_08_17.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import Command_Based_TeleOp_2024_08_17.Subsystems.VacuumSubsystem;

public class PowerVacuumCMD extends CommandBase {
    private final VacuumSubsystem m_vacuumSub;
    private final CRServo m_ContinousVacuumServo;
    private final double m_power;
    private final Telemetry m_dashboardTelemetry;
    private final ColorRangeSensor m_vacuumSensor;

    public PowerVacuumCMD(VacuumSubsystem vacuumSubsystem,
                          double power, CRServo ContinousVacuumServo, Telemetry dashboardTelemetry, ColorRangeSensor vacuumSensor ){
        m_power = power;
        m_vacuumSub = vacuumSubsystem;
        m_ContinousVacuumServo = ContinousVacuumServo;
        m_dashboardTelemetry = dashboardTelemetry;
        m_vacuumSensor = vacuumSensor;

        addRequirements(vacuumSubsystem);

    }
    @Override
    public void execute(){
        m_ContinousVacuumServo.set(m_power);
        m_dashboardTelemetry.addData("distace_Between_sample(CM)", m_vacuumSensor.getDistance(DistanceUnit.CM));
    }
    @Override
    public boolean isFinished() {
        return false;
    }
};