package com.emikhalets.simpleevents.domain.use_case.events

import com.emikhalets.simpleevents.data.AppRepository
import com.emikhalets.simpleevents.domain.AppResult
import com.emikhalets.simpleevents.domain.model.EventModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetEventsUseCase @Inject constructor(
    private val repository: AppRepository,
) {

    operator fun invoke(): Flow<List<EventModel>> {
        return repository.getAllEvents()
    }

    suspend operator fun invoke(id: Long): AppResult<EventModel> {
        return repository.getEventById(id)
    }
}
