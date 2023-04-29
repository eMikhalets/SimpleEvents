package com.emikhalets.simpleevents.presentation.screens.groups

import com.emikhalets.simpleevents.domain.entity.GroupEntity
import com.emikhalets.simpleevents.utils.AppAction

sealed class GroupsAction : AppAction {
    object AcceptError : GroupsAction()
    object GetGroups : GroupsAction()
    class UpdateGroup(val entity: GroupEntity, val enabled: Boolean) : GroupsAction()
}
