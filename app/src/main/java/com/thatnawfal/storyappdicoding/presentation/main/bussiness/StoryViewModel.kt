package com.thatnawfal.storyappdicoding.presentation.main.bussiness

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.thatnawfal.storyappdicoding.data.remote.response.StoryResponse
import com.thatnawfal.storyappdicoding.data.remote.response.UploadResponse
import com.thatnawfal.storyappdicoding.data.repository.StoriesRepository
import com.thatnawfal.storyappdicoding.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(
    private val storiesRepository: StoriesRepository
) : ViewModel() {

    fun getStories(
        _token: String,
        _size: Int = 4, _page: Int = 1, _loc: Int = 0
    ) : LiveData<Resource<StoryResponse>> {
        return storiesRepository.getStories(
            _token, _size, _page, _loc
        )
    }

    fun uploadStory(
        _token: String,
        _image: MultipartBody.Part,
        _desc: RequestBody
    ) : LiveData<Resource<UploadResponse>> {
        return storiesRepository.uploadStory(
            _token, _image, _desc
        )
    }
}