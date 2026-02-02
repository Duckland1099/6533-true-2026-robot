package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.LimelightHelpers;
import frc.robot.RobotContainer;

import frc.robot.Constants.limelight_TA;
import frc.robot.lib.Helpers;

public class AlignTA extends Command {
    public AlignTA() {
        addRequirements(RobotContainer.drivetrain, RobotContainer.vision);
    }

    @Override
    public boolean isFinished() {
        double ta = LimelightHelpers.getTA("limelight");

        if (ta == 0)
            return true;

        double percentError = Helpers
        .percentError(limelight_TA.TA_TARGET_DISTANCE, ta);

        System.out.println("Cur tA: " + ta + " with error " + percentError);

        return limelight_TA.TA_TARGET_DISTANCE_ALLOWED_ERROR >= percentError;
    }

    @Override
    public void execute() {
        RobotContainer.drivetrain.setControl(
                RobotContainer.vision.alignTA());
    }
}
