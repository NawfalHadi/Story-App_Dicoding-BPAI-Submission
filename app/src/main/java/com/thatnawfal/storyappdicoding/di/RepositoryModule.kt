package com.thatnawfal.storyappdicoding.di

import com.thatnawfal.storyappdicoding.data.remote.service.ApiService
import com.thatnawfal.storyappdicoding.data.repository.AuthenticationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthenticationRepository(
        apiService: ApiService
    ) : AuthenticationRepository {
        return AuthenticationRepository(apiService)
    }
}