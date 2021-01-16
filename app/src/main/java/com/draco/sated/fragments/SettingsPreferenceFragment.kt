package com.draco.sated.fragments

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.draco.sated.R
import com.draco.sated.viewmodels.SettingsPreferenceFragmentViewModel
import com.draco.sated.views.VerifyActivity
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class SettingsPreferenceFragment : PreferenceFragmentCompat() {
    private val viewModel: SettingsPreferenceFragmentViewModel by viewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        when (preference.key) {
            getString(R.string.pref_reset_resolution_key) -> viewModel.resetScale()
            getString(R.string.pref_custom_resolution_key) -> customResolution()
            getString(R.string.pref_developer_key) -> openURL(getString(R.string.developer_url))
            getString(R.string.pref_source_key) -> openURL(getString(R.string.source_url))
            getString(R.string.pref_contact_key) -> openURL(getString(R.string.contact_url))
            getString(R.string.pref_sample_key) -> openURL(getString(R.string.sample_url))
            getString(R.string.pref_licenses_key) -> {
                val intent = Intent(requireContext(), OssLicensesMenuActivity::class.java)
                startActivity(intent)
            }
            else -> return super.onPreferenceTreeClick(preference)
        }
        return true
    }

    private fun customResolution() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.pref_custom_resolution_title)
            .setView(R.layout.dialog_custom_resolution)
            .create()
            .also {
                it.setButton(AlertDialog.BUTTON_POSITIVE, "Ok") { _, _ ->
                    val width = it.findViewById<EditText>(R.id.width)!!.text.toString()
                    val height = it.findViewById<EditText>(R.id.height)!!.text.toString()
                    viewModel.applyResolutionStrings(width, height)

                    val intent = Intent(requireContext(), VerifyActivity::class.java)
                    startActivity(intent)
                }
            }
            .show()

    }

    private fun openURL(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        try {
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            Snackbar.make(requireView(), getString(R.string.snackbar_intent_failed), Snackbar.LENGTH_SHORT).show()
        }
    }
}