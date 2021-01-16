package com.draco.purr.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.draco.purr.models.Resolution
import com.draco.purr.utils.ResolutionUtils
import com.draco.purr.utils.WM
import java.lang.Exception

class SettingsPreferenceFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext
    private val wm = WM(context.contentResolver)
    private val resolutionUtils = ResolutionUtils(wm)

    fun resetScale() {
        wm.clearResolution()
        wm.clearDisplayDensity()
    }

    fun applyResolutionStrings(width: String, height: String): Boolean {
        try {
            val widthInt = Integer.parseInt(width)
            val heightInt = Integer.parseInt(height)

            val dpi = resolutionUtils.getDPI(Resolution(widthInt, heightInt))
            wm.setResolution(widthInt, heightInt)
            wm.setDisplayDensity(dpi)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

        return true
    }
}