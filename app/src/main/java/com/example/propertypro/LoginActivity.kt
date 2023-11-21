package com.example.propertypro

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.propertypro.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    // ...

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        // Get the email and password passed from BuyerRegistrationActivity
        val email = intent.getStringExtra("EMAIL")
        val password = intent.getStringExtra("PASSWORD")

        // Use email and password as needed, for example, pre-fill the login fields
        binding.emailEditText.setText(email)
        binding.passwordEditText.setText(password)

        // Set a ClickableSpan on the TextView
        val createAccountText = findViewById<TextView>(R.id.createAccountText)
        val span = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Handle click event, navigate to CategorySelectionActivity
                val intent = Intent(this@LoginActivity, CategorySelectionActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true // Make the text underline
            }
        }

        // Make only the specific text clickable
        val spannableString = SpannableString(createAccountText.text)
        spannableString.setSpan(span, 23, createAccountText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        createAccountText.text = spannableString

        // Set OnClickListener for the TextView (optional, since the ClickableSpan handles the click)
        // createAccountText.setOnClickListener {
        //    // Handle the click event here
        //    // For example, navigate to the registration screen
        //    val intent = Intent(this, SignupActivity::class.java)
        //    startActivity(intent)
        // }

        binding.loginButton.setOnClickListener {
            val loginEmail = binding.emailEditText.text.toString()
            val loginPassword = binding.passwordEditText.text.toString()

            if (loginEmail.isNotEmpty() && loginPassword.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(loginEmail, loginPassword)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Check if email is verified before allowing login
                            val user = firebaseAuth.currentUser
                            if (user != null && user.isEmailVerified) {
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            } else {
                                showToast("Email not verified. Please verify your email.")
                            }
                        } else {
                            showToast("Login failed: ${task.exception?.message}")
                        }
                    }
            } else {
                showToast("Fields cannot be empty")
            }
        }
    }

// ...

}
