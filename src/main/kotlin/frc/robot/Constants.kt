package frc.robot

import com.revrobotics.spark.config.SparkMaxConfig

class Constants {

    public class DriveConstants {
        public final val kFrontLeftMotorCANID = 0;
        public final val kFrontRightMotorCANID = 1;
        public final val kBackLeftMotorCANID = 2;
        public final val kBackRightMotorCANID = 3;

        public val kDriveNeoConfig = SparkMaxConfig().closedLoop.maxMotion
    }
}