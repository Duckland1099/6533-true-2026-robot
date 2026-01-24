// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
//import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

public class Intake extends SubsystemBase {

  private final SparkMax m_intake1;

  private final RelativeEncoder m_intake1Encoder;


  private final SparkClosedLoopController m_intake1Controller;

  private SparkMaxConfig intake1Config;
  /** Creates a new Intake. */
  public Intake() {

        m_intake1 = new SparkMax(1, MotorType.kBrushless);

        m_intake1Controller = m_intake1.getClosedLoopController();
        m_intake1Encoder = m_intake1.getEncoder();
        intake1Config = new SparkMaxConfig();
        intake1Config.encoder.velocityConversionFactor(1);

        intake1Config.closedLoop
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        // Set PID values for position control. We don't need to pass a closed loop
        // slot, as it will default to slot 0.
        .p(0.0002)
        .i(0)
        .d(0)
        .outputRange(-1, 1);


        m_intake1.configure(intake1Config, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
      }

      public void setIntake(double sp) {
        m_intake1Controller.setSetpoint(sp, ControlType.kPosition);

      }

      public double IntakeVel() {
        double Vel = m_intake1Encoder.getVelocity();
        return Vel;
      }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    
    SmartDashboard.putNumber("Intake Velocity", IntakeVel());
  }
}
