package com.emikhalets.simpleevents.utils

import android.content.Context
import com.emikhalets.simpleevents.utils.extensions.DEFAULT_EVENTS_HOUR
import com.emikhalets.simpleevents.utils.extensions.DEFAULT_EVENTS_MINUTE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Prefs @Inject constructor(@ApplicationContext context: Context) {

    private val sp = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)

    fun getEventsHour(): Int {
        return sp.getInt(EVENT_HOUR, DEFAULT_EVENTS_HOUR)
    }

    fun setEventsHour(value: Int) {
        sp.edit().putInt(EVENT_HOUR, value).apply()
    }

    fun getEventsMinute(): Int {
        return sp.getInt(EVENT_MINUTE, DEFAULT_EVENTS_MINUTE)
    }

    fun setEventsMinute(value: Int) {
        sp.edit().putInt(EVENT_MINUTE, value).apply()
    }

    fun getNotificationsEnabled(): Boolean {
        return sp.getBoolean(NOTIFICATIONS_ENABLED, true)
    }

    fun setNotificationsEnabled(value: Boolean) {
        sp.edit().putBoolean(NOTIFICATIONS_ENABLED, value).apply()
    }

    fun getFirstLaunch(): Boolean {
        return sp.getBoolean(FIRST_LAUNCH, true)
    }

    fun setFirstLaunch(value: Boolean) {
        sp.edit().putBoolean(FIRST_LAUNCH, value).apply()
    }

    var eventAlarmHour: Int
        get() = sp.getInt(EVENT_ALARM_HOUR, 9)
        set(value) = sp.edit().putInt(EVENT_ALARM_HOUR, value).apply()

    var eventAlarmMinute: Int
        get() = sp.getInt(EVENT_ALARM_MINUTE, 0)
        set(value) = sp.edit().putInt(EVENT_ALARM_MINUTE, value).apply()

    var eventAlarmsEnabled: Boolean
        get() = sp.getBoolean(EVENT_ALARMS_ENABLED, false)
        set(value) = sp.edit().putBoolean(EVENT_ALARMS_ENABLED, value).apply()

    var defaultEventAlarmsCreated: Boolean
        get() = sp.getBoolean(DEFAULT_EVENT_ALARMS_CREATED, false)
        set(value) = sp.edit().putBoolean(DEFAULT_EVENT_ALARMS_CREATED, value).apply()

    companion object {
        private const val EVENT_ALARM_HOUR = "EVENT_ALARM_HOUR"
        private const val EVENT_ALARM_MINUTE = "EVENT_ALARM_MINUTE"
        private const val EVENT_ALARMS_ENABLED = "EVENT_ALARMS_ENABLED"
        private const val DEFAULT_EVENT_ALARMS_CREATED = "DEFAULT_EVENT_ALARMS_CREATED"
    }
}