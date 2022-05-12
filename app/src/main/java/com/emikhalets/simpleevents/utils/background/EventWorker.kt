package com.emikhalets.simpleevents.utils.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.emikhalets.simpleevents.data.database.AppDatabase
import com.emikhalets.simpleevents.domain.entity.NotificationEvent
import com.emikhalets.simpleevents.utils.AppNotificationManager

class EventWorker(context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters) {

    override suspend fun doWork(): Result {
        return try {
            val notificationsDao = AppDatabase.get(applicationContext).notificationsGlobalDao
            val eventsDao = AppDatabase.get(applicationContext).eventsDao

            val eventsList = mutableListOf<NotificationEvent>()

            notificationsDao.getAllEntities().forEach { notificationTime ->
                if (notificationTime.enabled) {
                    val list = eventsDao.getEntityByDaysLeft(notificationTime.daysLeft)
                    if (list.isNotEmpty()) {
                        eventsList.add(NotificationEvent(notificationTime, list))
                    }
                }
            }

            if (eventsList.isNotEmpty()) {
                AppNotificationManager.sendEventsNotification(applicationContext, eventsList)
            }

            Result.success()
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure()
        }
    }
}