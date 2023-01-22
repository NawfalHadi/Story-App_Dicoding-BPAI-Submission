package com.thatnawfal.storyappdicoding.presentation.auth.bussiness

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.thatnawfal.storyappdicoding.data.remote.response.LoginResponse
import com.thatnawfal.storyappdicoding.data.repository.AuthenticationRepository
import com.thatnawfal.storyappdicoding.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authRepositry: AuthenticationRepository
) : ViewModel() {

    fun register(
        _name: String, _email: String, _password: String
    ): LiveData<Resource<LoginResponse>> {
        return authRepositry.register(
            _name, _email, _password
        )
    }

    fun login(
        _email: String, _password: String
    ): LiveData<Resource<LoginResponse>> {
        return authRepositry.login(
            _email, _password
        )
    }
}