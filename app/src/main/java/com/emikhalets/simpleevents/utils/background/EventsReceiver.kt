//package com.emikhalets.simpleevents.utils.background
//
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import androidx.work.OneTimeWorkRequestBuilder
//import androidx.work.WorkManager
//import androidx.work.workDataOf
//import com.emikhalets.mydates.utils.AppAlarmManager
//import com.emikhalets.mydates.utils.AppNotificationManager
//import com.emikhalets.mydates.utils.di.appComponent
//import com.emikhalets.mydates.utils.extentions.launchMainScope
//
//class EventsReceiver : BroadcastReceiver() {
//
//    override fun onReceive(context: Context, intent: Intent) {
//        AppAlarmManager.scheduleEventAlarm(context)
//        launchMainScope {
//            if (context.appComponent.appPreferences.getNotificationAll()) {
//                val isMonth = context.appComponent.appPreferences.getNotificationMonth()
//                val isWeek = context.appComponent.appPreferences.getNotificationWeek()
//                val isTwoDays = context.appComponent.appPreferences.getNotificationTwoDay()
//                val isDays = context.appComponent.appPreferences.getNotificationDay()
//                val isToday = context.appComponent.appPreferences.getNotificationToday()
//
//                val data = workDataOf(
//                    AppNotificationManager.DATA_NOTIFICATION_MONTH to isMonth,
//                    AppNotificationManager.DATA_NOTIFICATION_WEEK to isWeek,
//                    AppNotificationManager.DATA_NOTIFICATION_TWO_DAY to isTwoDays,
//                    AppNotificationManager.DATA_NOTIFICATION_DAY to isDays,
//                    AppNotificationManager.DATA_NOTIFICATION_TODAY to isToday
//                )
//                val work = OneTimeWorkRequestBuilder<EventWorker>().setInputData(data).build()
//                WorkManager.getInstance(context).enqueue(work)
//            }
//        }
//    }
//}