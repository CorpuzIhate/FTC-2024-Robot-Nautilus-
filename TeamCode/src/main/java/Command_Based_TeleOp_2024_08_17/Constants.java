package Command_Based_TeleOp_2024_08_17;

import com.acmerobotics.dashboard.config.Config;

@Config
public final class Constants{
    public static final class OdemetryConstants{
        public  static final double distanceFromSoosToCenter_INCHES = 2;
        public  static final double soosAngleOffset_radians = 2;
        //relative to robot

        //TODO check if these need to be negative
        public static double soosX;
        public static double soosY;

        public static double distanceFromOriginX_INCHES = 0;
        public static double distanceFromOriginY_INCHES = 0;
    }
    public static final class ShoulderPIDConstants{
        public static final double kP = 0.6;
        public static final double kI = 0;
        public static final double kD = 0.015;
        public static final double kF = 0;

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



}
