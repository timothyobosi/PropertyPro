package com.example.propertypro

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.propertypro.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val isSeller = true

        if (isSeller){


        }
    }
}
