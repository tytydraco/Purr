package com.draco.sated.views

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.draco.sated.R
import com.draco.sated.viewmodels.ResolutionActivityViewModel
import com.google.android.material.slider.Slider
import kotlin.math.roundToInt

class ResolutionActivity : AppCompatActivity() {
    private val viewModel: ResolutionActivityViewModel by viewModels()

    private lateinit var settings: ImageButton
    private lateinit var sampleImage: ImageView
    private lateinit var resSlider: Slider

    private lateinit var sharedPreferences: SharedPreferences

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
                .placeholder(sampleImage.drawable)
                .override(resolution.width, resolution.height)
                .fitCenter()
                .into(sampleImage)
        }

        resSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {}
            override fun onStopTrackingTouch(slider: Slider) {
                viewModel.scaleDisplay(slider.value)

                if (sharedPreferences.getBoolean(getString(R.string.pref_verify_key), true) && slider.value != 100f) {
                    val intent = Intent(this@ResolutionActivity, VerifyActivity::class.java)
                    startActivity(intent)
                }
            }
        })
    }
}