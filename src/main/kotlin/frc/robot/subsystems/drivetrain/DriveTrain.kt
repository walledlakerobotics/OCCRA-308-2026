package frc.robot.subsystems.drivetrain

import com.pathplanner.lib.auto.AutoBuilder
import edu.wpi.first.math.kinematics.ChassisSpeeds
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.constants.kDataBufferPort
import java.util.function.DoubleSupplier

class DriveTrain : SubsystemBase() {
    private val mDriveOdometry = DriveOdometry(kDataBufferPort)
    private val mMecanumDrive = MecanumDrive()

    init {

    }

    fun drive(speedX: DoubleSupplier, speedY: DoubleSupplier, rotation: DoubleSupplier) {
        mMecanumDrive.drive(ChassisSpeeds(speedX.asDouble, speedY.asDouble, rotation.asDouble))
    }

    fun stop() {
        mMecanumDrive.stop()
    }
}


