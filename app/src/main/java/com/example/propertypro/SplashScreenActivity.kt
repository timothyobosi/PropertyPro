package com.example.propertypro

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.firebase.ui.auth.AuthMethodPickerLayout
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import java.util.Arrays
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseUser
import java.util.concurrent.TimeUnit

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var providers: List<AuthUI.IdpConfig>
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var listener: FirebaseAuth.AuthStateListener

    companion object {
        private val LOGIN_REQUEST_CODE = 7171
    }

    override fun onStart() {
        super.onStart()
        delayMainActivity()
    }

    override fun onStop() {
        if (firebaseAuth !== null && listener != null) firebaseAuth.removeAuthStateListener(listener)
        super.onStop()
    }

    private fun delayMainActivity() {
        Completable.timer(2000, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
            .subscribe {
                showLoginLayout()
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_sign_in)
        init()
    }

    private fun init() {
        providers = Arrays.asList(
            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.EmailBuilder().build()
        )

        firebaseAuth = FirebaseAuth.getInstance()
        listener = FirebaseAuth.AuthStateListener { myFirebaseAuth ->
            val user = myFirebaseAuth.currentUser
            if (user != null) {
                if (!user.isEmailVerified) {
                    // User has not verified their email
                    Toast.makeText(
                        this@SplashScreenActivity,
                        "Please verify your email before signing in.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(this@SplashScreenActivity, "Welcome: ${user.uid}", Toast.LENGTH_SHORT).show()
                }
            } else {
                showLoginLayout()
            }
        }

        firebaseAuth.addAuthStateListener(listener)
    }

    private fun showLoginLayout() {
        val authMethodPickerLayout = AuthMethodPickerLayout.Builder(R.layout.layout_sign_in)
            .setPhoneButtonId(R.id.btn_phone_sign_in)
            .setEmailButtonId(R.id.btn_email_sign_in)
            .build()

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAuthMethodPickerLayout(authMethodPickerLayout)
            .setTheme(R.style.LoginTheme)
            .setAvailableProviders(providers)
            .setIsSmartLockEnabled(false)
            .build()

        startActivityForResult(signInIntent, LOGIN_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LOGIN_REQUEST_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) {
                    val userEmail = user.email
                    // Now you have the email address entered by the user
                    if (!user.isEmailVerified) {
                        sendEmailVerification(user)
                    }
                }
            } else {
                Toast.makeText(this@SplashScreenActivity, "" + response?.error?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendEmailVerification(user: FirebaseUser) {
        user.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this@SplashScreenActivity,
                        "Verification email sent to ${user.email}. Please verify before signing in.",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        this@SplashScreenActivity,
                        "Failed to send verification email. ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}
