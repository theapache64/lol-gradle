package com.theapache64.lolgradle

import com.github.sarxos.webcam.Webcam
import com.theapache64.lolgradle.utils.IS_LOGGER_ENABLED
import com.theapache64.lolgradle.utils.log
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.imageio.ImageIO
import javax.inject.Inject

/**
 * Created by theapache64 : May 24 Sun,2020 @ 11:41
 */
class LolGradlePlugin : Plugin<Project> {


    companion object {
        const val CONFIG_NAME = "lolGradle"
        const val PLUGIN_NAME = "lol-gradle"
        const val TASK_CAPTURE = "capture"
    }

    @Inject
    lateinit var lolGradleViewModel: LolGradleViewModel

    override fun apply(project: Project) {
        DaggerLolGradleComponent.builder().build().inject(this)
        lolGradleViewModel.init(project)
    }
}