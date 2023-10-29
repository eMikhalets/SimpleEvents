package com.emikhalets.simpleevents.presentation.screens.events_list

import androidx.compose.runtime.Immutable
import com.emikhalets.simpleevents.core.common.mvi.MviAction
import com.emikhalets.simpleevents.core.common.mvi.MviEffect
import com.emikhalets.simpleevents.core.common.mvi.MviState
import com.emikhalets.simpleevents.domain.StringValue
import com.emikhalets.simpleevents.domain.model.EventModel

object EventsListContract {

    @Immutable
    sealed class Action : MviAction {

        class SearchEvents(val query: String) : Action()
        object DropError : Action()
    }

    @Immutable
    sealed class Effect : MviEffect

    @Immutable
    data class State(
        val loading: Boolean = false,
        val error: StringValue? = null,
        val eventsList: List<EventModel> = emptyList(),
        val eventsMap: Map<Long, List<EventModel>> = emptyMap(),
    ) : MviState
}
