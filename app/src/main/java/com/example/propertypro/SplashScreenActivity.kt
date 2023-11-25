package com.example.propertypro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.propertypro.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Assuming you want to show the ProgressBar for a few seconds and then hide it
        showProgressBar()

        // Simulate a delay (e.g., network request, data loading)
        Handler(Looper.getMainLooper()).postDelayed({
            // Hide the ProgressBar after a delay
            hideProgressBar()

            // Launch LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()

        }, 3000) // Adjust the delay time as needed
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = ProgressBar.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = ProgressBar.GONE
    }
}
