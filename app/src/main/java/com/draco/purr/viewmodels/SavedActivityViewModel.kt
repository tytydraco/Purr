package com.draco.purr.viewmodels

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.Intent
import android.widget.EditText
import androidx.lifecycle.AndroidViewModel
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.draco.purr.R
import com.draco.purr.models.Resolution
import com.draco.purr.recyclers.SavedRecyclerAdapter
import com.draco.purr.utils.ResolutionUtils
import com.draco.purr.utils.WM
import com.draco.purr.views.VerifyActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.lang.Exception

class SavedActivityViewModel(application: Application) : AndroidViewModel(application) {
    val recyclerAdapter = SavedRecyclerAdapter(application.applicationContext)
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application.applicationContext)

    private val wm = WM(application.applicationContext.contentResolver)
    private val resolutionUtils = ResolutionUtils(wm)

    fun prepareRecycler(context: Context, recycler: RecyclerView) {
        recycler.apply {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(context)
        }
        recyclerAdapter.updateList()
    }

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

    fun add(context: Context) {
        MaterialAlertDialogBuilder(context)
            .setTitle(R.string.pref_custom_resolution_title)
            .setView(R.layout.dialog_custom_resolution)
            .create()
            .also {
                it.setButton(AlertDialog.BUTTON_POSITIVE, context.getString(R.string.apply)) { _, _ ->
                    val width = it.findViewById<EditText>(R.id.width)!!.text.toString()
                    val height = it.findViewById<EditText>(R.id.height)!!.text.toString()

                    recyclerAdapter.add("$width;$height")
                }
            }
            .show()
    }

    fun areYouSure(context: Context, callback: () -> Unit) {
        AlertDialog.Builder(context)
            .setTitle(R.string.delete)
            .setMessage(R.string.delete_confirm)
            .setPositiveButton(R.string.delete) { _, _ -> callback() }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }
}