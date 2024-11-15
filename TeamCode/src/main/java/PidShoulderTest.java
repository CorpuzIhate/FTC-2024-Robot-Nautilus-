import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.controller.wpilibcontroller.ArmFeedforward;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
@TeleOp
@Disabled
public class PidShoulderTest extends OpMode {
    public static double currentSetpoint;

    public static double kP = 0.6;
    public static double kI = 0;
    public static double kD = 0.015;
    public static double kF = 0;
    PIDFController feedforward;


    double output;
    boolean shoulderisAtpoint;

    private final FtcDashboard dashboard = FtcDashboard.getInstance();
    private final Telemetry dashboardTelemetry = dashboard.getTelemetry();

    Motor shoulderMotor;
    @Override
    public void init(){
        feedforward = new PIDFController(kP, kI, kD, kF);

        shoulderMotor = new Motor(hardwareMap,"shoulder_motor");
        shoulderMotor.resetEncoder();
        shoulderMotor.setRunMode(Motor.RunMode.RawPower);
    }
    public void loop(){
        output = feedforward.calculate(shoulderMotor.getCurrentPosition(), currentSetpoint);

        dashboardTelemetry.addData("kP",kP);
        dashboardTelemetry.addData("kI",kI);
        dashboardTelemetry.addData("kD",kD);
        dashboardTelemetry.addData("kF",kF);

        dashboardTelemetry.addData("position",shoulderMotor.getCurrentPosition());


        dashboardTelemetry.update();
        if(feedforward.atSetPoint()){
            shoulderisAtpoint = true;
            dashboardTelemetry.addData("atPoint","yes");
            shoulderMotor.stopMotor();


        }
        if(!shoulderisAtpoint){
            shoulderMotor.set(output);
        }


    }


}
