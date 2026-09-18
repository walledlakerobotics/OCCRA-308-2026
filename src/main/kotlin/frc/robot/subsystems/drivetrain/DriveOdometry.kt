package frc.robot.subsystems.drivetrain

import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.geometry.Translation2d
import edu.wpi.first.units.Units
import edu.wpi.first.units.measure.Distance
import edu.wpi.first.wpilibj.Encoder
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.constants.kChannelAForward
import frc.robot.constants.kChannelAStrafe
import frc.robot.constants.kChannelBForward
import frc.robot.constants.kChannelBStrafe
import frc.robot.constants.kDistanceFromCenter
import frc.robot.constants.kWheelDiameter
import kotlin.math.PI
import kotlin.math.sqrt

class DriveOdometry : SubsystemBase() {

    private val mForwardEncoder = Encoder(kChannelAForward, kChannelBForward)
    private val mStrafeEncoder = Encoder(kChannelAStrafe, kChannelBStrafe)
//    private val mGyro = AHRS()

    init {
        val distancePerPulse = kWheelDiameter.times(PI).`in`(Units.Meters)

        mForwardEncoder.distancePerPulse = distancePerPulse
        mStrafeEncoder.distancePerPulse = distancePerPulse
    }

    val deadMotorsDistance: Distance
        get() {
            /*
            *                       |\
            *                       | \  distance multiplied to the sqrt(2.0)
            * distance from center  |  \
            *                       |   \
            *                       ----- same distance
            * */
            val distance = kDistanceFromCenter.times(sqrt(2.0))

            return distance
        }

    val forwardTranslation: Translation2d
        get() {
            val x = mForwardEncoder.distance

            return Translation2d()
        }

    val strafeTranslation: Translation2d
        get() {
            val y = mStrafeEncoder.distance

            return Translation2d()
        }

    val pose: Pose2d
        get() {

            return Pose2d()
        }

}