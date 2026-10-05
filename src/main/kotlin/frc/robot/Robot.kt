package frc.robot

import edu.wpi.first.hal.FRCNetComm
import edu.wpi.first.hal.HAL
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.CommandScheduler
import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.util.WPILibVersion
import edu.wpi.first.wpilibj2.command.Commands

object Robot : TimedRobot() {
    private var autonomousCommand = Commands.none()
    private val robotContainer = RobotContainer()
    
    init {
        HAL.report(
            FRCNetComm.tResourceType.kResourceType_Language,
            FRCNetComm.tInstances.kLanguage_Kotlin,
            0,
            WPILibVersion.Version
        )
    }

    override fun robotPeriodic() {
        CommandScheduler.getInstance().run()
    }

    override fun disabledInit() {}

    override fun disabledPeriodic() {}

    override fun disabledExit() {}

    override fun autonomousInit() {
        autonomousCommand = robotContainer.autonomousCommand
        
        CommandScheduler.getInstance().schedule(autonomousCommand)
    }

    override fun autonomousPeriodic() {}

    override fun autonomousExit() {}

    override fun teleopInit() {
        CommandScheduler.getInstance().cancel(autonomousCommand)
    }

    override fun teleopPeriodic() {}

    override fun teleopExit() {}

    override fun testInit() {
        CommandScheduler.getInstance().cancelAll()
    }

    override fun testPeriodic() {}

    override fun testExit() {}
}
