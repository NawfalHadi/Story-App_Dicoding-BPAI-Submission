package com.thatnawfal.storyappdicoding.wrapper

sealed class Resource<T>(
    val payload: T? = null,
    val message: String? = null,
    val throwable: Throwable? = null,
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Empty<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(throwable: Throwable? = null, data: T? = null, message: String? = null) : Resource<T>(data, throwable = throwable, message = message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}