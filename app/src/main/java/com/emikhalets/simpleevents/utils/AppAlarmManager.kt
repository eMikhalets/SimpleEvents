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

    fun startEventsAlarm(context: Context) {
        try {
            val prefs = Prefs(context)

            val alarmManager = context.getSystemService(Application.ALARM_SERVICE) as AlarmManager
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                EVENTS_ALARM_REQUEST_CODE,
                Intent(context, EventsReceiver::class.java),
                getPendingFlag()
            )
            alarmManager.setAlarmClock(
                AlarmManager.AlarmClockInfo(
                    getAlarmTimestamp(prefs.getEventsHour(), prefs.getEventsMinute()),
                    pendingIntent
                ),
                pendingIntent
            )
        } catch (ex: SecurityException) {
            ex.printStackTrace()
        }
    }

    fun startAllAlarms(context: Context) {
        startEventsAlarm(context)
    }

    fun isAlarmsRunning(context: Context): Boolean {
        return isEventAlarmRunning(context)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    fun isEventAlarmRunning(context: Context): Boolean {
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            EVENTS_ALARM_REQUEST_CODE,
            Intent(context, EventsReceiver::class.java),
            PendingIntent.FLAG_NO_CREATE
        )
        return pendingIntent != null
    }

    private fun getPendingFlag(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_MUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
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