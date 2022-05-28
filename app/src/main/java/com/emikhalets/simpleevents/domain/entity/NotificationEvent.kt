package com.emikhalets.simpleevents.domain.entity

data class NotificationEvent(
    val notificationTime: NotificationGlobal,
    val events: List<EventEntity>,
)
