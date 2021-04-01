package com.demo.lib.utils

import com.squareup.moshi.Moshi
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@EntryPoint
@InstallIn(ApplicationComponent::class)
interface RemoteEntryPoint {

    fun moshi(): Moshi

}