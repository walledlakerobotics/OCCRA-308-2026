package frc.robot

import com.pathplanner.lib.auto.AutoBuilder
import com.pathplanner.lib.path.PathPlannerPath
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.Commands
import frc.robot.constants.kCoDriverController
import frc.robot.constants.kDriverController
import frc.robot.subsystems.drivetrain.DriveTrain
import frc.robot.subsystems.drivetrain.XDrive

class RobotContainer {

    private val mPathChooser = AutoBuilder.buildAutoChooser()

    public val drivetrain = DriveTrain()

    init {
        kDriverController(this)
        kCoDriverController(this)
    }


    val autonomousCommand: Command
        get() {
           return mPathChooser.selected
        }
}
