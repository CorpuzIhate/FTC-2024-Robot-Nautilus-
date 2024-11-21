package Nautilus_DocBotics_FTC_2024.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;

public class waitCMD extends CommandBase {
    private final double m_timeWaiting;
    private ElapsedTime runtime = new ElapsedTime();
    public waitCMD(double timeWaiting){
        m_timeWaiting = timeWaiting;


    }
    @Override
    public void initialize(){
        runtime.reset();
    }
    @Override
    public void execute(){

    }
    @Override
    public boolean isFinished() {
        if(runtime.seconds() >= m_timeWaiting){
            return true;
        }
        return  false;

    }
}
