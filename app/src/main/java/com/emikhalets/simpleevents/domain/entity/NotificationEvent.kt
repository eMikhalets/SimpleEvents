package com.emikhalets.simpleevents.domain.entity

import com.emikhalets.simpleevents.domain.entity.database.EventEntity
import com.emikhalets.simpleevents.domain.entity.database.NotificationGlobal

data class NotificationEvent(
    val notificationTime: NotificationGlobal,
    val events: List<EventEntity>,
)
