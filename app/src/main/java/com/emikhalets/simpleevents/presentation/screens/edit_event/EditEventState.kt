package com.emikhalets.simpleevents.presentation.screens.edit_event

import com.emikhalets.simpleevents.domain.entity.database.EventEntity

data class EditEventState(
    val event: EventEntity? = null,
    val updated: Int = -1,
    val error: String = "",
)
