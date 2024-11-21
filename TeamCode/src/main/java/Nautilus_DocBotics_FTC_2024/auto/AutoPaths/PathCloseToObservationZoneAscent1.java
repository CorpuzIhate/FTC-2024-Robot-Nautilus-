package Nautilus_DocBotics_FTC_2024.auto.AutoPaths;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import Nautilus_DocBotics_FTC_2024.Constants;
import Nautilus_DocBotics_FTC_2024.auto.AutoRobotContainer;

public class PathCloseToObservationZoneAscent1 extends AutoRobotContainer {
    @Override
    public void path(){
        schedule(new SequentialCommandGroup(


                        new InstantCommand(() -> {
                            shoulderSub.setSetpoint(Constants.ShoulderSetpoints.shoulderClearancePos);
                            elbowSub.setSetpoint(Constants.ElbowSetpoints.elbowClearancePos);
                        } ),
                        new WaitCommand(3000),
                        new InstantCommand(() -> {
                            shoulderSub.setSetpoint(Constants.ShoulderSetpoints.shoulderSubmersiblePickUpPos);
                            elbowSub.setSetpoint(Constants.ElbowSetpoints.elbowSubmersiblePickUpPos);
                        }),
                        new WaitCommand(3000),
                        new InstantCommand(() -> {
                            shoulderSub.setSetpoint(Constants.ShoulderSetpoints.shoulderfoldUpPos);
                            elbowSub.setSetpoint(Constants.ElbowSetpoints.elbowfoldUpPos);
                        })





                )


        );
    }
}

