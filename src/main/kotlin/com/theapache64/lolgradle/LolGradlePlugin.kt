package com.theapache64.lolgradle

import com.github.sarxos.webcam.Webcam
import com.theapache64.lolgradle.utils.IS_LOGGER_ENABLED
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by theapache64 : May 24 Sun,2020 @ 11:41
 */
class LolGradlePlugin : Plugin<Project> {

    companion object {
        const val TASK_CAPTURE = "capture"
    }

    override fun apply(project: Project) {

        val ext = project.extensions.create("lol-gradle-plugin-ext", LolGradlePluginExt::class.java)
        IS_LOGGER_ENABLED = ext.isLogging

        project.task(TASK_CAPTURE) {
            it.doLast {
                capture()
            }
        }
    }

    private fun capture() {
        println("Capturing lolpic...")
        val webCams = Webcam.getWebcams()
        println("${webCams.size} webcam(s) available")
    }
}