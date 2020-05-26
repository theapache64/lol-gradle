package com.theapache64.lolgradle.utils

import org.junit.Test
import java.io.File


/**
 * Created by theapache64 : May 26 Tue,2020 @ 13:04
 */
class DaVinciTest {
    @Test
    fun `Draw hello world`() {
        val imageFile = File("src/test/resources/davinci_sample_input.png")
        println(imageFile.exists())
    }
}