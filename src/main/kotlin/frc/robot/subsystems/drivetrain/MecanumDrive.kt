package frc.robot.subsystems.drivetrain

import com.revrobotics.PersistMode
import com.revrobotics.ResetMode
import com.revrobotics.spark.SparkBase
import com.revrobotics.spark.SparkLowLevel
import com.revrobotics.spark.SparkMax
import edu.wpi.first.math.kinematics.ChassisSpeeds
import edu.wpi.first.units.Units
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.constants.kBackLeftMotorCANID
import frc.robot.constants.kBackRightMotorCANID
import frc.robot.constants.kDistanceFromCenter
import frc.robot.constants.kFrontLeftMotorCANID
import frc.robot.constants.kFrontRightMotorCANID
import frc.robot.constants.kSparkMaxConfig
import kotlin.math.atan2

class MecanumDrive : SubsystemBase() {

    private val mFrontLeftNeo = SparkMax(kFrontLeftMotorCANID, SparkLowLevel.MotorType.kBrushless)
    private val mFrontRightNeo = SparkMax(kFrontRightMotorCANID, SparkLowLevel.MotorType.kBrushless)
    private val mBackLeftNeo = SparkMax(kBackLeftMotorCANID, SparkLowLevel.MotorType.kBrushless)
    private val mBackRightNeo = SparkMax(kBackRightMotorCANID, SparkLowLevel.MotorType.kBrushless)

    init {
        mFrontLeftNeo.configure(kSparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters)
        mFrontRightNeo.configure(kSparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters)
        mBackLeftNeo.configure(kSparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters)
        mBackRightNeo.configure(kSparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters)
    }

    /**
     *  Calculates each motors velocity vectors, and sets the set point for the output of the control loop. 
     *
     * @param speeds the velocity vector that is measured in m/s or (Meters per Second).
     * */
    public fun drive(speeds: ChassisSpeeds) {
        // factors in the angle of the vector, and the rotation of the vector
        val rotationFactor = kDistanceFromCenter.`in`(Units.Meter) * (speeds.omegaRadiansPerSecond + atan2(speeds.vyMetersPerSecond, speeds.vxMetersPerSecond))

        val frontLeftVelocity = speeds.vxMetersPerSecond + speeds.vyMetersPerSecond - rotationFactor
        mFrontLeftNeo.closedLoopController.setSetpoint(frontLeftVelocity, SparkBase.ControlType.kVelocity)

        val frontRightVelocity = speeds.vxMetersPerSecond + speeds.vyMetersPerSecond + rotationFactor
        mFrontRightNeo.closedLoopController.setSetpoint(frontRightVelocity, SparkBase.ControlType.kVelocity)

        val backLeftVelocity = speeds.vxMetersPerSecond + speeds.vyMetersPerSecond + rotationFactor
        mBackLeftNeo.closedLoopController.setSetpoint(backLeftVelocity, SparkBase.ControlType.kVelocity)

        val backRightVelocity = speeds.vxMetersPerSecond + speeds.vyMetersPerSecond - rotationFactor
        mBackRightNeo.closedLoopController.setSetpoint(backRightVelocity, SparkBase.ControlType.kVelocity)
    }
}