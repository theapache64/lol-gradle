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
        const val PLUGIN_NAME = "lol-gradle"
        const val TASK_CAPTURE = "capture"
        private const val DEFAULT_WAIT_IN_SEC = 5L
    }

    override fun apply(project: Project) {

        val ext = project.extensions.create("lolGradle", LolGradlePluginExt::class.java)

        project.task(TASK_CAPTURE) {
            it.doLast {
                capture(ext, project)
            }
        }
    }

    private fun capture(ext: LolGradlePluginExt, project: Project) {

        IS_LOGGER_ENABLED = ext.isLoggingEnabled


        println("Capturing lolpic...")
        // Getting timeout value
        val timeout = when (ext.lolPicStrategy) {
            LolGradlePluginExt.Strategy.NONE,
            LolGradlePluginExt.Strategy.FAIL -> TimeUnit.SECONDS.toMillis(DEFAULT_WAIT_IN_SEC)
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

            val dirName = ext.dirName ?: project.name
            val outputDir = ext.outputDir ?: "${System.getProperty("user.home")}/$PLUGIN_NAME/$dirName"

            val imageFile = File("$outputDir/${System.currentTimeMillis()}.png")

            if (!imageFile.parentFile.exists()) {
                require(imageFile.parentFile.mkdirs()) { "Failed to create parent directory" }
            }

            ImageIO.write(defaultCam.image, "PNG", imageFile)
        }
    }
}