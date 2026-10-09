package frc.robot.constants

import com.revrobotics.spark.config.SparkBaseConfig
import com.revrobotics.spark.config.SparkMaxConfig
import edu.wpi.first.units.Units
import edu.wpi.first.units.measure.Distance
import kotlin.math.PI

const val kMotorCANID = 9

// meters per sec
val kIntakeVelocity = 1.0

private val kDiameter = Distance.ofBaseUnits(0.0, Units.Millimeter)


val kIntakeMotorConfig: SparkMaxConfig
    get() {
        val config = SparkMaxConfig()

        config.smartCurrentLimit(45)

        config.closedLoop
            .pid(0.45, 0.0, 0.12)
            .maxOutput(1.0)

        config.idleMode(SparkBaseConfig.IdleMode.kCoast)

        config.encoder
            .velocityConversionFactor(PI)
            .positionConversionFactor(PI)

        return config
    }