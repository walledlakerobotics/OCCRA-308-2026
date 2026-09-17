package frc.robot.subsystems.drivetrain

import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.geometry.Transform2d
import edu.wpi.first.math.geometry.Translation2d
import edu.wpi.first.units.Units
import edu.wpi.first.wpilibj.ADIS16470_IMU
import edu.wpi.first.wpilibj.ADXRS450_Gyro
import edu.wpi.first.wpilibj.Encoder
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.constants.kChannelAForwardLeft
import frc.robot.constants.kChannelAForwardRight
import frc.robot.constants.kChannelAStrafe
import frc.robot.constants.kChannelBForwardLeft
import frc.robot.constants.kChannelBStrafe
import frc.robot.constants.kWheelDiameter
import java.util.function.Supplier
import kotlin.math.PI

class DriveOdometry : SubsystemBase() {

    // TODO: this doesn't factor in the rotation of the robot.

    private val mForwardLeftEncoder = Encoder(kChannelAForwardLeft, kChannelBForwardLeft)
    private val mForwardRightEncoder = Encoder(kChannelAForwardRight, kChannelBForwardLeft)
    private val mStrafeEncoder = Encoder(kChannelAStrafe, kChannelBStrafe)


    private val mGyro = ADXRS450_Gyro()

    init {
        val circumference = kWheelDiameter.times(PI).`in`(Units.Meter)

        mForwardLeftEncoder.distancePerPulse = circumference
        mForwardRightEncoder.distancePerPulse = circumference
        mStrafeEncoder.distancePerPulse = circumference

        // shaffleboard
        Shuffleboard.getTab("Odometry")
    }

    public fun resetOdometry() {
        mForwardLeftEncoder.reset()
        mForwardRightEncoder.reset()
        mStrafeEncoder.reset()
        mGyro.reset()
    }

    public val forwardLeftVector: Translation2d
        get() {
            val x = mForwardLeftEncoder.distance
            val y = mStrafeEncoder.distance


            // this calculates the distance traveled to make a right triangle to find the vector of the distance traveled.

            return Translation2d(x, y)
        }

    public val forwardRightVector: Translation2d
        get() {
            val x = mForwardRightEncoder.distance
            val y = mStrafeEncoder.distance

            return Translation2d(x, y)
        }

    public val gyroRotation: Rotation2d
        get() {
            val distanceTraveled = mStrafeEncoder.distance


            return Rotation2d()

        }
}