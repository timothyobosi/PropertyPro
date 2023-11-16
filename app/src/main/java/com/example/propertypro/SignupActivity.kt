package com.example.propertypro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import com.example.propertypro.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() , View.OnClickListener,View.OnFocusChangeListener, View.OnKeyListener{

    private lateinit var mBinding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySignupBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)



        //Listener to every input field
        mBinding.fullNameEt.onFocusChangeListener = this
        mBinding.emailEt.onFocusChangeListener = this
        mBinding.passwordEt.onFocusChangeListener = this
        mBinding.cPasswordEt.onFocusChangeListener =this
    }

    //function to validate full name
    private fun validFullName():Boolean{
        var errorMessage:String? = null
        val value:String = mBinding.fullNameEt.text.toString()
        if (value.isEmpty()){
            errorMessage= "Full  name Required"
        }

        if(errorMessage != null){
            mBinding.fullNameTil.apply {
                isErrorEnabled = true
                errorMessage = errorMessage
            }
        }

        return errorMessage == null
    }

    //function validate email field
    private fun validateEmail():Boolean{
        var errorMessage: String? =null
        //store value of email here
        val value = mBinding.emailEt.text.toString()
        if(value.isEmpty()){
            errorMessage="Email is required"
        }else if(!Patterns.EMAIL_ADDRESS.matcher(value).matches()){
            errorMessage ="Email address is invalid"
        }

        if(errorMessage != null){
            mBinding.emailTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return errorMessage == null
    }

    //function to validate password
    private fun validatePassword():Boolean{
        var errorMessage: String? =null
        //store value of password here
        val value = mBinding.passwordEt.text.toString()
        if(value.isEmpty()){
            errorMessage="Password is required"
        }else if(value.length < 8){
            errorMessage ="Password Must be 8 characters long"
        }

        if(errorMessage != null){
            mBinding.passwordTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return errorMessage == null

    }

    //validate confirmPassword
    private fun validateConfirmPassword(): Boolean{
        var errorMessage: String? =null
        //store value of password here
        val value = mBinding.cPasswordEt.text.toString()
        if(value.isEmpty()){
            errorMessage="Confirm Password is required"
        }else if(value.length < 8){
            errorMessage ="Password Must be 8 characters long"
        }

        if(errorMessage != null){
            mBinding.cPasswordTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

    //validate whether password is equal or not
    private fun validatePasswordAndConfirmPassword():Boolean{
        //declare variable to
        var errorMessage: String? = null
        val password = mBinding.passwordEt.text.toString()
        val confirmPassword = mBinding.cPasswordEt.text.toString()
        if(password != confirmPassword){
            errorMessage = "Confirm password doesn't match with Password"
        }

        if(errorMessage != null){
            mBinding.cPasswordTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return errorMessage== null
    }

    override fun onClick(view: View?) {

    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if(view != null){
            when(view.id){
                R.id.fullNameEt -> {
                    if(hasFocus){
                        if(mBinding.fullNameTil.isErrorEnabled){
                            mBinding.fullNameTil.isErrorEnabled = false
                        }
                    }else{
                        validFullName()
                    }
                }
                R.id.emailEt -> {
                    if (hasFocus){
                        if(mBinding.emailTil.isErrorEnabled){
                            mBinding.emailTil.isErrorEnabled = false
                        }
                    }else{
                        validateEmail()
                    }
                }
                R.id.passwordEt -> {
                    if(hasFocus){
                        if(mBinding.passwordTil.isErrorEnabled){
                            mBinding.passwordTil.isErrorEnabled = false
                        }
                    }else{
                        validatePassword()
                    }
                }
                R.id.cPasswordEt -> {
                    if (hasFocus){
                        if(mBinding.cPasswordTil.isErrorEnabled){
                            mBinding.cPasswordTil.isErrorEnabled = false
                        }
                    }else{
                        validateConfirmPassword()
                    }
                }
            }
        }
    }

    override fun onKey(view: View?, event: Int, keyEvent: KeyEvent?): Boolean {
        return false
    }
}