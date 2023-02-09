package com.thatnawfal.storyappdicoding.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thatnawfal.storyappdicoding.data.remote.response.StoryResponse
import com.thatnawfal.storyappdicoding.data.remote.response.UploadResponse
import com.thatnawfal.storyappdicoding.data.remote.service.ApiService
import com.thatnawfal.storyappdicoding.wrapper.Resource
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Multipart

class StoriesRepository(
    private val apiService: ApiService
) {

    private var storyResponse: MutableLiveData<Resource<StoryResponse>> = MutableLiveData()
    private var uploadResponse : MutableLiveData<Resource<UploadResponse>> = MutableLiveData()

    fun getStories(_token: String, _size: Int, _page: Int, _loc: Int): LiveData<Resource<StoryResponse>> {
        storyResponse.postValue(Resource.Loading())
        apiService.getStories(_token, _size, _page, _loc).enqueue(
            object : Callback<StoryResponse> {
                override fun onResponse(
                    call: Call<StoryResponse>,
                    response: Response<StoryResponse>
                ) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error){
                        storyResponse.postValue(Resource.Success(responseBody))
                    } else {
                        storyResponse.postValue(Resource.Error(message = responseBody?.message))
                    }
                }

                override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                    storyResponse.postValue(Resource.Error(throwable = t))
                }
            }
        )

        return storyResponse
    }

    fun uploadStory(_token: String, _image: MultipartBody.Part, _desc: RequestBody) : LiveData<Resource<UploadResponse>>{
        uploadResponse.postValue(Resource.Loading())
        apiService.uploadStory(_token, _image, _desc).enqueue(
            object : Callback<UploadResponse> {
                override fun onResponse(
                    call: Call<UploadResponse>,
                    response: Response<UploadResponse>
                ) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error){
                        uploadResponse.postValue(Resource.Success(responseBody))
                    } else {
                        uploadResponse.postValue(Resource.Error(message =  responseBody?.message))
                    }
                }

                override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                    uploadResponse.postValue(Resource.Error(throwable = t))
                }
            }
        )

        return uploadResponse
    }

    // upload story with lan and lat

}