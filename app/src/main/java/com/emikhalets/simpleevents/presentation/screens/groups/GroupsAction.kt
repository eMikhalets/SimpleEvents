package com.emikhalets.simpleevents.presentation.screens.groups

import com.emikhalets.simpleevents.utils.AppAction

sealed class GroupsAction : AppAction {
    object AcceptError : GroupsAction()
    object GetEvents : GroupsAction()
}
