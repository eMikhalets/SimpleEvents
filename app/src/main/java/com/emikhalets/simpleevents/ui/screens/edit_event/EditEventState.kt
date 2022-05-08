package com.emikhalets.simpleevents.ui.screens.edit_event

import com.emikhalets.simpleevents.domain.entity.EventEntity

data class EditEventState(
    val event: EventEntity? = null,
    val updatedCount: Int = 0,
    val error: String = "",
)
