package com.emikhalets.simpleevents.ui.screens.edit_event

import com.emikhalets.simpleevents.domain.entity.EventEntity

data class EditEventState(
    val event: EventEntity? = null,
    val updated: Int = -1,
    val error: String = "",
)
