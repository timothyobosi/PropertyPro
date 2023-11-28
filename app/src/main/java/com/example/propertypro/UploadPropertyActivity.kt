package com.example.propertypro

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.propertypro.databinding.ActivityUploadPropertyBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap

class UploadPropertyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadPropertyBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var mapView: MapView
    private var mapboxMap: MapboxMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadPropertyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Mapbox
        Mapbox.getInstance(this, "YOUR_MAPBOX_ACCESS_TOKEN")

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)

        mapView.getMapAsync { mapboxMap ->
            this.mapboxMap = mapboxMap
            // Set up the map style and other configurations here
        }

        binding.submitButton.setOnClickListener {
            uploadProperty()
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    private fun uploadProperty() {
        val propertyNumber = binding.propertyNumberEditText.text.toString()
        val price = binding.priceEditText.text.toString()

        // Validate input fields
        if (propertyNumber.isEmpty() || price.isEmpty()) {
            Toast.makeText(this, "Please fill in all details", Toast.LENGTH_SHORT).show()
            return
        }

        // Get other property details and location as needed

        // Get the current camera position from Mapbox
        val cameraPosition = mapboxMap?.cameraPosition

        // Check if the camera position is available
        if (cameraPosition != null) {
            // Upload property data to Firebase
            val userId = firebaseAuth.currentUser?.uid
            if (userId != null) {
                val propertyRef = database.reference.child("properties").push()
                propertyRef.setValue(
                    Property(
                        propertyNumber,
                        price.toDouble(),
                        // Set other property details here
                        userId,
                        cameraPosition.target.latitude,
                        cameraPosition.target.longitude
                    )
                )

                Toast.makeText(this, "Property uploaded successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                // Handle the case when the user is not authenticated
                Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Handle the case when the camera position is not available
            Toast.makeText(this, "Unable to get the map location", Toast.LENGTH_SHORT).show()
        }
    }
}
