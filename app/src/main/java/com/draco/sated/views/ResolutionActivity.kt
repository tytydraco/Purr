package com.draco.sated.views

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.draco.sated.R
import com.draco.sated.viewmodels.ResolutionActivityViewModel
import com.google.android.material.slider.Slider
import kotlin.math.roundToInt

class ResolutionActivity : AppCompatActivity() {
    private val viewModel: ResolutionActivityViewModel by viewModels()

    private lateinit var sampleImage: ImageView
    private lateinit var resSlider: Slider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sampleImage = findViewById(R.id.sample_image)
        resSlider = findViewById(R.id.res_slider)

        resSlider.setLabelFormatter {
            viewModel.getResolutionLabel(it)
        }

        val bitmap = ContextCompat.getDrawable(this, R.drawable.sample)!!.toBitmap()
        resSlider.addOnChangeListener { _, value, _ ->
            Glide
                .with(this)
                .load(R.drawable.sample)
                .placeholder(R.drawable.sample)
                .override(
                    (bitmap.width * (value / 100f)).roundToInt(),
                    (bitmap.height * (value / 100f)).roundToInt(),
                )
                .into(sampleImage)
        }

        resSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {}
            override fun onStopTrackingTouch(slider: Slider) {
                viewModel.scaleDisplay(slider.value)

                if (slider.value != 100f) {
                    val intent = Intent(this@ResolutionActivity, VerifyActivity::class.java)
                    startActivity(intent)
                }
            }
        })
    }
}