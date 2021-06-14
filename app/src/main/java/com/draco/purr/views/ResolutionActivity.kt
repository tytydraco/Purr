package com.draco.purr.views

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.draco.purr.R
import com.draco.purr.viewmodels.ResolutionActivityViewModel
import com.google.android.material.slider.Slider
import com.google.android.material.snackbar.Snackbar

class ResolutionActivity : AppCompatActivity() {
    private val viewModel: ResolutionActivityViewModel by viewModels()

    private lateinit var settings: ImageButton
    private lateinit var sampleImage: ImageView
    private lateinit var resSlider: Slider

    private lateinit var sharedPreferences: SharedPreferences

    val resolutionChangeCallback = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_CANCELED)
            resSlider.value = 100f
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resolution)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        settings = findViewById(R.id.settings)
        sampleImage = findViewById(R.id.sample_image)
        resSlider = findViewById(R.id.res_slider)

        settings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        resSlider.setLabelFormatter {
            viewModel.getResolutionLabel(it)
        }

        resSlider.addOnChangeListener { _, value, _ ->
            val resolution = viewModel.getResolution(value)
            Glide
                .with(this)
                .load(R.drawable.sample)
                .apply {
                    if (sampleImage.drawable != null)
                        placeholder(sampleImage.drawable)
                    else
                        placeholder(R.drawable.sample)
                }
                .override(resolution.width, resolution.height)
                .fitCenter()
                .into(sampleImage)
        }

        resSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {}
            override fun onStopTrackingTouch(slider: Slider) {
                if (!viewModel.scaleDisplay(slider.value)) {
                    Snackbar.make(slider, R.string.snackbar_api_error, Snackbar.LENGTH_SHORT).show()
                    return
                }

                if (sharedPreferences.getBoolean(getString(R.string.pref_verify_key), true) && slider.value != 100f) {
                    val intent = Intent(this@ResolutionActivity, VerifyActivity::class.java)
                    resolutionChangeCallback.launch(intent)
                }
            }
        })
    }
}