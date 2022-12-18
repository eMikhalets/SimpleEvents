package com.emikhalets.simpleevents

import android.app.Application
import com.emikhalets.simpleevents.data.database.AppDatabase
import com.emikhalets.simpleevents.domain.entity.database.EventAlarm
import com.emikhalets.simpleevents.utils.AppNotificationManager
import com.emikhalets.simpleevents.utils.Prefs
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        AppNotificationManager.createNotificationChannels(applicationContext)
        createDefaultEventAlarms()
    }

    private fun createDefaultEventAlarms() {
        val prefs = Prefs(this)
        if (!prefs.defaultEventAlarmsCreated) {
            CoroutineScope(Dispatchers.Default).launch {
                try {
                    val dao = AppDatabase.get(this@App).eventAlarmsDao
                    val alarms = listOf(
                        EventAlarm(getString(R.string.notifications_time_month), true, 30),
                        EventAlarm(getString(R.string.notifications_time_week), true, 7),
                        EventAlarm(getString(R.string.notifications_time_two_days), true, 2),
                        EventAlarm(getString(R.string.notifications_time_day), true, 1),
                        EventAlarm(getString(R.string.notifications_time_today), true, 0),
                    )
                    dao.drop()
                    dao.insert(alarms)
                    prefs.defaultEventAlarmsCreated = true
                } catch (ex: Exception) {
                    prefs.defaultEventAlarmsCreated = false
                }
            }
        }
    }
}
