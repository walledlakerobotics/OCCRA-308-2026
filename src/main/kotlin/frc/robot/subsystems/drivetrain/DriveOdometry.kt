package frc.robot.subsystems.drivetrain

import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.geometry.Translation2d
import edu.wpi.first.wpilibj.Notifier
import edu.wpi.first.wpilibj.SerialPort
import java.nio.ByteBuffer
import java.nio.ByteOrder


class DriveOdometry(portDir: String) : AutoCloseable {
    // port serial instance
    private var mSerialPort = SerialPort(115200, SerialPort.Port.kUSB, 8, SerialPort.Parity.kNone, SerialPort.StopBits.kOne)
    private var mByteBuffer: ByteBuffer = ByteBuffer.allocate(0)

    private val mNotifier = Notifier({
        synchronized(mByteBuffer) {
            try {
                val bytes = mSerialPort.read(Float.SIZE_BYTES * 6)
                mByteBuffer.clear()
                mByteBuffer.put(bytes)
            } catch (e: Exception) {
                println("data failed to read.")

                while (mSerialPort.bytesReceived > 0) {
                    mSerialPort.close()
                    mSerialPort = SerialPort(115200, SerialPort.Port.kUSB, 8, SerialPort.Parity.kNone, SerialPort.StopBits.kOne)

                    println("resetting")

                    Thread.sleep(10)
                }

            }
        }
    })

    init {
        mByteBuffer.order(ByteOrder.LITTLE_ENDIAN)
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