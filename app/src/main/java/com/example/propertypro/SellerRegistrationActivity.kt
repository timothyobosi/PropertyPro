package com.example.propertypro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.propertypro.databinding.SellerRegistrationBinding

class SellerRegistrationActivity : AppCompatActivity() {
    private lateinit var binding: SellerRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SellerRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}