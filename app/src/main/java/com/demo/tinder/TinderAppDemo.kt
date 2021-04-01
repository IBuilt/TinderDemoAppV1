package com.demo.tinder

import android.app.Application
import com.demo.lib.utils.ContextAccessor
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TinderAppDemo : Application() {

    override fun onCreate() {
        super.onCreate()
        ContextAccessor.context = applicationContext
    }
}