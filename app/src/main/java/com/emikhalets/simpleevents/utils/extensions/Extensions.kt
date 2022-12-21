package com.emikhalets.simpleevents.utils.extensions

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.domain.entity.database.EventEntity

fun EventEntity.calculateEventData(): EventEntity {
    return this.apply {
        age = date.turns
        days = date.daysLeft
    }
}

@Composable
fun EventEntity.formatHomeInfo(): String {
    val date = date.formatDateThisYear("EE, d MMM")
    val type = stringResource(eventType.nameRes)
    val turns = stringResource(R.string.event_list_item_turns, age)
    return if (age == 0 || withoutYear) "$date • $type" else "$date • $type • $turns"
}

fun EventEntity.formatNotificationInfo(context: Context): String {
    val type = context.getString(eventType.nameRes)
    val turns = context.getString(R.string.notification_turns, age)
    return if (age == 0 || withoutYear) "$type • $name" else "$type • $name • $turns"
}

fun getDefaultNotificationsGlobal(context: Context): List<NotificationGlobal> {
    return listOf(
        NotificationGlobal(
            nameEn = context.getString(R.string.notifications_time_month),
            enabled = true,
            daysLeft = 30
        ),
        NotificationGlobal(
            nameEn = context.getString(R.string.notifications_time_week),
            enabled = true,
            daysLeft = 7
        ),
        NotificationGlobal(
            nameEn = context.getString(R.string.notifications_time_two_days),
            enabled = true,
            daysLeft = 2
        ),
        NotificationGlobal(
            nameEn = context.getString(R.string.notifications_time_day),
            enabled = true,
            daysLeft = 1
        ),
        NotificationGlobal(
            nameEn = context.getString(R.string.notifications_time_today),
            enabled = true,
            daysLeft = 0
        )
    )
}