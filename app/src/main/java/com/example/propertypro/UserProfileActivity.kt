package com.example.propertypro

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserProfileActivity : AppCompatActivity() {

    private lateinit var textViewDisplayName: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var buttonUpdateProfile: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        textViewDisplayName = findViewById(R.id.textViewDisplayName)
        textViewEmail = findViewById(R.id.textViewEmail)
        buttonUpdateProfile = findViewById(R.id.buttonUpdateProfile)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Check if user is signed in (not null), and update UI accordingly
        val currentUser: FirebaseUser? = auth.currentUser
        updateUI(currentUser)

        // Set up the "Update Profile" button click listener
        buttonUpdateProfile.setOnClickListener {
            // Call a function to update the user's display name or other details
            // You can implement the necessary logic here
            // For example, show a dialog for the user to input new details
            // and then update the user's profile using FirebaseAuth API

            if (currentUser != null) {
                updateDisplayName(currentUser)
                saveUserProfileToDatabase(currentUser)
            }
        }
    }

    // Add this function to update UI based on user authentication status
    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            val displayName = currentUser.displayName
            val email = currentUser.email

            textViewDisplayName.text = displayName
            textViewEmail.text = email
            // Update other TextViews or UI components with additional user details
        }
    }

    private fun updateDisplayName(currentUser: FirebaseUser) {
        // Update the display name
        val newDisplayName = "New Display Name"
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(newDisplayName)
            // Add other updates if needed, e.g., setPhotoUri
            .build()

        currentUser.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Update successful
                    Log.d(TAG, "User profile updated.")
                    // Update UI or perform other actions
                } else {
                    // If update fails, log the error
                    Log.e(TAG, "Error updating user profile", task.exception)
                }
            }
    }

    private fun saveUserProfileToDatabase(currentUser: FirebaseUser) {
        // Save the user profile to the database
        val userId = currentUser.uid
        val databaseReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("users").child(userId)

        // Assume you have a UserProfile model class
        val userProfile = UserProfile("New Display Name", currentUser.email ?: "", /* other fields */)

        databaseReference.setValue(userProfile)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Database write successful
                    Log.d(TAG, "User profile saved to database.")
                    // Update UI or perform other actions
                } else {
                    // If write fails, log the error
                    Log.e(TAG, "Error saving user profile to database", task.exception)
                }
            }
    }

    companion object {
        private const val TAG = "UserProfileActivity"
    }
}
