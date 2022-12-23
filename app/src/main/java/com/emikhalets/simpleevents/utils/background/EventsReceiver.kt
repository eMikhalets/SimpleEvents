package com.emikhalets.simpleevents.utils.background

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.emikhalets.simpleevents.utils.AppAlarmManager

class EventsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "simple_events:tag")
        wl.acquire(2 * 60 * 1000)

        AppAlarmManager.setEventsAlarm(context)
        val work = OneTimeWorkRequestBuilder<EventWorker>().build()
        WorkManager.getInstance(context).enqueue(work)

        wl.release()
    }
}
