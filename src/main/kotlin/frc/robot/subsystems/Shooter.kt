package frc.robot.subsystems

import com.ctre.phoenix6.controls.VelocityDutyCycle
import com.ctre.phoenix6.hardware.TalonFX
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.constants.kShootCANID
import frc.robot.constants.kShooterConfig
import frc.robot.constants.kShootingVelocity

class Shooter : SubsystemBase() {
    
    private val shooterMotor = TalonFX(kShootCANID)
    
    init {
        shooterMotor.configurator.apply(kShooterConfig)
    }
     
    fun shoot(velocity: Double) {
        val request = VelocityDutyCycle(velocity)
        
        shooterMotor.setControl(request)
    }
    
    fun shoot(): Command {
        return runEnd({
            shoot(kShootingVelocity)           
        }, {
            stop()
        })
    }
    
    fun stop() {
        shoot(0.0)
    }

}