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

    companion object {
        private const val DEFAULT_WAIT_IN_SEC = 5L
    }

    fun capture(ext: LolGradlePluginExt, project: Project) {

        IS_LOGGER_ENABLED = ext.isLoggingEnabled


        log("Capturing lolpic...")
        // Getting timeout value
        val timeout = when (ext.lolPicStrategy) {
            LolGradlePluginExt.Strategy.NONE,
            LolGradlePluginExt.Strategy.FAIL -> TimeUnit.SECONDS.toMillis(DEFAULT_WAIT_IN_SEC)
            LolGradlePluginExt.Strategy.WAIT -> TimeUnit.SECONDS.toMillis(ext.waitTimeInSec)
            LolGradlePluginExt.Strategy.WAIT_FOREVER -> Long.MAX_VALUE
        }

        val webCams = Webcam.getWebcams(timeout)
        log("${webCams.size} webcam(s) available")

        if (webCams.isEmpty()) {
            if (ext.lolPicStrategy == LolGradlePluginExt.Strategy.FAIL) {
                throw IOException("No cam found.")
            }

        } else {

            // cam found
            val defaultCam = webCams.first()
            defaultCam.open()

            val dirName = ext.dirName ?: project.name
            val outputDir =
                ext.outputDir ?: "${System.getProperty("user.home")}/${LolGradlePlugin.PLUGIN_NAME}/$dirName"

            val imageFile = File("$outputDir/${System.currentTimeMillis()}.png")

            if (!imageFile.parentFile.exists()) {
                require(imageFile.parentFile.mkdirs()) { "Failed to create parent directory" }
            }

            ImageIO.write(defaultCam.image, "PNG", imageFile)
        }
    }
}