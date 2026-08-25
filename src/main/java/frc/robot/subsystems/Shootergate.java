// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.FeedForwardConfig;
//import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
public class Shootergate extends SubsystemBase {
  
  private final SparkMax m_gate;
  private final RelativeEncoder m_gateEncoder;
  private final SparkClosedLoopController m_gateController;
  private SparkMaxConfig gateConfig;
  /** Creates a new gate. */
  public Shootergate() {


     m_gate = new SparkMax(9, MotorType.kBrushless); //shooter gate
      
        m_gateController = m_gate.getClosedLoopController();
        m_gateEncoder = m_gate.getEncoder();
        gateConfig = new SparkMaxConfig();
        gateConfig.encoder.velocityConversionFactor(1);
        gateConfig.closedLoop.velocityFF(.000185); //ff 0.000185
        gateConfig.closedLoop
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        // Set PID values for position control. We don't need to pass a closed loop
        // slot, as it will default to slot 0.
        .p(0.1)
        .i(0)
        .d(0)
        .outputRange(-0.1, 0.2);
        m_gate.configure(gateConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);

  }
 public void gatepos(double pos) {
    m_gateController.setReference(pos, ControlType.kPosition, ClosedLoopSlot.kSlot0);  
  }
  public Command openclose(double sp) {
        return this.run(() -> m_gateController.setSetpoint(sp, ControlType.kPosition));

      }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
