package frc.robot.subsystems.drivetrain

import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.units.Units
import edu.wpi.first.wpilibj.Encoder
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.constants.kWheelDiameter
import kotlin.math.PI

class DriveOdometry : SubsystemBase() {

    private val mForwardLeftEncoder = Encoder(0, 0)
    private val mForwardRightEncoder = Encoder(0, 0)
    private val mStrafeEncoder = Encoder(0, 0)

    /**
     * This returns in meters
     * */
    public val pose2d: Pose2d
        get() {


            return Pose2d()
        }

    init {
        val circumference = kWheelDiameter.times(PI).`in`(Units.Meter)

        mForwardLeftEncoder.distancePerPulse = circumference
        mForwardRightEncoder.distancePerPulse = circumference
        mStrafeEncoder.distancePerPulse = circumference
    }

    public fun resetOdometry() {

    }

    override fun periodic() {
        super.periodic()



    }



}