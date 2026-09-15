import com.revrobotics.spark.SparkLowLevel.MotorType
import com.revrobotics.spark.SparkMax
import edu.wpi.first.math.kinematics.ChassisSpeeds
import edu.wpi.first.units.Units
import edu.wpi.first.units.measure.Angle
import kotlin.math.atan2

class XDrive {

    private val m_frontLeftNeo: SparkMax = SparkMax(0, MotorType.kBrushless)
    private val m_frontRightNeo: SparkMax = SparkMax(0, MotorType.kBrushless)
    private val m_backLeftNeo: SparkMax = SparkMax(0, MotorType.kBrushless)
    private val m_backRightNeo: SparkMax = SparkMax(0, MotorType.kBrushless)

    constructor() {
        
    }

    public fun drive(speeds: ChassisSpeeds) {
        val angle: Angle = Angle.ofBaseUnits(atan2(speeds.vxMetersPerSecond, speeds.vyMetersPerSecond), Units.Radians)

        val rotation: Double = angle.`in`(Units.Radians) + speeds.omegaRadiansPerSecond;

        // front velocity
        val frontLeftVelocity: Double = speeds.vxMetersPerSecond + speeds.vyMetersPerSecond + rotation;
        val frontRightVelocity: Double = speeds.vxMetersPerSecond - speeds.vyMetersPerSecond + rotation;

        // back velocity
        val backLeftVelocity: Double = -speeds.vxMetersPerSecond - speeds.vyMetersPerSecond - rotation;
        val backRightVelocity: Double = -speeds.vxMetersPerSecond + speeds.vyMetersPerSecond - rotation;


    }
}