package com.emikhalets.simpleevents.utils.extensions

import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.domain.entity.EventEntity
import com.emikhalets.simpleevents.domain.entity.NotificationGlobal

const val DEFAULT_EVENTS_HOUR = 9
const val DEFAULT_EVENTS_MINUTE = 0

fun EventEntity.calculateEventData(): EventEntity {
    return this.copy(
        age = date.turns,
        days = date.daysLeft
    )
}

fun getDefaultNotificationsGlobal(): List<NotificationGlobal> {
    return listOf(
        NotificationGlobal(nameRes = R.string.notifications_time_month,
            enabled = true, daysLeft = 30),
        NotificationGlobal(nameRes = R.string.notifications_time_week,
            enabled = true, daysLeft = 7),
        NotificationGlobal(nameRes = R.string.notifications_time_two_days,
            enabled = true, daysLeft = 2),
        NotificationGlobal(nameRes = R.string.notifications_time_day,
            enabled = true, daysLeft = 1),
        NotificationGlobal(nameRes = R.string.notifications_time_today,
            enabled = true, daysLeft = 0)
    )
}