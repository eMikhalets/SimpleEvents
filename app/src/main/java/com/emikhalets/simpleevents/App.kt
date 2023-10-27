package com.emikhalets.simpleevents

import android.app.Application
import com.emikhalets.simpleevents.data.database.AppDatabase
import com.emikhalets.simpleevents.domain.entity.AlarmEntity
import com.emikhalets.simpleevents.utils.AppNotificationManager
import com.emikhalets.simpleevents.utils.Prefs
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        AppNotificationManager.createNotificationChannels(applicationContext)
        createDefaultEventAlarms()
    }

    private fun createDefaultEventAlarms() {
        val prefs = Prefs(this)
        if (!prefs.defaultEventAlarmsCreated) {
            // TODO: move to shared view model
            CoroutineScope(Dispatchers.Default).launch {
                try {
                    val dao = AppDatabase.getInstance(this@App).alarmsDao
                    val alarms = listOf(
                        AlarmEntity(
                            id = 1,
                            nameEn = getString(R.string.notifications_time_month),
                            enabled = true,
                            days = 30,
                        ),
                        AlarmEntity(
                            id = 2,
                            nameEn = getString(R.string.notifications_time_week),
                            enabled = true,
                            days = 7,
                        ),
                        AlarmEntity(
                            id = 3,
                            nameEn = getString(R.string.notifications_time_two_days),
                            enabled = true,
                            days = 2,
                        ),
                        AlarmEntity(
                            id = 4,
                            nameEn = getString(R.string.notifications_time_day),
                            enabled = true,
                            days = 1,
                        ),
                        AlarmEntity(
                            id = 5,
                            nameEn = getString(R.string.notifications_time_today),
                            enabled = true,
                            days = 0,
                        ),
                    )
                    dao.drop()
//                    dao.insert(alarms)
                    prefs.defaultEventAlarmsCreated = true
                } catch (ex: Exception) {
                    prefs.defaultEventAlarmsCreated = false
                }
            }
        }
    }
}
