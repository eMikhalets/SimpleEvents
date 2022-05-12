package com.emikhalets.simpleevents.utils.background

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.emikhalets.simpleevents.utils.AppAlarmManager
import com.emikhalets.simpleevents.utils.Prefs

class EventsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        AppAlarmManager.startEventsAlarm(context)
        if (Prefs(context).getNotificationsEnabled()) {
            val work = OneTimeWorkRequestBuilder<EventWorker>().build()
            WorkManager.getInstance(context).enqueue(work)
        }
    }
}