// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest.RobotCentric;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.ctre.phoenix6.swerve.SwerveRequest;
import frc.robot.Constants.limelight_TA;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;
import frc.robot.RobotContainer;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.util.Units;
import frc.robot.Constants.DRIVE;

public class vision extends SubsystemBase {
     private final SwerveRequest.RobotCentric m_robotCentricRequest = new SwerveRequest.RobotCentric()
        .withDriveRequestType(DriveRequestType.OpenLoopVoltage);

         

        
public SwerveRequest alignLR() { 

  
  


  //PLACE HOLDER UNTIL I GET CONTROL OVER OF THE DAMN ROBOT
  double kp_aim = 0.015;

        double tx = (LimelightHelpers.getTX("limelight"));

        double rotationSpeed = -tx * kp_aim;

        return m_robotCentricRequest.withVelocityX(0).withVelocityY(rotationSpeed).withRotationalRate(0);
    }

public SwerveRequest alignTA() {
        double kp_distance = 0.02 * 0.01;

        double tA = (LimelightHelpers.getTA("limelight"));

        if (tA > limelight_TA.TA_TARGET_DISTANCE) {
            tA = -tA;

            kp_distance = 0.01 * 0.01;
        }

        double distanceSpeed = tA * kp_distance;

        return m_robotCentricRequest.withVelocityX(distanceSpeed).withVelocityY(0).withRotationalRate(0);
    }
  public SwerveRequest alignMT2() {
        RobotCentric driveRobotCentric = new SwerveRequest.RobotCentric()
            .withDeadband(DRIVE.MAX_SPEED * 0.01).withRotationalDeadband(DRIVE.MAX_ANGULAR_RATE * 0.01) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors

        double tx = LimelightHelpers.getTX("limelight");
        double ty = LimelightHelpers.getTY("limelight");

        var goalX = .38;
        var goalY = .145;

        // if(side == ReefSides.RIGHT) {
        //     goalX = .38;
        //     goalY = -.145;    
        // }
        
        var xError = goalX - tx;
        var yError = goalY - ty;

        xError *= 2.0;
        yError *= 6.0;

        double yVel = MathUtil.clamp(yError, -1, 1);
        double xVel = MathUtil.clamp(xError, -1, 1);

        return driveRobotCentric
            // TX = Front/Back
            .withVelocityX(-xVel * (DRIVE.MAX_SPEED / 6.0))
            // TY = Left/Right
            .withVelocityY(yVel * (DRIVE.MAX_SPEED / 6.0)); }
            // .withTargetDirection(Rotation2d.fromDegrees(angle))

  public vision() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
} 


