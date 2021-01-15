package com.draco.sated.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.draco.sated.utils.WM

class VerifyActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext
    private val wm = WM(context.contentResolver)

    fun resetScale() {
        wm.clearResolution()
        wm.clearDisplayDensity()
    }
}