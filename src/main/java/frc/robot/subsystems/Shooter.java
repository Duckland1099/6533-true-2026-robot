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
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
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


public class Shooter extends SubsystemBase {

  private final SparkMax m_shoot1;
  private final RelativeEncoder m_shoot1Encoder;
  private final SparkClosedLoopController m_shoot1Controller;
  private SparkMaxConfig shoot1Config;
  private final SparkMax m_shoot2;
  private final RelativeEncoder m_shoot2Encoder;
  private final SparkClosedLoopController m_shoot2Controller;
  private SparkMaxConfig shoot2Config;
  
  /** Creates a new Shooter. */
  public Shooter() {


        m_shoot1 = new SparkMax(4, MotorType.kBrushless);
        m_shoot1Controller = m_shoot1.getClosedLoopController();
        m_shoot1Encoder = m_shoot1.getEncoder();
        shoot1Config = new SparkMaxConfig();
        shoot1Config.encoder.velocityConversionFactor(1);
        
        shoot1Config.closedLoop.velocityFF(0.00016199999663513154); //ff 0.000185
        shoot1Config.closedLoop
        
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        // Set PID values for position control. We don't need to pass a closed loop
        // slot, as it will default to slot 0.
        .p(0.00009999999747378752)
        .i(0)
        .d(0)
        .outputRange(-0, 0);
         m_shoot2 = new SparkMax(6, MotorType.kBrushless);
        
        m_shoot2Controller = m_shoot2.getClosedLoopController();
        m_shoot2Encoder = m_shoot1.getEncoder();
        shoot2Config = new SparkMaxConfig();
        shoot2Config.encoder.velocityConversionFactor(1);

        shoot2Config.closedLoop.velocityFF(.000185); //ff 0.000185
        shoot2Config.closedLoop
        
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        // Set PID values for position control. We don't need to pass a closed loop
        // slot, as it will default to slot 0.
         .p(0.00009999999747378752)
        .i(0)
        .d(0)
        .outputRange(-0, 0);
        
        shoot2Config.follow(4);
        


    }
    public Command shoot(double sp) {
        return this.run(() -> m_shoot1Controller.setSetpoint(sp, ControlType.kVelocity));
      }
  

public double ShooterVel() {
        double ShootVel = m_shoot1Encoder.getVelocity();
        return ShootVel;
}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
 
 SmartDashboard.putNumber("Shooter Velocity", ShooterVel());
 
  }
}




      
   

  
   