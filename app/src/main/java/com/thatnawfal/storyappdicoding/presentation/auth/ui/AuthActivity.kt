package com.thatnawfal.storyappdicoding.presentation.auth.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.thatnawfal.storyappdicoding.R
import com.thatnawfal.storyappdicoding.databinding.ActivityAuthBinding
import com.thatnawfal.storyappdicoding.presentation.auth.bussiness.AuthenticationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    private val authViewModel: AuthenticationViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}