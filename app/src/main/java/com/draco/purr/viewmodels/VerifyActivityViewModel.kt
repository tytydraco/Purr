package com.draco.purr.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.draco.purr.utils.WM

class VerifyActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val wm = WM(application.applicationContext.contentResolver)

    fun resetScale() {
        wm.clearResolution()
        wm.clearDisplayDensity()
    }
}