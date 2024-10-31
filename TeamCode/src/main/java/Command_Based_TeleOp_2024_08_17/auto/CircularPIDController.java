package Command_Based_TeleOp_2024_08_17.auto;

public class CircularPIDController {
    private final double m_kP;
    private  double setPoint;
    private double error;
    private double output;

    private double distance_1;
    private double distance_2;


    private  double possibleSetPoint_1;
    private  double possibleSetPoint_2;
    public  CircularPIDController(double kP){
        m_kP = kP;
    }
    public double Calculate(double setpoint,
                            double currentAngle){
        possibleSetPoint_1 = setPoint + ( 2 * Math.PI);
        possibleSetPoint_2 = setPoint - ( 2 * Math.PI);

        distance_1 = possibleSetPoint_1 - currentAngle;
        distance_2 = -possibleSetPoint_1 + currentAngle;


         error = setPoint - currentAngle;


         output *= error;
         return output;
    }


}
