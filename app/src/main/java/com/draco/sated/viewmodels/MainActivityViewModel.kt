package com.draco.sated.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.draco.sated.models.Resolution
import com.draco.sated.utils.WM
import kotlin.math.roundToInt

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext
    val wm = WM(context.contentResolver)

    private val realResolution = with(wm.getRealResolution()) {
        Resolution(x, y)
    }

    fun scaleResolution(percentage: Float): Resolution {
        return Resolution(
            (realResolution.width * percentage).roundToInt(),
            (realResolution.height * percentage).roundToInt()
        )
    }
}