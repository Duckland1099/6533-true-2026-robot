// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.FeedForwardConfig;
//import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import com.revrobotics.RelativeEncoder;


public class Index extends SubsystemBase {
  public boolean x;



  private final SparkMax m_intake2;
  private final RelativeEncoder m_intake2Encoder;
  private final SparkClosedLoopController m_intake2Controller;
  private SparkMaxConfig intake2Config;


  /** Creates a new Intake. */
  public Index() {
        
        
        m_intake2 = new SparkMax(2, MotorType.kBrushless); //intake
        m_intake2Controller = m_intake2.getClosedLoopController();
        m_intake2Encoder = m_intake2.getEncoder();
        intake2Config = new SparkMaxConfig();
        intake2Config.encoder.velocityConversionFactor(1);
        intake2Config.closedLoop.velocityFF(0.002); //ff 0.000185
        intake2Config.closedLoop
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        // Set PID values for position control. We don't need to pass a closed loop
        // slot, as it will default to slot 0.
        .p(0.000001)
        .i(0)
        .d(0)
        .outputRange(-1, 1);
        m_intake2.configure(intake2Config, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);

     
      }

     
  

      public Command setindex(double sp) {
        return this.run(() -> m_intake2Controller.setSetpoint(sp, ControlType.kVelocity));

      }

  



      public double indexVel() {
        double indVel = m_intake2Encoder.getVelocity();
        return indVel;
      }

    

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    //toggIntake();

    SmartDashboard.putNumber("Indexer", indexVel());
   
    SmartDashboard.putBoolean("X value", x);
  
  }
}
