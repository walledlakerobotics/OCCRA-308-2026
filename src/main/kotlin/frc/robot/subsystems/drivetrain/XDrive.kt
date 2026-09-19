package frc.robot.subsystems.drivetrain

import com.revrobotics.PersistMode
import com.revrobotics.ResetMode
import com.revrobotics.spark.SparkBase
import com.revrobotics.spark.SparkLowLevel.MotorType
import com.revrobotics.spark.SparkMax
import edu.wpi.first.math.kinematics.ChassisSpeeds
import edu.wpi.first.units.Units
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.constants.kBackLeftMotorCANID
import frc.robot.constants.kBackRightMotorCANID
import frc.robot.constants.kDistanceFromCenter
import frc.robot.constants.kFrontLeftMotorCANID
import frc.robot.constants.kFrontRightMotorCANID
import frc.robot.constants.kSparkMaxConfig
import java.util.function.DoubleSupplier
import kotlin.math.sqrt

class XDrive : SubsystemBase() {

    private val frontLeftNeo = SparkMax(kFrontLeftMotorCANID, MotorType.kBrushless)
    private val frontRightNeo = SparkMax(kFrontRightMotorCANID, MotorType.kBrushless)
    private val backLeftNeo = SparkMax(kBackLeftMotorCANID, MotorType.kBrushless)
    private val backRightNeo = SparkMax(kBackRightMotorCANID, MotorType.kBrushless)

    init {
        frontLeftNeo.configure(kSparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters)
        frontRightNeo.configure(kSparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters)
        backLeftNeo.configure(kSparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters)
        backRightNeo.configure(kSparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters)
    }

    /**
     *  Gets the angle of the vector and calculates the rotation, and direction of the chassis.
     *  Sets the target setpoint of the velocity.
     *  @param speeds the requested velocity vector, in meters per second.
    * */
    public fun drive(speeds: ChassisSpeeds) {
        val rotation = speeds.omegaRadiansPerSecond * kDistanceFromCenter.`in`(Units.Meter)

        // front velocity
        val frontLeftVelocity = (speeds.vxMetersPerSecond + speeds.vyMetersPerSecond) * sqrt(2.0) + rotation
        frontLeftNeo.closedLoopController.setSetpoint(frontLeftVelocity, SparkBase.ControlType.kVelocity)

        val frontRightVelocity = (speeds.vxMetersPerSecond - speeds.vyMetersPerSecond) * sqrt(2.0) + rotation;
        frontRightNeo.closedLoopController.setSetpoint(frontRightVelocity, SparkBase.ControlType.kVelocity)

        // back velocity
        val backLeftVelocity = (-speeds.vxMetersPerSecond - speeds.vyMetersPerSecond) * sqrt(2.0) - rotation
        backLeftNeo.closedLoopController.setSetpoint(backLeftVelocity, SparkBase.ControlType.kVelocity)

        val backRightVelocity = (-speeds.vxMetersPerSecond + speeds.vyMetersPerSecond) * sqrt(2.0) - rotation;
        backRightNeo.closedLoopController.setSetpoint(backRightVelocity, SparkBase.ControlType.kVelocity)
    }

    public fun drive(velocityX: DoubleSupplier, velocityY: DoubleSupplier, rotation: DoubleSupplier) =
        drive(ChassisSpeeds(velocityX.asDouble, velocityY.asDouble, rotation.asDouble))


    /**
     * This sets the velocity to zero.
     * */
    public fun stop() {
        drive(ChassisSpeeds())
    }
}
