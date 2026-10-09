package frc.robot

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.Commands
import edu.wpi.first.wpilibj2.command.button.CommandXboxController
import frc.robot.constants.deadbandOutput
import frc.robot.constants.kCoDriverControllerPort
import frc.robot.constants.kDriverControllerPort
import frc.robot.subsystems.drivetrain.MecanumDrive

class RobotContainer {
    val driverController = CommandXboxController(kDriverControllerPort)
    val coDriverController = CommandXboxController(kCoDriverControllerPort)

    val drivetrain = MecanumDrive()

    init {
        // config controller binds. 
        drivetrain.defaultCommand = drivetrain.drive(
            { deadbandOutput(driverController.leftX) },
            { deadbandOutput(driverController.leftY) },
            { deadbandOutput(driverController.rightX) }
        )
    }


    val autonomousCommand: Command
        get() {
            // return mPathChooser.selected
            return Commands.none()
        }

}
