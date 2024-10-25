package Command_Based_TeleOp_2024_08_17.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Config
@Autonomous
public class PIDAuto extends LinearOpMode {
    public static double KMoveP = 0.06;
    public static double KMoveI = 0;
    public static double KMoveD = 0;
    public static double KMoveF = 0;

    public static double KTurnP = 0.01;
    public static double KTurnI = 0;
    public static double KTurnD = 0;
    public static double KTurnF = 0;

    public static double offsetX = -4.17;
    public static double offsetY = 4.91;
    public static double offsetH = 0;

    public static double setPointX = 0;
    public static double setPointY = 0;

    private  PIDFController xPosController;
    private  PIDFController yPosController;
    private  PIDFController hPosController;

    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;

    private SparkFunOTOS Otos;

    private final FtcDashboard dashboard = FtcDashboard.getInstance();
    private final Telemetry dashboardTelemetry = dashboard.getTelemetry();

    private final ElapsedTime runtime = new ElapsedTime();


    @Override
    public void runOpMode(){
        frontLeft = hardwareMap.get(DcMotor.class, "front_left");
        frontRight = hardwareMap.get(DcMotor.class, "front_right");
        backLeft = hardwareMap.get(DcMotor.class, "back_left");
        backRight = hardwareMap.get(DcMotor.class, "back_right");

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        dashboardTelemetry.addData("p", KMoveP);
        dashboardTelemetry.addData("I", KMoveI);
        dashboardTelemetry.addData("D",KMoveD);
        dashboardTelemetry.addData("F", KMoveF);

        xPosController = new PIDFController(KMoveP, KMoveI,KMoveD, KMoveF);
        yPosController = new PIDFController(KMoveP, KMoveI,KMoveD, KMoveF);
        hPosController = new PIDFController(KTurnP, KTurnI,KTurnD, KTurnF);

        Otos = hardwareMap.get(SparkFunOTOS.class, "sensor_otos");
        dashboardTelemetry.addData("hPosSetpoint", 0);

        dashboardTelemetry.addData("Turning P",0);
        dashboardTelemetry.addData("Turning I", 0);
        dashboardTelemetry.addData("Turning D", 0);
        dashboardTelemetry.addData("Turning F", 0);
        configureOtos();

        dashboardTelemetry.addData("angular scaler",Otos.getAngularScalar());
        dashboardTelemetry.addData("linear scaler",Otos.getLinearScalar());
        waitForStart();
        //turnRobot(96); // 90 degrees
        moveRobot(setPointX,setPointY);

    }






    private void configureOtos(){
        // Set the desired units for linear and angular measurements. Can be either
        // meters or inches for linear, and radians or degrees for angular. If not
        // set, the default is inches and degrees. Note that this setting is not
        // persisted in the sensor, so you need to set at the start of all your
        // OpModes if using the non-default value.
        // myOtos.setLinearUnit(DistanceUnit.METER);
        Otos.setLinearUnit(DistanceUnit.INCH);
        // myOtos.setAngularUnit(AnguleUnit.RADIANS);
        Otos.setAngularUnit(AngleUnit.DEGREES);

        // Assuming you've mounted your sensor to a robot and it's not centered,
        // you can specify the offset for the sensor relative to the center of the
        // robot. The units default to inches and degrees, but if you want to use
        // different units, specify them before setting the offset! Note that as of
        // firmware version 1.0, these values will be lost after a power cycle, so
        // you will need to set them each time you power up the sensor. For example, if
        // the sensor is mounted 5 inches to the left (negative X) and 10 inches
        // forward (positive Y) of the center of the robot, and mounted 90 degrees
        // clockwise (negative rotation) from the robot's orientation, the offset
        // would be {-5, 10, -90}. These can be any value, even the angle can be
        // tweaked slightly to compensate for imperfect mounting (eg. 1.3 degrees).
        SparkFunOTOS.Pose2D offset = new SparkFunOTOS.Pose2D(offsetX, offsetY, offsetH);
        Otos.setOffset(offset);


        // Here we can set the linear and angular scalars, which can compensate for
        // scaling issues with the sensor measurements. Note that as of firmware
        // version 1.0, these values will be lost after a power cycle, so you will
        // need to set them each time you power up the sensor. They can be any value
        // from 0.872 to 1.127 in increments of 0.001 (0.1%). It is recommended to
        // first set both scalars to 1.0, then calibrate the angular scalar, then
        // the linear scalar. To calibrate the angular scalar, spin the robot by
        // multiple rotations (eg. 10) to get a precise error, then set the scalar
        // to the inverse of the error. Remember that the angle wraps from -180 to
        // 180 degrees, so for example, if after 10 rotations counterclockwise
        // (positive rotation), the sensor reports -15 degrees, the required scalar
        // would be 3600/3585 = 1.004. To calibrate the linear scalar, move the
        // robot a known distance and measure the error; do this multiple times at
        // multiple speeds to get an average, then set the linear scalar to the
        // inverse of the error. For example, if you move the robot 100 inches and
        // the sensor reports 103 inches, set the linear scalar to 100/103 = 0.971

        // actual distance divided traveled distance
        Otos.setLinearScalar(1.01);
        Otos.setAngularScalar(1.07);

        // The IMU on the OTOS includes a gyroscope and accelerometer, which could
        // have an offset. Note that as of firmware version 1.0, the calibration
        // will be lost after a power cycle; the OTOS performs a quick calibration
        // when it powers up, but it is recommended to perform a more thorough
        // calibration at the start of all your OpModes. Note that the sensor must
        // be completely stationary and flat during calibration! When calling
        // calibrateImu(), you can specify the number of samples to take and whether
        // to wait until the calibration is complete. If no parameters are provided,
        // it will take 255 samples and wait until done; each sample takes about
        // 2.4ms, so about 612ms total
        Otos.calibrateImu();

        // Reset the tracking algorithm - this resets the position to the origin,
        // but can also be used to recover from some rare tracking erro rs
        Otos.resetTracking();

        // After resetting the tracking, the OTOS will report that the robot is at
        // the origin. If your robot does not start at the origin, or you have
        // another source of location information (eg. vision odometry), you can set
        // the OTOS location to match and it will continue to track from there.
        SparkFunOTOS.Pose2D currentPosition = new SparkFunOTOS.Pose2D(0, 0, 0);
        Otos.setPosition(currentPosition);

        // Get the hardware and firmware version
        SparkFunOTOS.Version hwVersion = new SparkFunOTOS.Version();
        SparkFunOTOS.Version fwVersion = new SparkFunOTOS.Version();
        Otos.getVersionInfo(hwVersion, fwVersion);

        dashboardTelemetry.addData("offsetsX", Otos.getOffset().x);
        dashboardTelemetry.addData("offsetsY", Otos.getOffset().y);
        dashboardTelemetry.addData("offsetsH", Otos.getOffset().h);

    }



