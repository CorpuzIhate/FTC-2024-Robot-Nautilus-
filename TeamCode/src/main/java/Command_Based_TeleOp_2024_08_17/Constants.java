package Command_Based_TeleOp_2024_08_17;

import com.acmerobotics.dashboard.config.Config;

@Config
public final class Constants{
    public static final class ShoulderPIDConstants{
        public static final double kP = 0.001;
        public static final double kI = 0;
        public static final double kD = 0;
        public static final double kF = 0;

    }
    public static final class ElbowPIDConstants{
        public static final double kP = 0.001;
        public static final double kI = 0;
        public static final double kD = 0;
        public static final double kF = 0;

    }
    public static final class AutoConstants{
        public static final double turnkP = 0.01;
        public static final double turnkI = 0;
        public static final double turnkD = 0;
        public static final double turnkF = 0;
        public static final double movekP = 0.01;
        public static final double movekI = 0;
        public static final double movekD = 0;
        public static final double movekF = 0;
    }
    public static final class ShoulderSetpoints{
        public static final double middleShoulderPos = 500;

        public static final double upperShoulderPos = 1700;
    };
    public static final class ElbowSetpoints{
        public static final double middleElbowPos = 1000;

        public static final double upperElbowPos = 2300;
    };




}
