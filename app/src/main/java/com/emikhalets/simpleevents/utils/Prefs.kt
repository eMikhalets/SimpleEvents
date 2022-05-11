package com.emikhalets.simpleevents.utils

import android.content.Context
import com.emikhalets.simpleevents.utils.extensions.DEFAULT_EVENTS_HOUR
import com.emikhalets.simpleevents.utils.extensions.DEFAULT_EVENTS_MINUTE

class Prefs(context: Context) {

    private val sp = context.getSharedPreferences("SimpleEventsPreferences", Context.MODE_PRIVATE)

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

    fun getLastUpdate(): Long {
        return sp.getLong(LAST_UPDATE, 0)
    }

    fun setLastUpdate(value: Long) {
        sp.edit().putLong(LAST_UPDATE, value).apply()
    }

    fun getFirstLaunch(): Boolean {
        return sp.getBoolean(FIRST_LAUNCH, true)
    }

    fun setFirstLaunch(value: Boolean) {
        sp.edit().putBoolean(FIRST_LAUNCH, value).apply()
    }

    private companion object {
        const val EVENT_HOUR = "sp_event_hour"
        const val EVENT_MINUTE = "sp_event_minute"
        const val LAST_UPDATE = "sp_last_update"
        const val FIRST_LAUNCH = "sp_first_launch"
    }
}