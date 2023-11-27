package com.example.propertypro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class PropertyDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_details)

        // Retrieve property data from the intent
        val property: Property = intent.getSerializableExtra("property") as Property

        // Bind property data to UI components in the layout
        // Add your code here to bind data to UI components
    }
}
