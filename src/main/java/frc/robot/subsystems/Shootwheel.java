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
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.revrobotics.RelativeEncoder;
import com.thethriftybot.devices.ThriftyNova;
import com.thethriftybot.devices.ThriftyNova.EncoderType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.configs.*;
import com.ctre.phoenix6.hardware.*;
import com.ctre.phoenix6.signals.*;
import com.ctre.phoenix6.swerve.*;
import com.ctre.phoenix6.swerve.SwerveModuleConstants.*;


public class Shootwheel extends SubsystemBase {

  
  private final TalonFX m_shoot3;
  private final TalonFXConfiguration m_shoot3Config;
  /** Creates a new Shooter. */
  public Shootwheel() {

       m_shoot3 = new TalonFX(7);
       m_shoot3Config = new TalonFXConfiguration();
       m_shoot3Config.OpenLoopRamps.DutyCycleOpenLoopRampPeriod = 0;
     //  var request = new VelocityVoltage(0).withSlot(0);
     //  m_shoot3.setControl(request.withVelocity(0).withFeedForward(0));

            m_shoot3Config.CurrentLimits.StatorCurrentLimit = 30.0;
        m_shoot3Config.CurrentLimits.StatorCurrentLimitEnable = true;
       m_shoot3Config.MotorOutput.NeutralMode = NeutralModeValue.Coast;


        m_shoot3Config.Slot0.kP = 0.0;
        m_shoot3Config.Slot0.kI = 0.0;
        m_shoot3Config.Slot0.kD = 0.0;
        m_shoot3Config.Slot0.kV = 0.0;
    
  } 
 
      public Command shooterwheel(double sp) {

       return this.run(() -> m_shoot3.set(sp)); }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }}




      
   

  
   