    public void turnRobot(double hPosSetpoint){

        double hPos = Otos.getPosition().h;
        double hOutput = hPosController.calculate(hPos,hPosSetpoint);
        while(!hPosController.atSetPoint()){
            hPos = Otos.getPosition().h;
            UpdateAutoTelemetry(0, 0, hPosController);


            hOutput = hPosController.calculate(hPos,hPosSetpoint);
            dashboardTelemetry.addData("hOutput",hOutput);

            setMotorSpeeds(0,0,-hOutput);
        }
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }


    public void moveRobot(double xPosSetpoint, double yPosSetpoint){
        double xPos = Otos.getPosition().x;
        double yPos = Otos.getPosition().y;

        double xOutput = xPosController.calculate(xPos,xPosSetpoint);
        double yOutput = yPosController.calculate(yPos,yPosSetpoint);



        while(!xPosController.atSetPoint() || !yPosController.atSetPoint() ){
            xPos = Otos.getPosition().x;
            yPos = Otos.getPosition().y;

            UpdateAutoTelemetry(xPosSetpoint, yPosSetpoint, hPosController);

            xOutput = xPosController.calculate(xPos,xPosSetpoint);
            yOutput = yPosController.calculate(yPos,yPosSetpoint);
            setMotorSpeeds(xOutput,yOutput, 0);
        }
    }



    public void setMotorSpeeds(double forwardPower, double strafePower,
                               double rotationPower){

        forwardPower *= -1;
        strafePower *= -1;
        rotationPower *= -1;


        double frontLeftSpeed = forwardPower - strafePower - rotationPower;
        double backLeftSpeed = forwardPower + strafePower - rotationPower;
        double frontRightSpeed = forwardPower + strafePower + rotationPower;
        double backRightSpeed= forwardPower -strafePower + rotationPower;
//test
        //math.max tale 2 doubles and figure out which one is higher
        // This is used to determine the current max speed as different sides of the robot
        // may have their motors moving faster


        double max = Math.max(Math.abs(frontLeftSpeed), Math.abs(frontRightSpeed));

        //first we compare the front motors. then we compare that with the back motors to find
        // the fastest motor
        max = Math.max(max, Math.abs(backLeftSpeed));
        max = Math.max(max, Math.abs(backRightSpeed));


        // if the faster motor at the moment has a power over 1, we divide all motors by the max
        if (max > 1.0) {
            frontLeftSpeed /= max;
            frontRightSpeed /= max;
            backLeftSpeed /= max;
            backRightSpeed /= max;
        }
        frontLeft.setPower(frontLeftSpeed);
        frontRight.setPower(frontRightSpeed);
        backRight.setPower(backRightSpeed);
        backLeft.setPower(backLeftSpeed);


    }
    public void UpdateAutoTelemetry(double xPosSetpoint, double yPosSetpoint, PIDFController hController){
        dashboardTelemetry.addData("xPosSetpoint", xPosSetpoint);
        dashboardTelemetry.addData("yPosSetpoint", yPosSetpoint);
        dashboardTelemetry.addData("hPosSetpoint", hController.getSetPoint());


        dashboardTelemetry.addData("pos x", Otos.getPosition().x);
        dashboardTelemetry.addData("pos y", Otos.getPosition().y);
        dashboardTelemetry.addData("pos h", Otos.getPosition().h);

        dashboardTelemetry.addData("Turning P", hController.getP());
        dashboardTelemetry.addData("Turning I", hController.getI());
        dashboardTelemetry.addData("Turning D", hController.getD());
        dashboardTelemetry.addData("Turning F", hController.getF());


        dashboardTelemetry.update();

    }
}
