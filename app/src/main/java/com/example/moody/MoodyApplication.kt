package com.example.moody

import android.app.Application
import com.example.moody.data.PreferenceManager

/**
 * Custom Application class that can be used to initialize global services and store data that affects multiple flows.
 */
class MoodyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        preferenceManager = PreferenceManager(this)
    }

    companion object {
        lateinit var preferenceManager: PreferenceManager
            private set
    }
}