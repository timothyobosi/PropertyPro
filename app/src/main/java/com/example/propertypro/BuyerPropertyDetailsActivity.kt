package com.example.propertypro

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.propertypro.databinding.ActivityBuyerPropertyDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class BuyerPropertyDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBuyerPropertyDetailsBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var propertyId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyerPropertyDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        propertyId = intent.getStringExtra("PROPERTY_ID") ?: ""

        loadPropertyDetails()
    }

    private fun loadPropertyDetails() {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null && propertyId.isNotEmpty()) {
            val propertyRef = database.reference.child("properties").child(propertyId)
            propertyRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val property = dataSnapshot.getValue(Property::class.java)
                    if (property != null) {
                        displayPropertyDetails(property)
                    } else {
                        // Handle the case where the property is not found
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors
                }
            })
        }
    }

    private fun displayPropertyDetails(property: Property) {
        binding.propertyNumberTextView.text = "Property Number: ${property.propertyNumber}"
        binding.priceTextView.text = "Price: ${property.price}"
        // Display other property details as needed
    }
}
