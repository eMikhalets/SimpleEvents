//package com.emikhalets.simpleevents.utils.background
//
//import android.content.Context
//import androidx.work.CoroutineWorker
//import androidx.work.WorkerParameters
//import com.emikhalets.mydates.data.database.AppDatabase
//import com.emikhalets.mydates.data.database.ListResult
//import com.emikhalets.mydates.data.database.entities.Event
//import com.emikhalets.mydates.data.repositories.RoomRepositoryImpl
//import com.emikhalets.mydates.utils.*
//
//class EventWorker(context: Context, parameters: WorkerParameters) :
//    CoroutineWorker(context, parameters) {
//
//    override suspend fun doWork(): Result {
//        val database = AppDatabase.get(applicationContext).eventDao
//        val repo = RoomRepositoryImpl(database)
//
//        val events = HashMap<String, List<Event>>()
//
//        if (inputData.getBoolean(AppNotificationManager.DATA_NOTIFICATION_MONTH, false)) {
//            when (val result = repo.getAllByDaysLeft(30)) {
//                is ListResult.Success -> events[AppNotificationManager.DATA_NOTIFICATION_MONTH] = result.data
//                is ListResult.Error -> return Result.failure()
//                ListResult.EmptyList -> {
//                }
//            }
//        }
//
//        if (inputData.getBoolean(AppNotificationManager.DATA_NOTIFICATION_WEEK, false)) {
//            when (val result = repo.getAllByDaysLeft(7)) {
//                is ListResult.Success -> events[AppNotificationManager.DATA_NOTIFICATION_WEEK] = result.data
//                is ListResult.Error -> return Result.failure()
//                ListResult.EmptyList -> {
//                }
//            }
//        }
//
//        if (inputData.getBoolean(AppNotificationManager.DATA_NOTIFICATION_TWO_DAY, false)) {
//            when (val result = repo.getAllByDaysLeft(2)) {
//                is ListResult.Success -> events[AppNotificationManager.DATA_NOTIFICATION_TWO_DAY] = result.data
//                is ListResult.Error -> return Result.failure()
//                ListResult.EmptyList -> {
//                }
//            }
//        }
//
//        if (inputData.getBoolean(AppNotificationManager.DATA_NOTIFICATION_TWO_DAY, false)) {
//            when (val result = repo.getAllByDaysLeft(1)) {
//                is ListResult.Success -> events[AppNotificationManager.DATA_NOTIFICATION_TWO_DAY] = result.data
//                is ListResult.Error -> return Result.failure()
//                ListResult.EmptyList -> {
//                }
//            }
//        }
//
//        if (inputData.getBoolean(AppNotificationManager.DATA_NOTIFICATION_TODAY, false)) {
//            when (val result = repo.getAllByDaysLeft(0)) {
//                is ListResult.Success -> events[AppNotificationManager.DATA_NOTIFICATION_TODAY] = result.data
//                is ListResult.Error -> return Result.failure()
//                ListResult.EmptyList -> {
//                }
//            }
//        }
//
//        if (events.isNotEmpty()) {
//            AppNotificationManager.sendEventsNotification(applicationContext, events)
//        }
//
//        return Result.success()
//    }
//}