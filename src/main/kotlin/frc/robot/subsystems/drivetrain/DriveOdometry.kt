package frc.robot.subsystems.drivetrain

import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.geometry.Translation2d
import edu.wpi.first.wpilibj.Notifier
import edu.wpi.first.wpilibj.SerialPort
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import frc.robot.constants.kBufferCapacity
import frc.robot.constants.kStartingFrameSignature
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.concurrent.locks.ReentrantLock


class DriveOdometry : AutoCloseable {


    // TODO: Need to make a sum check

    // port serial instance
    private var serialPort =
        SerialPort(115200, SerialPort.Port.kUSB, 8, SerialPort.Parity.kNone, SerialPort.StopBits.kOne)

    private var byteBuffer: ByteBuffer = ByteBuffer.allocate(kBufferCapacity) // need to get the size of struct 

    private var odometryData: OdometryData = OdometryData(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    private val odometryDataLock: ReentrantLock = ReentrantLock()

    // updates in seprate thread in different intervals
    private val notifier = Notifier {

        synchronized(byteBuffer) {
            try {
                // getting data
                val bytes = serialPort.read(kBufferCapacity)
                byteBuffer.clear()
                byteBuffer.put(bytes)

            } catch (e: Exception) {

                // reassigns the port, and prevents memory leak
                serialPort.close()

                serialPort =
                    SerialPort(115200, SerialPort.Port.kUSB, 8, SerialPort.Parity.kNone, SerialPort.StopBits.kOne)
            }
        }

        synchronized(odometryDataLock) {
            var signature = byteBuffer.getInt()

            // offsets the buffer until it finds the starting point. 
            while (signature != kStartingFrameSignature && byteBuffer.remaining() >= Int.SIZE_BYTES) {
                byteBuffer.position(byteBuffer.position() + 1)
                signature = byteBuffer.getInt()
            }

            // gets data
            val x = byteBuffer.getFloat()
            val y = byteBuffer.getFloat()
            val r = byteBuffer.getFloat()

            val vx = byteBuffer.getFloat()
            val vy = byteBuffer.getFloat()
            val vr = byteBuffer.getFloat()

            odometryData = OdometryData(x, y, r, vx, vy, vr)
        }


    }

    val position: Pose2d
        get() {
            var pose: Pose2d

            synchronized(odometryDataLock) {
                pose = Pose2d(
                    Translation2d(odometryData.positionX.toDouble(), odometryData.positionY.toDouble()),
                    Rotation2d(odometryData.rotationHeading.toDouble())
                )
            }

            return pose
        }

    val velocity: Pose2d
        get() {
            var pose: Pose2d

            synchronized(odometryDataLock) {
                pose = Pose2d(
                    Translation2d(odometryData.velocityX.toDouble(), odometryData.velocityY.toDouble()),
                    Rotation2d(odometryData.velocityRotationHeading.toDouble())
                )
            }

            return pose
        }

    init {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
        notifier.startPeriodic(0.01)

        val tab = Shuffleboard.getTab("Odometry")

        tab.addDouble("X-Position") {
            position.x
        }

        tab.addDouble("Y-Position") {
            position.y
        }

        tab.addDouble("Rotation-Deg") {
            position.rotation.degrees
        }

        tab.addDouble("X-Velocity") {
            velocity.x
        }

        tab.addDouble("Y-Velocity") {
            velocity.y
        }

        tab.addDouble("Rotation-Velocity-Deg") {
            velocity.rotation.degrees
        }

    }

    override fun close() {
        serialPort.close()
        notifier.close()
    }
}

private data class OdometryData(
    val positionX: Float,
    val positionY: Float,
    val rotationHeading: Float,
    val velocityX: Float,
    val velocityY: Float,
    val velocityRotationHeading: Float
) {
    fun sumData(): Float {
        return positionX + positionY + rotationHeading +
                velocityX + velocityY + velocityRotationHeading
    }
}


