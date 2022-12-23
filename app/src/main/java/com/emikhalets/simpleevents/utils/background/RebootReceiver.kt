package com.emikhalets.simpleevents.utils.background

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.emikhalets.simpleevents.utils.AppAlarmManager
import com.emikhalets.simpleevents.utils.Prefs

class RebootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            if (Prefs(context).eventAlarmsEnabled) {
                AppAlarmManager.setEventsAlarm(context)
            }
        }
    }
}
