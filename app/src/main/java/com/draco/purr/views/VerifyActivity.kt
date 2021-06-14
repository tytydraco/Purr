package com.draco.purr.views

import android.animation.Animator
import android.animation.ObjectAnimator
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.animation.LinearInterpolator
import android.widget.CheckBox
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.draco.purr.R
import com.draco.purr.viewmodels.VerifyActivityViewModel
import com.google.android.material.button.MaterialButton

class VerifyActivity : AppCompatActivity() {
    private val viewModel: VerifyActivityViewModel by viewModels()

    private lateinit var verifyBox1: CheckBox
    private lateinit var verifyBox2: CheckBox
    private lateinit var verifyBox3: CheckBox
    private lateinit var verifyBox4: CheckBox
    private lateinit var back: MaterialButton
    private lateinit var progress: ProgressBar

    private var verifySum = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)

        window.decorView.setOnApplyWindowInsetsListener { view, windowInsets ->
            view.post { immersive() }
            return@setOnApplyWindowInsetsListener windowInsets
        }

        verifyBox1 = findViewById(R.id.verify_1)
        verifyBox2 = findViewById(R.id.verify_2)
        verifyBox3 = findViewById(R.id.verify_3)
        verifyBox4 = findViewById(R.id.verify_4)
        back = findViewById(R.id.back)
        progress = findViewById(R.id.progress)

        back.setOnClickListener {
            undoResolution()
        }

        ObjectAnimator.ofInt(progress, "progress", progress.progress, progress.max)
            .setDuration(10 * 1000)
            .also {
                it.interpolator = LinearInterpolator()
                it.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator?) {}
                    override fun onAnimationCancel(animation: Animator?) {}
                    override fun onAnimationRepeat(animation: Animator?) {}
                    override fun onAnimationEnd(animation: Animator?) {
                        if (!this@VerifyActivity.isDestroyed)
                            undoResolution()
                    }
                })
            }
            .start()

        /* Close activity only if user ticks all four boxes */
        listOf(
            verifyBox1,
            verifyBox2,
            verifyBox3,
            verifyBox4
        ).forEach {
            it.setOnCheckedChangeListener { _, _ ->
                it.isClickable = false
                verifySum += 1

                if (verifySum == 4) {
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            }
        }
    }

    private fun immersive() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            with (window.insetsController!!) {
                hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_FULLSCREEN
        }
    }

    private fun undoResolution() {
        viewModel.resetScale()
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    /* Disallow exit */
    override fun onBackPressed() {}
}