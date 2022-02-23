package com.draco.purr.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.draco.purr.R
import com.draco.purr.viewmodels.SavedActivityViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SavedActivity : AppCompatActivity() {
    private val viewModel: SavedActivityViewModel by viewModels()
    private lateinit var add: FloatingActionButton
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved)

        add = findViewById(R.id.add)
        recyclerView = findViewById(R.id.recycler_view)

        viewModel.prepareRecycler(this, recyclerView)
        viewModel.recyclerAdapter.pickHook = {
            val splits = it.split(";")
            val width = splits[0]
            val height = splits[1]
            
            if (viewModel.applyResolutionStrings(width, height) && viewModel.sharedPreferences.getBoolean(getString(R.string.pref_verify_key), true)) {
                val intent = Intent(this, VerifyActivity::class.java)
                startActivity(intent)
            }

            finish()
        }

        viewModel.recyclerAdapter.deleteHook = {
            viewModel.areYouSure(this) { viewModel.recyclerAdapter.delete(it) }
        }

        add.setOnClickListener { viewModel.add(this) }
    }
}