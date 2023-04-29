package com.emikhalets.simpleevents.presentation.screens.group_item

import com.emikhalets.simpleevents.domain.entity.EventEntity
import com.emikhalets.simpleevents.domain.entity.GroupEntity
import com.emikhalets.simpleevents.utils.AppState
import com.emikhalets.simpleevents.utils.UiString

data class GroupItemState(
    val group: GroupEntity? = null,
    val eventsList: List<EventEntity> = emptyList(),
    val loading: Boolean = false,
    val deleted: Boolean = false,
    val error: UiString? = null,
) : AppState
