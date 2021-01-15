package com.draco.sated.views

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.draco.sated.R
import com.draco.sated.viewmodels.MainActivityViewModel
import com.google.android.material.slider.Slider

class MainActivity : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var resSlider: Slider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        resSlider = findViewById(R.id.res_slider)

        resSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {}

            override fun onStopTrackingTouch(slider: Slider) {
                val newResolution = viewModel.resolutionUtils.scaleResolution(slider.value)
                viewModel.setResolutionAndDensity(newResolution)
            }
        })
    }
}