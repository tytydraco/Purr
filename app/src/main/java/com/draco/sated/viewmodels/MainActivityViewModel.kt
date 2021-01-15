package com.draco.sated.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.draco.sated.utils.WM

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext
    val wm = WM(context.contentResolver)
}