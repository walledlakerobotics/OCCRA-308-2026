package frc.robot.subsystems.drive

import com.revrobotics.PersistMode
import com.revrobotics.ResetMode
import com.revrobotics.spark.SparkBase
import com.revrobotics.spark.SparkLowLevel.MotorType
import com.revrobotics.spark.SparkMax
import com.revrobotics.spark.config.MAXMotionConfig
import com.revrobotics.spark.config.SparkMaxConfig
import edu.wpi.first.math.kinematics.ChassisSpeeds
import edu.wpi.first.units.Units
import edu.wpi.first.units.measure.Angle
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants
import kotlin.math.atan2

class XDrive : SubsystemBase {

    private val m_frontLeftNeo = SparkMax(0, MotorType.kBrushless);
    private val m_frontRightNeo = SparkMax(0, MotorType.kBrushless);
    private val m_backLeftNeo = SparkMax(0, MotorType.kBrushless);
    private val m_backRightNeo = SparkMax(0, MotorType.kBrushless);

    private val m_config = SparkMaxConfig();

    constructor() {

        // TODO: configure the Neos and PID tune them, also config trapezoidal profiling using MAXMotion (:3).

        m_frontLeftNeo.configure(m_config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        m_frontRightNeo.configure(m_config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        m_backLeftNeo.configure(m_config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        m_backRightNeo.configure(m_config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    }

    /**
     *  Gets the angle of the vector and calculates the rotation, and direction of the chassis,
     *  using trapezoidal profiling from the closed-loop, setting the target set point of the velocity.
     *  @param speeds the requested velocity vector.
    * */
    public fun drive(speeds: ChassisSpeeds) {
        val angle = Angle.ofBaseUnits(atan2(speeds.vxMetersPerSecond, speeds.vyMetersPerSecond), Units.Radians);
        val rotation = angle.`in`(Units.Radians) + speeds.omegaRadiansPerSecond;

        // front velocity
        val frontLeftVelocity = speeds.vxMetersPerSecond + speeds.vyMetersPerSecond + rotation;
        m_frontLeftNeo.closedLoopController.setSetpoint(frontLeftVelocity, SparkBase.ControlType.kVelocity);

        val frontRightVelocity = speeds.vxMetersPerSecond - speeds.vyMetersPerSecond + rotation;
        m_frontRightNeo.closedLoopController.setSetpoint(frontRightVelocity, SparkBase.ControlType.kVelocity);

        // back velocity
        val backLeftVelocity = -speeds.vxMetersPerSecond - speeds.vyMetersPerSecond - rotation;
        m_backLeftNeo.closedLoopController.setSetpoint(backLeftVelocity, SparkBase.ControlType.kVelocity);

        val backRightVelocity = -speeds.vxMetersPerSecond + speeds.vyMetersPerSecond - rotation;
        m_backRightNeo.closedLoopController.setSetpoint(backRightVelocity, SparkBase.ControlType.kVelocity);





    }
}
