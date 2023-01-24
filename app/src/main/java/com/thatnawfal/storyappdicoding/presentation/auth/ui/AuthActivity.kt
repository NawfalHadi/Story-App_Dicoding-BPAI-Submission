package com.thatnawfal.storyappdicoding.presentation.auth.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.thatnawfal.storyappdicoding.R
import com.thatnawfal.storyappdicoding.databinding.ActivityAuthBinding
import com.thatnawfal.storyappdicoding.presentation.auth.bussiness.AuthenticationViewModel
import com.thatnawfal.storyappdicoding.presentation.main.ui.HomeActivity
import com.thatnawfal.storyappdicoding.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    private val authViewModel: AuthenticationViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding.btnSignup){
            setOnClickListener {
                val signup = isSignupValid()
                if (signup[3] as Boolean) {
                    signupValid(signup[0] as String, signup[1] as String, signup[2] as String)
                }
            }
        }

        with(binding.btnSignin){
            setOnClickListener {
                val signin = isSigninValid()
                if (signin[2] as Boolean) {
                    signinValid(signin[0] as String, signin[1] as String)
                }
            }
        }
    }

    private fun isSignupValid(): ArrayList<Any> {
        with(binding){

            return arrayListOf(
                etSignupName.text.toString(),
                etSignupEmail.text.toString(),
                etSignupPassword.text.toString(),
                true
            )
        }
    }

    private fun signupValid(_name: String, _email: String, _pass: String) {
        authViewModel.register(_name, _email, _pass).observe(this){
            when(it){
                is Resource.Loading -> {
                    Log.e("Sign Up", "Signing Up...")
                }
                is Resource.Error -> {
                    Log.e("Sign Up", (it.message ?: it.payload?.message).toString())
                }
                is Resource.Success -> {
                    startActivity(Intent(this@AuthActivity, HomeActivity::class.java))
                    finish()
                }
                else -> {
                    Log.e("Sign Up", "Unkown")
                }
            }
        }
    }

    private fun isSigninValid(): ArrayList<Any> {
        with(binding){
            return arrayListOf(
                etSigninEmail.text.toString(),
                etSigninPassword.text.toString(),
                true
            )
        }
    }

    private fun signinValid(_email: String, _pass: String) {
        authViewModel.login(_email, _pass).observe(this){
            when(it){
                is Resource.Loading -> {
                    Log.e("Sign In", "Signing In...")
                }
                is Resource.Error -> {
                    Log.e("Sign In", (it.message ?: it.payload?.message).toString())
                }
                is Resource.Success -> {
                    startActivity(Intent(this@AuthActivity, HomeActivity::class.java))
                    finish()
                }
                else -> {
                    Log.e("Sign In", "Unkown")
                }
            }
        }
    }
}