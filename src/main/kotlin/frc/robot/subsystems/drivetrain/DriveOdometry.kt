package frc.robot.subsystems.drivetrain

import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.geometry.Translation2d
import edu.wpi.first.wpilibj.Notifier
import edu.wpi.first.wpilibj.SerialPort
import frc.robot.constants.kBufferCapacity
import java.nio.ByteBuffer
import java.nio.ByteOrder


class DriveOdometry : AutoCloseable {

    // TODO: need to convert c struct into kotlin struct

    // port serial instance
    private var serialPort =
        SerialPort(115200, SerialPort.Port.kUSB, 8, SerialPort.Parity.kNone, SerialPort.StopBits.kOne)
    private var byteBuffer: ByteBuffer = ByteBuffer.allocate(kBufferCapacity)

    private val odometryData: OdometryData = OdometryData(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)

    private val mNotifier = Notifier {
        synchronized(byteBuffer) {
            try {
                val bytes = serialPort.read(kBufferCapacity)
                byteBuffer.clear()
                byteBuffer.put(bytes)

            } catch (e: Exception) {
                serialPort.close()

                serialPort =
                    SerialPort(115200, SerialPort.Port.kUSB, 8, SerialPort.Parity.kNone, SerialPort.StopBits.kOne)
            }
        }
    }

    val position: Pose2d
        get() {
            return Pose2d(
                Translation2d(odometryData.positionX.toDouble(), odometryData.positionY.toDouble()),
                Rotation2d(odometryData.rotationHeading.toDouble())
            )
        }

    val velocity: Pose2d
        get() {
            return Pose2d(
                Translation2d(odometryData.velocityX.toDouble(), odometryData.velocityY.toDouble()),
                Rotation2d(odometryData.velocityRotationHeading.toDouble())
            )
        }

    init {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
        mNotifier.startPeriodic(0.01)
    }


    override fun close() {
        serialPort.close()
        mNotifier.close()
    }
}

private data class OdometryData(
    val positionX: Float,
    val positionY: Float,
    val rotationHeading: Float,
    val velocityX: Float,
    val velocityY: Float,
    val velocityRotationHeading: Float
)
