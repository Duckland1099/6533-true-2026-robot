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
  //private ThriftyNova m_intake1;
  //private ThriftyNova m_intake2; 
  private final SparkMax m_intake1;
  private final RelativeEncoder m_intake1Encoder;
  private final SparkClosedLoopController m_intake1Controller;
  private SparkMaxConfig intake1Config;

  private final SparkMax m_intake2;
  private final RelativeEncoder m_intake2Encoder;
  private final SparkClosedLoopController m_intake2Controller;
  private SparkMaxConfig intake2Config;

  private final SparkMax m_intake3;
  private final RelativeEncoder m_intake3Encoder;
  private final SparkClosedLoopController m_intake3Controller;
  private SparkMaxConfig intake3Config;

  /** Creates a new Intake. */
  public Intake() {
        
        m_intake1 = new SparkMax(1, MotorType.kBrushless); //intake
        m_intake1Controller = m_intake1.getClosedLoopController();
        m_intake1Encoder = m_intake1.getEncoder();
        intake1Config = new SparkMaxConfig();
        intake1Config.encoder.velocityConversionFactor(1);
        intake1Config.closedLoop.velocityFF(.00018); //ff 0.000185
        intake1Config.closedLoop
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        // Set PID values for position control. We don't need to pass a closed loop
        // slot, as it will default to slot 0.
        .p(0.00001)
        .i(0)
        .d(0)
        .outputRange(-0, 0);
        m_intake1.configure(intake1Config, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);

        m_intake2 = new SparkMax(2, MotorType.kBrushless); //intake
        m_intake2Controller = m_intake2.getClosedLoopController();
        m_intake2Encoder = m_intake2.getEncoder();
        intake2Config = new SparkMaxConfig();
        intake2Config.encoder.velocityConversionFactor(1);
        intake2Config.closedLoop.velocityFF(0); //ff 0.000185
        intake2Config.closedLoop
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        // Set PID values for position control. We don't need to pass a closed loop
        // slot, as it will default to slot 0.
        .p(0.00000)
        .i(0)
        .d(0)
        .outputRange(-0, 0);
        m_intake2.configure(intake2Config, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);

        m_intake3 = new SparkMax(3, MotorType.kBrushless); //intake rocker
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

         

      }

     
  

  
     public double getIntake() {
    return m_intake1Encoder.getPosition();
  }

  public void Intakepos(double pos) {
    m_intake1Controller.setReference(pos, ControlType.kPosition, ClosedLoopSlot.kSlot0);  
  }
 public void ResetIntake() {
    m_intake1Encoder.setPosition(0);
  }

  

      public Command setIntake(double sp) {
        return this.run(() -> m_intake1Controller.setSetpoint(sp, ControlType.kVelocity));

      }
      
      public Command Deploy(double sp) {
        return this.run(() -> m_intake3Controller.setSetpoint(sp, ControlType.kPosition));

      }
      public Command setindexer(double sp) {
        return this.run(() -> m_intake3Controller.setSetpoint(sp, ControlType.kVelocity));
      }

     public void toggIntake() {
       if (x = true) {
        setIntake(2500); 
       } else {
         setIntake(0);
       }
     }


   

      public double IntakeVel() {
        double intVel = m_intake1Encoder.getVelocity();
        return intVel;
      }

      public double indexVel() {
        double indVel = m_intake2Encoder.getVelocity();
        return indVel;
      }

      public double IntakePos() {
        double IntPos = m_intake3Encoder.getPosition();
        return IntPos;
      }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    toggIntake();
    SmartDashboard.putNumber("Intake Velocity", IntakeVel());
    SmartDashboard.putNumber("Indexer", indexVel());
    SmartDashboard.putNumber("Intake Pos", IntakePos());
    SmartDashboard.putBoolean("X value", x);
  
  }
}
