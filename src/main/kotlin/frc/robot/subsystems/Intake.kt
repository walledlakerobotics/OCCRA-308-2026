package frc.robot.subsystems

import com.revrobotics.PersistMode
import com.revrobotics.ResetMode
import com.revrobotics.spark.SparkBase
import com.revrobotics.spark.SparkLowLevel
import com.revrobotics.spark.SparkMax
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.constants.kMotorCANID
import frc.robot.constants.kIntakeMotorConfig


class Intake : SubsystemBase() {

    val intakeMotor = SparkMax(kMotorCANID, SparkLowLevel.MotorType.kBrushless)

    init {
        intakeMotor.configure(kIntakeMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters)
    }

    fun intake(v: () -> Double) {
        intakeMotor.closedLoopController.setSetpoint(v(), SparkBase.ControlType.kVelocity)
    }

    fun stop() {
        intake { 0.0 }
    }

}