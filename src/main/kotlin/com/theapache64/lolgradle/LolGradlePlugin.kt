package com.theapache64.lolgradle

import com.github.sarxos.webcam.Webcam
import com.theapache64.lolgradle.utils.IS_LOGGER_ENABLED
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.imageio.ImageIO

/**
 * Created by theapache64 : May 24 Sun,2020 @ 11:41
 */
class LolGradlePlugin : Plugin<Project> {


    companion object {
        const val TASK_CAPTURE = "capture"
    }

    override fun apply(project: Project) {

        project.task(TASK_CAPTURE) {
            it.doLast {
                capture(project)
            }
        }
    }

    private fun capture(project: Project) {

        val ext = project.extensions.create("lol-gradle", LolGradlePluginExt::class.java)
        IS_LOGGER_ENABLED = ext.isLoggingEnabled

        println("Capturing lolpic...")
        val timeout = when (ext.lolPicStrategy) {
            LolGradlePluginExt.Strategy.NONE, LolGradlePluginExt.Strategy.FAIL -> TimeUnit.SECONDS.toMillis(2)
            LolGradlePluginExt.Strategy.WAIT -> TimeUnit.SECONDS.toMillis(ext.waitTimeInSec)
            LolGradlePluginExt.Strategy.WAIT_FOREVER -> Long.MAX_VALUE
        }

        val webCams = Webcam.getWebcams(timeout)
        println("${webCams.size} webcam(s) available")

        if (webCams.isEmpty()) {
            if (ext.lolPicStrategy == LolGradlePluginExt.Strategy.FAIL) {
                throw IOException("No cam found.")
            }

        } else {
            // cam found
            val defaultCam = webCams.first()
            defaultCam.open()
            val imageFile =
                File("${System.getProperty("user.home")}/lol-gradle/${project.name}/${System.currentTimeMillis()}.png")

            if (!imageFile.parentFile.exists()) {
                require(imageFile.parentFile.mkdirs()) { "Failed to create parent directory" }
            }
            ImageIO.write(defaultCam.image, "PNG", imageFile)
        }
    }
}