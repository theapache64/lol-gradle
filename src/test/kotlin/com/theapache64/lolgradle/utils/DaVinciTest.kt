package com.theapache64.lolgradle.utils

import org.junit.Test
import java.io.File


/**
 * Created by theapache64 : May 26 Tue,2020 @ 13:04
 */
class DaVinciTest {
    @Test
    fun `Draw modern`() {
        val inputFile = File("src/test/resources/da_vinci_sample_input.png")
        val outputFile = File("temp/da_vinci_sample_output.png")
        val daVinci = DaVinci(inputFile)
        daVinci.drawModern("Hello World!!", outputFile)
        open(outputFile, 2)
    }

    private fun open(outputFile: File, openForInSec: Int) {
        val process = Runtime.getRuntime().exec(
            arrayOf("eog", outputFile.absolutePath)
        )
        Thread.sleep(openForInSec * 1000L)
        process.destroy()
    }
}