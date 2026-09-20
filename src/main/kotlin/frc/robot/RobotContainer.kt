package frc.robot

import com.pathplanner.lib.auto.AutoBuilder
import edu.wpi.first.wpilibj2.command.Command
import frc.robot.constants.kCoDriverController
import frc.robot.constants.kDriverController
import frc.robot.subsystems.drivetrain.DriveTrain

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
