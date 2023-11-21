package com.example.propertypro

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import com.example.propertypro.databinding.ActivityBuyerRegistrationBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class BuyerRegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBuyerRegistrationBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyerRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        // Toggle visibility for password
        binding.passwordInputLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
        binding.passwordInputLayout.setEndIconOnClickListener {
            // Handle password visibility toggle click here
            val isPasswordVisible =
                binding.passwordEditText.inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            togglePasswordVisibility(!isPasswordVisible, binding.passwordEditText)
        }

        binding.registerButton.setOnClickListener {

            val phoneNumber = binding.phoneNumberEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val confirmPassword = binding.confirmPasswordEditText.text.toString()

            // Validate input values
            if (email.isEmpty()) {
                binding.emailEditText.error = "Email cannot be empty"
                return@setOnClickListener
            }

            if (password.length < 6) {
                binding.passwordEditText.error = "Password must be at least 6 characters"
                return@setOnClickListener
            }

            if (confirmPassword != password) {
                binding.confirmPasswordEditText.error = "Passwords do not match"
                return@setOnClickListener
            }

            // Create user with email and password
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Send email verification
                        sendEmailVerification()
                    } else {
                        // Handle registration failure
                        showToast("Registration failed: ${task.exception?.message}")
                    }
                }
        }

        binding.loginLinkTextView.setOnClickListener {
            navigateToSignIn()
        }
    }

    private fun togglePasswordVisibility(isVisible: Boolean, editText: TextInputEditText) {
        if (isVisible) {
            // Show password
            editText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            // Hide password
            editText.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        // Move cursor to the end of the text
        editText.setSelection(editText.text?.length ?: 0)
    }

    // ...

    private fun sendEmailVerification() {
        val user = firebaseAuth.currentUser
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    showToast("Verification email sent to ${user.email}")
                    // Redirect to VerificationActivity only if email verification is successful
                    val verificationIntent = Intent(this, VerificationActivity::class.java)
                    startActivity(verificationIntent)
                } else {
                    showToast("Failed to send verification email. ${task.exception?.message}")
                }
            }
    }

// ...


    private fun showToast(message: String) {
        // Display a Toast with the given message
    }

    // Function to navigate to the sign-in page
    private fun navigateToSignIn() {
        val signInIntent = Intent(this, LoginActivity::class.java)
        // Pass email and password to LoginActivity
        signInIntent.putExtra("EMAIL", binding.emailEditText.text.toString())
        signInIntent.putExtra("PASSWORD", binding.passwordEditText.text.toString())
        startActivity(signInIntent)
        finish() // Optional: Close the registration activity
    }
}
