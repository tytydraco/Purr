package com.draco.purr.utils

import com.draco.purr.models.Resolution
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

class ResolutionUtils(wm: WM) {
    val realResolution = with(wm.getRealResolution()) {
        Resolution(x, y)
    }

    /**
     * Divide a diagonal pixel width by this float to return an appropriate DPI
     */
    private val resolutionDivisor = getDiagonalPixels(realResolution) / wm.getRealDensity()

    /**
     * Given a resolution, calculate the diagonal pixel width (no precision loss)
     */
    private fun getDiagonalPixels(resolution: Resolution): Double {
        return sqrt(
            resolution.width.toDouble().pow(2) +
            resolution.height.toDouble().pow(2)
        )
    }

    /**
     * Given a resolution, calculate an appropriately scaled DPI
     */
    fun getDPI(resolution: Resolution): Int {
        val diagonalPixels = getDiagonalPixels(resolution)
        return (diagonalPixels / resolutionDivisor).roundToInt()
    }

    /**
     * Given a percentage, calculate a scaled resolution
     */
    fun scaleResolution(resolution: Resolution, percent: Float): Resolution {
        val scale = percent / 100f
        return Resolution(
            (resolution.width * scale).roundToInt(),
            (resolution.height * scale).roundToInt()
        )
    }
}