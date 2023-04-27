package com.emikhalets.simpleevents.presentation.screens.groups

import com.emikhalets.simpleevents.domain.entity.EventEntity
import com.emikhalets.simpleevents.domain.usecase.events.GetEventsUseCase
import com.emikhalets.simpleevents.utils.BaseViewModel
import com.emikhalets.simpleevents.utils.UiString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase,
) : BaseViewModel<GroupsState, GroupsAction>() {

    private var searchJob: Job? = null
    private var eventsList = listOf<EventEntity>()

    override fun createInitialState() = GroupsState()

    override fun handleEvent(action: GroupsAction) {
        when (action) {
            GroupsAction.GetGroups -> getEvents()
            GroupsAction.AcceptError -> resetError()
        }
    }

    private fun resetError() = setState { it.copy(error = null) }

    private fun getEvents() {
        launchIO {
            setState { it.copy(loading = true) }
            getEventsUseCase()
                .onSuccess { result ->
                    result.collectLatest { list ->
//                        setState { it.copy(loading = false, eventsMap = mapEventsList(list)) }
                    }
                }
                .onFailure { error ->
                    val uiError = UiString.Message(error.message)
                    setState { it.copy(loading = false, error = uiError) }
                }
        }
    }
}
