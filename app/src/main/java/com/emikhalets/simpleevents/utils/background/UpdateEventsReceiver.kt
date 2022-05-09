//package com.emikhalets.simpleevents.utils.background
//
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import androidx.work.OneTimeWorkRequestBuilder
//import androidx.work.WorkManager
//import com.emikhalets.mydates.utils.AppAlarmManager
//
//class UpdateEventsReceiver : BroadcastReceiver() {
//
//    override fun onReceive(context: Context, intent: Intent) {
////        AppAlarmManager.scheduleUpdatingAlarm(context)
//        val worker = OneTimeWorkRequestBuilder<UpdateEventsWorker>().build()
//        WorkManager.getInstance(context).enqueue(worker)
//    }
//}