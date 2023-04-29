package com.emikhalets.simpleevents.presentation.screens.group_item

import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.domain.usecase.events.GetEventByGroupUseCase
import com.emikhalets.simpleevents.domain.usecase.groups.DeleteGroupUseCase
import com.emikhalets.simpleevents.domain.usecase.groups.GetGroupsUseCase
import com.emikhalets.simpleevents.utils.BaseViewModel
import com.emikhalets.simpleevents.utils.UiString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class GroupItemViewModel @Inject constructor(
    private val getGroupsUseCase: GetGroupsUseCase,
    private val deleteGroupUseCase: DeleteGroupUseCase,
    private val getEventByGroupUseCase: GetEventByGroupUseCase,
) : BaseViewModel<GroupItemState, GroupItemAction>() {

    override fun createInitialState() = GroupItemState()

    override fun handleEvent(action: GroupItemAction) {
        when (action) {
            is GroupItemAction.GetGroup -> getGroup(action.id)
            GroupItemAction.GetEvents -> getEvents()
            GroupItemAction.AcceptError -> resetError()
        }
    }

    private fun resetError() = setState { it.copy(error = null) }

    private fun getGroup(id: Long?) {
        id ?: return
        launchIO {
            setState { it.copy(loading = true) }
            getGroupsUseCase(id)
                .onSuccess { result ->
                    result.collectLatest { entity ->
                        setState { it.copy(loading = false, group = entity) }
                    }
                }
                .onFailure { error ->
                    val uiError = UiString.Message(error.message)
                    setState { it.copy(loading = false, error = uiError) }
                }
        }
    }

    private fun getEvents() {
        val entity = state.value.group ?: return
        launchIO {
            setState { it.copy(loading = true) }
            getEventByGroupUseCase(entity)
                .onSuccess { result ->
                    result.collectLatest { list ->
                        setState { it.copy(loading = false, eventsList = list) }
                    }
                }
                .onFailure { error ->
                    val uiError = UiString.Message(error.message)
                    setState { it.copy(loading = false, error = uiError) }
                }
        }
    }

    private fun deleteGroup() {
        val entity = state.value.group ?: return
        launchIO {
            setState { it.copy(loading = true) }
            deleteGroupUseCase(entity)
                .onSuccess { result ->
                    if (result > 0) {
                        setState { it.copy(loading = false, group = entity) }
                    } else {
                        val uiError = UiString.Resource(R.string.error_delete)
                        setState { it.copy(loading = false, error = uiError) }
                    }
                }
                .onFailure { error ->
                    val uiError = UiString.Message(error.message)
                    setState { it.copy(loading = false, error = uiError) }
                }
        }
    }
}
