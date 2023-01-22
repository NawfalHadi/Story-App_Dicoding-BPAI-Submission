package com.thatnawfal.storyappdicoding.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thatnawfal.storyappdicoding.data.remote.response.LoginResponse
import com.thatnawfal.storyappdicoding.data.remote.service.ApiService
import com.thatnawfal.storyappdicoding.wrapper.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthenticationRepository(
    private val apiService: ApiService
) {

    private var authResponse : MutableLiveData<Resource<LoginResponse>> = MutableLiveData()

    fun register(
        _name: String, _email: String, _password: String
    ): LiveData<Resource<LoginResponse>> {
        authResponse.postValue(Resource.Loading())
        apiService.register(_name, _email, _password).enqueue(
            object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        authResponse.postValue(Resource.Success(responseBody))
                    } else {
                        authResponse.postValue(Resource.Error(message = responseBody?.message))
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    authResponse.postValue(Resource.Error(throwable = t))
                }
            }
        )

        return authResponse
    }

    fun login(
        _email: String, _password: String
    ): LiveData<Resource<LoginResponse>>{
        authResponse.postValue(Resource.Loading())
        apiService.login(_email, _password).enqueue(
            object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        authResponse.postValue(Resource.Success(responseBody))
                    } else {
                        authResponse.postValue(Resource.Error(message = responseBody?.message))
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    authResponse.postValue(Resource.Error(throwable = t))
                }

            }
        )
        return authResponse
    }

}