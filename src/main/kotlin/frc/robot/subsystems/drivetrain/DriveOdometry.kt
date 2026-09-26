package frc.robot.subsystems.drivetrain

import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.geometry.Translation2d
import edu.wpi.first.wpilibj.Notifier
import edu.wpi.first.wpilibj.SerialPort
import java.io.ByteArrayInputStream
import java.nio.ByteBuffer


class DriveOdometry(portDir: String) : AutoCloseable {
    // port serial instance
    private val mSerialPort = SerialPort(115200, SerialPort.Port.kUSB, 8, SerialPort.Parity.kNone, SerialPort.StopBits.kOne)

    private var mByteBuffer: ByteBuffer = ByteBuffer.allocate(0)

    private val mNotifier = Notifier({
        synchronized(mByteBuffer) {
            val bytes = mSerialPort.read(Float.SIZE_BYTES * 6)
            mByteBuffer = ByteBuffer.wrap(bytes)
        }
    })

    init {
        mNotifier.startPeriodic(0.01)
    }

     val position: Pose2d
        get() {
            var x = 0.0f
            var y = 0.0f
            var rotation = 0.0f

            synchronized(mByteBuffer) {
                val buffer = mByteBuffer

                x =  buffer.getFloat(0)
                y = buffer.getFloat(1)
                rotation = buffer.getFloat(2)
            }


            return Pose2d(Translation2d(x.toDouble(), y.toDouble()), Rotation2d(rotation.toDouble()))
        }

    val velocity: Pose2d
        get() {
            var x = 0.0f
            var y = 0.0f
            var rotation = 0.0f

            synchronized(mByteBuffer) {
                val buffer = mByteBuffer

                x =  buffer.getFloat(3)
                y = buffer.getFloat(4)
                rotation = buffer.getFloat(5)
            }

            return Pose2d(Translation2d(x.toDouble(), y.toDouble()), Rotation2d(rotation.toDouble()))
        }

    override fun close() {
        mSerialPort.close()
        mNotifier.close()
    }
}