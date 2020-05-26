package com.theapache64.lolgradle

import com.github.sarxos.webcam.Webcam
import kotlinx.coroutines.*
import org.junit.Test
import java.io.File
import javax.imageio.ImageIO

/**
 * Created by theapache64 : May 26 Tue,2020 @ 10:59
 */

class WebcamTest {
    @Test
    fun `Timeout test`() {
        val thread = Thread {
            val webcam = Webcam.getDefault(2000)
            println("Opening cam")
            webcam.open()

            println("Cam opened")
            ImageIO.write(webcam.image, "PNG", File("sample_1.png"))
            println("Cam done!")
        }
        thread.start()
        thread.join(5000)
        println("Wait done")
    }
}
