package com.emikhalets.simpleevents.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Prefs @Inject constructor(@ApplicationContext context: Context) {

    private val sp = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)

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
