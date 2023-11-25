package com.example.propertypro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.propertypro.databinding.SellerRegistrationBinding

class SellerRegistration : AppCompatActivity() {

    private lateinit var binding: SellerRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_registration)
    }
}