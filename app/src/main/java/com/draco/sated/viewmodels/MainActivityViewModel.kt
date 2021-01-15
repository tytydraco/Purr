package com.draco.sated.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.draco.sated.models.Resolution
import com.draco.sated.utils.ResolutionUtils
import com.draco.sated.utils.WM
import kotlin.math.roundToInt

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext
    val wm = WM(context.contentResolver)
    val resolutionUtils = ResolutionUtils(wm)

    fun setResolutionAndDensity(resolution: Resolution) {
        wm.setResolution(resolution.width, resolution.height)
        wm.setDisplayDensity(resolutionUtils.getDPI(resolution))
    }
}