package com.draco.sated.utils

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.provider.Settings
import android.view.Display
import com.draco.sated.models.IWindowManager
import com.draco.sated.models.WindowManagerGlobal

@SuppressLint("PrivateApi")
class WM(private val contentResolver: ContentResolver) {
    companion object {
        const val USER_ID = -3

        val GLOBAL_SETTINGS_BLACKLIST_KEYS = listOf(
            "hidden_api_policy",
            "hidden_api_policy_pre_p_apps",
            "hidden_api_policy_p_apps"
        )
    }

    /**
     * IWindowManager instance
     */
    private val iWindowManager: IWindowManager

    init {
        /* Unblock private APIs */
        for (key in GLOBAL_SETTINGS_BLACKLIST_KEYS) {
            if (Settings.Global.getInt(contentResolver, key) != 1)
                Settings.Global.putInt(contentResolver, key, 1)
        }

        /* Resolve window manager */
        iWindowManager = Class.forName(WindowManagerGlobal.className)
            .getMethod(WindowManagerGlobal.getWindowManagerService)
            .invoke(null) as IWindowManager
    }

    /**
     * Set display resolution
     */
    fun setResolution(x: Int, y: Int) {
        Class.forName(IWindowManager.className)
            .getMethod(
                IWindowManager.setForcedDisplaySize,
                Int::class.javaPrimitiveType,
                Int::class.javaPrimitiveType,
                Int::class.javaPrimitiveType
            )
            .invoke(
                iWindowManager,
                Display.DEFAULT_DISPLAY,
                x,
                y
            )
    }

    /**
     * Clear display resolution
     */
    fun clearResolution() {
        Class.forName(IWindowManager.className)
            .getMethod(
                IWindowManager.clearForcedDisplaySize,
                Int::class.javaPrimitiveType
            )
            .invoke(
                iWindowManager,
                Display.DEFAULT_DISPLAY
            )
    }

    /**
     * Set display density
     */
    fun setDisplayDensity(density: Int) {
        Class.forName(IWindowManager.className)
            .getMethod(
                IWindowManager.setForcedDisplayDensity,
                Int::class.javaPrimitiveType,
                Int::class.javaPrimitiveType
            )
            .invoke(
                iWindowManager,
                Display.DEFAULT_DISPLAY,
                density
            )

        Class.forName(IWindowManager.className)
            .getMethod(
                IWindowManager.setForcedDisplayDensityForUser,
                Int::class.javaPrimitiveType,
                Int::class.javaPrimitiveType,
                Int::class.javaPrimitiveType
            )
            .invoke(
                iWindowManager,
                Display.DEFAULT_DISPLAY,
                density,
                USER_ID
            )
    }

    /**
     * Clear display density
     */
    fun clearDisplayDensity() {
        Class.forName(IWindowManager.className)
            .getMethod(
                IWindowManager.clearForcedDisplayDensity,
                Int::class.javaPrimitiveType
            )
            .invoke(
                iWindowManager,
                Display.DEFAULT_DISPLAY
            )

        Class.forName(IWindowManager.className)
            .getMethod(
                IWindowManager.clearForcedDisplayDensityForUser,
                Int::class.javaPrimitiveType,
                Int::class.javaPrimitiveType
            )
            .invoke(
                iWindowManager,
                Display.DEFAULT_DISPLAY,
                USER_ID
            )
    }
}