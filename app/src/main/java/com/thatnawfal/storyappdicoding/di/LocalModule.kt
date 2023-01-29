package com.thatnawfal.storyappdicoding.di

import android.content.Context
import com.thatnawfal.storyappdicoding.data.local.datastore.AccountDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideAccountPreferences(
        @ApplicationContext ctx: Context
    ) : AccountDataStore {
        return AccountDataStore(ctx)
    }
}