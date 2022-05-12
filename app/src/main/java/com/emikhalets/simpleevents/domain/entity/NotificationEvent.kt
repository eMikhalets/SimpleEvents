package com.emikhalets.simpleevents.domain.entity

import com.emikhalets.simpleevents.data.database.EventEntityDB
import com.emikhalets.simpleevents.data.database.NotificationGlobal

data class NotificationEvent(
    val notificationTime: NotificationGlobal,
    val events: List<EventEntityDB>,
)
