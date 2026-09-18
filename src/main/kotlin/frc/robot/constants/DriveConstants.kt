package frc.robot.constants

import com.revrobotics.spark.config.SparkBaseConfig
import com.revrobotics.spark.config.SparkMaxConfig
import edu.wpi.first.units.Units
import edu.wpi.first.units.measure.Distance
import edu.wpi.first.units.measure.LinearVelocity

// TODO: configure the Neos and PID tune them, also config trapezoidal profiling using MAXMotion (:3).

val kDistanceFromCenter: Distance = Distance.ofBaseUnits(0.0, Units.Meters)

const val kFrontLeftMotorCANID = 0
const val kFrontRightMotorCANID = 1
const val kBackLeftMotorCANID = 2
const val kBackRightMotorCANID = 3

val kSparkMaxConfig: SparkMaxConfig
    get() {
        val config = SparkMaxConfig()

        // pid control
        config.closedLoop.pid(1.0, 0.0, 1.0)
        config.closedLoop.maxOutput(0.8) // the max dutycycle coming from PID

        config.idleMode(SparkBaseConfig.IdleMode.kCoast)
        config.smartCurrentLimit(45) // amps

        return config
    }