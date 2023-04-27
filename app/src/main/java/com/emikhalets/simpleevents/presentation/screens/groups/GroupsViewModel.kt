package com.emikhalets.simpleevents.presentation.screens.groups

import com.emikhalets.simpleevents.domain.usecase.groups.GetGroupsUseCase
import com.emikhalets.simpleevents.utils.BaseViewModel
import com.emikhalets.simpleevents.utils.UiString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel @Inject constructor(
    private val getGroupsUseCase: GetGroupsUseCase,
) : BaseViewModel<GroupsState, GroupsAction>() {

    override fun createInitialState() = GroupsState()

    override fun handleEvent(action: GroupsAction) {
        when (action) {
            GroupsAction.GetGroups -> getGroups()
            GroupsAction.AcceptError -> resetError()
        }
    }

    private fun resetError() = setState { it.copy(error = null) }

    private fun getGroups() {
        launchIO {
            setState { it.copy(loading = true) }
            getGroupsUseCase()
                .onSuccess { result ->
                    result.collectLatest { list ->
                        setState { it.copy(loading = false, groupsList = list) }
                    }
                }
                .onFailure { error ->
                    val uiError = UiString.Message(error.message)
                    setState { it.copy(loading = false, error = uiError) }
                }
        }
    }
}
