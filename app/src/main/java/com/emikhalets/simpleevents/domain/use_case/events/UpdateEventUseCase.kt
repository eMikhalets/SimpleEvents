package com.emikhalets.simpleevents.domain.use_case.events

import com.emikhalets.simpleevents.data.AppRepository
import com.emikhalets.simpleevents.domain.AppResult
import com.emikhalets.simpleevents.domain.model.EventModel
import javax.inject.Inject

class UpdateEventUseCase @Inject constructor(
    private val repository: AppRepository,
) {

    suspend operator fun invoke(model: EventModel): AppResult<Int> {
        return repository.updateEvent(model)
    }
}
