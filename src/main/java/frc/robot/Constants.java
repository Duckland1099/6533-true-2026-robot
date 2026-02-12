package frc.robot;

import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import frc.robot.subsystems.drive.TunerConstants;
public class Constants {
        

         public class limelight_TA {
                  //placeholder values
                public static final double TA_TARGET_DISTANCE = 15;
                public static final double TA_TARGET_DISTANCE_ALLOWED_ERROR = 0.15;
        }

              public class DRIVE {
                public static final double MAX_SPEED = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond);
                public static final double MAX_ANGULAR_RATE = RotationsPerSecond.of(0.75).in(RadiansPerSecond);

                public static final double DRIVE_DEADBAND = 0.1;
                public static final double ROTATION_DEADBAND = 0.05;

                public static final double SLOW_DOWN_RATE = 0.2;
        }
        }
