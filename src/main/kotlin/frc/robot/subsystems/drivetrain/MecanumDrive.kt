package frc.robot.subsystems.drivetrain

import com.revrobotics.PersistMode
import com.revrobotics.ResetMode
import com.revrobotics.spark.SparkBase
import com.revrobotics.spark.SparkLowLevel
import com.revrobotics.spark.SparkMax
import edu.wpi.first.math.kinematics.ChassisSpeeds
import edu.wpi.first.math.kinematics.MecanumDriveKinematics
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.constants.*

class MecanumDrive : SubsystemBase() {

    private val frontLeftNeo = SparkMax(kFrontLeftMotorCANID, SparkLowLevel.MotorType.kBrushless)
    private val frontRightNeo = SparkMax(kFrontRightMotorCANID, SparkLowLevel.MotorType.kBrushless)
    private val backLeftNeo = SparkMax(kBackLeftMotorCANID, SparkLowLevel.MotorType.kBrushless)
    private val backRightNeo = SparkMax(kBackRightMotorCANID, SparkLowLevel.MotorType.kBrushless)

    private val kinematics = MecanumDriveKinematics(
        kFrontLeftNeoTranslation,
        kFrontRightNeoTranslation,
        kBackLeftNeoTranslation,
        kBackRightNeoTranslation
    )

    init {
        frontLeftNeo.configure(kLeftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters)
        frontRightNeo.configure(kRightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters)
        backLeftNeo.configure(kLeftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters)
        backRightNeo.configure(kRightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters)
    }
    
    fun drive(speeds: ChassisSpeeds) {
        // gets kinematic velocity's
        val v = kinematics.toWheelSpeeds(speeds)

        // sets the set point.
        frontLeftNeo.closedLoopController.setSetpoint(v.frontLeftMetersPerSecond, SparkBase.ControlType.kVelocity)
        frontRightNeo.closedLoopController.setSetpoint(v.frontRightMetersPerSecond, SparkBase.ControlType.kVelocity)
        backRightNeo.closedLoopController.setSetpoint(v.rearRightMetersPerSecond, SparkBase.ControlType.kVelocity)
        backLeftNeo.closedLoopController.setSetpoint(v.rearLeftMetersPerSecond, SparkBase.ControlType.kVelocity)
    }

    fun drive(x: () -> Double, y: () -> Double, theta: () -> Double): Command {
        return run {
            drive(
                ChassisSpeeds(
                    x() * kSpeedFactor,
                    y() * kSpeedFactor,
                    theta() * kSpeedFactor
                )
            )
        }
    }

//    /**
//     *  Calculates each motors velocity vectors, and sets the set point for the output of the control loop.
//     *
//     * @param speeds the velocity vector that is measured in m/s or (Meters per Second).
//     * */
//    public fun drive(speeds: ChassisSpeeds) {
//        // factors in the angle of the vector, and the rotation of the vector
//        val rotationFactor = (kDistanceFromCenter.`in`(Units.Meter) * 0.5) * (speeds.omegaRadiansPerSecond + atan2(speeds.vyMetersPerSecond, speeds.vxMetersPerSecond))
//
//        val frontLeftVelocity = speeds.vxMetersPerSecond + speeds.vyMetersPerSecond - rotationFactor
//        mFrontLeftNeo.closedLoopController.setSetpoint(frontLeftVelocity, SparkBase.ControlType.kVelocity)
//
//        val frontRightVelocity = speeds.vxMetersPerSecond + speeds.vyMetersPerSecond + rotationFactor
//        mFrontRightNeo.closedLoopController.setSetpoint(frontRightVelocity, SparkBase.ControlType.kVelocity)
//
//        val backLeftVelocity = speeds.vxMetersPerSecond + speeds.vyMetersPerSecond + rotationFactor
//        mBackLeftNeo.closedLoopController.setSetpoint(backLeftVelocity, SparkBase.ControlType.kVelocity)
//
//        val backRightVelocity = speeds.vxMetersPerSecond + speeds.vyMetersPerSecond - rotationFactor
//        mBackRightNeo.closedLoopController.setSetpoint(backRightVelocity, SparkBase.ControlType.kVelocity)
//    }

    /**
     *
     * stops the drive
     *
     * */
    fun stop() {
        drive(ChassisSpeeds())
    }
}