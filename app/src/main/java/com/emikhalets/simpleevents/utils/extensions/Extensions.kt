package com.emikhalets.simpleevents.utils.extensions

import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.data.database.EventEntityDB
import com.emikhalets.simpleevents.data.database.NotificationGlobal

const val DEFAULT_UPDATE_EVENTS_HOUR = 0
const val DEFAULT_UPDATE_EVENTS_MINUTE = 0
const val DEFAULT_EVENTS_HOUR = 9
const val DEFAULT_EVENTS_MINUTE = 0

fun EventEntityDB.calculateEventData(): EventEntityDB {
    return this.copy(
        ageTurns = date.turns,
        daysCount = date.daysLeft
    )
}

fun getDefaultNotificationsGlobal(): List<NotificationGlobal> {
    return listOf(
        NotificationGlobal(nameRes = R.string.notifications_time_month, enabled = true),
        NotificationGlobal(nameRes = R.string.notifications_time_week, enabled = true),
        NotificationGlobal(nameRes = R.string.notifications_time_two_days, enabled = true),
        NotificationGlobal(nameRes = R.string.notifications_time_day, enabled = true),
        NotificationGlobal(nameRes = R.string.notifications_time_today, enabled = true)
    )
}