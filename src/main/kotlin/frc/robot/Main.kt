@file:JvmName("Main")

package frc.robot

import edu.wpi.first.hal.FRCNetComm
import edu.wpi.first.hal.HAL
import edu.wpi.first.wpilibj.RobotBase

fun main(args: Array<String>) {
    RobotBase.startRobot {
        HAL.report(FRCNetComm.tResourceType.kResourceType_Language, FRCNetComm.tInstances.kLanguage_Kotlin)
        Robot()
    }
}