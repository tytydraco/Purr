package com.draco.sated.views

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.draco.sated.R
import com.draco.sated.viewmodels.PermissionActivityViewModel
import com.google.android.material.snackbar.Snackbar

class PermissionActivity : AppCompatActivity() {
    private val viewModel: PermissionActivityViewModel by viewModels()

    private lateinit var learnMore: ImageButton
    private lateinit var command: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (viewModel.isPermissionsGranted()) {
            startMainActivity()
            return
        }

        setContentView(R.layout.activity_permission)
        learnMore = findViewById(R.id.learn_more)
        command = findViewById(R.id.command)

        learnMore.setOnClickListener {
            val url = getString(R.string.learn_more_url)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            try {
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
                Snackbar.make(learnMore, getString(R.string.snackbar_intent_failed), Snackbar.LENGTH_SHORT).show()
            }
        }

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
        val intent = Intent(this, ResolutionActivity::class.java)
        startActivity(intent)
    }
}