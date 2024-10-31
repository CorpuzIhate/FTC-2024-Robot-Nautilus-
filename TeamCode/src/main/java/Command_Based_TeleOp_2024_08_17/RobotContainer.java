package Command_Based_TeleOp_2024_08_17;


import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.PerpetualCommand;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import Command_Based_TeleOp_2024_08_17.Commands.MoveArmJointCMD;
import Command_Based_TeleOp_2024_08_17.Commands.PowerVacuumCMD;
import Command_Based_TeleOp_2024_08_17.Commands.TeleOpJoystickRobotCentricCMD;
import Command_Based_TeleOp_2024_08_17.Commands.TelemetryManagerCMD;
import Command_Based_TeleOp_2024_08_17.Subsystems.MecanumDriveBaseSubsystem;
import Command_Based_TeleOp_2024_08_17.Subsystems.TelemetryManagerSubsystem;
import Command_Based_TeleOp_2024_08_17.Subsystems.VacuumSubsystem;
import Command_Based_TeleOp_2024_08_17.Subsystems.armJointSubsystem;


@TeleOp(name = "Command Base Test")
public class RobotContainer extends CommandOpMode {


    private BNO055IMU imu;


    double fwdPwr;
    double strafePwr;
    double rotationPwr;

    Motor frontLeft;
    Motor frontRight;
    Motor backLeft;
    Motor backRight;
    Motor shoulderMotor;

    Motor elbowMotor;

    CRServo ContinousVacuumServo;

    private MecanumDriveBaseSubsystem mecanumDriveBaseSub;
    private TelemetryManagerSubsystem telemetryManagerSub;
    //TODO refactor Vacuum servos so that they're accessed through the Vacuum sub

    private  VacuumSubsystem vacuumSubsystem = new VacuumSubsystem();

    private armJointSubsystem elbowSub;
    private armJointSubsystem shoulderSub;

    public GamepadEx driverOP;
    public GamepadButton moveShouldertoBottomPos;
    public GamepadButton moveShouldertoMiddlePos;

    public GamepadButton moveShouldertoUpperPos;
    public Button vacuumIntakeButton;
    public Button vacuumOutakeButton;

    public SparkFunOTOS Otos;

    private MecanumDriveBaseSubsystem mecanumDriveBaseSub;

    private final TelemetryManagerSubsystem telemetryManagerSub = new TelemetryManagerSubsystem();
    private  final VacuumSubsystem vacuumSubsystem = new VacuumSubsystem();

