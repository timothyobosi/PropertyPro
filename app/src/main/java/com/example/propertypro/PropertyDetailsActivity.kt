package com.example.propertypro

// PropertyDetailsActivity.kt
// PropertyDetailsActivity.kt
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.propertypro.databinding.ActivityPropertyDetailsBinding
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot



class PropertyDetailsActivity : AppCompatActivity() {

    private val TAG = "PropertyDetailsActivity"
    private lateinit var binding: ActivityPropertyDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPropertyDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve property ID from the intent
        val propertyId: String = intent.getStringExtra("propertyId")!!

        // Fetch property details from Firestore
        FirestoreUtil.getPropertyDetails(propertyId)
            .addOnSuccessListener(OnSuccessListener<DocumentSnapshot> { documentSnapshot ->
                val propertyDetails = documentSnapshot.toObject(PropertyDetails::class.java)

                // Bind property details to UI components in the layout
                // Assuming you have TextView and ImageView components in your layout
                binding.textViewTitle.text = propertyDetails?.title
                binding.textViewDescription.text = propertyDetails?.description

                // Load property image using Glide
                Glide.with(this)
                    .load(propertyDetails?.imageUrl)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(binding.imageViewProperty)
            })
            .addOnFailureListener(OnFailureListener { exception ->
                // Handle failure
                Log.e(TAG, "Error getting property details", exception)
            })
    }
}
