package frc.robot

import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.Commands
import edu.wpi.first.wpilibj2.command.button.CommandXboxController
import frc.robot.constants.deadbandOutput
import frc.robot.constants.kCoDriverControllerPort
import frc.robot.constants.kDriverControllerPort
import frc.robot.subsystems.drivetrain.MecanumDrive

class RobotContainer {
    val mDriverController = CommandXboxController(kDriverControllerPort)
    val mCoDriverController = CommandXboxController(kCoDriverControllerPort)

    val drivetrain = MecanumDrive()

    init {
        drivetrain.defaultCommand = drivetrain.drive(
            { deadbandOutput(mDriverController.leftX) },
            { deadbandOutput(mDriverController.leftY) },
            { deadbandOutput(mDriverController.rightX) }
        )
    }


    val autonomousCommand: Command
        get() {
//           return mPathChooser.selected
            return Commands.none()
        }
}
