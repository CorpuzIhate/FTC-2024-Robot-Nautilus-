package Arm_A_Kraken_DocBotics_FTC_2024.auto;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.PerpetualCommand;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import Arm_A_Kraken_DocBotics_FTC_2024.Commands.MoveArmJointCMD;
import Arm_A_Kraken_DocBotics_FTC_2024.Commands.TelemetryManagerCMD;
import Arm_A_Kraken_DocBotics_FTC_2024.Constants;
import Arm_A_Kraken_DocBotics_FTC_2024.Subsystems.MecanumDriveBaseSubsystem;

import Arm_A_Kraken_DocBotics_FTC_2024.Subsystems.TelemetryManagerSubsystem;
import Arm_A_Kraken_DocBotics_FTC_2024.Subsystems.VacuumSubsystem;
import Arm_A_Kraken_DocBotics_FTC_2024.Subsystems.armJointSubsystem;


public class AutoRobotContainer extends CommandOpMode {
    public Motor frontLeft;
    public Motor frontRight;
    public Motor backLeft;
    public Motor backRight;

    Motor shoulderMotor;
    Motor elbowMotor;

    CRServo continousVacuumServo;
    public ColorRangeSensor vacuumSensor;

    public SparkFunOTOS Otos;

    public armJointSubsystem elbowSub;
    public armJointSubsystem shoulderSub;
    public MecanumDriveBaseSubsystem mecanumDriveBaseSub;
    public TelemetryManagerSubsystem telemetryManagerSub;
    public VacuumSubsystem vacuumSubsystem;



    @Override
    public void initialize() {
        Constants.AutoConstants.isAuto = true;
        frontLeft = new Motor(hardwareMap, "front_left");
        frontRight = new Motor(hardwareMap, "front_right");
        backLeft = new Motor(hardwareMap, "back_left");
        backRight = new Motor(hardwareMap, "back_right");

        shoulderMotor = new Motor(hardwareMap,"shoulder_motor");
        shoulderMotor.setRunMode(Motor.RunMode.RawPower);


//philip was here :)

        elbowMotor = new Motor(hardwareMap,"elbow_motor");
        elbowMotor.setRunMode(Motor.RunMode.RawPower);
        elbowMotor.setInverted(true);


        continousVacuumServo = new CRServo(hardwareMap, "Vacuum_Servo");
        continousVacuumServo.setRunMode(Motor.RunMode.RawPower);
        vacuumSensor = hardwareMap.get(ColorRangeSensor.class, "Vaccum_Distance_Sensor");


        frontLeft.setRunMode(Motor.RunMode.RawPower);
        frontRight.setRunMode(Motor.RunMode.RawPower);
        backLeft.setRunMode(Motor.RunMode.RawPower);
        backRight.setRunMode(Motor.RunMode.RawPower);
        backLeft.setInverted(true);
        backRight.setInverted(true);

        Otos = hardwareMap.get(SparkFunOTOS.class, "sensor_otos");
        configureOtos();

        initSubsystems();
        telemetryManagerSub.setDefaultCommand(new PerpetualCommand(new TelemetryManagerCMD(telemetryManagerSub)));
        shoulderSub.setDefaultCommand(new MoveArmJointCMD(telemetryManagerSub.getTelemetryObject(),
                shoulderSub));
        elbowSub.setDefaultCommand(new MoveArmJointCMD(telemetryManagerSub.getTelemetryObject(),
                elbowSub));
        path();






    }





    private void initSubsystems(){
        mecanumDriveBaseSub = new MecanumDriveBaseSubsystem(frontLeft, frontRight
                ,backRight, backLeft, Otos);
        telemetryManagerSub = new TelemetryManagerSubsystem();
        VacuumSubsystem vacuumSubsystem = new VacuumSubsystem();

        shoulderSub = new armJointSubsystem(
                shoulderMotor,
                new PIDFController(
                        Constants.ShoulderPIDConstants.kP,
                        Constants.ShoulderPIDConstants.kI,
                        Constants.ShoulderPIDConstants.kD,
                        Constants.ShoulderPIDConstants.kF),
                "shoulder",
                0.5,
                0.5,
                300
        );

        elbowSub = new armJointSubsystem(
                elbowMotor,
                new PIDFController(
                        Constants.ElbowPIDConstants.kP,
                        Constants.ElbowPIDConstants.kI,
                        Constants.ElbowPIDConstants.kD,
                        Constants.ElbowPIDConstants.kF),
                "elbow",
                0.5,
                0.5,
                100


        );

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
        SparkFunOTOS.Pose2D offset = new SparkFunOTOS.Pose2D(0, 0, 0);
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
        // but can also be used to recover from some rare tracking errors
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


    }
    public void path(){

    }


}

