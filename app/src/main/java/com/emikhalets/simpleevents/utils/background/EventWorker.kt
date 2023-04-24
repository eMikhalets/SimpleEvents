package com.emikhalets.simpleevents.utils.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.emikhalets.simpleevents.data.database.AppDatabase
import com.emikhalets.simpleevents.domain.entity.EventAlarmWrapper
import com.emikhalets.simpleevents.utils.AppAlarmManager
import com.emikhalets.simpleevents.utils.AppNotificationManager
import com.emikhalets.simpleevents.utils.extensions.calculateEventData

class EventWorker(context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters) {

    override suspend fun doWork(): Result {
        return try {
            val database = AppDatabase.getInstance(applicationContext)
            val alarmsDao = database.alarmsDao
            val eventsDao = database.eventsDao

            val sourceEvents = eventsDao.getAllEntities()
                .map { it.calculateEventData() }
                .sortedBy { it.days }

            val eventsList = mutableListOf<EventAlarmWrapper>()

            alarmsDao.getAll()
                .filter { it.enabled }
                .forEach { notification ->
                    val list = sourceEvents.filter { it.days == notification.days }
                    if (list.isNotEmpty()) {
                        eventsList.add(EventAlarmWrapper(notification, list))
                    }
                }

            if (eventsList.isNotEmpty()) {
                AppNotificationManager.sendEventsNotification(applicationContext, eventsList)
            }

            AppAlarmManager.setEventsAlarm(applicationContext)
            Result.success()
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure()
        }
    }
}
