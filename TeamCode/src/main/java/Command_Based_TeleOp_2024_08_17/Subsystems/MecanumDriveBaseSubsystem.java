package Command_Based_TeleOp_2024_08_17.Subsystems;


import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.geometry.Vector2d;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;

;

import Command_Based_TeleOp_2024_08_17.Constants;


public class MecanumDriveBaseSubsystem extends SubsystemBase {
    private final Motor m_FL, m_FR, m_BR, m_BL;
    private final SparkFunOTOS m_OTOS;



    public MecanumDriveBaseSubsystem(Motor FL, Motor FR,
                                     Motor BR, Motor BL,
                                     SparkFunOTOS OTOS){
        m_FL = FL;
        m_FR = FR;
        m_BL = BL;
        m_BR = BR;
        m_OTOS = OTOS;
    }
    @Override
    public void  periodic(){

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
    public SparkFunOTOS.Pose2D getPosed2D(){

        return m_OTOS.getPosition();

    }
    public SparkFunOTOS.Pose2D convertSoosCentricPosToRobotCentricPos(SparkFunOTOS.Pose2D soosPos){

        //vector from origin to SOOS


        Vector2d soosToOrigin;
        Vector2d originToSoos;
        double soosHeading;
        Vector2d soosToCenterOfRobot;


        soosToOrigin =
                new Vector2d(
                        Constants.OdemetryConstants.distanceFromOriginX_INCHES,
                        Constants.OdemetryConstants.distanceFromOriginY_INCHES);
        //vector from origin to soos
        originToSoos = new Vector2d(soosPos.x,soosPos.y);

        soosHeading = soosPos.h;

        //vector from soos to center of robot

        //we rotate the vector from Soos to Origin  by the Soos and subtract it by the vector
        //from origin to soos
        soosToCenterOfRobot = originToSoos.minus(soosToOrigin.rotateBy(soosHeading));

        return new SparkFunOTOS.Pose2D(soosToCenterOfRobot.getX(), soosToCenterOfRobot.getY(),soosPos.h);
    }


    public Vector2d fieldVelocityToRobotVelocity(Vector2d desiredFieldVelocity, double angle_radians ){

        Vector2d desiredRelativeVelocity;

        // we get the angle of desiredFieldPos and rotate it by our heading.
        //we added cos and sin to get the new x and y components of the desiredFieldPos
        Vector2d direction = new Vector2d(
                Math.cos(angle_radians + Math.atan(desiredFieldVelocity.getX() / desiredFieldVelocity.getY())),
                Math.sin(angle_radians + Math.atan(desiredFieldVelocity.getX() / desiredFieldVelocity.getY()))
        );
        // if both the components of is negative, we multiple the direction
        //by -1 because if both inputs of atan are negative
        // it becomes positive
        if(desiredFieldVelocity.getX() < 0 && desiredFieldVelocity.getY() < 0){
            direction.scale(-1);
        }

        desiredRelativeVelocity =  direction.scale(desiredFieldVelocity.magnitude());
        return  desiredRelativeVelocity;
    }

    public Vector2d fieldVelocityToRobotVelocity_V2(Vector2d desiredFieldVelocity, double angle_radians ){

        Vector2d desiredRelativeVelocity;

        double desiredRelativeVelocity_x = desiredFieldVelocity.getX() * Math.cos(angle_radians) +
                desiredFieldVelocity.getY() * Math.sin(angle_radians);

        double desiredRelativeVelocity_y = desiredFieldVelocity.getY() * Math.cos(angle_radians) -
                desiredFieldVelocity.getX() * Math.sin(angle_radians);


        desiredRelativeVelocity = new Vector2d(desiredRelativeVelocity_x, desiredRelativeVelocity_y);
        return  desiredFieldVelocity;

    }



}
