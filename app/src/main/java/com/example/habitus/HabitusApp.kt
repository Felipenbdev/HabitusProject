package com.example.habitus

import android.app.Application
import androidx.work.Configuration
import com.example.habitus.network.RetrofitInstance

class HabitusApp : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()
        RetrofitInstance.init(this)
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .build()
}