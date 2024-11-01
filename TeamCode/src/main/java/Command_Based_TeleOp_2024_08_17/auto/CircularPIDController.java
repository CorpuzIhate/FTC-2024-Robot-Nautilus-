package Command_Based_TeleOp_2024_08_17.auto;

public class CircularPIDController {
    private final double m_kP;
    private double setPoint;
    private double error;
    private double output;

    private double distance_1;
    private double distance_2;


    private double possibleSetPoint_1;
    private double possibleSetPoint_2;
    private double distance_REALSetpoint;

    public CircularPIDController(double kP) {
        m_kP = kP;
    }

    public double Calculate(double setpoint,
                            double currentAngle) {
        double output;
        possibleSetPoint_1 = setPoint - (2 * Math.PI);
        possibleSetPoint_2 = setPoint + (2 * Math.PI);

        distance_REALSetpoint = setpoint - currentAngle;
        distance_1 = currentAngle - possibleSetPoint_1;
        distance_2 = possibleSetPoint_2 - currentAngle;

        //compare the distances between the 3 setpoints
        //find the one with lowest distance (error)
        double error = Math.min(Math.min(distance_REALSetpoint, distance_1), distance_2);

        output = error * m_kP;
        return output;

        }
}

