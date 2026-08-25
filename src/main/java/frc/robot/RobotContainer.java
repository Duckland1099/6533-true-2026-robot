// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.FollowPathCommand;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.subsystems.Intake;
import frc.robot.Robot;

import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shootwheel;
import frc.robot.subsystems.procam;
import frc.robot.subsystems.drive.CommandSwerveDrivetrain;
import frc.robot.subsystems.drive.TunerConstants;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Shootergate;
import frc.robot.AlignRight;
public class RobotContainer {
   
   
    private double MaxSpeed = 9 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.85).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
    private final SwerveRequest.RobotCentric forwardStraight = new SwerveRequest.RobotCentric()
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage);



    private final CommandXboxController driverController = new CommandXboxController(0);
    private final CommandXboxController OpControler = new CommandXboxController(1);

    
    public final static CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

public final Intake intake = new Intake();
public final Shooter shooter = new Shooter();
public final Index index = new Index();
public final Shootwheel shootwheel = new Shootwheel();
public final Shootergate openclose = new Shootergate();
 private final procam visionCam = new procam();

    /* Path follower */
    private final SendableChooser<Command> autoChooser;

    public RobotContainer() {
      //intake
    NamedCommands.registerCommand("intake-and-index", Commands.parallel(
    intake.setIntake(3500),
    index.setindex(0)
));
      

 NamedCommands.registerCommand("intake-and-index off", Commands.parallel(
    intake.setIntake(0),
    index.setindex(0)
));
NamedCommands.registerCommand("intake-and-index out", Commands.parallel(
    intake.setIntake(-3500),
    index.setindex(250)
));

//shooter
 NamedCommands.registerCommand("shoot", Commands.parallel(
    index.setindex(0),
    shooter.shoot(4500),
    shootwheel.shooterwheel(0)
    

)); 
 NamedCommands.registerCommand("stopshoot", Commands.parallel(
    index.setindex(0),
    shooter.shoot(0),
    shootwheel.shooterwheel(0)
    

)); 
 NamedCommands.registerCommand("open", Commands.parallel(
    index.setindex(-250),
    openclose.openclose(3))
    

); 
 NamedCommands.registerCommand("close", Commands.parallel(
    index.setindex(0),
    openclose.openclose(0))
    

); 
    NamedCommands.registerCommand("Align", new AlignRight(drivetrain, visionCam)); // should work if the bind on the driver works
    NamedCommands.registerCommand("close", openclose.openclose(0));
    NamedCommands.registerCommand("warmshooter", shooter.shoot(0));  
    NamedCommands.registerCommand("deploy", intake.Deploy(-25));
    NamedCommands.registerCommand("undeploy", intake.Deploy(5));


        autoChooser = AutoBuilder.buildAutoChooser("New Auto");
        SmartDashboard.putData("Auto Mode", autoChooser);

        configureBindings();

        // Warmup PathPlanner to avoid Java pauses
        FollowPathCommand.warmupCommand().schedule();
    }

   

    private void configureBindings() {
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-driverController.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(-driverController.getLeftX() * MaxSpeed) // Drive left with negative X (left)
                    .withRotationalRate(-driverController.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
            )
        );

        // Idle while the robot is disabled. This ensures the configured
        // neutral mode is applied to the drive motors while disabled.
        final var idle = new SwerveRequest.Idle();
        RobotModeTriggers.disabled().whileTrue(
            drivetrain.applyRequest(() -> idle).ignoringDisable(true)
        );

        driverController.a().whileTrue(drivetrain.applyRequest(() -> brake));
        driverController.b().whileTrue(drivetrain.applyRequest(() ->
            point.withModuleDirection(new Rotation2d(-driverController.getLeftY(), -driverController.getLeftX()))
        ));
    
        driverController.povUp().whileTrue(drivetrain.applyRequest(() ->
            forwardStraight.withVelocityX(0.5).withVelocityY(0))
        );
        driverController.povDown().whileTrue(drivetrain.applyRequest(() ->
            forwardStraight.withVelocityX(-0.5).withVelocityY(0))
        );

//driverController.y().onTrue(shooter.fireServotest());


        driverController.rightBumper().onTrue(
Commands.parallel(
        intake.setIntake(3500),
        index.setindex(0)
    )
);

driverController.rightBumper().onFalse(
    Commands.parallel(
        intake.setIntake(0),
        index.setindex(0)
    )
);


OpControler.a().onTrue(
    intake.Deploy(-25)   // deploy position
);

OpControler.b().onTrue(
    intake.Deploy(5)   // retract position
);




OpControler.rightBumper().onTrue(
    Commands.parallel(
        shooter.shoot(4500),
        index.setindex(0),
        shootwheel.shooterwheel(0)
       
    )
);

OpControler.rightBumper().onFalse(
    Commands.parallel(
        shooter.shoot(0),
        index.setindex(0),
        shootwheel.shooterwheel(0),
        openclose.openclose(0)


        
    )
);

OpControler.leftBumper().onTrue(
    Commands.parallel(
         openclose.openclose(3),
        index.setindex(-250),
        shootwheel.shooterwheel(0)
       
    )
);

OpControler.leftBumper().onFalse(
    Commands.parallel(
         openclose.openclose(0),
        index.setindex(0),
        shootwheel.shooterwheel(0)
       
    )
);
//OpControler.x().onTrue(servos.fireServotest());

OpControler.rightTrigger().onTrue(shooter.shoot(-250));
OpControler.rightTrigger().onFalse(shooter.shoot(-0));



OpControler.y().onTrue(
    Commands.parallel(
        shooter.shoot(-250),
        openclose.openclose(0),
         index.setindex(250)
        
    )
);


OpControler.y().onFalse(
    Commands.parallel(
        shooter.shoot(-0),
        openclose.openclose(0),
        index.setindex(0)
    )
);


        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        driverController.back().and(driverController.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        driverController.back().and(driverController.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        driverController.start().and(driverController.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        driverController.start().and(driverController.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        // Reset the field-centric heading on left bumper press.
        //driverController.leftBumper().onTrue(drivetrain.runOnce(drivetrain::seedFieldCentric));
        // Align with the left bumper press
        driverController.leftBumper().whileTrue(new AlignRight(drivetrain, visionCam)); 

       

        

    }


    public Command getAutonomousCommand() {
        /* Run the path selected from the auto chooser */
        return autoChooser.getSelected();
    }
}