    @Override
    public void initialize() {
        fwdPwr = -gamepad1.left_stick_y;
        strafePwr = -gamepad1.left_stick_x;
        rotationPwr = -gamepad1.right_stick_x;

//TODO put constant tags into constants
        frontLeft = new Motor(hardwareMap, "front_left");
        frontRight = new Motor(hardwareMap, "front_right");
        backLeft = new Motor(hardwareMap, "back_left");
        backRight = new Motor(hardwareMap, "back_right");

        shoulderMotor = new Motor(hardwareMap,"shoulder_motor");
        shoulderMotor.setRunMode(Motor.RunMode.RawPower);


        elbowMotor = new Motor(hardwareMap,"elbow_motor");
        elbowMotor.setRunMode(Motor.RunMode.RawPower);

        ContinousVacuumServo = new CRServo(hardwareMap, "Vacuum_Servo");

        frontLeft.setRunMode(Motor.RunMode.RawPower);
        frontRight.setRunMode(Motor.RunMode.RawPower);
        backLeft.setRunMode(Motor.RunMode.RawPower);
        backRight.setRunMode(Motor.RunMode.RawPower);

        ContinousVacuumServo.setRunMode(Motor.RunMode.RawPower);

        backLeft.setInverted(true);
        backRight.setInverted(true);
        driverOP = new GamepadEx(gamepad1);

        moveShouldertoBottomPos = new GamepadButton(driverOP, GamepadKeys.Button.X);
        moveShouldertoMiddlePos = new GamepadButton(driverOP, GamepadKeys.Button.Y);
        moveShouldertoUpperPos = new GamepadButton(driverOP, GamepadKeys.Button.DPAD_DOWN);
        vacuumIntakeButton = new GamepadButton(driverOP, GamepadKeys.Button.A);
        vacuumOutakeButton = new GamepadButton(driverOP, GamepadKeys.Button.B);


        BNO055IMU.Parameters myIMUparameters;

        myIMUparameters = new BNO055IMU.Parameters();



        Otos = hardwareMap.get(SparkFunOTOS.class, "sensor_otos");
        configureOtos();
        initSubsystems();
        runCommands();


    }
    private void initSubsystems(){
        mecanumDriveBaseSub = new MecanumDriveBaseSubsystem(
                frontLeft, frontRight, backLeft, backRight, Otos);
        telemetryManagerSub = new TelemetryManagerSubsystem();
        shoulderSub = new armJointSubsystem(
                shoulderMotor,
                new PIDFController(
                        Constants.ShoulderPIDConstants.kP,
                        Constants.ShoulderPIDConstants.kI,
                        Constants.ShoulderPIDConstants.kD,
                        Constants.ShoulderPIDConstants.kF),
                "shoulder"
        );

        elbowSub = new armJointSubsystem(
                elbowMotor,
                new PIDFController(
                        Constants.ElbowPIDConstants.kP,
                        Constants.ElbowPIDConstants.kI,
                        Constants.ElbowPIDConstants.kD,
                        Constants.ElbowPIDConstants.kF),
                "elbow"


        );


    }
    private void runCommands(){
        telemetryManagerSub.setDefaultCommand(new PerpetualCommand(new TelemetryManagerCMD(telemetryManagerSub)));

        mecanumDriveBaseSub.setDefaultCommand(new TeleOpJoystickRobotCentricCMD(mecanumDriveBaseSub,
                telemetryManagerSub.getTelemetryObject(), driverOP::getLeftY, driverOP::getRightX, driverOP::getLeftX));

        moveShouldertoUpperPos.whenPressed(new InstantCommand(() -> {

            shoulderSub.setSetpoint(Constants.ShoulderSetpoints.upperShoulderPos);
            elbowSub.setSetpoint(Constants.ElbowSetpoints.upperElbowPos);

        }));

        moveShouldertoMiddlePos.whenPressed(new InstantCommand(() -> {

            shoulderSub.setSetpoint(Constants.ShoulderSetpoints.middleShoulderPos);
            elbowSub.setSetpoint(Constants.ElbowSetpoints.middleElbowPos);
        }));

        moveShouldertoBottomPos.whenPressed(new InstantCommand(() -> {

            shoulderSub.setSetpoint(0);
            elbowSub.setSetpoint(0);
        }));


        shoulderSub.setDefaultCommand(new MoveArmJointCMD(
                telemetryManagerSub.getTelemetryObject(),
                shoulderSub,
                false
        ));
        elbowSub.setDefaultCommand(new MoveArmJointCMD(
                telemetryManagerSub.getTelemetryObject(),
                elbowSub,
                false
        ));
        //   moveShouldertoMiddlePos.whenPressed(new MoveArmUpCMDG(0,0,shoulderSub, telemetryManagerSub.getTelemetryObject()));
        // vacuumButton.whileHeld(new PowerVacuumCMD(vacuumSubsystem, 1,ContinousVacuumServo)).whenReleased(new PowerVacuumCMD(vacuumSubsystem, 0,ContinousVacuumServo));


        //shoulderSub.setDefaultCommand(new MoveShoulderCMD(shoulderSub, shoulderMotor,telemetryManagerSub.getTelemetryObject() ));
//
        vacuumIntakeButton.whileHeld(new PowerVacuumCMD(vacuumSubsystem, -1,ContinousVacuumServo))
                .whenReleased(new PowerVacuumCMD(vacuumSubsystem, 0,ContinousVacuumServo));
        vacuumOutakeButton.whileHeld(new PowerVacuumCMD(vacuumSubsystem, 1,ContinousVacuumServo))
               .whenReleased(new PowerVacuumCMD(vacuumSubsystem, 0,ContinousVacuumServo));

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
        Otos.setLinearScalar(0.9);
        Otos.setAngularScalar(0.9);

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



}
