package com.emikhalets.simpleevents

import android.app.Application
import com.emikhalets.simpleevents.data.database.AppDatabase
import com.emikhalets.simpleevents.utils.AppNotificationManager
import com.emikhalets.simpleevents.utils.Prefs
import com.emikhalets.simpleevents.utils.extensions.getDefaultNotificationsGlobal
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltAndroidApp
class SimpleEventsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        AppNotificationManager.createNotificationChannels(applicationContext)

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        if (Prefs(applicationContext).getFirstLaunch()) {
            CoroutineScope(Dispatchers.IO).launch {
                val list = getDefaultNotificationsGlobal(applicationContext)
                AppDatabase.get(applicationContext).notificationsGlobalDao.insert(list)
                Prefs(applicationContext).setFirstLaunch(false)
            }
        }
    }

    private fun createDefaultEventAlarms() {

    }
}
