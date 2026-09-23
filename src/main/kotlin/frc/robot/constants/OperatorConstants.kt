package frc.robot.constants

import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.event.EventLoop
import frc.robot.RobotContainer
import java.util.function.DoubleSupplier
import kotlin.math.abs
import kotlin.math.atan2


const val kDeadBandThreshold = 0.0

/**
 * Creates Driver Controller
 * @param container this is the robot container.
 * */
fun kDriverController(container: RobotContainer): Lazy<XboxController> =
    lazy {
        val controller = XboxController(0)
        val axisLoop = EventLoop()

        // probs better way.

        axisLoop.bind {
            // drive controller.
            container.drivetrain.drive({
                controller.leftX
            }, {
                controller.leftY
            }, {
                val y = controller.rightY;
                val x = controller.rightX;

                atan2(y, x)
            })
        }

        controller.axisGreaterThan(0, kDeadBandThreshold, axisLoop)
        controller.axisGreaterThan(1, kDeadBandThreshold, axisLoop)


        // other configs


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