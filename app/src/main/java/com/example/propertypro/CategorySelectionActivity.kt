package com.example.propertypro

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.propertypro.databinding.CategoryselectionactivityBinding

class CategorySelectionActivity : AppCompatActivity() {

    private lateinit var binding: CategoryselectionactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("CategorySelection", "onCreate called")
        setContentView(R.layout.categoryselectionactivity) // Use the correct layout resource

        val buyerButton = findViewById<Button>(R.id.buyerButton)
        val sellerButton = findViewById<Button>(R.id.sellerButton)

        buyerButton.setOnClickListener {
            navigateToRegistration(BuyerRegistrationActivity::class.java)
        }

        sellerButton.setOnClickListener {
            navigateToRegistration(SellerRegistrationActivity::class.java)
        }
    }

    private fun navigateToRegistration(registrationActivity: Class<*>) {
        val intent = Intent(this, registrationActivity)
        startActivity(intent)
    }
}
