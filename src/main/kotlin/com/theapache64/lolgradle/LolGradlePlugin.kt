package com.theapache64.lolgradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

/**
 * Created by theapache64 : May 24 Sun,2020 @ 11:41
 */
class LolGradlePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val dir = File(System.getProperty("user.home"))
        println("home is ${dir.absolutePath}")
    }
}