// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.


package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.VisionConstants;

import frc.robot.subsystems.drive.CommandSwerveDrivetrain;
import frc.robot.subsystems.procam;
import edu.wpi.first.wpilibj2.command.Command;





public class Align extends Command {
  private procam m_Vision;
  private Timer dontSeeTagTimer, stopTimer;
  private CommandSwerveDrivetrain m_Swerve;
private boolean isAligned = false;
  public Align(CommandSwerveDrivetrain swerve, procam limelight) {

    this.m_Vision = limelight;
    this.m_Swerve = swerve;

    addRequirements(swerve, limelight);
  }

  @Override
  public void initialize() {

    stopTimer = new Timer();
    stopTimer.start();
    isAligned = false; // incase we need conditonal stuff 
    dontSeeTagTimer = new Timer();
    dontSeeTagTimer.start();
  }

  @Override
  public void execute( ) {

    if (m_Vision.getTV()) {

     double xOffset = m_Vision.xoff(); // the horizontal offset of the april tag
     double yOffset = m_Vision.yoff(); // distance from the april tag

     double strafeGain =  -0.012;  // strafe gains
     double forwardGain = 0.012; // forward gains
     double rotGain = 0.010; // rotation gains

       double strafe = -xOffset * strafeGain; 
     //    double strafe = -(xOffset + 0) * the higher of a number the more right and the lower more left?
     double forward = -(yOffset + 23) * forwardGain; // lower the number the more further the robot goes from the april tag, same for higher but the close it geats
     double rot = xOffset * rotGain;
     //double rot = -(xOffset + 0) * rotGain; unknown effect if it's higher vs lower

boolean xAligned = Math.abs(xOffset) < VisionConstants.xTolerance; 
boolean yAligned = Math.abs(yOffset) < VisionConstants.yTolerance;
boolean aligned = xAligned && yAligned;



// deadbands, might be helpful
if (xAligned) strafe = 0;
if (xAligned) rot = 0;
if (yAligned) forward = 0;


  
    strafe  = MathUtil.clamp(strafe, -1, 1);
    forward = MathUtil.clamp(forward, -1, 1);
    rot     = MathUtil.clamp(rot, -1, 1);



      dontSeeTagTimer.reset();
  
      // Move to align
      m_Swerve.drive(
        forward,
        strafe,
        rot,
        false
      );

  

if (!aligned) {
    stopTimer.reset();
}
isAligned = stopTimer.hasElapsed(VisionConstants.validationTime);

    } else {

      m_Swerve.drive(0,0,0,true); // does nothing if the robot doesnt see any april tags in sight
      isAligned = false;
    }


  SmartDashboard.putBoolean(getName(),isAligned);
  SmartDashboard.putBoolean(getName(), isAligned);
  }

  
public boolean isAligned() { return isAligned; }



 
  @Override
  public void end(boolean interrupted) {
 
    m_Swerve.drive(0,0,0,true);
    isAligned = false;
  }

  @Override
  public boolean isFinished() {

    return dontSeeTagTimer.hasElapsed(VisionConstants.waitTime) ||
           stopTimer.hasElapsed(VisionConstants.validationTime);

  }
  
  public void periodic() {
 SmartDashboard.putBoolean("Aligned1", isAligned());
  }
}