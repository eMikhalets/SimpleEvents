package com.emikhalets.simpleevents.presentation.screens.group_item

import com.emikhalets.simpleevents.utils.AppAction

sealed class GroupItemAction : AppAction {
    object AcceptError : GroupItemAction()
    object GetEvents : GroupItemAction()
    class GetGroup(val id: Long?) : GroupItemAction()
}
