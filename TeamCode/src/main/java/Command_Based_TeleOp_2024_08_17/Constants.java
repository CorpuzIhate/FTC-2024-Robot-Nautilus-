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
    public static final class ShoulderSetpoints{
        public static final double intakeShoulderPos_1 = -2000;
        public static final double intakeShoulderPos_2 = -3200;
        public static final double middleShoulderPos = -2600;

        public static final double upperShoulderPos = -3200;// hard stop
    };
    public static final class ElbowSetpoints{
        public static final double intakeElbowPos_1 = 1000;
        public static final double intakeElbowPos_2 = 1000;
        public static final double middleElbowPos = 1700;

        public static final double upperElbowPos = 3300;
    };




}
