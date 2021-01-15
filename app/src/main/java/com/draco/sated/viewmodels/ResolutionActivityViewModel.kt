package com.draco.sated.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.draco.sated.models.Resolution
import com.draco.sated.utils.ResolutionUtils
import com.draco.sated.utils.WM
import kotlin.math.roundToInt

class ResolutionActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext
    private val wm = WM(context.contentResolver)
    private val resolutionUtils = ResolutionUtils(wm)

    fun getResolutionLabel(scale: Float): String {
        val newResolution = resolutionUtils.scaleResolution(
            resolutionUtils.realResolution,
            scale
        )
        return "${newResolution.width}x${newResolution.height}"
    }

    fun scaleDisplay(scale: Float) {
        val resolution = resolutionUtils.scaleResolution(resolutionUtils.realResolution, scale)
        wm.setResolution(resolution.width, resolution.height)
        wm.setDisplayDensity(resolutionUtils.getDPI(resolution))
    }
}