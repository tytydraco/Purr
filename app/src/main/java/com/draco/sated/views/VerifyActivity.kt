package com.draco.sated.views

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.draco.sated.R
import com.draco.sated.viewmodels.VerifyActivityViewModel

class VerifyActivity : AppCompatActivity() {
    private val viewModel: VerifyActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)
    }
}