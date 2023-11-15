package com.example.propertypro

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.propertypro.databinding.ActivityLoginBinding
import com.example.propertypro.databinding.ActivitySignupBinding
import com.example.propertypro.databinding.SplashScreenBinding
import com.firebase.ui.auth.AuthMethodPickerLayout
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import java.util.Arrays
import java.util.concurrent.TimeUnit

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var providers: List<AuthUI.IdpConfig>
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var listener:FirebaseAuth.AuthStateListener

    private lateinit var binding: SplashScreenBinding

    companion object{
        private val LOGIN_REQUEST_CODE = 7171

    }


    override fun onStart() {
        super.onStart()
        delayMainActivity();
    }

    override fun onStop() {
        if(firebaseAuth !== null && listener != null) firebaseAuth.removeAuthStateListener(listener)
        super.onStop()
    }
    private fun delayMainActivity(){

        Completable.timer(2000, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
            .subscribe {
                showLoginLayout()
            }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init(){
        providers = Arrays.asList(
            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        firebaseAuth = FirebaseAuth.getInstance()
        listener = FirebaseAuth.AuthStateListener { myFirebaseAuth->
            val user = myFirebaseAuth.currentUser
            if (user != null)
                Toast.makeText(this@SplashScreenActivity,"Welcome:"+user.uid,Toast.LENGTH_SHORT).show()
            else
                showLoginLayout()
        }


    }

    private fun showLoginLayout(){

        val authMethodPickerLayout = AuthMethodPickerLayout.Builder(R.layout.layout_sign_in)
            .setPhoneButtonId(R.id.btn_phone_sign_in)
            .setGoogleButtonId(R.id.btn_google_sign_in)
            .build()

        binding = SplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            } else {
                Toast.makeText(this@SplashScreenActivity, "" + response?.error?.message, Toast.LENGTH_SHORT).show()
            }
        }

    }



}