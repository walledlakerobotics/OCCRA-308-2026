package frc.robot

import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.Commands
import frc.robot.constants.kCoDriverController
import frc.robot.constants.kDriverController
import frc.robot.subsystems.drivetrain.XDrive

class RobotContainer {

    private val m_driveTrain = XDrive();

    init {
        kDriverController(this)
        kCoDriverController(this)
    }

    val autonomousCommand: Command
        get() = Commands.print("No autonomous command configured")
}
