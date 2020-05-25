import com.github.sarxos.webcam.Webcam
import java.io.File
import javax.imageio.ImageIO

/**
 * Created by theapache64 : May 25 Mon,2020 @ 12:26
 */

fun main(args: Array<String>) {
    Webcam.getDefault().apply {
        open()
        ImageIO.write(image, "PNG", File("sample.png"))
    }
}