package com.emikhalets.simpleevents.utils.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class UpdateEventsWorker(context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters) {

    override suspend fun doWork(): Result {
//        val database = AppDatabase.get(applicationContext).eventDao
//        val repo = RoomRepositoryImpl(database)
//
//        when (repo.updateEvents()) {
//            is CompleteResult.Error -> {
//                AppNotificationManager.sendErrorUpdateNotification(applicationContext)
//            }
//            CompleteResult.Complete -> {
//                applicationContext.appComponent.appPreferences.setEventsLastUpdateTime(Date().time)
//            }
//        }

        return Result.success()
    }
}