package frc.robot

import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.Commands
import edu.wpi.first.wpilibj2.command.button.CommandXboxController
import frc.robot.constants.kDriverControllerPort
import frc.robot.subsystems.drivetrain.MecanumDrive

class RobotContainer {

//    private val mPathChooser = AutoBuilder.buildAutoChooser()

    val drivetrain = MecanumDrive()
    val driverController = CommandXboxController(kDriverControllerPort)

    init {
//        kDriverController(this)
//        kCoDriverController(this)
        drivetrain.defaultCommand = drivetrain.drive(
            driverController::getLeftY,
            driverController::getLeftX,
            driverController::getRightX
        )
        
        // drivetrain.defaultCommand = drivetrain.drive({ 0.5 }, { 0.0 }, { 0.0 })
    }


    val autonomousCommand: Command
        get() {
//           return mPathChooser.selected
            return Commands.none()
        }
}
