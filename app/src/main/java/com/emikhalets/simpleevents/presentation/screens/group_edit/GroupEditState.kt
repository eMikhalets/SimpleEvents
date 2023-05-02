package com.emikhalets.simpleevents.presentation.screens.group_edit

import com.emikhalets.simpleevents.domain.entity.GroupEntity
import com.emikhalets.simpleevents.utils.AppState
import com.emikhalets.simpleevents.utils.UiString

data class GroupEditState(
    val group: GroupEntity? = null,
    val loading: Boolean = false,
    val saved: Boolean = false,
    val error: UiString? = null,
) : AppState
