package com.example.propertypro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.propertypro.databinding.ActivityBuyerLoginBinding

class BuyerLogin : AppCompatActivity() {

    private lateinit var binding: ActivityBuyerLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buyer_login)
    }
}