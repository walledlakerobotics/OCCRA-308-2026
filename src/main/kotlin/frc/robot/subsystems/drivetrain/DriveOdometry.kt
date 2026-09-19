package frc.robot.subsystems.drivetrain

import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import java.io.BufferedInputStream
import java.io.FileInputStream


class DriveOdometry(port: String) {

    // data as they come
    private val mPortBufferStream = BufferedInputStream(FileInputStream(port));

    // buffer tempory memory
    public val bufferedData: String
        get() = mPortBufferStream.read().toString()

    public val pose: Pose2d
        get() {
            val data = bufferedData

            // this should send over a json dataset.

            println(data)


            return Pose2d()
        }

    init {
        Shuffleboard.getTab("Odometry")

        // gui stuff



    }






}