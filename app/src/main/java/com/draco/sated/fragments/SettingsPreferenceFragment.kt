package com.draco.sated.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.draco.sated.R

class SettingsPreferenceFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }
}