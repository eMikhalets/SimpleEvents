package com.emikhalets.simpleevents.utils.background

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.emikhalets.simpleevents.utils.AppAlarmManager

class UpdateEventsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        AppAlarmManager.startUpdatingAlarm(context)
        val worker = OneTimeWorkRequestBuilder<UpdateEventsWorker>().build()
        WorkManager.getInstance(context).enqueue(worker)
    }
}