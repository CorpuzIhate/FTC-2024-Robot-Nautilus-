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
    public static final class ShoulderSetpoints{
        public static final double middleArmPos = 1200;

        public static final double upperArmPos = 1700;
    };




}
