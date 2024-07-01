package com.example.insidejob

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.insidejob.databinding.ActivityCustomLoadingBinding
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.github.ybq.android.spinkit.style.Wave
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CustomLoadingActivity : AppCompatActivity() {
    private val binding : ActivityCustomLoadingBinding by lazy {
        ActivityCustomLoadingBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val progressBar = binding.spinKit as ProgressBar
        val doubleBounce: Sprite = Wave()
        progressBar.indeterminateDrawable = doubleBounce

        //loading for specified time
//        val loadingDuration = 5000L
//
//        Handler(Looper.getMainLooper()).postDelayed({
//            finish() // Finish this activity and return to the previous one
//        }, loadingDuration)

        // Load data in the background
        loadDataInBackground()
    }

    private fun loadDataInBackground() {
        CoroutineScope(Dispatchers.IO).launch {
            // Simulate data loading (replace this with actual data loading logic)
            val dataLoaded = fetchDataFromNetworkOrDatabase()

            withContext(Dispatchers.Main) {
                // Once data is loaded, finish the activity
                if (dataLoaded) {
                    finish()
                } else {
                    // Handle error (optional)
                }
            }
        }
    }

    private suspend fun fetchDataFromNetworkOrDatabase(): Boolean {
        // Simulate a network/database operation
        delay(5000) // Simulate a 3-second loading time
        // Return true if data is successfully loaded, false otherwise
        return true
    }

    private fun enableEdgeToEdge(){

    }
}