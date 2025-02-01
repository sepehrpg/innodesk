package com.example.sharedpref.di

import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object SharedPrefModule {

    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

}