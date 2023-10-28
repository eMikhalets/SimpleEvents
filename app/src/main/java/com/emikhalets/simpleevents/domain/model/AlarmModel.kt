package com.emikhalets.simpleevents.domain.model

data class AlarmModel(
    val id: Long,
    val name: String,
    val enabled: Boolean,
    val days: Int,
)
