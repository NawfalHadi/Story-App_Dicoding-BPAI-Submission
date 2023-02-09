package com.thatnawfal.storyappdicoding.data.remote.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class StoryResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("listStory")
    val listStory: List<Story>,
    @SerializedName("message")
    val message: String?,
    @SerializedName("story")
    val story: Story?
)

@Parcelize
data class Story(
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: String,
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("lon")
    val lon: Double?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("photoUrl")
    val photoUrl: String?
) : Parcelable

data class UploadResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("error")
    val error: Boolean
)