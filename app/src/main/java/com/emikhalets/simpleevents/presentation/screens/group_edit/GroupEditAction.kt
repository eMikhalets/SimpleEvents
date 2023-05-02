package com.emikhalets.simpleevents.presentation.screens.group_edit

import com.emikhalets.simpleevents.utils.AppAction

sealed class GroupEditAction : AppAction {
    object AcceptError : GroupEditAction()
    class GetGroup(val id: Long?) : GroupEditAction()
    class UpdateGroup(val name: String, val enabled: Boolean) : GroupEditAction()
}
