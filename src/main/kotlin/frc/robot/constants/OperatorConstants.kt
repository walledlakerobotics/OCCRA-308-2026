package frc.robot.constants

import edu.wpi.first.wpilibj.XboxController
import frc.robot.RobotContainer

/**
 * Creates Driver Controller
 * @param container this is the robot container.
 * */
fun kDriverController(container: RobotContainer): Lazy<XboxController> =
    lazy {
        val controller = XboxController(0)

        // config here

        controller
    }

/**
 * Creates coDriver Controller
 * @param container this is the robot container.
 * */
fun kCoDriverController(container: RobotContainer): Lazy<XboxController> =
    lazy {
        val controller = XboxController(1)

        // config here

        controller
    }

