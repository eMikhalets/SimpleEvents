package com.emikhalets.simpleevents.utils

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.emikhalets.simpleevents.utils.background.EventsReceiver
import java.util.*

object AppAlarmManager {

    private const val EVENTS_ALARM_REQUEST_CODE = 7

    fun setEventsAlarm(context: Context) {
        try {
            val prefs = Prefs(context)
            val alarmManager = context.getSystemService(Application.ALARM_SERVICE) as AlarmManager
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                EVENTS_ALARM_REQUEST_CODE,
                Intent(context, EventsReceiver::class.java),
                getPendingFlag(false)
            )
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                getAlarmTimestamp(prefs.eventAlarmHour, prefs.eventAlarmMinute),
                pendingIntent
            )
        } catch (ex: SecurityException) {
            ex.printStackTrace()
        }
    }

    fun cancelAlarm(context: Context) {
        try {
            val alarmManager = context.getSystemService(Application.ALARM_SERVICE) as AlarmManager
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                EVENTS_ALARM_REQUEST_CODE,
                Intent(context, EventsReceiver::class.java),
                getPendingFlag(true)
            )
            alarmManager.cancel(pendingIntent)
            pendingIntent.cancel()
        } catch (ex: SecurityException) {
            ex.printStackTrace()
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    fun isAlarmsRunning(context: Context): Boolean {
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            EVENTS_ALARM_REQUEST_CODE,
            Intent(context, EventsReceiver::class.java),
            PendingIntent.FLAG_NO_CREATE
        )
        return pendingIntent != null
    }

    private fun getPendingFlag(cancel: Boolean): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_MUTABLE
        } else {
            if (cancel) PendingIntent.FLAG_CANCEL_CURRENT else PendingIntent.FLAG_UPDATE_CURRENT
        }
    }

    private fun getAlarmTimestamp(hour: Int, minute: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        if (calendar.time < Date()) calendar.add(Calendar.DAY_OF_MONTH, 1)
        return calendar.timeInMillis
    }
}
