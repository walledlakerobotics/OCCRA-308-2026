package frc.robot.subsystems.drivetrain

import edu.wpi.first.math.kinematics.ChassisSpeeds
import edu.wpi.first.wpilibj2.command.SubsystemBase

class DriveTrain : SubsystemBase() {
    private val driveOdometry = DriveOdometry()
    private val mecanumDrive = MecanumDrive()

    fun drive(speedX: () -> Double, speedY: () -> Double, rotation: () -> Double) {
        mecanumDrive.drive(ChassisSpeeds(speedX(), speedY(), rotation()))
    }

    fun stop() {
        mecanumDrive.stop()
    }


    override fun periodic() {

    }
}


