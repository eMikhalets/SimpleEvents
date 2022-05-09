package com.emikhalets.simpleevents

import android.app.Application
import com.emikhalets.simpleevents.utils.AppNotificationManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SimpleEventsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        AppNotificationManager.createNotificationChannels(applicationContext)
    }
}