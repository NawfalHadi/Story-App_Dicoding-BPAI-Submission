package com.thatnawfal.storyappdicoding.data.remote.service

import com.thatnawfal.storyappdicoding.data.remote.response.LoginResponse
import com.thatnawfal.storyappdicoding.data.remote.response.LoginResult
import com.thatnawfal.storyappdicoding.data.remote.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    // Authentication

    @FormUrlEncoded
    @POST("v1/register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("v1/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    // Create Story

    @Multipart
    @POST("v1/stories")
    fun uploadStory(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): Call<StoryResponse>

    @Multipart
    @POST("v1/stories")
    fun uploadStory(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: RequestBody,
        @Part("lon") lon: RequestBody,
    ): Call<StoryResponse>

    // Shows Story

    @GET("v1/stories")
    fun getStories(
        @Header("Authorization") token: String
    ): Call<StoryResponse>

//    @GET("v1/stories")


}