package Arm_A_Kraken_DocBotics_FTC_2024;

import com.acmerobotics.dashboard.config.Config;

@Config
public final class Constants{
    public static final class ShoulderPIDConstants{
        public static final double kP = 0.001;
        public static final double kI = 0;
        public static final double kD = 0;
        public static final double kF = 0;

    }
    //test
    public static final class ElbowPIDConstants{
        public static final double kP = 0.001;
        public static final double kI = 0;
        public static final double kD = 0;
        public static final double kF = 0;

    }
    public static final class ShoulderSetpoints{
        public static final double shoulderClearancePos = 800;
        public static final double shoulderSubmersiblePickUpPos = 800;
        public static final double middleShoulderPos = 2660;

        public static final double highBasketShoulderPos = 3300;// hard stop
        public static final double shoulderClimbInit = 0;


    }
    public static final class ElbowSetpoints{
        public static final double elbowClearancePos = 2400;
        public static final double elbowSubmersiblePickUpPos = 3000;
        public static final double middleElbowPos = 4000;

        public static final double highBasketElbowPos = 3200;

        public  static final double elbowClimbInit = 0 ;

    };

    public static final class OdemetryConstants{
        public  static final double soosAngleOffset_radians = 2;
        //relative to robot

        //TODO check if these need to be negative
        public static double soosX;
        public static double soosY;

        public static double distanceFromOriginX_INCHES = -4.0135;
        public static double distanceFromOriginY_INCHES = 5.1875;
    }
    public static final class AutoConstants{
        public static final double turnkP = 0.01;
        public static final double turnkI = 0;
        public static final double turnkD = 0;
        public static final double turnkF = 0;

        public static final double movekP = 0.06;
        public static final double movekI = 0;
        public static final double movekD = 0;
        public static final double movekF = 0;
    }
    public static final class encoderAutoConstants{
        public static final double     DRIVE_SPEED             = 0.25;
        public static final double     TURN_SPEED              = 0.5;

        public static final double     COUNTS_PER_MOTOR_REV    = 28 ;    // REV ultraplanetary motor
        public static final double     DRIVE_GEAR_REDUCTION    = 20 ;     // External Gearing.
        public static final double     WHEEL_DIAMETER_INCHES   = 3 ;     // For figuring circumference
        public static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                (WHEEL_DIAMETER_INCHES * 3.1415);

    }


}
