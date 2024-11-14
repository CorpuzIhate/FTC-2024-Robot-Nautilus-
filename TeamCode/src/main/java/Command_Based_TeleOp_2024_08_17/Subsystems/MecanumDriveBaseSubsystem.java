package Command_Based_TeleOp_2024_08_17.Subsystems;


import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.geometry.Vector2d;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;

;

import Command_Based_TeleOp_2024_08_17.Constants;


public class MecanumDriveBaseSubsystem extends SubsystemBase {
    public final Motor m_FL, m_FR, m_BR, m_BL;
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
        strafePower *= 1;
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


        //origin is (0,0) and is wherever the robot starts on initialization
        //vector from origin to SOOS


        Vector2d centerOfRobotToSoos;
        Vector2d fieldOriginToSoos;
        Vector2d fieldOriginToRobotCenter;

        double soosHeading_radians;




        centerOfRobotToSoos =
                new Vector2d(
                        Constants.OdemetryConstants.distanceFromOriginX_INCHES,
                        Constants.OdemetryConstants.distanceFromOriginY_INCHES);
        //vector from origin to soos
        fieldOriginToSoos = new Vector2d(soosPos.x,soosPos.y);


        //soos heading is in degrees
        soosHeading_radians = Math.toRadians(soosPos.h);

        //vector from soos to center of robot

        //we rotate the vector from Soos to Origin  by the Soos and subtract it by the vector
        //from origin to soos
        fieldOriginToRobotCenter = fieldOriginToSoos.minus(centerOfRobotToSoos.rotateBy(soosHeading_radians));

        return new SparkFunOTOS.Pose2D(fieldOriginToRobotCenter.getX(), fieldOriginToRobotCenter.getY(),soosHeading_radians);
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

    public Vector2d fieldVelocityToRobotVelocity_V2(Vector2d desiredFieldVelocity, double robot_heading_radians ){

        Vector2d desiredRobotRelativeVelocity;

        double desiredRobotRelativeVelocity_x = desiredFieldVelocity.getX() * Math.cos(robot_heading_radians) +
                desiredFieldVelocity.getY() * Math.sin(robot_heading_radians);

        double desiredRobotRelativeVelocity_y = desiredFieldVelocity.getY() * Math.cos(robot_heading_radians) -
                desiredFieldVelocity.getX() * Math.sin(robot_heading_radians);


        desiredRobotRelativeVelocity = new Vector2d(desiredRobotRelativeVelocity_x, desiredRobotRelativeVelocity_y);
        return  desiredRobotRelativeVelocity;

    }



}
