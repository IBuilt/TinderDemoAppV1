package com.demo.tinder.di

import com.demo.tinder.data.source.remote.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideUserApi(retrofit: Retrofit): UserApi =
            retrofit.create(UserApi::class.java)
}