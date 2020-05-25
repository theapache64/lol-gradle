package com.theapache64.lolgradle

import com.github.sarxos.webcam.Webcam
import com.theapache64.lolgradle.utils.IS_LOGGER_ENABLED
import com.theapache64.lolgradle.utils.log
import org.gradle.api.Project
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.imageio.ImageIO
import javax.inject.Inject

/**
 * Created by theapache64 : May 25 Mon,2020 @ 11:42
 */
class LolGradleViewModel @Inject constructor() {

    private lateinit var project: Project
    private lateinit var ext: LolGradlePluginExt

    companion object {
        private const val DEFAULT_WAIT_IN_SEC = 5L
        const val MSG_WEBCAM_FOUND = "Webcam available"
        const val CONFIG_NAME = "lolGradle"
        const val PLUGIN_NAME = "lol-gradle"
        const val TASK_CAPTURE = "capture"
    }

    fun init(project: Project) {
        this.project = project
        this.ext = project.extensions.create(CONFIG_NAME, LolGradlePluginExt::class.java)

        // Define capture task
        project.task(TASK_CAPTURE) {
            it.doLast {
                capture()
            }
        }

        // Defining when capture task should be executed
        ext.captureOn.forEach { taskName ->
            project.tasks.getByName(taskName).dependsOn(TASK_CAPTURE)
        }

    }

    private fun capture() {

        IS_LOGGER_ENABLED = ext.isLoggingEnabled

        log("Capturing lolpic...")

        // Getting timeout value
        val timeout = when (ext.lolPicStrategy) {
            LolGradlePluginExt.Strategy.NONE,
            LolGradlePluginExt.Strategy.FAIL -> TimeUnit.SECONDS.toMillis(DEFAULT_WAIT_IN_SEC)
            LolGradlePluginExt.Strategy.WAIT -> TimeUnit.SECONDS.toMillis(ext.waitTimeInSec)
            LolGradlePluginExt.Strategy.WAIT_FOREVER -> Long.MAX_VALUE
        }

        val webCam = Webcam.getDefault(timeout)
        log(MSG_WEBCAM_FOUND)

        if (webCam == null) {
            if (ext.lolPicStrategy == LolGradlePluginExt.Strategy.FAIL) {
                throw IOException("No cam found.")
            }
        } else {

            // cam found
            webCam.open()

            val dirName = ext.dirName ?: project.name
            val outputDir =
                ext.outputDir ?: "${System.getProperty("user.home")}/${PLUGIN_NAME}/$dirName"

            val imageFile = File("$outputDir/${System.currentTimeMillis()}.png")

            if (!imageFile.parentFile.exists()) {
                require(imageFile.parentFile.mkdirs()) { "Failed to create parent directory" }
            }

            ImageIO.write(webCam.image, "PNG", imageFile)
            webCam.close()
        }
    }


}