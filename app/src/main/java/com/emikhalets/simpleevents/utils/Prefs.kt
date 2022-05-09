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

    private companion object {
        const val EVENT_HOUR = "sp_event_hour"
        const val EVENT_MINUTE = "sp_event_minute"
    }
}