package com.draco.sated.views

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.draco.sated.R
import com.draco.sated.viewmodels.PermissionActivityViewModel
import com.google.android.material.snackbar.Snackbar

class PermissionActivity : AppCompatActivity() {
    private val viewModel: PermissionActivityViewModel by viewModels()

    private lateinit var command: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (viewModel.isPermissionsGranted()) {
            startMainActivity()
            return
        }

        setContentView(R.layout.activity_permission)
        command = findViewById(R.id.command)

        command.setOnClickListener {
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("ADB Command", command.text.toString())
            clipboardManager.setPrimaryClip(clip)

            Snackbar.make(command, R.string.copied, Snackbar.LENGTH_SHORT).show()
        }

        viewModel.permissionGranted.observe(this) {
            if (it == true)
                startMainActivity()
        }
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}