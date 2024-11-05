package Command_Based_TeleOp_2024_08_17.auto;

import android.accounts.OnAccountsUpdateListener;

public class CircularPIDController {
    private final double m_kP;
    private double setPoint;
    private double error;
    private double output;

    private double errorTolerance = 0.05;


    private double clockwiseDistanceToSetpoint;
    private double counterClockwiseDistanceToSetpoint;


    public CircularPIDController(double kP) {
        m_kP = kP;
    }

    public double calculate(double setpoint,
                            double currentAngle) {
        double output;

        counterClockwiseDistanceToSetpoint = Math.abs(currentAngle- setpoint);
        clockwiseDistanceToSetpoint = (2 * Math.PI) - counterClockwiseDistanceToSetpoint;


        //compare the clockwise and counter clockwise angles.
        if( counterClockwiseDistanceToSetpoint <= clockwiseDistanceToSetpoint){
            error = counterClockwiseDistanceToSetpoint; // if the counterclockwise angle
            //is shorter, set the error to that
        }
        else{
            // if the clockwise angle
            //is shorter, set the error to that
            error = clockwiseDistanceToSetpoint * -1;
        }
        output = error * m_kP;
        return output;
    };
    public double getError(){
        return error;
    }
    public Boolean atSetPoint(){
        return Math.abs(error) <= setPoint + errorTolerance;
    }

    public Boolean isCounterClockwiseAngleShorter(){
        return   counterClockwiseDistanceToSetpoint <= clockwiseDistanceToSetpoint;

    }

}

