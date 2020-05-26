package com.theapache64.lolgradle.utils

import java.awt.BasicStroke
import java.awt.Color
import java.awt.Font
import java.awt.RenderingHints
import java.io.File
import java.io.InputStream
import java.lang.Math.sqrt
import javax.imageio.ImageIO

/**
 * Created by theapache64 : May 26 Tue,2020 @ 13:04
 */

class DaVinci(
    private val inputFile: File
) {

    companion object {
        private val transBlack by lazy { Color(0, 0, 0, 180) }
        private val robotoRegular by lazy {
            val fontRes = DaVinci::class.java.getResourceAsStream("/roboto_thin.ttf")
            Font.createFont(Font.TRUETYPE_FONT, fontRes)
        }

        private const val WIDTH = 640
        private const val HEIGHT = 480
        private val DIAGONAL = kotlin.math.sqrt((WIDTH * WIDTH + HEIGHT * HEIGHT).toDouble())
        private const val FONT_SIZE = 30

        private fun calcFontSize(videoWidth: Int, videoHeight: Int): Float {
            return (kotlin.math.sqrt((videoWidth * videoWidth + videoHeight * videoHeight).toDouble()) / DIAGONAL * FONT_SIZE).toFloat()
        }
    }

    private val input = ImageIO.read(inputFile)

    /**
     * To draw modern style on the given image
     */
    fun drawModern(text: String, outputFile: File) {

        // Canvas
        val canvas = input.createGraphics()
        canvas.setRenderingHint(
            RenderingHints.KEY_FRACTIONALMETRICS,
            RenderingHints.VALUE_FRACTIONALMETRICS_ON
        );
        canvas.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB
        );

        // Draw transparent color
        canvas.paint = transBlack
        canvas.fillRect(0, 0, input.width, input.height)

        // Draw text
        canvas.paint = Color.WHITE
        val fontSize = calcFontSize(input.width, input.height)
        canvas.font = robotoRegular.deriveFont(Font.PLAIN, fontSize)
        val fm = canvas.fontMetrics
        val tx = (input.width / 2) - (fm.stringWidth(text) / 2)
        val ty = (input.height / 2) + (fm.height / 2)
        canvas.drawString(text, tx, ty)
        canvas.dispose()

        outputFile.delete()
        if (!outputFile.parentFile.exists()) {
            outputFile.parentFile.mkdirs()
        }

        ImageIO.write(input, "PNG", outputFile)
    }
}