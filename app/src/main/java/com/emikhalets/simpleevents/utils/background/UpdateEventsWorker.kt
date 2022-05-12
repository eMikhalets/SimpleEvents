package com.emikhalets.simpleevents.utils.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.emikhalets.simpleevents.data.database.AppDatabase
import com.emikhalets.simpleevents.data.database.EventEntityDB
import com.emikhalets.simpleevents.utils.AppNotificationManager
import com.emikhalets.simpleevents.utils.extensions.calculateEventData

class UpdateEventsWorker(context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters) {

    override suspend fun doWork(): Result {
        return try {
            val eventsDao = AppDatabase.get(applicationContext).eventsDao
            val updatedEvents = mutableListOf<EventEntityDB>()
            eventsDao.getAllEntities()
                .forEach { event -> updatedEvents.add(event.calculateEventData()) }

            eventsDao.updateAll(updatedEvents)
            Result.success()
        } catch (ex: Exception) {
            ex.printStackTrace()
            AppNotificationManager.sendUpdateError(applicationContext)
            Result.failure()
        }
    }
}