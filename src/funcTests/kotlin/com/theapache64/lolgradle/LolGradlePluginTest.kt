@file:Suppress("FunctionName")

package com.theapache64.lolgradle


import com.winterbe.expekt.should
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

/**
 * Created by theapache64 : May 24 Sun,2020 @ 12:51
 */
class LolGradlePluginTest {


    @get:Rule
    val testProjectDir = TemporaryFolder()

    private lateinit var buildGradleFile: File
    private lateinit var gradleRunner: GradleRunner

    @Before
    fun setUp() {
        buildGradleFile = testProjectDir.newFile("build.gradle")

        // Adding lol-gradle plugin
        buildGradleFile.appendText(
            """
            plugins {
                id 'java'
                id 'com.theapache64.lol-gradle'
            }
        """.trimIndent()
        )

        gradleRunner = GradleRunner.create()
            .withPluginClasspath()
            .withProjectDir(testProjectDir.root)
            .withTestKitDir(testProjectDir.newFolder())
    }

    @Test
    fun `check test setup`() {
        val result = gradleRunner.withArguments("tasks")
            .build()

        println(result.output)
    }

    @Test
    fun `Capture`() {
        val result = gradleRunner
            .withArguments(LolGradlePlugin.TASK_CAPTURE)
            .build()

        result.task(":${LolGradlePlugin.TASK_CAPTURE}")!!.outcome.should.equal(TaskOutcome.SUCCESS)
        result.output.should.contain("webcam(s) available")
    }
}