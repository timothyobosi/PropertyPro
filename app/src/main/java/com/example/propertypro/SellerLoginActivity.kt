package com.example.propertypro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.example.propertypro.databinding.ActivitySellerLoginBinding

class SellerLoginActivity : AppCompatActivity(){

    private lateinit var binding: ActivitySellerLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}