package frc.robot.subsystems.drivetrain

import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.constants.kDataBufferPort
import java.util.function.DoubleSupplier

class DriveTrain : SubsystemBase() {
    private val mXDrive = XDrive()
    private val mDriveOdometry = DriveOdometry(kDataBufferPort)


    init {


    }

    fun drive(speedX: DoubleSupplier, speedY: DoubleSupplier, rotation: DoubleSupplier) {
        mXDrive.drive(speedX, speedY, rotation)
    }
}


