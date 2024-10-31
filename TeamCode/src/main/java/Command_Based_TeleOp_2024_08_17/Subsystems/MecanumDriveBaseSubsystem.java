package Command_Based_TeleOp_2024_08_17.Subsystems;


import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;

;


public class MecanumDriveBaseSubsystem extends SubsystemBase {
    private final Motor m_FL, m_FR, m_BR, m_BL;



    public MecanumDriveBaseSubsystem(Motor FL, Motor FR, Motor BR, Motor BL){
        m_FL = FL;
        m_FR = FR;
        m_BL = BL;
        m_BR = BR;

    }
    @Override
    public void  periodic(){

    }
    public void setMotorSpeeds(double forwardPower, double strafePower,
                                  double rotationPower){

        forwardPower *= 1;
        strafePower *= -1;
        rotationPower *= -1;

        forwardPower = smoothJoystickInputs(forwardPower);
        strafePower = smoothJoystickInputs(strafePower);
        rotationPower = smoothJoystickInputs(rotationPower);

        double frontLeftSpeed = forwardPower - strafePower - rotationPower;
        double backLeftSpeed = forwardPower + strafePower - rotationPower;
        double frontRightSpeed = forwardPower + strafePower + rotationPower;
        double backRightSpeed= forwardPower -strafePower + rotationPower;

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
        m_FL.set(frontLeftSpeed);
        m_FR.set(frontRightSpeed);
        m_BR.set(backRightSpeed);
        m_BL.set(backLeftSpeed);


    }
    public double smoothJoystickInputs(double input){
        double exp = 2.2;
        if(input >= 0  && input <= 1 ){
            return Math.pow(input,exp);
        }
        if( input <= 0 && input >= -1 ){
            return Math.pow(-input,exp) * -1;
        }
        return 0;
    }






}
