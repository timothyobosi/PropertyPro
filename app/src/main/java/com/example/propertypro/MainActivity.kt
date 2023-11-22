package com.example.propertypro

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference

    private lateinit var userEmailTextView: TextView
    private lateinit var userNameTextView: TextView
    private lateinit var profileImageView: ImageView
    private lateinit var uploadButton: Button


    private var selectedImageUri: Uri? = null

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val imageUri: Uri? = result.data?.data
            if (imageUri != null) {
                // Image selected successfully
                profileImageView.setImageURI(imageUri)
                selectedImageUri = imageUri
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        storage = FirebaseStorage.getInstance()
        storageReference = storage.reference

        userEmailTextView = findViewById(R.id.user_email)
        userNameTextView = findViewById(R.id.user_name)
        profileImageView = findViewById(R.id.profile_image)
        uploadButton = findViewById(R.id.upload_button)

        // Fetch user information from Firebase
        val user = auth.currentUser
        if (user != null) {
            userEmailTextView.text = user.email
            userNameTextView.text = user.displayName
        }

        // Open the gallery to select an image
        profileImageView.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickImageLauncher.launch(galleryIntent)
        }

        // Upload button click listener
        uploadButton.setOnClickListener {
            // Check if an image is selected
            if (selectedImageUri != null) {
                // Upload the image to Firebase Storage
                val imageRef = storageReference.child("profile_images/${auth.currentUser?.uid}")
                imageRef.putFile(selectedImageUri!!)
                    .addOnSuccessListener {
                        // Image upload success
                        imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                            // Save the download URL to the database
                            database.child("users").child(auth.currentUser?.uid!!).child("profileImageUrl")
                                .setValue(downloadUri.toString())
                        }
                    }
                    .addOnFailureListener { exception ->
                        // Handle unsuccessful uploads
                        // Log.e(TAG, "Error uploading image", exception)
                    }
            }
        }
    }
}
