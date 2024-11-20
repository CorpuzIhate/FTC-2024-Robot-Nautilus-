package Arm_A_Kraken_DocBotics_FTC_2024.AutoCommands;



import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import Arm_A_Kraken_DocBotics_FTC_2024.Constants;
import Arm_A_Kraken_DocBotics_FTC_2024.Subsystems.MecanumDriveBaseSubsystem;

public class MoveRobotEncoderXYCMD extends CommandBase {
    private final MecanumDriveBaseSubsystem m_MecanumDriveBaseSubsystem;
    private final Telemetry m_dashboardTelemetry;

    private final double m_leftInches;
    private final double m_rightInches;

    private final double m_timeoutS;
    private final double m_speed;
    private DcMotor frontLeft;
    private DcMotor frontRight;

    private DcMotor backLeft;
    private DcMotor backRight;
    private final ElapsedTime runtime = new ElapsedTime();
    public MoveRobotEncoderXYCMD(double leftInches,
                                 double rightInches,
                                 double timeoutS,
                                 double speed,
                                 MecanumDriveBaseSubsystem mecanumDriveBaseSubsystem,
                                 Telemetry dashboardTelemetry) {
        m_leftInches = leftInches;
        m_rightInches = rightInches;
        m_timeoutS = timeoutS;
        m_speed = speed;

        m_MecanumDriveBaseSubsystem = mecanumDriveBaseSubsystem;
        m_dashboardTelemetry = dashboardTelemetry;
    }

    @Override
    public void initialize() {

        frontLeft = m_MecanumDriveBaseSubsystem.m_FL.motor;
        frontRight = m_MecanumDriveBaseSubsystem.m_FR.motor;
        backLeft = m_MecanumDriveBaseSubsystem.m_BL.motor;
        backRight = m_MecanumDriveBaseSubsystem.m_BR.motor;

        int newFrontLeftTarget;
        int newFrontRightTarget;
        int newBackLeftTarget;
        int newBackRightTarget;




        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        newFrontLeftTarget = frontLeft.getCurrentPosition() + (int)(m_leftInches * Constants.encoderAutoConstants.COUNTS_PER_INCH);
        newFrontRightTarget = frontRight.getCurrentPosition() + (int)(m_rightInches * Constants.encoderAutoConstants.COUNTS_PER_INCH);

        newBackLeftTarget = backLeft.getCurrentPosition() + ((int)(m_leftInches * Constants.encoderAutoConstants.COUNTS_PER_INCH));
        newBackRightTarget = backRight.getCurrentPosition() + ((int)(m_rightInches * Constants.encoderAutoConstants.COUNTS_PER_INCH));


        frontLeft.setTargetPosition(newFrontLeftTarget);
        frontRight.setTargetPosition(newFrontRightTarget);

        backLeft.setTargetPosition(newBackLeftTarget);
        backRight.setTargetPosition(newBackRightTarget);

        m_dashboardTelemetry.addData("fL target ", newFrontLeftTarget);
        m_dashboardTelemetry.addData("fR target", newFrontRightTarget);
        m_dashboardTelemetry.addData("bL target", newBackLeftTarget);
        m_dashboardTelemetry.addData("bR target", newBackRightTarget);



        // Turn On RUN_TO_POSITION
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        runtime.reset();
    }

    @Override
    public void execute() {
        frontLeft.setPower(Math.abs(m_speed));
        backLeft.setPower(Math.abs(m_speed));
//
        //responsible for powering motors
        frontRight.setPower(Math.abs(m_speed));
        backRight.setPower(Math.abs(m_speed));

        m_dashboardTelemetry.addData("fL ", frontLeft.getCurrentPosition());
        m_dashboardTelemetry.addData("fR ", frontRight.getCurrentPosition());
        m_dashboardTelemetry.addData("bL ", backLeft.getCurrentPosition());
        m_dashboardTelemetry.addData("bR ", backRight.getCurrentPosition());


        m_dashboardTelemetry.update();
    }

    @Override
    public boolean isFinished(){
        if((runtime.seconds() < m_timeoutS))
        {
            return false;
        }
        if(frontRight.isBusy()  || frontLeft.isBusy() || backLeft.isBusy() || backRight.isBusy())
        {
            return false;
        }


            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0);

            // Turn off RUN_TO_POSITION
            frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            return true;



    }


}
