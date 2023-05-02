package com.emikhalets.simpleevents.presentation.screens.group_edit

import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.domain.entity.GroupEntity
import com.emikhalets.simpleevents.domain.usecase.groups.AddGroupUseCase
import com.emikhalets.simpleevents.domain.usecase.groups.GetGroupsUseCase
import com.emikhalets.simpleevents.utils.BaseViewModel
import com.emikhalets.simpleevents.utils.UiString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class GroupEditViewModel @Inject constructor(
    private val getGroupsUseCase: GetGroupsUseCase,
    private val addGroupUseCase: AddGroupUseCase,
) : BaseViewModel<GroupEditState, GroupEditAction>() {

    override fun createInitialState() = GroupEditState()

    override fun handleEvent(action: GroupEditAction) {
        when (action) {
            GroupEditAction.AcceptError -> resetError()
            is GroupEditAction.GetGroup -> getGroup(action.id)
            is GroupEditAction.UpdateGroup -> updateGroup(action.name, action.enabled)
        }
    }

    private fun resetError() = setState { it.copy(error = null) }

    private fun getGroup(id: Long?) {
        if (id == null) {
            val message = UiString.Resource(R.string.error_internal)
            setState { it.copy(error = message) }
            return
        }
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

    private fun updateGroup(name: String, enabled: Boolean) {
        val entity = state.value.group ?: GroupEntity(0, "", false)
        launchIO {
            setState { it.copy(loading = true) }
            addGroupUseCase(entity.copy(name = name, isAlarmsEnabled = enabled))
                .onSuccess {
                    setState { it.copy(loading = false, saved = true) }
                }
                .onFailure { error ->
                    val uiError = UiString.Message(error.message)
                    setState { it.copy(loading = false, error = uiError) }
                }
        }
    }
}
