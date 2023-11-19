package com.example.propertypro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.propertypro.databinding.ActivityBuyerRegistrationBinding


class BuyerRegistrationActivity : AppCompatActivity(){

    private lateinit var binding: ActivityBuyerRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyerRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}