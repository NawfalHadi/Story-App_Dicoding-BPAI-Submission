package com.thatnawfal.storyappdicoding.presentation.auth.bussiness

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.thatnawfal.storyappdicoding.data.local.datastore.AccountDataStore
import com.thatnawfal.storyappdicoding.data.remote.response.LoginResponse
import com.thatnawfal.storyappdicoding.data.remote.response.LoginResult
import com.thatnawfal.storyappdicoding.data.repository.AuthenticationRepository
import com.thatnawfal.storyappdicoding.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authRepository: AuthenticationRepository,
    private val accountPref: AccountDataStore
) : ViewModel() {

    fun register(
        _name: String, _email: String, _password: String
    ): LiveData<Resource<LoginResponse>> {
        return authRepository.register(
            _name, _email, _password
        )
    }

    fun login(
        _email: String, _password: String
    ): LiveData<Resource<LoginResponse>> {
        return authRepository.login(
            _email, _password
        )
    }

    fun saveSession(
        _token: String, _name: String, _userId: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            accountPref.loginDataSet(
                _token, _name, _userId
            )
        }
    }

    fun cleanSession(){
        viewModelScope.launch(Dispatchers.IO) {
            accountPref.logoutDataSet()
        }
    }

    fun getSession(): LiveData<LoginResult> {
        return accountPref.getData().asLiveData()
    }

}