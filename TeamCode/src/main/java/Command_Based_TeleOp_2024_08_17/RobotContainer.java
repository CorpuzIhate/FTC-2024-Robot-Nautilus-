package Command_Based_TeleOp_2024_08_17;


import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.PerpetualCommand;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import Command_Based_TeleOp_2024_08_17.CommandGroups.MoveArmUpCMDG;
import Command_Based_TeleOp_2024_08_17.Commands.MoveArmJointCMD;
import Command_Based_TeleOp_2024_08_17.Commands.PowerVacuumCMD;
import Command_Based_TeleOp_2024_08_17.Commands.TeleOpJoystickRobotCentricCMD;
import Command_Based_TeleOp_2024_08_17.Commands.TelemetryManagerCMD;
import Command_Based_TeleOp_2024_08_17.Subsystems.MecanumDriveBaseSubsystem;
import Command_Based_TeleOp_2024_08_17.Subsystems.armSubsystem;
import Command_Based_TeleOp_2024_08_17.Subsystems.TelemetryManagerSubsystem;
import Command_Based_TeleOp_2024_08_17.Subsystems.VacuumSubsystem;


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

    ServoEx targetVacuumServo;
    CRServo ContinousVacuumServo;

    private MecanumDriveBaseSubsystem mecanumDriveBaseSub;
    private TelemetryManagerSubsystem telemetryManagerSub;
    //TODO refactor Vacuum servos so that they're accessed through the Vacuum sub

    private  VacuumSubsystem vacuumSubsystem = new VacuumSubsystem();
    private armSubsystem armSub;

    public GamepadEx driverOP;
    public Button vacuumButton;
    public GamepadButton moveShouldertoBottomPos;
    public GamepadButton moveShouldertoMiddlePos;

    public GamepadButton moveShouldertoUpperPos;



    @Override
    public void initialize() {
        fwdPwr = -gamepad1.left_stick_y;
        strafePwr = -gamepad1.left_stick_x;
        rotationPwr = -gamepad1.right_stick_x;

        //TODO sensor and IMU setups into their subsystem
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
        vacuumButton = new GamepadButton(driverOP, GamepadKeys.Button.A);

        moveShouldertoBottomPos = new GamepadButton(driverOP, GamepadKeys.Button.X);
        moveShouldertoMiddlePos = new GamepadButton(driverOP, GamepadKeys.Button.Y);
        moveShouldertoUpperPos = new GamepadButton(driverOP, GamepadKeys.Button.DPAD_DOWN);



        BNO055IMU.Parameters myIMUparameters;

        myIMUparameters = new BNO055IMU.Parameters();


        myIMUparameters.angleUnit = myIMUparameters.angleUnit.RADIANS;

        myIMUparameters.calibrationDataFile = "BNO055IMUCalibration.json";

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(myIMUparameters);

        initSubsystems();
        runCommands();


    }
    private void initSubsystems(){
        mecanumDriveBaseSub = new MecanumDriveBaseSubsystem(
                frontLeft, frontRight, backLeft, backRight);
        telemetryManagerSub = new TelemetryManagerSubsystem();

        armSub = new armSubsystem(shoulderMotor, elbowMotor);


    }
    private void runCommands(){
        telemetryManagerSub.setDefaultCommand(new PerpetualCommand(new TelemetryManagerCMD(telemetryManagerSub)));

        mecanumDriveBaseSub.setDefaultCommand(new TeleOpJoystickRobotCentricCMD(mecanumDriveBaseSub,
                telemetryManagerSub.getTelemetryObject(), driverOP::getLeftY, driverOP::getLeftX, driverOP::getRightX));


        moveShouldertoUpperPos.whenPressed(new InstantCommand(() -> {

            armSub.getShoulderJoint().setSetpoint(Constants.ShoulderSetpoints.upperShoulderPos);
            armSub.getElbowJoint().setSetpoint(Constants.ElbowSetpoints.upperElbowPos);

                }));

        moveShouldertoMiddlePos.whenPressed(new InstantCommand(() -> {

            armSub.getShoulderJoint().setSetpoint(Constants.ShoulderSetpoints.middleShoulderPos);
            armSub.getElbowJoint().setSetpoint(Constants.ElbowSetpoints.middleElbowPos);
                }));

        moveShouldertoBottomPos.whenPressed(new InstantCommand(() -> {

            armSub.getShoulderJoint().setSetpoint(0);
            armSub.getElbowJoint().setSetpoint(0);
        }));

        armSub.setDefaultCommand(new MoveArmJointCMD(armSub, telemetryManagerSub.getTelemetryObject(), armSub.getElbowJoint(), armSub.elbowSetpoint, false));

      //  moveShouldertoMiddlePos.whenPressed(new MoveArmUpCMDG(0,0,armSub, telemetryManagerSub.getTelemetryObject()));
       // vacuumButton.whileHeld(new PowerVacuumCMD(vacuumSubsystem, 1,ContinousVacuumServo)).whenReleased(new PowerVacuumCMD(vacuumSubsystem, 0,ContinousVacuumServo));

    }



}

