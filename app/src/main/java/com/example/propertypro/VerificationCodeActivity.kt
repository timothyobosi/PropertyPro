package com.example.propertypro

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.propertypro.HomeActivity
import com.example.propertypro.databinding.ActivityVerificationCodeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class VerificationCodeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerificationCodeBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.verifyButton.setOnClickListener {
            // Get email and password from the intent
            val email = intent.getStringExtra("EMAIL")
            val password = intent.getStringExtra("PASSWORD")

            // Perform email and password verification using Firebase Authentication
            if (email != null && password != null) {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Check if the email is verified
                            val user: FirebaseUser? = firebaseAuth.currentUser
                            if (user != null && user.isEmailVerified) {
                                // Email is verified, navigate to the next activity
                                val intent = Intent(this, HomeActivity::class.java)
                                startActivity(intent)
                                finish() // Optional: finish the current activity
                            } else {
                                Toast.makeText(
                                    this,
                                    "Email not verified. Please check your email for verification.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
