package Command_Based_TeleOp_2024_08_17.AutoCommands;

import static Command_Based_TeleOp_2024_08_17.Constants.ecnoderAutoConstants.COUNTS_PER_INCH;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import Command_Based_TeleOp_2024_08_17.Constants;
import Command_Based_TeleOp_2024_08_17.Subsystems.MecanumDriveBaseSubsystem;

public class MoveRobotEncoderXYCMD extends CommandBase {
    private final MecanumDriveBaseSubsystem m_MecanumDriveBaseSubsystem;
    private final Telemetry m_dashboardTelemetry;

    private final double m_leftInches;
    private final double m_rightInches;


    public MoveRobotEncoderXYCMD(double leftInches, double rightInches, double yPosSetpoint, MecanumDriveBaseSubsystem mecanumDriveBaseSubsystem,
                                 Telemetry dashboardTelemetry) {
        m_leftInches = leftInches;
        m_rightInches = rightInches;


        m_MecanumDriveBaseSubsystem = mecanumDriveBaseSubsystem;
        m_dashboardTelemetry = dashboardTelemetry;
    }

    @Override
    public void initialize() {
        int newFrontLeftTarget;
        int newFrontRightTarget;
        int newBackLeftTarget;
        int newBackRightTarget;

        Motor frontLeft = m_MecanumDriveBaseSubsystem.m_FL;
        Motor frontRight = m_MecanumDriveBaseSubsystem.m_FR;

        Motor backLeft = m_MecanumDriveBaseSubsystem.m_BL;
        Motor backRight = m_MecanumDriveBaseSubsystem.m_BR;

        newFrontLeftTarget = frontLeft.getCurrentPosition() + (int)(m_leftInches * Constants.encoderAutoConstants.COUNTS_PER_INCH);
        newFrontRightTarget = frontRight.getCurrentPosition() + (int)(m_rightInches * Constants.encoderAutoConstants.COUNTS_PER_INCH);

        newBackLeftTarget = backLeft.getCurrentPosition() - ((int)(m_leftInches * Constants.encoderAutoConstants.COUNTS_PER_INCH));
        newBackRightTarget = backRight.getCurrentPosition() - ((int)(m_rightInches * Constants.encoderAutoConstants.COUNTS_PER_INCH));


        frontLeft.setTargetPosition(newFrontLeftTarget);
        frontRight.setTargetPosition(newFrontRightTarget);

        backLeft.setTargetPosition(newBackLeftTarget);
        backRight.setTargetPosition(newBackRightTarget);

        m_dashboardTelemetry.addData("fL target ", newFrontLeftTarget);
        m_dashboardTelemetry.addData("fR target", frontRight.getTargetPosition());
        m_dashboardTelemetry.addData("bL target", backLeft.getTargetPosition());
        dashboardTelemetry.addData("bR target", backRight.getTargetPosition());



        // Turn On RUN_TO_POSITION
        frontLeft.setRunMode(Motor.RunMode.PositionControl);
        frontRight.setRunMode(Motor.RunMode.PositionControl);
        backLeft.setRunMode(Motor.RunMode.PositionControl);
        backRight.setRunMode(Motor.RunMode.PositionControl);
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished(){

        return false;
    }


}
