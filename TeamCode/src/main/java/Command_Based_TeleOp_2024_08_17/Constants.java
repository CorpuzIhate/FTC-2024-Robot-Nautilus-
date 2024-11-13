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
        public static final double shoulderClearancePos = 800;
        public static final double shoulderPickUpPos = 270;
        public static final double middleShoulderPos = 1700;

        public static final double highBasketShoulderPos = 3300;// hard stop

//        public static final double intakeElbowPos_1 = 800;
//        public static final double elbowPickUpPos = 800;
//        public static final double middleElbowPos = 1700;
//
//        public static final double highBasketElbowPos = 3300;
    }
    public static final class ElbowSetpoints{
        public static final double elbowClearancePos = 2600;
        public static final double elbowPickUpPos = 2100;
        public static final double middleElbowPos = 2900;

        public static final double highBasketElbowPos = 3200;

//        public static final double intakeShoulderPos_1 = 2000;
//        public static final double shoulderPickUpPos = 3000;
//        public static final double middleShoulderPos = 2600;
//
//        public static final double highBasketShoulderPos = 3200;// hard stop

    };




}
