package com.emikhalets.simpleevents.domain.entity

import com.emikhalets.simpleevents.utils.enums.EventType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventEntity(
    @SerialName("id") val id: Long,
    @SerialName("date") val date: Long,
    @SerialName("name") val name: String,
    @SerialName("age") val ageTurns: Int,
    @SerialName("days_left") val daysCount: Int,
    @SerialName("type") val eventType: EventType,
    @SerialName("note") val note: String,
)
