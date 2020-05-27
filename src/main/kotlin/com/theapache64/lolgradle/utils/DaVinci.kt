package com.theapache64.lolgradle.utils

import java.awt.*
import java.awt.geom.Point2D
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.imageio.ImageIO


/**
 * Created by theapache64 : May 26 Tue,2020 @ 13:04
 */

class DaVinci(
    inputFile: File
) {

    companion object {
        private val transBlack by lazy { Color(0, 0, 0, 150) }
        private val fontRobotoRegular by lazy {
            val fontRes = DaVinci::class.java.getResourceAsStream("/roboto_thin.ttf")
            Font.createFont(Font.TRUETYPE_FONT, fontRes)
        }

        private val fontImpact by lazy {
            val fontRes = DaVinci::class.java.getResourceAsStream("/impact.ttf")
            Font.createFont(Font.TRUETYPE_FONT, fontRes)
        }


        private const val WIDTH = 640
        private const val HEIGHT = 480
        private val DIAGONAL = kotlin.math.sqrt((WIDTH * WIDTH + HEIGHT * HEIGHT).toDouble())
        private val dateFormat = SimpleDateFormat("EEE, MMM dd, YYYY h:mm a")
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

        // Title font size
        val titleFontSize = calcFontSize(30)
        canvas.font = fontRobotoRegular.deriveFont(Font.PLAIN, titleFontSize)
        val titleFm = canvas.fontMetrics
        val titleX = (input.width / 2) - (titleFm.stringWidth(text) / 2)
        val titleY = (input.height / 2) + (titleFm.height / 2)
        canvas.drawString(text, titleX, titleY)

        // Date font size
        val dateFontSize = calcFontSize(17)
        canvas.font = fontRobotoRegular.deriveFont(Font.PLAIN, dateFontSize)
        val dateFm = canvas.fontMetrics
        val dateText = dateFormat.format(Date())
        val dateX = (input.width / 2) - (dateFm.stringWidth(dateText) / 2)
        val dateY = input.height - dateFm.height
        canvas.drawString(dateText, dateX, dateY)

        canvas.dispose()

        if (!outputFile.parentFile.exists()) {
            outputFile.parentFile.mkdirs()
        }

        ImageIO.write(input, "PNG", outputFile)
    }

    private fun calcFontSize(fontSize: Int): Float {
        val imageWidth = input.width
        val imageHeight = input.height
        return (kotlin.math.sqrt((imageWidth * imageWidth + imageHeight * imageHeight).toDouble()) / DIAGONAL * fontSize).toFloat()
    }

    fun drawImpact(text: String, outputFile: File) {

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

        // Draw text
        canvas.paint = Color.WHITE

        // Title font size
        val titleFontSize = calcFontSize(30)
        canvas.font = fontImpact.deriveFont(Font.PLAIN, titleFontSize)
        val titleFm = canvas.fontMetrics
        val titleX = 10
        val titleY = titleFm.height
        canvas.drawString(text, titleX, titleY)

        // TODO : Add stroke

        canvas.dispose()

        if (!outputFile.parentFile.exists()) {
            outputFile.parentFile.mkdirs()
        }

        ImageIO.write(input, "PNG", outputFile)
    }
}