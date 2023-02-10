package com.thatnawfal.storyappdicoding.presentation.main.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.thatnawfal.storyappdicoding.databinding.FragmentProfileBinding
import com.thatnawfal.storyappdicoding.presentation.auth.bussiness.AuthenticationViewModel
import com.thatnawfal.storyappdicoding.presentation.auth.ui.AuthActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding

    private val authViewModel: AuthenticationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Go To Permission
        // Change Language
        // Logout
        binding.buttonLogout.setOnClickListener {
            authViewModel.cleanSession()

            startActivity(Intent(requireContext(), AuthActivity::class.java))
            activity?.finish()
        }
    }
}