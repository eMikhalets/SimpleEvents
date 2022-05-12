package com.emikhalets.simpleevents.utils

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.emikhalets.simpleevents.utils.background.EventsReceiver
import com.emikhalets.simpleevents.utils.background.UpdateEventsReceiver
import com.emikhalets.simpleevents.utils.extensions.DEFAULT_UPDATE_EVENTS_HOUR
import com.emikhalets.simpleevents.utils.extensions.DEFAULT_UPDATE_EVENTS_MINUTE
import java.util.*

object AppAlarmManager {

    private const val EVENTS_ALARM_REQUEST_CODE = 7
    private const val UPDATING_ALARM_REQUEST_CODE = 8

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

    fun startUpdatingAlarm(context: Context) {
        try {
            val alarmManager = context.getSystemService(Application.ALARM_SERVICE) as AlarmManager
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                UPDATING_ALARM_REQUEST_CODE,
                Intent(context, UpdateEventsReceiver::class.java),
                getPendingFlag()
            )
            alarmManager.setAlarmClock(
                AlarmManager.AlarmClockInfo(
                    getAlarmTimestamp(DEFAULT_UPDATE_EVENTS_HOUR, DEFAULT_UPDATE_EVENTS_MINUTE),
                    pendingIntent
                ),
                pendingIntent
            )
        } catch (ex: SecurityException) {
            ex.printStackTrace()
        }
    }

    fun startAllAlarms(context: Context) {
        startUpdatingAlarm(context)
        startEventsAlarm(context)
    }

    fun isAlarmsRunning(context: Context): Boolean {
        return isEventAlarmRunning(context) || isUpdatingAlarmRunning(context)
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

    @SuppressLint("UnspecifiedImmutableFlag")
    fun isUpdatingAlarmRunning(context: Context): Boolean {
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            UPDATING_ALARM_REQUEST_CODE,
            Intent(context, UpdateEventsReceiver::class.java),
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