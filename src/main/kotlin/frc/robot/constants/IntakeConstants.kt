package frc.robot.constants

import com.revrobotics.spark.config.SparkMaxConfig

const val kMotorCANID = 9

val kIntakeMotorConfig: SparkMaxConfig
    get() {
        val config = SparkMaxConfig()

        config.smartCurrentLimit(45)
        config.closedLoop.maxOutput(1.0)

        config.closedLoop.pid(2.0, 1.0, 2.0)

        return config
    }