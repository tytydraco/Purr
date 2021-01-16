package com.draco.purr.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.draco.purr.models.Resolution
import com.draco.purr.utils.ResolutionUtils
import com.draco.purr.utils.WM

class ResolutionActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext
    private val wm = WM(context.contentResolver)
    private val resolutionUtils = ResolutionUtils(wm)

    fun getResolution(scale: Float): Resolution {
        val resolution = resolutionUtils.scaleResolution(
            resolutionUtils.realResolution,
            scale
        )
        return Resolution(resolution.width, resolution.height)
    }

    fun getResolutionLabel(scale: Float): String {
        if (scale == 100f)
            return "Default"

        val resolution = getResolution(scale)
        return "${resolution.width}x${resolution.height}"
    }

    fun scaleDisplay(scale: Float): Boolean {
        val resolution = resolutionUtils.scaleResolution(
            resolutionUtils.realResolution,
            scale
        )
        
        try {
            wm.setResolution(resolution.width, resolution.height)
            wm.setDisplayDensity(resolutionUtils.getDPI(resolution))
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
            return false
        }

        return true
    }
}