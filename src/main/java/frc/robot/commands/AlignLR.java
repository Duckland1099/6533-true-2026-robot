package frc.robot.commands;

import frc.robot.LimelightHelpers;
import frc.robot.RobotContainer;
import frc.robot.Constants.limelight_TA;

import edu.wpi.first.wpilibj2.command.Command;

public class AlignLR extends Command {
    public AlignLR() {
        addRequirements(RobotContainer.drivetrain, RobotContainer.vision);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(LimelightHelpers.getTX("limelight")) < 2.5;
    }

    @Override
    public void execute() {
        RobotContainer.drivetrain.setControl(
                RobotContainer.vision.alignLR());
    }
}
