package com.draco.purr.viewmodels

import android.app.Application
import android.content.pm.PackageManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PermissionActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext

    private val _permissionGranted = MutableLiveData(false)
    val permissionGranted: LiveData<Boolean> = _permissionGranted

    fun askRootPermission() {
        try {
            ProcessBuilder(
                "su",
                "-c",
                "pm grant com.draco.purr android.permission.WRITE_SECURE_SETTINGS"
            ).start()
        } catch (_: Exception) {}
    }

    fun isPermissionsGranted(): Boolean {
        return context.checkSelfPermission(android.Manifest.permission.WRITE_SECURE_SETTINGS) ==
            PackageManager.PERMISSION_GRANTED
    }

    private fun startPermissionCheckLoop() {
        viewModelScope.launch(Dispatchers.IO) {
            while (!isPermissionsGranted())
                delay(100)
            _permissionGranted.postValue(true)
        }
    }

    init {
        if (!isPermissionsGranted()) {
            viewModelScope.launch(Dispatchers.IO) {
                askRootPermission()
            }
            startPermissionCheckLoop()
        }
    }
}