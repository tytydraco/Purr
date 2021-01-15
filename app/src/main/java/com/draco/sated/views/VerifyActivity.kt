package com.draco.sated.views

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import android.widget.CheckBox
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.draco.sated.R
import com.draco.sated.viewmodels.VerifyActivityViewModel

class VerifyActivity : AppCompatActivity() {
    private val viewModel: VerifyActivityViewModel by viewModels()

    private lateinit var verifyBox1: CheckBox
    private lateinit var verifyBox2: CheckBox
    private lateinit var verifyBox3: CheckBox
    private lateinit var verifyBox4: CheckBox
    private lateinit var progress: ProgressBar

    private var verifySum = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)

        verifyBox1 = findViewById(R.id.verify_1)
        verifyBox2 = findViewById(R.id.verify_2)
        verifyBox3 = findViewById(R.id.verify_3)
        verifyBox4 = findViewById(R.id.verify_4)
        progress = findViewById(R.id.progress)

        ObjectAnimator.ofInt(progress, "progress", progress.progress, progress.max)
            .setDuration(15 * 1000)
            .also {
                it.interpolator = DecelerateInterpolator()
                it.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator?) {}
                    override fun onAnimationCancel(animation: Animator?) {}
                    override fun onAnimationRepeat(animation: Animator?) {}
                    override fun onAnimationEnd(animation: Animator?) {
                        if (!this@VerifyActivity.isDestroyed) {
                            viewModel.resetScale()
                            finish()
                        }
                    }
                })
            }
            .start()

        val verifyBoxes = listOf(
            verifyBox1,
            verifyBox2,
            verifyBox3,
            verifyBox4
        )

        /* Close activity only if user ticks all four boxes */
        verifyBoxes.forEach {
            it.setOnCheckedChangeListener { _, _ ->
                it.isEnabled = false
                verifySum += 1

                if (verifySum == 4)
                    finish()
            }
        }
    }

    /* Disallow exit */
    override fun onBackPressed() {}
}