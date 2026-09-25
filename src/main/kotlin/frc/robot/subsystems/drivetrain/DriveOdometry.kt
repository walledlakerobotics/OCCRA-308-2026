package frc.robot.subsystems.drivetrain

import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.wpilibj.SerialPort
import java.io.ByteArrayInputStream
import java.nio.ByteBuffer


class DriveOdometry(portDir: String) : AutoCloseable {

    // TODO: would I need to use the flush function depending on if I reading a float from the serial, also create pose2d.

    // port serial instance
    private val mSerialPort = SerialPort(115200, SerialPort.Port.kUSB, 8, SerialPort.Parity.kNone, SerialPort.StopBits.kOne)

    // byte stream reading the bytes and returns the
    private val mByteStream: ByteArrayInputStream
        get() {
            return mSerialPort.read(Float.SIZE_BYTES * 6).inputStream()
        }

    // gets the buffer
    val dataBuffer: ByteBuffer
        get() {
            val bytes = mByteStream.readNBytes(Float.SIZE_BYTES)

            return ByteBuffer.wrap(bytes)
        }

    val position: Pose2d
        get() {
            val buffer = dataBuffer

            val x =  buffer.getFloat(0)
            val y = buffer.getFloat(1)
            val rotation = buffer.getFloat(2)

            return Pose2d()
        }

    val velocity: Pose2d
        get() {
            val buffer = dataBuffer

            val x = buffer.getFloat(3)
            val y = buffer.getFloat(4)
            val rotation = buffer.getFloat(5)

            return Pose2d()
        }


    override fun close() {
        mSerialPort.close()
    }
}