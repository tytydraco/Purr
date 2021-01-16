package com.draco.purr.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.draco.purr.utils.WM

class VerifyActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext
    private val wm = WM(context.contentResolver)

    fun resetScale() {
        wm.clearResolution()
        wm.clearDisplayDensity()
    }
}