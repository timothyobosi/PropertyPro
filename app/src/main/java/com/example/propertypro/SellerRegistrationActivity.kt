package com.example.propertypro

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class SellerRegistrationActivity : AppCompatActivity() {

    private lateinit var phoneNumberLayout: TextInputLayout
    private lateinit var idNumberLayout: TextInputLayout
    private lateinit var nameLayout: TextInputLayout
    private lateinit var emailLayout: TextInputLayout
    private lateinit var passwordLayout: TextInputLayout
    private lateinit var confirmPasswordLayout: TextInputLayout

    private lateinit var phoneNumberEditText: TextInputEditText
    private lateinit var idNumberEditText: TextInputEditText
    private lateinit var nameEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var confirmPasswordEditText: TextInputEditText

    private lateinit var registerButton: Button
    private lateinit var loginLinkTextView: TextView

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var sellerInfoRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_registration)

        // Initialize Firebase
        database = FirebaseDatabase.getInstance()
        sellerInfoRef = database.getReference(Common.SELLER_INFO_REFERENCE)

        // Initialize Views
        phoneNumberLayout = findViewById(R.id.phoneNumberInputLayout)
        idNumberLayout = findViewById(R.id.idNumberInputLayout)
        nameLayout = findViewById(R.id.nameInputLayout)
        emailLayout = findViewById(R.id.emailInputLayout)
        passwordLayout = findViewById(R.id.passwordInputLayout)
        confirmPasswordLayout = findViewById(R.id.confirmPasswordInputLayout)

        phoneNumberEditText = findViewById(R.id.phoneNumberEditText)
        idNumberEditText = findViewById(R.id.idNumberEditText)
        nameEditText = findViewById(R.id.nameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)

        registerButton = findViewById(R.id.registerButton)
        loginLinkTextView = findViewById(R.id.loginLinkTextView)

        auth = FirebaseAuth.getInstance()

        // Set click listener for Register Button
        registerButton.setOnClickListener { onRegisterButtonClick() }

        // Set click listener for Login Link TextView
        loginLinkTextView.setOnClickListener { navigateToSignIn() }

        // Add ValueEventListener to listen for changes in the database
        sellerInfoRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Check if data is updated
                // Log or print dataSnapshot to see the data
                Log.d("Firebase", "Data updated: $dataSnapshot")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
                Log.e("Firebase", "Error: ${databaseError.message}")
            }
        })
    }

    private fun onRegisterButtonClick() {
        val phoneNumber = phoneNumberEditText.text.toString()
        val idNumber = idNumberEditText.text.toString()
        val name = nameEditText.text.toString()
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val confirmPassword = confirmPasswordEditText.text.toString()

        if (validateInputFields(phoneNumber, idNumber, name, email, password, confirmPassword)) {
            // Perform the registration process and send verification link
            registerUser(email, password, phoneNumber, idNumber, name)
        }
    }

    private fun validateInputFields(
        phoneNumber: String,
        idNumber: String,
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (phoneNumber.isEmpty() || idNumber.isEmpty() || name.isEmpty() ||
            email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
        ) {
            showToast("All fields must be filled")
            return false
        }

        if (password != confirmPassword) {
            showToast("Password and Confirm Password do not match")
            return false
        }

        // You can add more validation rules as needed

        return true
    }

    private fun registerUser(email: String, password: String, phoneNumber: String, idNumber: String, name: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Send verification email
                    sendVerificationEmail()
                    // Save user information to Firebase Realtime Database
                    saveUserInfoToFirebase(email, phoneNumber, idNumber, name)
                } else {
                    // Handle registration failure
                    handleRegistrationFailure(task.exception)
                }
            }
    }

    private fun sendVerificationEmail() {
        val user = auth.currentUser
        user?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showToast("Verification email sent to ${user.email}")
                } else {
                    showToast("Failed to send verification email.")
                }
            }
    }

    private fun saveUserInfoToFirebase(email: String, phoneNumber: String, idNumber: String, name: String) {
        val user = auth.currentUser
        user?.let {
            // Hash the password (you can use a secure hashing algorithm)
            val hashedPassword = hashPassword(passwordEditText.text.toString())

            // Create a SellerInfoModel object
            val sellerInfo = SellerInfoModel()
            sellerInfo.email = email
            sellerInfo.phoneNumber = phoneNumber
            sellerInfo.idNumber = idNumber
            sellerInfo.fullName = name
            sellerInfo.password = hashedPassword

            // Save user information to Firebase Realtime Database
            sellerInfoRef.child("seller_info").child(it.uid).setValue(sellerInfo)
        }
    }

    private fun handleRegistrationFailure(exception: Exception?) {
        when (exception) {
            is FirebaseAuthUserCollisionException -> showToast("Email is already registered.")
            is FirebaseAuthInvalidCredentialsException -> showToast("Invalid email or password format.")
            else -> showToast("Registration failed. Please try again.")
        }
    }

    private fun hashPassword(password: String): String {
        // Implement password hashing algorithm (e.g., use a secure hashing library)
        // For demonstration purposes, you can use a simple hash function
        return password.hashCode().toString()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToSignIn() {
        // Navigate to SellerLoginActivity
        val intent = Intent(this, SellerLoginActivity::class.java)
        startActivity(intent)
    }
}
