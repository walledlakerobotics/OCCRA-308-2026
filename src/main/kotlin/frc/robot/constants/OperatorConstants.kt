package frc.robot.constants

import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.event.EventLoop
import frc.robot.RobotContainer
import java.util.function.DoubleSupplier
import kotlin.math.abs

const val kDeadBandThreshold = 0.2
const val kDriverControllerPort = 0
const val kCoDriverControllerPort = 1

fun deadbandOutput(axis: Double): Double {
    if (abs(axis) > kDeadBandThreshold)
        return axis

    return 0.0
}