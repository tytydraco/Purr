package com.draco.purr.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.draco.purr.R
import com.draco.purr.fragments.SettingsPreferenceFragment

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, SettingsPreferenceFragment())
            .commit()
    }
}