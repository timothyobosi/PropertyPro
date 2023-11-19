package com.example.propertypro


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.propertypro.databinding.ActivityBuyerLoginBinding
import com.google.firebase.auth.FirebaseAuth

class BuyerLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBuyerLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyerLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.loginButton.setOnClickListener {
            loginUser()
        }

        binding.signUpText.setOnClickListener {
            onSignUpClick(it)
        }

        binding.forgotPasswordText.setOnClickListener {
            onForgotPasswordClick(it)
        }
    }

    private fun loginUser() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        // Validate email and password if needed

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Login successful, navigate to HomeActivity
                    val intent = Intent(this@BuyerLoginActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish() // Optional: Close the login activity
                } else {
                    // Login failed, display an error message
                    Toast.makeText(
                        this@BuyerLoginActivity,
                        "Login failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun onSignUpClick(view: View) {
        // Handle click for "Do not have an account? Sign up"
        // For example, you can navigate to the sign-up activity
        // Replace the following line with your desired action
        Toast.makeText(this, "Sign Up Clicked", Toast.LENGTH_SHORT).show()
    }

    fun onForgotPasswordClick(view: View) {
        // Handle click for "Forgotten password?"
        // For example, you can navigate to the forgotten password activity
        // Replace the following line with your desired action
        Toast.makeText(this, "Forgot Password Clicked", Toast.LENGTH_SHORT).show()
    }
}
