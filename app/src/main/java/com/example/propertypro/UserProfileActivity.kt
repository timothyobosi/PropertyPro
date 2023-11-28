package com.example.propertypro

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class UserProfileActivity : AppCompatActivity() {

    private lateinit var textViewDisplayName: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var buttonUpdateProfile: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var userRef: DocumentReference
    private val db = FirebaseFirestore.getInstance()

    private var currentUser: FirebaseUser? = null
    private var userProfile: UserProfile? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        textViewDisplayName = findViewById(R.id.textViewDisplayName)
        textViewEmail = findViewById(R.id.textViewEmail)
        buttonUpdateProfile = findViewById(R.id.buttonUpdateProfile)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Check if the activity was launched from nav_profile
        val isFromProfile = intent.getBooleanExtra(KEY_FROM_PROFILE, false)
        if (isFromProfile) {
            // Update UI for the profile view
            userRef = db.collection("users").document(auth.currentUser!!.uid)
            updateUIForProfile()
        } else {
            // Check if user is signed in (not null), and update UI accordingly
            currentUser = auth.currentUser
            userRef = db.collection("users").document(currentUser!!.uid)
            updateUI(userProfile)
        }

        // Set up the "Update Profile" button click listener
        buttonUpdateProfile.setOnClickListener {
            if (currentUser != null) {
                updateDisplayName(currentUser!!)
                saveUserProfileToDatabase()
            }
        }
    }

    private fun updateUIForProfile() {
        // Customize the UI for the profile view
        // For example, hide the "Update Profile" button or show additional information
        buttonUpdateProfile.visibility = View.GONE

        // Fetch user data from Firestore and update UI
        userRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    userProfile = documentSnapshot.toObject(UserProfile::class.java)
                    updateUI(userProfile)
                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error fetching user profile from Firestore", e)
            }
    }

    private fun updateUI(userProfile: UserProfile?) {
        // Update UI based on the UserProfile model
        if (userProfile != null) {
            textViewDisplayName.text = userProfile.displayName
            textViewEmail.text = userProfile.email
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
                    userProfile?.displayName = newDisplayName
                    // Update UI or perform other actions
                } else {
                    // If update fails, log the error
                    Log.e(TAG, "Error updating user profile", task.exception)
                }
            }
    }

    private fun saveUserProfileToDatabase() {
        // Save the user profile to Firestore
        val updatedUserProfile = UserProfile(
            userProfile?.displayName ?: "",
            userProfile?.email ?: "",
            /* other fields */
        )

        userRef.set(updatedUserProfile)
            .addOnSuccessListener {
                // Database write successful
                Log.d(TAG, "User profile saved to Firestore.")
                // Update UI or perform other actions
            }
            .addOnFailureListener { e ->
                // If write fails, log the error
                Log.e(TAG, "Error saving user profile to Firestore", e)
            }
    }

    companion object {
        private const val TAG = "UserProfileActivity"
        private const val KEY_FROM_PROFILE = "from_profile"
    }
}
