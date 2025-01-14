package Nautilus_DocBotics_FTC_2024.Subsystems;


import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.geometry.Vector2d;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.util.ElapsedTime;

;

import Nautilus_DocBotics_FTC_2024.Constants;


public class MecanumDriveBaseSubsystem extends SubsystemBase {
    public final Motor m_FL, m_FR, m_BR, m_BL;
    private final SparkFunOTOS m_OTOS;
    private ElapsedTime slewRateTimer = new ElapsedTime();
    private double[] previousInput = {0,0,0};
    private double test;




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
//note when you change this. It will affect both AUTO and TELE-OP
        forwardPower *= 1;
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
    public double smoothJoystickInputs(double input){
        double exp = 1.3;
        if(input >= 0  && input <= 1 ){
            return Math.pow(input,exp);
        }
        if( input <= 0 && input >= -1 ){
            return Math.pow(-input,exp) * -1;
        }
        return 0;
    }
    public SparkFunOTOS.Pose2D getPosed2D(){


        // y is now forward and back, x is now left and Right
        return new SparkFunOTOS.Pose2D (m_OTOS.getPosition().y,m_OTOS.getPosition().x, m_OTOS.getPosition().h);

    }
    public SparkFunOTOS.Pose2D convertSoosCentricPosToRobotCentricPos(SparkFunOTOS.Pose2D soosPos){


        //origin is (0,0) and is wherever the robot starts on initialization
        //vector from origin to SOOS


        Vector2d centerOfRobotToSoos;
        Vector2d fieldOriginToSoos;
        Vector2d fieldOriginToRobotCenter;

        double soosHeading_degrees = soosPos.h;




        centerOfRobotToSoos =
                new Vector2d(
                        Constants.OdemetryConstants.distanceFromOriginX_INCHES,
                        Constants.OdemetryConstants.distanceFromOriginY_INCHES);
        //vector from origin to soos
        fieldOriginToSoos = new Vector2d(soosPos.x,soosPos.y);



        //vector from soos to center of robot

        //we rotate the vector from Soos to Origin  by the Soos and subtract it by the vector
        //from origin to soos
        fieldOriginToRobotCenter = fieldOriginToSoos.minus(centerOfRobotToSoos.rotateBy(soosHeading_degrees));

        return new SparkFunOTOS.Pose2D(fieldOriginToRobotCenter.getX(), fieldOriginToRobotCenter.getY(),soosHeading_degrees);
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

    public double[] slewRateLimiter(double[] joystickInput ){


        double deltaTime_seconds = slewRateTimer.seconds();
        double[] signalDerivative = new double[3];
        double[] slewedOutput = new double[3];

        for(int i = 0; i < 3; i++) {
            signalDerivative[i] = (joystickInput[i] - previousInput[i]) / deltaTime_seconds;


            if (Math.abs(joystickInput[i]) < 1E-2) { // check if there
                // is almost no input to the Joystick
                previousInput[i] = 0;

                slewedOutput[i] = 0;
            }

            if (Math.signum(joystickInput[i]) != Math.signum(previousInput[i]) && previousInput[i] != 0) { // check if the robot is turning
                previousInput[i] = 0;
                slewedOutput[i] = previousInput[i];
            }


            if (Math.abs(signalDerivative[i]) > Constants.teleOpConstants.teleOpSenstiivty) // if the absolute value of the
            // joystick signal has greater than teleOpSenstiivty
            // set the derivative of the signal to teleOpSenstiivty
            {

                //this returns a signal with a derivative = teleOpSenstiivty and depends on the sign

                slewedOutput[i] = (Math.signum(joystickInput[i]) * Constants.teleOpConstants.teleOpSenstiivty * deltaTime_seconds)
                        + previousInput[i];

                previousInput[i] = slewedOutput[i];
                slewRateTimer.reset();



            }
            slewRateTimer.reset();
            slewedOutput[i] =  previousInput[i];
        }
        return slewedOutput;
    }



}
