package com.emikhalets.simpleevents.domain.entity

import com.emikhalets.simpleevents.utils.enums.EventType
import kotlinx.serialization.Serializable

@Serializable
data class EventEntity(
    val id: Long,
    val date: Long,
    val name: String,
    val eventType: EventType,
    val note: String,
    val withoutYear: Boolean,
    val days: Int,
    val age: Int,
)
