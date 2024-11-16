package Arm_A_Kraken_DocBotics_FTC_2024.SystemsCheck;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
@TeleOp
@Disabled
public class DeadArmTest extends OpMode {
    public static double currentSetpoint;

    public static double kP = 0.6;
    public static double kI = 0;
    public static double kD = 0.015;
    public static double kF = 0;
    PIDFController shoulderPID;
    PIDFController ElbowPID;

    double output;
    boolean shoulderisAtpoint;

    private final FtcDashboard dashboard = FtcDashboard.getInstance();
    private final Telemetry dashboardTelemetry = dashboard.getTelemetry();

    Motor shoulderMotor;
    Motor elbowMotor;

    @Override
    public void init(){
        shoulderPID = new PIDFController(kP, kI, kD, kF);

        shoulderMotor = new Motor(hardwareMap,"shoulder_motor");

        shoulderMotor.setRunMode(Motor.RunMode.RawPower);
        shoulderMotor = new Motor(hardwareMap,"shoulder_motor");
        shoulderMotor.setRunMode(Motor.RunMode.RawPower);
    //    shoulderMotor.resetEncoder();
        elbowMotor = new Motor(hardwareMap,"elbow_motor");
        elbowMotor.setRunMode(Motor.RunMode.RawPower);
        elbowMotor.setInverted(true);
    }
    public void loop(){

        dashboardTelemetry.addData("position",shoulderMotor.getCurrentPosition());
        dashboardTelemetry.addData("position",elbowMotor.getCurrentPosition());


        dashboardTelemetry.update();



    }


}
