// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  
public static class VisionConstants {
    public static final String LIMELIGHT_NAME = "limelight";
    /* 
    public static final double MOVE_P = 0.300000;
    public static final double MOVE_I = 0.000000;
    public static final double MOVE_D = 0.000600;

    public static final double ROTATE_P = 0.030000;
    public static final double ROTATE_I = 0.000000;
    public static final double ROTATE_D = 0.000100;
    */


    public static final double xTolerance = 1; //placeholder value
    public static final double yTolerance = 1;
  

    public static final double X_REEF_ALIGNMENT_P = 0.15;
    public static final double Y_REEF_ALIGNMENT_P = 0.1;
    public static final double ROT_REEF_ALIGNMENT_P = 0.03;
    
    public static final double ROT_SETPOINT_REEF_ALIGNMENT = 0;  
    public static final double ROT_TOLERANCE_REEF_ALIGNMENT = 0.5;
    public static final double X_SETPOINT_REEF_ALIGNMENT = -0.5;  
    public static final double X_TOLERANCE_REEF_ALIGNMENT = 0.005;
    public static final double Y_SETPOINT_REEF_ALIGNMENT = 0.4;  
    public static final double Y_TOLERANCE_REEF_ALIGNMENT = 0.1;
  
    public static final double waitTime = 1;
    public static final double validationTime = 0.3;

    public static final double branchAngle = 22d; //Degrees
    public static final double branchTolerance = 2.2; //Degrees

    public static final double TOLERANCE = 0.01;
  }
}
