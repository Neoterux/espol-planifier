package com.neoterux.espfier.custom

import com.neoterux.espfier.utils.insets
import kotlin.math.roundToInt
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt

/**
 * ### Color
 * This class represents a color in format rgba, with the optional alpha channel.
 *
 * @param r: The red channel accepts values from 0 to 255
 * @param g: The green channel accepts values from 0 to 255
 * @param b: The blue channel accepts values from 0 to 255
 * @param alpha: The alpha channel, contains values from 0 to 1
 */
data class Color(val r: Int, val g: Int, val b: Int,  val alpha: Double = 1.0): Comparable<Color> {

    companion object {
        /**
         * @param includeAlpha: determines if the alpha channel would be generated randomly
         * @return a new Random color
         */
        fun random(includeAlpha: Boolean = false): Color {
            val alpha = if (includeAlpha) 1.0  else Random.nextDouble()

            return Color(nextChannel(), nextChannel(), nextChannel(), alpha)
        }

        /**
         * @return the contrast color of the specified color
         */
        fun Color.contrast(): Color {
            val luminance = (0.299 * r + 0.587 * g + 0.114 * b)/255
            val lum = if (luminance > 0.5) 0 else 255
            return Color(lum, lum, lum)
        }

        private fun nextChannel() = nextInt(256)
    }

    /**
     * Compares the color with another to check if represents the same color, includes a comparison
     * of the alpha channel.
     */
    override operator fun compareTo(other: Color): Int =
        r - other.r + g - other.g + b - other.b + alpha.compareTo(other.alpha)

    /**
     * @return the hexadecimal representation of the current Color.
     */
    fun hex(): String = "#%02x%02x%02x".format(r, g, b).uppercase()

    /**
     * @return the hexadecimal representation with the alpha channel.
     */
    fun hexAlpha(): String = hex() + "%02x".format((alpha*255).roundToInt()).uppercase()

    /**
     * @return the rgb string with css format of the current color
     */
    fun rgb():String = "rgb($r, $g, $b)"

    /**
     * @return the rgb string with the alpha channel
     */
    fun rgba():String = "rgba($r,$g,$b,%.2f)".format(alpha)

}
