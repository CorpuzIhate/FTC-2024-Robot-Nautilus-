package Command_Based_TeleOp_2024_08_17;


import com.arcrobotics.ftclib.command.CommandOpMode;
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
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;


import Command_Based_TeleOp_2024_08_17.Commands.MoveShoulderCMD;
import Command_Based_TeleOp_2024_08_17.Commands.PowerVacuumCMD;
import Command_Based_TeleOp_2024_08_17.Commands.TeleOpJoystickRobotCentricCMD;
import Command_Based_TeleOp_2024_08_17.Commands.TelemetryManagerCMD;
import Command_Based_TeleOp_2024_08_17.Subsystems.MecanumDriveBaseSubsystem;
import Command_Based_TeleOp_2024_08_17.Subsystems.ShoulderSubsystem;
import Command_Based_TeleOp_2024_08_17.Subsystems.TelemetryManagerSubsystem;
import Command_Based_TeleOp_2024_08_17.Subsystems.VacuumSubsystem;


@TeleOp(name = "Command Base Test")
public class RobotContainer extends CommandOpMode {




    double fwdPwr;
    double strafePwr;
    double rotationPwr;

    Motor frontLeft;
    Motor frontRight;
    Motor backLeft;
    Motor backRight;
    Motor shoulderMotor;


    CRServo ContinousVacuumServo;

    private MecanumDriveBaseSubsystem mecanumDriveBaseSub;
    private TelemetryManagerSubsystem telemetryManagerSub;
    //TODO refactor Vacuum servos so that they're accessed through the Vacuum sub

    private  VacuumSubsystem vacuumSubsystem = new VacuumSubsystem();
    public ColorRangeSensor vacuumSensor;
    private ShoulderSubsystem shoulderSub;

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

        //TODO put constant tags into constants
        frontLeft = new Motor(hardwareMap, "front_left");
        frontRight = new Motor(hardwareMap, "front_right");
        backLeft = new Motor(hardwareMap, "back_left");
        backRight = new Motor(hardwareMap, "back_right");

        shoulderMotor = new Motor(hardwareMap,"shoulder_motor");
        shoulderMotor.setRunMode(Motor.RunMode.RawPower);

        ContinousVacuumServo = new CRServo(hardwareMap, "Vacuum_Servo");
        vacuumSensor = hardwareMap.get(ColorRangeSensor.class, "Vaccum_Distance_Sensor");

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




        initSubsystems();
        runCommands();


    }
    private void initSubsystems(){
        mecanumDriveBaseSub = new MecanumDriveBaseSubsystem(
                frontLeft, frontRight, backLeft, backRight);
        telemetryManagerSub = new TelemetryManagerSubsystem();
        shoulderSub = new ShoulderSubsystem(shoulderMotor);


    }
    private void runCommands(){
        telemetryManagerSub.setDefaultCommand(new PerpetualCommand(new TelemetryManagerCMD(telemetryManagerSub)));

        mecanumDriveBaseSub.setDefaultCommand(new TeleOpJoystickRobotCentricCMD(mecanumDriveBaseSub,
                telemetryManagerSub.getTelemetryObject(), driverOP::getLeftY, driverOP::getLeftX, driverOP::getRightX));

        shoulderSub.setDefaultCommand(new MoveShoulderCMD(shoulderSub, telemetryManagerSub.getTelemetryObject(),moveShouldertoBottomPos, moveShouldertoMiddlePos, moveShouldertoUpperPos));

        vacuumButton.whileHeld(new PowerVacuumCMD(vacuumSubsystem, 1,
                        ContinousVacuumServo,telemetryManagerSub.getTelemetryObject() ,vacuumSensor))
                .whenReleased(new PowerVacuumCMD(vacuumSubsystem, 0,
                        ContinousVacuumServo,telemetryManagerSub.getTelemetryObject() ,vacuumSensor));

    }




}

