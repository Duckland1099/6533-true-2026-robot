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
import com.thethriftybot.devices.ThriftyNova;
import com.thethriftybot.devices.ThriftyNova.EncoderType;

public class Intake extends SubsystemBase {
  public boolean x;
  private ThriftyNova m_intake1;
  private ThriftyNova m_intake2; 
  private final SparkMax m_intake3;
  private final RelativeEncoder m_intake3Encoder;
  private final SparkClosedLoopController m_intake3Controller;
  private SparkMaxConfig intake3Config;

  /** Creates a new Intake. */
  public Intake() {
        
        m_intake3 = new SparkMax(3, MotorType.kBrushless);
        
        m_intake3Controller = m_intake3.getClosedLoopController();
        m_intake3Encoder = m_intake3.getEncoder();
        intake3Config = new SparkMaxConfig();
        intake3Config.encoder.velocityConversionFactor(1);

        intake3Config.closedLoop.velocityFF(.000185); //ff 0.000185
        intake3Config.closedLoop
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        // Set PID values for position control. We don't need to pass a closed loop
        // slot, as it will default to slot 0.
        .p(0)
        .i(0)
        .d(0)
        .outputRange(-0, 0);
       
    

        m_intake3.configure(intake3Config, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);

         m_intake2 = new ThriftyNova(2); 
         m_intake2.useEncoderType(EncoderType.INTERNAL);
         m_intake2.setBrakeMode(false);
         //m_intake2.setInversion(); // not sure if we'll need this or we can do it in the robotcontainer
         m_intake2.pid0.setP(0);
         m_intake2.pid0.setI(0);
         m_intake2.pid0.setD(0);
         m_intake2.pid0.setFF(0);

         
         m_intake1 = new ThriftyNova(1); 
         m_intake1.useEncoderType(EncoderType.INTERNAL);
         m_intake1.setBrakeMode(false);
         //m_intake2.setInversion(); // not sure if we'll need this or we can do it in the robotcontainer
         m_intake1.pid0.setP(0);
         m_intake1.pid0.setI(0);
         m_intake1.pid0.setD(0);
         m_intake1.pid0.setFF(0);

      }

     
  

  
     public double getIntake() {
    return m_intake3Encoder.getPosition();
  }

  public void Intakepos(double pos) {
    m_intake3Controller.setReference(pos, ControlType.kPosition, ClosedLoopSlot.kSlot0);  
  }
 public void ResetIntake() {
    m_intake3Encoder.setPosition(0);
  }

  

      public Command setIntake(double sp) {
     
        return this.run(() ->   m_intake1.setVelocity(sp));
      }
      
      public Command Depoly(double sp) {
        return this.run(() -> m_intake3Encoder.setPosition(sp));

      }
      public Command setindexer(double sp) {
        return this.run(() ->   m_intake2.setVelocity(sp));
      }

     public void toggIntake() {
       if (x = true) {
        setIntake(2500); 
       } else {
         setIntake(0);
       }
     }


   

      public double IntakeVel() {
        double Vel = m_intake3Encoder.getVelocity();
        return Vel;
      }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    toggIntake();
    SmartDashboard.putNumber("Intake Velocity", IntakeVel());
    SmartDashboard.putBoolean("X value", x);
  
  }
}
