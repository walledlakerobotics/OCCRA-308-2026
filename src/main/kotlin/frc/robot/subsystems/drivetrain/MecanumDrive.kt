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

    private val mFrontLeftNeo = SparkMax(kFrontLeftMotorCANID, SparkLowLevel.MotorType.kBrushless)
    private val mFrontRightNeo = SparkMax(kFrontRightMotorCANID, SparkLowLevel.MotorType.kBrushless)
    private val mBackLeftNeo = SparkMax(kBackLeftMotorCANID, SparkLowLevel.MotorType.kBrushless)
    private val mBackRightNeo = SparkMax(kBackRightMotorCANID, SparkLowLevel.MotorType.kBrushless)

    private val kinematics = MecanumDriveKinematics(
        kFrontLeftNeoTranslation,
        kFrontRightNeoTranslation,
        kBackLeftNeoTranslation,
        kBackRightNeoTranslation
    )

    init {
        mFrontLeftNeo.configure(kLeftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters)
        mFrontRightNeo.configure(kRightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters)
        mBackLeftNeo.configure(kLeftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters)
        mBackRightNeo.configure(kRightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters)
    }

    fun drive(speeds: ChassisSpeeds) {
        // gets kinematic velocity's
        val v = kinematics.toWheelSpeeds(speeds)

        // sets the set point.
        mFrontLeftNeo.closedLoopController.setSetpoint(v.frontLeftMetersPerSecond, SparkBase.ControlType.kVelocity)
        mFrontRightNeo.closedLoopController.setSetpoint(v.frontRightMetersPerSecond, SparkBase.ControlType.kVelocity)
        mBackRightNeo.closedLoopController.setSetpoint(v.rearRightMetersPerSecond, SparkBase.ControlType.kVelocity)
        mBackLeftNeo.closedLoopController.setSetpoint(v.rearLeftMetersPerSecond, SparkBase.ControlType.kVelocity)
        
        // mFrontLeftNeo.set(0.5)
        // mFrontRightNeo.set(0.5)
        // mBackLeftNeo.set(0.5)
        // mBackRightNeo.set(0.5)
    }

    fun drive(x: () -> Double, y: () -> Double, theta: () -> Double): Command {
        return run {
            drive(
                ChassisSpeeds(
                    x() * 10,
                    y() * 10,
                    theta() * 10
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