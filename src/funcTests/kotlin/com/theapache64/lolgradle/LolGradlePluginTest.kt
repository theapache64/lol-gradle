@file:Suppress("FunctionName")

package com.theapache64.lolgradle

import com.theapache64.expekt.should
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.*
import org.junit.rules.TemporaryFolder
import java.io.File

/**
 * Created by theapache64 : May 24 Sun,2020 @ 12:51
 * To pass this test, a camera should be connected to the system.
 */
class LolGradlePluginTest {


    companion object {
        private lateinit var process: Process

        @BeforeClass
        @JvmStatic
        fun beforeClass() {
            println("Cam opened")
            process = Runtime.getRuntime().exec("droidcam-cli 192.168.1.101 4747")
        }

        @AfterClass
        @JvmStatic
        fun afterClass() {
            println("Cam closed")
            process.destroy()
        }

    }

    @get:Rule
    val testProjectDir = TemporaryFolder()


    @Test
    fun `check test setup`() {
        val gradleRunner = getRunner(
            """
            plugins {
                id 'java'
                id 'com.theapache64.lol-gradle'
            }
        """.trimIndent()
        )
        val result = gradleRunner.withArguments("tasks")
            .build()

        println(result.output)
    }

    private fun getRunner(gradleFileContent: String): GradleRunner {
        val buildGradleFile = testProjectDir.newFile("build.gradle")

        // Adding lol-gradle plugin
        buildGradleFile.appendText(gradleFileContent)

        return GradleRunner.create()
            .withPluginClasspath()
            .withProjectDir(testProjectDir.root)
            .withTestKitDir(testProjectDir.newFolder())
    }

    private fun assertOutputDirHasOneLolPic(
        outputDir: File,
        gradleRunner: GradleRunner,
        taskName: String = LolGradleViewModel.TASK_CAPTURE,
        taskOutcome: TaskOutcome = TaskOutcome.SUCCESS
    ): File {

        outputDir.deleteRecursively()

        val result = gradleRunner
            .withArguments(taskName)
            .build()

        result.task(":$taskName")!!.outcome.should.equal(taskOutcome)
        result.output.should.contain(LolGradleViewModel.MSG_WEBCAM_FOUND)
        outputDir.listFiles().should.not.`null`
        outputDir.listFiles()?.size.should.equal(1)
        return outputDir.listFiles()!!.first()
    }

    @Test
    fun `Capture success`() {

        val gradleRunner = getRunner(
            """
            plugins {
                id 'java'
                id 'com.theapache64.lol-gradle'
            }
        """.trimIndent()
        )

        val outputDir = File("${System.getProperty("user.home")}/lol-gradle/${gradleRunner.projectDir.name}")
        assertOutputDirHasOneLolPic(outputDir, gradleRunner)
    }

    @Test
    fun `Dir name config`() {
        val dirName = "MyLolPicsz"
        val gradleRunner = getRunner(
            """
            plugins {
                id 'java'
                id 'com.theapache64.lol-gradle'
            }
            
            lolGradle {
                dirName = "$dirName"    
            }
        """.trimIndent()
        )

        val outputDir = File("${System.getProperty("user.home")}/lol-gradle/$dirName")
        val image = assertOutputDirHasOneLolPic(outputDir, gradleRunner)
        image.parentFile.name.should.equal(dirName)
    }

    @Test
    fun `Output path config`() {
        val outputPath = "${System.getProperty("user.home")}/Desktop/MyLolPicsAtDesktop"
        val gradleRunner = getRunner(
            """
            plugins {
                id 'java'
                id 'com.theapache64.lol-gradle'
            }
            
            lolGradle {
                outputDir = "$outputPath"    
            }
        """.trimIndent()
        )

        val outputDir = File(outputPath)
        val image = assertOutputDirHasOneLolPic(outputDir, gradleRunner)
        image.parentFile.absolutePath.should.equal(outputPath)
    }


    @Test
    fun `Capture on`() {
        val gradleRunner = getRunner(
            """
            plugins {
                id 'java'
                id 'com.theapache64.lol-gradle'
            }
            
            lolGradle{
                captureOn = ["clean"]    
            }
        """.trimIndent()
        )

        val outputDir = File("${System.getProperty("user.home")}/lol-gradle/${gradleRunner.projectDir.name}")
        assertOutputDirHasOneLolPic(
            outputDir,
            gradleRunner,
            "clean",
            TaskOutcome.UP_TO_DATE
        )
    }

    @Test
    fun `Impact style`() {
        val outputPath = "${System.getProperty("user.home")}/Desktop/MyLolPicsAtDesktop"
        val gradleRunner = getRunner(
            """
            plugins {
                id 'java'
                id 'com.theapache64.lol-gradle'
            }
            
            lolGradle {
                style = "IMPACT"   
            }
        """.trimIndent()
        )

        val outputDir = File(outputPath)
        val image = assertOutputDirHasOneLolPic(outputDir, gradleRunner)
        image.name.should.contain("impact")
    }

}